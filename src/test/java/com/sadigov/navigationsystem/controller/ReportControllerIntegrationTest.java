package com.sadigov.navigationsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sadigov.navigationsystem.dto.ReportDto;
import com.sadigov.navigationsystem.dto.ReportEventDto;
import com.sadigov.navigationsystem.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ReportController.class)
class ReportControllerIntegrationTest {

    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ReportService reportService;
    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void testReceiveReport_validDto_shouldReturnCreated() throws Exception {
        ReportDto reportDto = new ReportDto();
        reportDto.setDistance(10f);
        reportDto.setTimestamp(LocalDateTime.now());
        reportDto.setMobileStationId(UUID.randomUUID());

        ReportEventDto reportEventDto = new ReportEventDto();
        reportEventDto.setBaseStationId(UUID.randomUUID());
        reportEventDto.setReports(Collections.singletonList(reportDto));

        mockMvc.perform(MockMvcRequestBuilders.post("/report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reportEventDto)))
                .andExpect(status().isCreated());

        verify(reportService).saveReportEvent(any());
    }

    @Test
    void testReceiveReport_invalidDto_shouldReturnBadRequest() throws Exception {
        ReportEventDto reportEventDto = new ReportEventDto();
        reportEventDto.setBaseStationId(null);
        reportEventDto.setReports(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reportEventDto)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(reportService);
    }

}
