package com.github.brunomndantas.flashscore.api.serviceInterface.controllers;

import com.github.brunomndantas.flashscore.api.logic.domain.season.Season;
import com.github.brunomndantas.flashscore.api.logic.domain.season.SeasonKey;
import com.github.brunomndantas.flashscore.api.serviceInterface.config.Routes;
import com.github.brunomndantas.repository4j.IRepository;
import com.github.brunomndantas.repository4j.exception.RepositoryException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Get season by key", description = "Returns the season with the given key if found, otherwise returns 404")
    @ApiResponse(responseCode = "200", description = "Season found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Season.class)))
    @ApiResponse(responseCode = "404", description = "Season not found", content = @Content(mediaType = "text/plain"))
    public Season getSeason(@PathVariable String sportId, @PathVariable String regionId, @PathVariable String competitionId, @PathVariable String seasonId) throws RepositoryException {
        return repository.get(new SeasonKey(sportId, regionId, competitionId, seasonId));
    }
    
}
