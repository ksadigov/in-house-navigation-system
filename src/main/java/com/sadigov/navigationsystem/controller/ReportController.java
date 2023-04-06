package com.sadigov.navigationsystem.controller;

import com.sadigov.navigationsystem.dto.ReportEventDto;
import com.sadigov.navigationsystem.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {
    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<Void> receiveReport(@Valid @RequestBody ReportEventDto reportDto) {
        reportService.saveReportEvent(reportDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

