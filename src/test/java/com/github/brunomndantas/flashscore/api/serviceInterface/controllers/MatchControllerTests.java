package com.github.brunomndantas.flashscore.api.serviceInterface.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.brunomndantas.flashscore.api.logic.domain.match.Match;
import com.github.brunomndantas.flashscore.api.logic.domain.match.MatchKey;
import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;
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

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = MatchController.class)
class MatchControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private IRepository<MatchKey, Match> repository;


    @Test
    public void shouldReturnMatch() throws Exception {
        MatchKey matchKey = new MatchKey("Match");
        Match match = new Match(matchKey, new TeamKey("Home", "Home"), new TeamKey("Home", "Away"), 1, 1, new Date(), null, null, null, null);

        Mockito
            .when(repository.get(Mockito.any(MatchKey.class)))
            .thenReturn(match);

        String url = Routes.MATCH_ROUTE
                .replace("{matchId}", matchKey.getMatchId());

        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(match)));
    }

}