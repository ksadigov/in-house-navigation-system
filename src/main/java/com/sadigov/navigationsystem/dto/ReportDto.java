package com.sadigov.navigationsystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ReportDto {
    @JsonProperty("mobile_station_id")
    private UUID mobileStationId;
    private Float distance;
    private LocalDateTime timestamp;
}
