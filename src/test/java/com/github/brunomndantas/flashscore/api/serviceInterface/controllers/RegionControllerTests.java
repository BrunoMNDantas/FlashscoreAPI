package com.github.brunomndantas.flashscore.api.serviceInterface.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.brunomndantas.flashscore.api.logic.domain.region.Region;
import com.github.brunomndantas.flashscore.api.logic.domain.region.RegionKey;
import com.github.brunomndantas.flashscore.api.serviceInterface.config.Routes;
import com.github.brunomndantas.repository4j.IRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = RegionController.class)
class RegionControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private IRepository<RegionKey, Region> repository;


    @Test
    public void shouldReturnRegion() throws Exception {
        String sportId = "Sport";
        String regionId = "Region";
        Region region = new Region(new RegionKey(sportId, regionId), "Reg", new LinkedList<>());

        Mockito
            .when(repository.get(Mockito.any(RegionKey.class)))
            .thenReturn(region);

        String url = Routes.REGION_ROUTE
                .replace("{sportId}", sportId)
                .replace("{regionId}", regionId);

        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(region)));
    }

}