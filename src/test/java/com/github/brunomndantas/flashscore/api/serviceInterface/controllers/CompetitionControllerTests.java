package com.github.brunomndantas.flashscore.api.serviceInterface.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.brunomndantas.flashscore.api.logic.domain.competition.Competition;
import com.github.brunomndantas.flashscore.api.logic.domain.competition.CompetitionKey;
import com.github.brunomndantas.flashscore.api.serviceInterface.config.Routes;
import com.github.brunomndantas.flashscore.api.serviceInterface.dtos.competition.CompetitionDTO;
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
@WebMvcTest(controllers = CompetitionController.class)
class CompetitionControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private IRepository<CompetitionKey, Competition> repository;


    @Test
    public void shouldReturnCompetition() throws Exception {
        String sportId = "Sport";
        String regionId = "Region";
        String competitionId = "Competition";
        Competition competition = new Competition(new CompetitionKey(sportId, regionId, competitionId), "Comp", new LinkedList<>());
        CompetitionDTO competitionDTO = new CompetitionDTO(competition);

        Mockito
            .when(repository.get(Mockito.any(CompetitionKey.class)))
            .thenReturn(competition);

        String url = Routes.COMPETITION_ROUTE
                .replace("{sportId}", sportId)
                .replace("{regionId}", regionId)
                .replace("{competitionId}", competitionId);

        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(competitionDTO)));
    }

}