package com.xevgnov.aviation.service;

import com.xevgnov.aviation.client.AviationAirportClient;
import com.xevgnov.aviation.dto.Airport;
import com.xevgnov.aviation.exception.AviationAirportException;
import com.xevgnov.aviation.exception.RetryableAirportClientException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class RetryableAirportClientImplTest {

    @MockitoBean
    private AviationAirportClient aviationAirportClient;

    @Autowired
    private RetryableAirportClient retryableAirportClient;

    @Test
    @DirtiesContext
    public void getAirportByIcaoCodeSuccessfullyWith3Retries() {
        //Given
        Airport airport = new Airport();
        airport.facilityName("Test Airport");
        String icaoCode = "UUEE";
        Map<String, List<Airport>> response = Map.of(icaoCode, List.of(airport));
        when(aviationAirportClient.get(icaoCode))
                .thenThrow(new AviationAirportException("Server error", 500))
                .thenThrow(new AviationAirportException("Server error", 500))
                .thenReturn(response);
        //When
        Optional<Airport> airportOpt = retryableAirportClient.getAirportByIcaoCode(icaoCode);
        //Then
        verify(aviationAirportClient, times(3)).get(icaoCode);
        assertThat(airportOpt).isPresent();
        assertThat(airportOpt.get().getFacilityName()).isEqualTo(airport.getFacilityName());
    }

    @Test
    public void getAirportByIcaoCodeFailsAfter3UnsuccessfulRetries() {
        //Given
        Airport airport = new Airport();
        airport.facilityName("Test Airport");
        String icaoCode = "UUEE";
        when(aviationAirportClient.get(icaoCode))
                .thenThrow(new AviationAirportException("Server error", 500))
                .thenThrow(new AviationAirportException("Server error", 500))
                .thenThrow(new AviationAirportException("Server error", 500));
        //When Then
        assertThatThrownBy(() -> retryableAirportClient.getAirportByIcaoCode(icaoCode))
                .isInstanceOf(RetryableAirportClientException.class)
                .hasCauseInstanceOf(AviationAirportException.class)
                .hasMessage("Failed to get airport data. Details [[500][Server error]]");
        verify(aviationAirportClient, times(3)).get(icaoCode);
    }

    @Test
    public void getAirportByIcaoCodeFailsWithNoRetryOn400Code() {
        //Given
        Airport airport = new Airport();
        airport.facilityName("Test Airport");
        String icaoCode = "UUEE";
        when(aviationAirportClient.get(icaoCode))
                .thenThrow(new AviationAirportException("Invalid input", 400));
        //When Then
        assertThatThrownBy(() -> retryableAirportClient.getAirportByIcaoCode(icaoCode))
                .isInstanceOf(AviationAirportException.class)
                .hasMessage("[400][Invalid input]");
        verify(aviationAirportClient, times(1)).get(icaoCode);
        verifyNoMoreInteractions(aviationAirportClient);
    }
}