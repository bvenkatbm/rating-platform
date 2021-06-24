package com.daytodayhealth.rating.domain;

import com.daytodayhealth.rating.entity.RatingEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
public class ProductRating {

    private Float averageRating;

    private Long ratingCount;

    private Page<RatingEntity> ratings;
}
