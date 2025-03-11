package com.github.brunomndantas.flashscore.api.serviceInterface.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.brunomndantas.flashscore.api.logic.domain.team.Team;
import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;
import com.github.brunomndantas.flashscore.api.serviceInterface.config.Routes;
import com.github.brunomndantas.flashscore.api.serviceInterface.dtos.team.TeamDTO;
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
@WebMvcTest(controllers = TeamController.class)
class TeamControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private IRepository<TeamKey, Team> repository;


    @Test
    public void shouldReturnTeam() throws Exception {
        TeamKey teamKey = new TeamKey("Name", "Id");
        Team team = new Team(teamKey, "T", null, 0, null, new LinkedList<>());
        TeamDTO teamDTO = new TeamDTO(team);

        Mockito
            .when(repository.get(Mockito.any(TeamKey.class)))
            .thenReturn(team);

        String url = Routes.TEAM_ROUTE
                .replace("{teamName}", teamKey.getTeamName())
                .replace("{teamId}", teamKey.getTeamId());

        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(teamDTO)));
    }

}