package com.xevgnov.aviation.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.xevgnov.aviation.dto.Airport;

import java.util.List;
import java.util.Map;

@FeignClient(value = "airport-client", url = "${spring.cloud.openfeign.client.config.airport-client.url}")
public interface AviationAirportClient {

    /**
     * documentation https://aviationapi.com/docs/
     * example: https://api.aviationapi.com/v1/airports?apt=KAVL
     * @param icao String ICAO airport code - 4-letter code with upper-cased characters
     * @return Map with key "airports" and List of Airport objects as value
     */
    @GetMapping(value = "/airports?apt={icao}", produces = "application/json")
    Map<String, List<Airport>> get(@PathVariable("icao") String icao);


}