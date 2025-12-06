package com.xevgnov.aviation.service;

import com.xevgnov.aviation.client.AviationAirportClient;
import com.xevgnov.aviation.dto.Airport;
import com.xevgnov.aviation.exception.RetryableAirportClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class RetryableAirportClientImpl implements RetryableAirportClient {

    private final AviationAirportClient aviationAirportClient;

    public RetryableAirportClientImpl(AviationAirportClient aviationAirportClient) {
        this.aviationAirportClient = aviationAirportClient;
    }

    @Override
    @Cacheable("airports")
    @Retryable(
            retryFor = {RetryableAirportClientException.class},
            maxAttemptsExpression = "${airport.client.retry.maxAttempts:3}",
            backoff = @Backoff(delayExpression = "${airport.client.retry.delay:200}",
                    maxDelayExpression = "${airport.client.retry.maxDelay:2000}"))
    public Optional<Airport> getAirportByIcaoCode(String icaoCode) {
        try {
            Map<String, List<Airport>> airportData = aviationAirportClient.get(icaoCode);
            if (isAirportExists(icaoCode, airportData)) {
                return Optional.of(airportData.get(icaoCode).getFirst());
            }
            return Optional.empty();
        } catch (Throwable e) {
            log.error("Failed to get airport data for ICAO code {}: {}", icaoCode, e.getMessage());
            if (is4xxCode(e)) {
                throw e;
            }
            throw new RetryableAirportClientException(
                    String.format("Failed to get airport data. Details [%s]", e.getMessage()), e);
        }
    }

    private boolean isAirportExists(String icaoCode, Map<String, List<Airport>> airportData) {
        return !CollectionUtils.isEmpty(airportData)
                && airportData.containsKey(icaoCode)
                && !CollectionUtils.isEmpty(airportData.get(icaoCode));
    }

    private boolean is4xxCode(Throwable e) {
        return e.getMessage() != null && e.getMessage().startsWith("[4");
    }
}
