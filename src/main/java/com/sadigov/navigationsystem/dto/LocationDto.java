package com.sadigov.navigationsystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocationDto {
    private UUID mobileId;
    @JsonProperty("x")
    private Float longitude;
    @JsonProperty("y")
    private Float latitude;
    @JsonProperty("error_radius")
    private Float errorRadius;
    @JsonProperty("error_code")
    private Integer errorCode;
    @JsonProperty("error_description")
    private String errorDescription;
}
