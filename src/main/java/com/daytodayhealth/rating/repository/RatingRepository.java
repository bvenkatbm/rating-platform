package com.daytodayhealth.rating.repository;

import com.daytodayhealth.rating.domain.ProductOverallRating;
import com.daytodayhealth.rating.entity.ProductEntity;
import com.daytodayhealth.rating.entity.RatingEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface RatingRepository extends JpaRepository<RatingEntity, UUID> {

    @Query(value = "select re from RatingEntity re where re.productId = :product and (:rating is null or re.rating = :rating)")
    Page<RatingEntity> findByProductAndPaged(@Param("product") ProductEntity productEntity, Pageable pageable,
                                             @Param("rating") Integer rating);

    @Query(value = "select new com.daytodayhealth.rating.domain.ProductOverallRating(COUNT(re), AVG(re.rating)) from RatingEntity re " +
            "where re.productId = :product")
    ProductOverallRating findOverallRating(@Param("product") ProductEntity product);

    Optional<RatingEntity> findByProductIdAndRatedBy(ProductEntity productEntity, UUID postedBy);
}
