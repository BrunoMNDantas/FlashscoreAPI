package com.github.brunomndantas.flashscore.api.serviceInterface.controllers;

import com.github.brunomndantas.flashscore.api.logic.domain.match.Match;
import com.github.brunomndantas.flashscore.api.logic.domain.match.MatchKey;
import com.github.brunomndantas.flashscore.api.serviceInterface.config.Routes;
import com.github.brunomndantas.repository4j.IRepository;
import com.github.brunomndantas.repository4j.exception.RepositoryException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "05. Matches", description = "Operations related to Matches")
@RestController
public class MatchController {

    private IRepository<MatchKey, Match> repository;


    public MatchController(IRepository<MatchKey, Match> repository) {
        this.repository = repository;
    }


    @GetMapping(Routes.MATCH_ROUTE)
    public Match getMatch(@PathVariable String matchId) throws RepositoryException {
        return repository.get(new MatchKey(matchId));
    }
    
}
