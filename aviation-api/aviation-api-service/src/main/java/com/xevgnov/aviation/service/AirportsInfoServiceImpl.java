package com.xevgnov.aviation.service;

import com.xevgnov.aviation.dto.Airport;
import com.xevgnov.aviation.dto.AirportDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AirportsInfoServiceImpl implements AirportsInfoService {

    private final RetryableAirportClient airportClient;

    public AirportsInfoServiceImpl(RetryableAirportClient airportClient) {
        this.airportClient = airportClient;
    }

    @Override
    public Optional<AirportDto> getAirportByIcaoCode(String icaoCode) {
        log.info("Getting airport information by ICAO code {}", icaoCode);
        Optional<Airport> airport = airportClient.getAirportByIcaoCode(icaoCode);
        if (airport.isEmpty()) {
            log.warn("No airport data found for ICAO code {}.", icaoCode);
            return Optional.empty();
        }
        return Optional.of(mapToAirportDto(airport.get()));
    }

    private AirportDto mapToAirportDto(Airport airport) {
        AirportDto airportDto = AirportDto.builder()
                .facilityName(airport.getFacilityName())
                .icaoIdent(airport.getIcaoIdent())
                .faaIdent(airport.getFaaIdent())
                .city(airport.getCity())
                .state(airport.getState())
                .elevation(airport.getElevation())
                .latitude(airport.getLatitude())
                .latitudeSec(airport.getLatitudeSec())
                .longitude(airport.getLongitude())
                .longitudeSec(airport.getLongitudeSec())
                .manager(airport.getManager())
                .managerPhone(airport.getManagerPhone())
                .magneticVariation(airport.getMagneticVariation())
                .region(airport.getRegion())
                .siteNumber(airport.getSiteNumber())
                .status(airport.getStatus())
                .certificationTypedate(airport.getCertificationTypedate())
                .effectiveDate(airport.getEffectiveDate())
                .build();
        log.info("Converted airport data for ICAO {} to airport DTO {}", airport.getIcaoIdent(), airportDto);
        return airportDto;
    }
}
