package com.linktic.reservation_system_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Custom response class to encapsulate the response of an operation.
 * Provides detailed information about the operation's result, including success status,
 * message, data, and HTTP status code.
 *
 * @param <T> the type of the data included in the response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {

    /**
     * Indicates whether the operation was successful or not.
     */
    private Boolean success;

    /**
     * Message providing details about the operation result.
     */
    private String message;

    /**
     * The HTTP status code associated with the response.
     */
    private Integer statusCode;

    /**
     * The data returned from the operation, if any.
     */
    private T data;

}
