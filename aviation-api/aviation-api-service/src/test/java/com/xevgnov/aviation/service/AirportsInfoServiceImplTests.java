package com.xevgnov.aviation.service;

import com.xevgnov.aviation.dto.Airport;
import com.xevgnov.aviation.dto.AirportDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AirportsInfoServiceImplTests {

    @Mock
    private RetryableAirportClient retryableAirportClient;

    @InjectMocks
    AirportsInfoServiceImpl airportsService;

    @Test
    public void getAirportByIcaoCodeSuccessfully() {
        //Given
        Airport airport = new Airport();
        airport.facilityName("Test Airport");
        String icaoCode = "UUEE";
        when(retryableAirportClient.getAirportByIcaoCode(icaoCode)).thenReturn(Optional.of(airport));
        //When
        Optional<AirportDto> airportDto = airportsService.getAirportByIcaoCode(icaoCode);
        //Then
        assertThat(airportDto).isPresent();
        assertThat(airportDto.get().getFacilityName()).isEqualTo(airport.getFacilityName());
    }

    @Test
    public void getAirportByIcaoCodeReturnsEmptyOptionalForNonExistingCode() {
        //Given
        Airport airport = new Airport();
        airport.facilityName("Test Airport");
        String icaoCode = "UUEE";
        when(retryableAirportClient.getAirportByIcaoCode(icaoCode)).thenReturn(Optional.empty());
        //When
        Optional<AirportDto> airportDto = airportsService.getAirportByIcaoCode(icaoCode);
        //Then
        assertThat(airportDto).isEmpty();
    }

}