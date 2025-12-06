package com.xevgnov.aviation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AirportDto {

    private String siteNumber;

    private String facilityName;

    private String faaIdent;

    private String icaoIdent;

    private String region;

    private String state;

    private String city;

    private String manager;

    private String managerPhone;

    private String latitude;

    private String latitudeSec;

    private String longitude;

    private String longitudeSec;

    private String elevation;

    private String magneticVariation;

    private String status;

    private String certificationTypedate;

    private String effectiveDate;

}
