package com.github.brunomndantas.flashscore.api.serviceInterface.controllers;

import com.github.brunomndantas.flashscore.api.logic.domain.competition.Competition;
import com.github.brunomndantas.flashscore.api.logic.domain.competition.CompetitionKey;
import com.github.brunomndantas.flashscore.api.serviceInterface.config.Routes;
import com.github.brunomndantas.flashscore.api.serviceInterface.dtos.competition.CompetitionDTO;
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

@Tag(name = "03. Competitions", description = "Operations related to Competitions")
@RestController
public class CompetitionController {

    private IRepository<CompetitionKey, Competition> repository;


    public CompetitionController(IRepository<CompetitionKey, Competition> repository) {
        this.repository = repository;
    }


    @GetMapping(Routes.COMPETITION_ROUTE)
    @Operation(summary = "Get competition by key", description = "Returns the competition with the given key if found, otherwise returns 404")
    @ApiResponse(responseCode = "200", description = "Competition found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Competition.class)))
    @ApiResponse(responseCode = "404", description = "Competition not found", content = @Content(mediaType = "text/plain"))
    public CompetitionDTO getCompetition(@PathVariable String sportId, @PathVariable String regionId, @PathVariable String competitionId) throws RepositoryException {
        Competition competition = repository.get(new CompetitionKey(sportId, regionId, competitionId));
        return competition == null ? null : new CompetitionDTO(competition);
    }
    
}