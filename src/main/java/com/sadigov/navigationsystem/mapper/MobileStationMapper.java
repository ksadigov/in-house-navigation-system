package com.sadigov.navigationsystem.mapper;

import com.sadigov.navigationsystem.dto.LocationDto;
import com.sadigov.navigationsystem.entity.MobileStation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface MobileStationMapper {

    @Mapping(target = "mobileId", source = "mobileStationId")
    @Mapping(target = "errorCode", constant = "404")
    @Mapping(target = "errorDescription", constant = "Mobile station not found in the database")
    LocationDto mobileStationNotFound(UUID mobileStationId);

    @Mapping(target = "mobileId", source = "mobileStationId")
    @Mapping(target = "errorCode", constant = "400")
    @Mapping(target = "errorDescription", constant = "Mobile station has not reported a location yet")
    LocationDto mobileStationHasNoLocation(UUID mobileStationId);

    @Mapping(target = "longitude", source = "mobileStation.lastKnownLongitude")
    @Mapping(target = "latitude", source = "mobileStation.lastKnownLatitude")
    @Mapping(target = "mobileId", source = "mobileStationId")
    LocationDto mobileStationToLocationDto(MobileStation mobileStation, UUID mobileStationId);

}


