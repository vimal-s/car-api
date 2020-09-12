package com.udacity.vehicles.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

/**
 * Declares methods to return errors and other messages from the API.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
class ApiError {

    private final String message;
    private final List<String> errorChain;

    ApiError(String message, List<String> errorChain) {
        this.message = message;
        this.errorChain = errorChain;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getErrorChain() {
        return errorChain;
    }
}
