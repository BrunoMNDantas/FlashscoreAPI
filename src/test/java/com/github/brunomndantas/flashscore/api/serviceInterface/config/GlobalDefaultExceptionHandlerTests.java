package com.github.brunomndantas.flashscore.api.serviceInterface.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.brunomndantas.flashscore.api.logic.domain.competition.Competition;
import com.github.brunomndantas.flashscore.api.logic.domain.competition.CompetitionKey;
import com.github.brunomndantas.flashscore.api.serviceInterface.controllers.CompetitionController;
import com.github.brunomndantas.repository4j.IRepository;
import com.github.brunomndantas.repository4j.exception.NonExistentEntityException;
import com.github.brunomndantas.repository4j.exception.RepositoryException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CompetitionController.class)
public class GlobalDefaultExceptionHandlerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private IRepository<CompetitionKey, Competition> repository;


    @Test
    public void shouldHandleGlobalExceptions() throws Exception {
        RepositoryException exception = new RepositoryException("Error");
        Mockito
            .when(repository.get(Mockito.any(CompetitionKey.class)))
            .thenThrow(exception);

        String url = Routes.COMPETITION_ROUTE
                .replace("{sportId}", "s")
                .replace("{regionId}", "r")
                .replace("{competitionId}", "r");

        mockMvc.perform(get(url))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("There was an error. Please try again later."));
    }

    @Test
    public void shouldHandleNonExistentEntityExceptions() throws Exception {
        NonExistentEntityException exception = new NonExistentEntityException("Non Existent entity");
        Mockito
                .when(repository.get(Mockito.any(CompetitionKey.class)))
                .thenThrow(exception);

        String url = Routes.COMPETITION_ROUTE
                .replace("{sportId}", "s")
                .replace("{regionId}", "r")
                .replace("{competitionId}", "r");

        mockMvc.perform(get(url))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

}