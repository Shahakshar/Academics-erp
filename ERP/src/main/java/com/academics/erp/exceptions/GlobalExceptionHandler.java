package com.academics.erp.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle invalid credentials during login
    @ExceptionHandler(BadCredentialsException.class)
    public ProblemDetail handleBadCredentials(BadCredentialsException exception) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, exception.getMessage());
        detail.setProperty("description", "Invalid username or password. Please try again.");
        return detail;
    }

    // Handle account status issues (locked, disabled, etc.)
    @ExceptionHandler(AccountStatusException.class)
    public ProblemDetail handleAccountStatus(AccountStatusException exception) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, exception.getMessage());
        detail.setProperty("description", "Account is either locked, disabled, or not active.");
        return detail;
    }

    // Handle access denied errors
    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail handleAccessDenied(AccessDeniedException exception) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, exception.getMessage());
        detail.setProperty("description", "You do not have permission to access this resource.");
        return detail;
    }

    // Handle JWT signature validation issues
    @ExceptionHandler(SignatureException.class)
    public ProblemDetail handleInvalidSignature(SignatureException exception) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, exception.getMessage());
        detail.setProperty("description", "The provided JWT token signature is invalid.");
        return detail;
    }

    // Handle expired JWT tokens
    @ExceptionHandler(ExpiredJwtException.class)
    public ProblemDetail handleExpiredJwt(ExpiredJwtException exception) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, exception.getMessage());
        detail.setProperty("description", "The provided JWT token has expired. Please log in again.");
        return detail;
    }

    // Handle validation errors for @Valid annotated objects
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationException(MethodArgumentNotValidException exception) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation failed.");

        // Extract validation errors
        Map<String, String> validationErrors = new HashMap<>();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        detail.setProperty("validationErrors", validationErrors);
        return detail;
    }

    // Handle employee not found (custom exception)
    @ExceptionHandler(EmployeeNotFoundException.class)
    public ProblemDetail handleEmployeeNotFound(EmployeeNotFoundException exception) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
        detail.setProperty("description", "The employee you are trying to access does not exist.");
        return detail;
    }

    // Handle all other exceptions
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneralException(Exception exception) {
        exception.printStackTrace(); // TODO: Send stack trace to an observability tool like Sentry, Datadog, etc.
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred.");
        detail.setProperty("description", exception.getMessage());
        return detail;
    }
}
