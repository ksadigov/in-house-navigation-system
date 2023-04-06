package com.sadigov.navigationsystem.service;

import com.sadigov.navigationsystem.dto.LocationDto;
import com.sadigov.navigationsystem.entity.MobileStation;
import com.sadigov.navigationsystem.mapper.MobileStationMapper;
import com.sadigov.navigationsystem.repository.MobileStationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final MobileStationRepository mobileStationRepository;
    private final MobileStationMapper mobileStationMapper;

    public LocationDto getMobileStationLocation(UUID mobileStationId) {
        Optional<MobileStation> mobileStation = mobileStationRepository.findById(mobileStationId);

        if (!mobileStation.isPresent()) {
            return mobileStationMapper.mobileStationNotFound(mobileStationId);
        }

        if (mobileStation.get().getLastKnownLongitude() <= 0 || mobileStation.get().getLastKnownLatitude() <= 0) {
            return mobileStationMapper.mobileStationHasNoLocation(mobileStationId);
        }

        return mobileStationMapper.mobileStationToLocationDto(mobileStation.get(), mobileStationId);
    }
}


