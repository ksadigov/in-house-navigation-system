package com.sadigov.navigationsystem.service;

import com.sadigov.navigationsystem.dto.ReportDto;
import com.sadigov.navigationsystem.dto.ReportEventDto;
import com.sadigov.navigationsystem.entity.BaseStation;
import com.sadigov.navigationsystem.entity.MobileStation;
import com.sadigov.navigationsystem.exception.BaseStationNotFoundException;
import com.sadigov.navigationsystem.repository.BaseStationRepository;
import com.sadigov.navigationsystem.repository.MobileStationRepository;
import lombok.var;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock
    private BaseStationRepository baseStationRepository;
    @Mock
    private MobileStationRepository mobileStationRepository;
    private ReportService testObj;

    @BeforeEach
    public void setUp() {
        testObj = new ReportService(baseStationRepository, mobileStationRepository);
    }

    @Test
    void testSaveReportEvent_validDto_shouldSaveMobileStation() {
        UUID baseStationId = UUID.randomUUID();
        UUID mobileStationId = UUID.randomUUID();
        float distance = 5f;
        float x = 1.0f;
        float y = 2.0f;
        var reportDto = new ReportDto();
        reportDto.setMobileStationId(mobileStationId);
        reportDto.setDistance(distance);

        var reportEventDto = new ReportEventDto();
        reportEventDto.setBaseStationId(baseStationId);
        reportEventDto.setReports(Collections.singletonList(reportDto));

        var baseStation = new BaseStation();
        baseStation.setUuid(baseStationId);
        baseStation.setX(x);
        baseStation.setY(y);
        baseStation.setDetectionRadiusInMeters(100f);

        var mobileStation = new MobileStation();
        mobileStation.setUuid(mobileStationId);

        when(baseStationRepository.findById(baseStationId)).thenReturn(Optional.of(baseStation));
        when(mobileStationRepository.findById(mobileStationId)).thenReturn(Optional.of(mobileStation));

        testObj.saveReportEvent(reportEventDto);

        // Assert
        verify(baseStationRepository).findById(baseStationId);
        verify(mobileStationRepository).findById(mobileStationId);
        verify(mobileStationRepository).save(mobileStation);

        assertThat(mobileStation.getLastKnownLongitude()).isEqualTo(x);
        assertThat(mobileStation.getLastKnownLatitude()).isEqualTo(y);
        assertThat(mobileStation.getBaseStation()).isEqualTo(baseStation);
    }

    @Test
    void testSaveReportEvent_baseStationNotFound_shouldThrowException() {
        UUID baseStationId = UUID.randomUUID();
        UUID mobileStationId = UUID.randomUUID();
        float distance = 5f;
        var reportDto = new ReportDto();
        reportDto.setMobileStationId(mobileStationId);
        reportDto.setDistance(distance);

        var reportEventDto = new ReportEventDto();
        reportEventDto.setBaseStationId(baseStationId);
        reportEventDto.setReports(Collections.singletonList(reportDto));

        when(baseStationRepository.findById(baseStationId)).thenReturn(Optional.empty());

        assertThrows(BaseStationNotFoundException.class, () -> testObj.saveReportEvent(reportEventDto));

        verify(baseStationRepository).findById(baseStationId);
        verify(mobileStationRepository, never()).findById(any());
        verify(mobileStationRepository, never()).save(any());
    }
}

