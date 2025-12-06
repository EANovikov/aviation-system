package com.xevgnov.aviation.service;

import com.xevgnov.aviation.dto.AirportDto;

import java.util.Optional;

public interface AirportsInfoService {

    Optional<AirportDto> getAirportByIcaoCode(String icaoCode);
}
