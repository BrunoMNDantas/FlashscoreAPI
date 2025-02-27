package com.github.brunomndantas.flashscore.api.serviceInterface.controllers;

import com.github.brunomndantas.flashscore.api.logic.domain.match.Match;
import com.github.brunomndantas.flashscore.api.logic.domain.match.MatchKey;
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

@Tag(name = "05. Matches", description = "Operations related to Matches")
@RestController
public class MatchController {

    private IRepository<MatchKey, Match> repository;


    public MatchController(IRepository<MatchKey, Match> repository) {
        this.repository = repository;
    }


    @GetMapping(Routes.MATCH_ROUTE)
    @Operation(summary = "Get match by key", description = "Returns the match with the given key if found, otherwise returns 404")
    @ApiResponse(responseCode = "200", description = "Match found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Match.class)))
    @ApiResponse(responseCode = "404", description = "Match not found", content = @Content(mediaType = "text/plain"))
    public Match getMatch(@PathVariable String matchId) throws RepositoryException {
        return repository.get(new MatchKey(matchId));
    }
    
}
