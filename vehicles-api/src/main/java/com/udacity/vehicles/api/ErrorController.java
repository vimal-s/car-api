package com.udacity.vehicles.api;

import com.udacity.vehicles.service.CarNotFoundException;
import com.udacity.vehicles.service.ManufacturerNotFoundException;
import com.udacity.vehicles.service.ServiceUnavailableException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Implements the Error controller related to any errors handled by the Vehicles API
 */
@ControllerAdvice
public class ErrorController extends ResponseEntityExceptionHandler {

    private static final String DEFAULT_VALIDATION_FAILED_MESSAGE = "Validation failed";

    @ExceptionHandler({CarNotFoundException.class, ManufacturerNotFoundException.class})
    public ResponseEntity<ApiError> handle(RuntimeException e) {
        List<String> errors =
                Arrays
                        .stream(e.getStackTrace())
                        .map(StackTraceElement::getClassName)
                        .collect(Collectors.toList());

        ApiError apiError = new ApiError(e.getMessage(), errors);
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<ApiError> handle(ServiceUnavailableException e) {
        List<String> errors =
                Arrays
                        .stream(e.getStackTrace())
                        .map(StackTraceElement::getClassName)
                        .collect(Collectors.toList());

        ApiError apiError = new ApiError(e.getMessage(), errors);
        return new ResponseEntity<>(apiError, HttpStatus.FAILED_DEPENDENCY);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handle(Exception e) {
        List<String> errors =
                Arrays
                        .stream(e.getStackTrace())
                        .map(StackTraceElement::getClassName)
                        .collect(Collectors.toList());

        ApiError apiError = new ApiError(
                e.getMessage() == null ? "Some error occurred." : e.getMessage(), errors);
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        List<String> errors =
                ex.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(error ->
                                error.getField() + ": " + error.getDefaultMessage()).collect(
                        Collectors.toList());

        ApiError apiError = new ApiError(DEFAULT_VALIDATION_FAILED_MESSAGE, errors);
        return handleExceptionInternal(ex, apiError, headers, HttpStatus.BAD_REQUEST, request);
    }
}
