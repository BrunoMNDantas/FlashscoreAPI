package com.github.brunomndantas.flashscore.api.serviceInterface.controllers;

import com.github.brunomndantas.flashscore.api.logic.domain.competition.Competition;
import com.github.brunomndantas.flashscore.api.logic.domain.competition.CompetitionKey;
import com.github.brunomndantas.flashscore.api.serviceInterface.config.Routes;
import com.github.brunomndantas.repository4j.IRepository;
import com.github.brunomndantas.repository4j.exception.RepositoryException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "03. Competitions", description = "Operations related to Competitions")
@RestController
public class CompetitionController {

    private IRepository<CompetitionKey, Competition> repository;


    public CompetitionController(IRepository<CompetitionKey, Competition> repository) {
        this.repository = repository;
    }


    @GetMapping(Routes.COMPETITION_ROUTE)
    public Competition getCompetition(@PathVariable String sportId, @PathVariable String regionId, @PathVariable String competitionId) throws RepositoryException {
        return repository.get(new CompetitionKey(sportId, regionId, competitionId));
    }
    
}
