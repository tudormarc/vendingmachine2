package com.tudormarc.vendingmachine2.errors;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * Thanks to Bruno Cleite
 */
@Data
public class ApiError {

    private HttpStatus status;
    private String message;

    ApiError(HttpStatus status) {
        this.status = status;
    }

    ApiError(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

}
