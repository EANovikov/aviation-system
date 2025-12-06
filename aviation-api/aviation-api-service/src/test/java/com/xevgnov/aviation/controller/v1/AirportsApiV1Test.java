package com.xevgnov.aviation.controller.v1;

import com.xevgnov.aviation.dto.AirportDto;
import com.xevgnov.aviation.service.AirportsInfoService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AirportsApiV1.class)
class AirportsApiV1Test {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AirportsInfoService airportsInfoService;

    @Test
    @SneakyThrows
    void getAirportDataSuccessfullyAndGetStatus200() {
        // Given
        String icao = "KJFK";
        AirportDto airportDto = AirportDto.builder()
                .city("City")
                .facilityName("Airport")
                .state("State")
                .icaoIdent(icao)
                .status("Active")
                .build();
        when(airportsInfoService.getAirportByIcaoCode(icao)).thenReturn(Optional.of(airportDto));
        // When Then
        mockMvc.perform(get("/v1/airports/{icao}", icao))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value(airportDto.getCity()))
                .andExpect(jsonPath("$.facilityName").value(airportDto.getFacilityName()))
                .andExpect(jsonPath("$.state").value(airportDto.getState()))
                .andExpect(jsonPath("$.icaoIdent").value(airportDto.getIcaoIdent()))
                .andExpect(jsonPath("$.status").value(airportDto.getStatus()));
    }

    @Test
    @SneakyThrows
    void getAirportDataSuccessfullyForNonExistingIcaoAndGetStatus404() {
        // Given
        String icao = "NONE";
        when(airportsInfoService.getAirportByIcaoCode(icao)).thenReturn(Optional.empty());
        // When Then
        mockMvc.perform(get("/v1/airports/{icao}", icao))
                .andExpect(status().isNotFound());
    }

    @ParameterizedTest
    @ValueSource(strings = {"ABC", "ABCDE", "1234", "ABC1"})
    @SneakyThrows
    void getAirportDataSuccessfullyForInvalidIcaoAndGetStatus400(String icao) {
        // Given
        when(airportsInfoService.getAirportByIcaoCode(icao)).thenReturn(Optional.empty());
        // When Then
        mockMvc.perform(get("/v1/airports/{icao}", icao))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("getAirportData.icao: ICAO facility identifier must contain 4 upper-cased characters"));
    }
}