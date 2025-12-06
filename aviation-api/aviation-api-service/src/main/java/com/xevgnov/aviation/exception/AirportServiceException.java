package com.xevgnov.aviation.exception;

public class AirportServiceException extends RuntimeException {

    public AirportServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public AirportServiceException(String message) {
        super(message);
    }
}
