package com.sadigov.navigationsystem.controller;

import com.sadigov.navigationsystem.dto.LocationDto;
import com.sadigov.navigationsystem.service.LocationService;
import lombok.var;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LocationControllerIntegrationTest {

    private MockMvc mockMvc;
    @MockBean
    private LocationService locationService;
    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void testGetLocation_whenMobileStationFound_shouldReturnOk() throws Exception {
        UUID mobileStationId = UUID.randomUUID();

        var locationDto = new LocationDto();
        locationDto.setMobileId(mobileStationId);
        locationDto.setLongitude(10.0f);
        locationDto.setLatitude(20.0f);
        locationDto.setErrorRadius(0.0f);
        locationDto.setErrorCode(0);
        locationDto.setErrorDescription(null);

        when(locationService.getMobileStationLocation(mobileStationId)).thenReturn(locationDto);

        mockMvc.perform(get("/location/{mobileStationId}", mobileStationId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mobileId").value(mobileStationId.toString()))
                .andExpect(jsonPath("$.x").value(10.0))
                .andExpect(jsonPath("$.y").value(20.0));

        verify(locationService, times(1)).getMobileStationLocation(mobileStationId);
    }
}
