package com.github.brunomndantas.flashscore.api.serviceInterface.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.brunomndantas.flashscore.api.logic.domain.season.Season;
import com.github.brunomndantas.flashscore.api.logic.domain.season.SeasonKey;
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
@WebMvcTest(controllers = SeasonController.class)
class SeasonControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private IRepository<SeasonKey, Season> repository;


    @Test
    public void shouldReturnSeason() throws Exception {
        String sportId = "Sport";
        String regionId = "Region";
        String competitionId = "Competition";
        String seasonId = "Season";
        Season season = new Season(new SeasonKey(sportId, regionId, competitionId, seasonId), 2020, 2021, new LinkedList<>());

        Mockito
            .when(repository.get(Mockito.any(SeasonKey.class)))
            .thenReturn(season);

        String url = Routes.SEASON_ROUTE
                .replace("{sportId}", sportId)
                .replace("{regionId}", regionId)
                .replace("{competitionId}", competitionId)
                .replace("{seasonId}", seasonId);

        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(season)));
    }

}
