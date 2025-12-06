package com.xevgnov.aviation.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;
import java.io.InputStream;

public class AviationAirportErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        AviationAirportError details;
        try (InputStream bodyIs = response.body()
                .asInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            details = mapper.readValue(bodyIs, AviationAirportError.class);
        } catch (IOException e) {
            return new AviationAirportException(e.getMessage(), response.status());
        }
        return new AviationAirportException(details.toString(), response.status());
    }
}
