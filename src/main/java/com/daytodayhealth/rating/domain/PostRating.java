package com.daytodayhealth.rating.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Data
public class PostRating {

    @JsonIgnore
    private UUID postedBy;
    private int rating;
    private String message;

}
