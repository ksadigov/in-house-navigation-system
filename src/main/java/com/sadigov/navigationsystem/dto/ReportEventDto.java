package com.sadigov.navigationsystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
public class ReportEventDto {
    @NotNull
    @JsonProperty("base_station_id")
    private UUID baseStationId;
    private List<ReportDto> reports;
}

