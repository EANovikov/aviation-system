package com.xevgnov.aviation.exception;

public class AviationAirportException extends RuntimeException {

    public AviationAirportException(String message, int statusCode) {
        super(String.format("[%d][%s]", statusCode, message));
    }

}
