package com.sadigov.navigationsystem.service;

import com.sadigov.navigationsystem.dto.ReportDto;
import com.sadigov.navigationsystem.dto.ReportEventDto;
import com.sadigov.navigationsystem.entity.BaseStation;
import com.sadigov.navigationsystem.entity.MobileStation;
import com.sadigov.navigationsystem.exception.BaseStationNotFoundException;
import com.sadigov.navigationsystem.repository.BaseStationRepository;
import com.sadigov.navigationsystem.repository.MobileStationRepository;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final BaseStationRepository baseStationRepository;
    private final MobileStationRepository mobileStationRepository;

    public void saveReportEvent(ReportEventDto reportEventDto) {
        BaseStation baseStation = baseStationRepository.findById(reportEventDto.getBaseStationId())
                .orElseThrow(() -> new BaseStationNotFoundException(String.format("Could not find Base station for UUID=%s",
                        reportEventDto.getBaseStationId().toString())));

        reportEventDto.getReports().stream()
                .filter(mobileStationReportDto -> mobileStationReportDto.getDistance() <= baseStation.getDetectionRadiusInMeters())
                .map(ReportDto::getMobileStationId).distinct()
                .map(mobileStationId -> mobileStationRepository.findById(mobileStationId)
                        .orElseGet(() -> {
                            var newMobileStation = new MobileStation();
                            newMobileStation.setUuid(mobileStationId);
                            return mobileStationRepository.save(newMobileStation);
                        })).forEach(mobileStation -> {
                    mobileStation.setLastKnownLongitude(baseStation.getX());
                    mobileStation.setLastKnownLatitude(baseStation.getY());
                    mobileStation.setBaseStation(baseStation);
                    mobileStationRepository.save(mobileStation);
                });
    }
}


