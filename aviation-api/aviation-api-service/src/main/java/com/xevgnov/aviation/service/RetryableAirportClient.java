package com.xevgnov.aviation.service;

import com.xevgnov.aviation.dto.Airport;

import java.util.Optional;

public interface RetryableAirportClient {

    /**
     * Retries client calls on non 4xx statuses
     */
    Optional<Airport> getAirportByIcaoCode(String icaoCode);
}
