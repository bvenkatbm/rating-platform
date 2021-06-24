package com.daytodayhealth.rating.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProblemDetails {

    private String message;
    private LocalDateTime time;
    private int errorCode;
    private String path;
}
