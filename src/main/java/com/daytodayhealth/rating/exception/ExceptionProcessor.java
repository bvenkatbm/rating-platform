package com.daytodayhealth.rating.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class ExceptionProcessor {

    @ExceptionHandler(value = {RatingRangeException.class, AlreadyRatedException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ProblemDetails handleBadRequest(HttpServletRequest request, Exception exception) {
        return problemDetails(request, exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ProblemDetails handleInternalServerError(HttpServletRequest request, Exception exception) {
        log.error("Error: ", exception);
        return problemDetails(request, "Something went wrong, Please try later.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {NoProductException.class, ValidProductException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ProblemDetails handleNotFoundException(HttpServletRequest request, Exception exception) {
        return problemDetails(request, exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    private ProblemDetails problemDetails(HttpServletRequest request, String message, HttpStatus status) {
        ProblemDetails details = new ProblemDetails();
        details.setErrorCode(status.value());
        details.setMessage(message);
        details.setPath(request.getRequestURI());
        details.setTime(LocalDateTime.now());
        return details;
    }
}
