package com.sadigov.navigationsystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ReportEventDto {
    @JsonProperty("base_station_id")
    private UUID baseStationId;
    private List<ReportDto> reports;
}

