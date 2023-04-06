package com.sadigov.navigationsystem.service;

import com.sadigov.navigationsystem.dto.LocationDto;
import com.sadigov.navigationsystem.entity.MobileStation;
import com.sadigov.navigationsystem.exception.MobileStationNotFoundException;
import com.sadigov.navigationsystem.mapper.MobileStationMapper;
import com.sadigov.navigationsystem.repository.MobileStationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final MobileStationRepository mobileStationRepository;
    private final MobileStationMapper mobileStationMapper;

    public LocationDto getMobileStationLocation(UUID mobileStationId) {
        MobileStation mobileStation = mobileStationRepository.findById(mobileStationId)
                .orElseThrow(() -> new MobileStationNotFoundException(String.format("Could not find Mobile station with UUID=%s", mobileStationId)));

        if (mobileStation == null) {
            return mobileStationMapper.mobileStationNotFound(mobileStationId);
        }

        if (mobileStation.getLastKnownLongitude() <= 0 || mobileStation.getLastKnownLatitude() <= 0) {
            return mobileStationMapper.mobileStationHasNoLocation(mobileStationId);
        }

        return mobileStationMapper.mobileStationToLocationDto(mobileStation, mobileStationId);
    }
}


