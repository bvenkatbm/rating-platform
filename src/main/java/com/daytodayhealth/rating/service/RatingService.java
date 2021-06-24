package com.daytodayhealth.rating.service;

import com.daytodayhealth.rating.domain.PostRating;
import com.daytodayhealth.rating.domain.ProductOverallRating;
import com.daytodayhealth.rating.domain.ProductRating;
import com.daytodayhealth.rating.entity.ProductEntity;
import com.daytodayhealth.rating.entity.RatingEntity;
import com.daytodayhealth.rating.exception.AlreadyRatedException;
import com.daytodayhealth.rating.exception.RatingRangeException;
import com.daytodayhealth.rating.exception.ValidRatingMessageException;
import com.daytodayhealth.rating.repository.RatingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class RatingService {

    @Autowired
    RatingRepository ratingRepository;

    @Autowired
    ProductService productService;

    @Autowired
    PlatformUserService userService;

    // add new rating if already added returns exception
    public void addRating(PostRating postRating, String productId) {

        Instant instant = Instant.now();

        UUID product = productService.validateProduct(productId);

        // validate rating range
        // additionally can be done from UI also
        int rating = postRating.getRating();
        validateRatingRange(rating);

        //validateRatingMessage(postRating.getMessage());

        ProductEntity productEntity = productService.checkProductExistence(product);
        Optional<RatingEntity> ratingEntityOptional = ratingRepository.findByProductIdAndRatedBy(productEntity, postRating.getPostedBy());
        if (ratingEntityOptional.isPresent())
            throw new AlreadyRatedException("Already rated, please try editing your old rating.");

        RatingEntity ratingEntity = new RatingEntity();
        ratingEntity.setRating(rating);
        ratingEntity.setMessage(postRating.getMessage());
        ratingEntity.setPostedAt(Date.from(instant));
        ratingEntity.setProductId(productEntity);
        ratingEntity.setRatedBy(postRating.getPostedBy());

        ratingRepository.save(ratingEntity);
    }

    //todo: check this
    private void validateRatingMessage(String message) {
        if (null != message && message.length() > Integer.MAX_VALUE)
            throw new ValidRatingMessageException("Please give us reasons to understand your ratings");
    }

    private void validateRatingRange(int rating) {
        boolean isInRange = rating >= 1 && rating <= 5;
        if (!isInRange)
            throw new RatingRangeException("Please provide a rating between 1 to 5");
    }

    // get product rating based on rating filter and
    // default ordered by latest rating and no filter
    public ProductRating getProductRating(String productId, Pageable pageable, Integer rating) {
        UUID product = productService.validateProduct(productId);
        ProductEntity productEntity = productService.checkProductExistence(product);
        if (null != rating)
            validateRatingRange(rating);

        ProductRating productRating = new ProductRating();
        Page<RatingEntity> ratingEntities = ratingRepository.findByProductAndPaged(productEntity, pageable, rating);

        ProductOverallRating overallRating = ratingRepository.findOverallRating(productEntity);
        productRating.setRatingCount(overallRating.getRatingCount());
        if (null == overallRating.getAverageRating())
            overallRating.setAverageRating(0.0d);

        float averageRating = Float.parseFloat(String.format("%.1f", overallRating.getAverageRating()));
        productRating.setAverageRating(averageRating);

        if(ratingEntities.hasContent())
            userService.setNames(ratingEntities.getContent());
        productRating.setRatings(ratingEntities);

        productEntity.setAverageRating(averageRating);
        productEntity.setRatingCount(overallRating.getRatingCount());
        productService.add(productEntity);

        return productRating;
    }

    // edit rating, if already added else add new rating
    public void editRating(PostRating postRating, String productId) {
        // validation steps
        // todo: probably make it common
        Instant instant = Instant.now();
        UUID product = productService.validateProduct(productId);
        int rating = postRating.getRating();
        validateRatingRange(rating);
        ProductEntity productEntity = productService.checkProductExistence(product);

        Optional<RatingEntity> ratingEntityOptional = ratingRepository.findByProductIdAndRatedBy(productEntity, postRating.getPostedBy());
        RatingEntity ratingEntity = ratingEntityOptional.orElse(new RatingEntity());


        ratingEntity.setRating(rating);
        ratingEntity.setMessage(postRating.getMessage());
        ratingEntity.setPostedAt(Date.from(instant));
        ratingEntity.setProductId(productEntity);
        ratingEntity.setRatedBy(postRating.getPostedBy());

        ratingRepository.save(ratingEntity);
    }
}
