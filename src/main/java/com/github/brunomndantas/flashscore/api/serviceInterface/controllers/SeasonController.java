package com.github.brunomndantas.flashscore.api.serviceInterface.controllers;

import com.github.brunomndantas.flashscore.api.logic.domain.season.Season;
import com.github.brunomndantas.flashscore.api.logic.domain.season.SeasonKey;
import com.github.brunomndantas.flashscore.api.serviceInterface.config.Routes;
import com.github.brunomndantas.repository4j.IRepository;
import com.github.brunomndantas.repository4j.exception.RepositoryException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "04. Seasons", description = "Operations related to Seasons")
@RestController
public class SeasonController {

    private IRepository<SeasonKey, Season> repository;


    public SeasonController(IRepository<SeasonKey, Season> repository) {
        this.repository = repository;
    }


    @GetMapping(Routes.SEASON_ROUTE)
    public Season getSeason(@PathVariable String sportId, @PathVariable String regionId, @PathVariable String competitionId, @PathVariable String seasonId) throws RepositoryException {
        return repository.get(new SeasonKey(sportId, regionId, competitionId, seasonId));
    }
    
}
