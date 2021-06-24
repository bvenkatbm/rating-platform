package com.daytodayhealth.rating.controller;

import com.daytodayhealth.rating.domain.PlatformUserPrincipal;
import com.daytodayhealth.rating.domain.PostRating;
import com.daytodayhealth.rating.domain.ProductRating;
import com.daytodayhealth.rating.service.RatingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;


@RestController
@Slf4j
public class RatingController {

    @Autowired
    RatingService ratingService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping(value = "/product/{product}/rating", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductRating> displayRatings(@PathVariable(name = "product") String product,
                                                        @PageableDefault(sort = "postedAt", direction = Sort.Direction.DESC) Pageable pageable,
                                                        @RequestParam(name = "rating", required = false) Integer rating) {
        log.info("Display ratings for product: {}", product);
        ProductRating productRating = ratingService.getProductRating(product, pageable, rating);
        if (productRating.getRatings().hasContent())
            return ResponseEntity.ok(productRating);
        else return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PostMapping(value = "/product/{product}/add-rating",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addRating(@RequestBody PostRating postRating,
                                            @PathVariable(name = "product") String product) throws URISyntaxException {
        log.info("Add ratings for product: {}", product);
        PlatformUserPrincipal principal = (PlatformUserPrincipal) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        postRating.setPostedBy(principal.getUser().getId());
        ratingService.addRating(postRating, product);
        return ResponseEntity.created(new URI("/product/"+ product+"/add-rating"))
                .body("Added your rating, thanks for your feedback");
    }

    @PutMapping(value = "/product/{product}/edit-rating",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> editRating(@RequestBody PostRating postRating,
                                           @PathVariable(name = "product") String product) throws URISyntaxException {
        log.info("Edit ratings for product: {}", product);
        PlatformUserPrincipal principal = (PlatformUserPrincipal) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        // adding the owner id to avoid editing ratings by other users.
        // you can edit only your rating principle
        postRating.setPostedBy(principal.getUser().getId());
        ratingService.editRating(postRating, product);
        return ResponseEntity.created(new URI("/product/"+ product+"/edit-rating"))
                .body("Updated your rating, thanks for your feedback");
    }

}
