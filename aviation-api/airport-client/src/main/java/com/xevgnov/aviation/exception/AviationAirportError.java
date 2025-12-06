package com.xevgnov.aviation.exception;

import lombok.Data;

@Data
public class AviationAirportError {

    private String timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

}
