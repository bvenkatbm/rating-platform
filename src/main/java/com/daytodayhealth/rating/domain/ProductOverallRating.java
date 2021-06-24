package com.daytodayhealth.rating.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductOverallRating {

    private Long ratingCount;
    private Double averageRating;

}
