package com.sadigov.navigationsystem.service;

import com.sadigov.navigationsystem.dto.LocationDto;
import com.sadigov.navigationsystem.entity.MobileStation;
import com.sadigov.navigationsystem.mapper.MobileStationMapper;
import com.sadigov.navigationsystem.repository.MobileStationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocationServiceTest {

    @Mock
    private MobileStationRepository mobileStationRepository;
    @Mock
    private MobileStationMapper mobileStationMapper;
    private LocationService testObj;

    @BeforeEach
    void setUp() {
        testObj = new LocationService(mobileStationRepository, mobileStationMapper);
    }

    @Test
    void testGetMobileStationLocation_mobileStationNotFound() {
        UUID mobileStationId = UUID.randomUUID();
        when(mobileStationRepository.findById(mobileStationId)).thenReturn(Optional.empty());

        LocationDto result = testObj.getMobileStationLocation(mobileStationId);

        verify(mobileStationRepository).findById(mobileStationId);
        verify(mobileStationMapper).mobileStationNotFound(mobileStationId);
        assertEquals(result, mobileStationMapper.mobileStationNotFound(mobileStationId));
    }

    @Test
    void testGetMobileStationLocation_mobileStationHasNoLocation() {
        UUID mobileStationId = UUID.randomUUID();
        MobileStation mobileStation = new MobileStation();
        mobileStation.setUuid(mobileStationId);
        when(mobileStationRepository.findById(mobileStationId)).thenReturn(Optional.of(mobileStation));
        when(mobileStationMapper.mobileStationHasNoLocation(mobileStationId)).thenReturn(new LocationDto());

        LocationDto result = testObj.getMobileStationLocation(mobileStationId);

        verify(mobileStationRepository).findById(mobileStationId);
        verify(mobileStationMapper).mobileStationHasNoLocation(mobileStationId);
        assertEquals(result, mobileStationMapper.mobileStationHasNoLocation(mobileStationId));
    }

    @Test
    void testGetMobileStationLocation_mobileStationFound() {
        UUID mobileStationId = UUID.randomUUID();
        MobileStation mobileStation = new MobileStation();
        mobileStation.setUuid(mobileStationId);
        mobileStation.setLastKnownLongitude(1.0f);
        mobileStation.setLastKnownLatitude(2.0f);
        when(mobileStationRepository.findById(mobileStationId)).thenReturn(Optional.of(mobileStation));
        when(mobileStationMapper.mobileStationToLocationDto(mobileStation, mobileStationId)).thenReturn(new LocationDto());

        LocationDto result = testObj.getMobileStationLocation(mobileStationId);

        verify(mobileStationRepository).findById(mobileStationId);
        verify(mobileStationMapper).mobileStationToLocationDto(mobileStation, mobileStationId);
        assertEquals(result, mobileStationMapper.mobileStationToLocationDto(mobileStation, mobileStationId));
    }
}
