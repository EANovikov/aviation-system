package com.xevgnov.aviation.controller;

import com.xevgnov.aviation.dto.AirportDto;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Validated
public interface AirportsApi {

    /*
    sample call: GET http://localhost:8080/v1/airports/KJFK
    icao Examples:
       KJFK: New York John F. Kennedy (USA)
       KAVL: ASHEVILLE RGNL
     */

    @GetMapping("/{icao}")
    ResponseEntity<AirportDto> getAirportData(@PathVariable("icao")
                                              @Pattern(regexp = "[A-Z]{4}",
                                                      message = "ICAO facility identifier must contain 4 upper-cased characters")
                                              String icao);

}
