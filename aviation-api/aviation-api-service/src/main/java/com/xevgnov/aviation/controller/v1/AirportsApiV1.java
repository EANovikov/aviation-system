package com.xevgnov.aviation.controller.v1;

import com.xevgnov.aviation.controller.AirportsApi;
import com.xevgnov.aviation.dto.AirportDto;
import com.xevgnov.aviation.service.AirportsInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/airports")
public class AirportsApiV1 implements AirportsApi {

    private final AirportsInfoService airportsInfoService;

    public AirportsApiV1(AirportsInfoService airportsInfoService) {
        this.airportsInfoService = airportsInfoService;
    }

    @Override
    public ResponseEntity<AirportDto> getAirportData(String icao) {
        return ResponseEntity.of(airportsInfoService.getAirportByIcaoCode(icao));
    }
}
