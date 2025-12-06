package com.xevgnov.aviation.exception;

public class RetryableAirportClientException extends RuntimeException {

    public RetryableAirportClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public RetryableAirportClientException(String message) {
        super(message);
    }
}
