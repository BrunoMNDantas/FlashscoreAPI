package com.github.brunomndantas.flashscore.api.serviceInterface.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.brunomndantas.flashscore.api.logic.domain.sport.Sport;
import com.github.brunomndantas.flashscore.api.logic.domain.sport.SportKey;
import com.github.brunomndantas.flashscore.api.serviceInterface.config.Routes;
import com.github.brunomndantas.flashscore.api.serviceInterface.dtos.sport.SportDTO;
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
@WebMvcTest(controllers = SportController.class)
class SportControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private IRepository<SportKey, Sport> repository;


    @Test
    public void shouldReturnSport() throws Exception {
        SportKey sportKey = new SportKey("Sport");
        Sport sport = new Sport(sportKey, "Spo", new LinkedList<>());
        SportDTO sportDTO = new SportDTO(sport);

        Mockito
            .when(repository.get(Mockito.any(SportKey.class)))
            .thenReturn(sport);

        String url = Routes.SPORT_ROUTE
                .replace("{sportId}", sportKey.getSportId());

        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(sportDTO)));
    }

}