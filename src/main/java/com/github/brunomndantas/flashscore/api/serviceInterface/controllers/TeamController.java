package com.github.brunomndantas.flashscore.api.serviceInterface.controllers;

import com.github.brunomndantas.flashscore.api.logic.domain.team.Team;
import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;
import com.github.brunomndantas.flashscore.api.serviceInterface.config.Routes;
import com.github.brunomndantas.flashscore.api.serviceInterface.dtos.team.TeamDTO;
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

@Tag(name = "06. Teams", description = "Operations related to Teams")
@RestController
public class TeamController {

    private IRepository<TeamKey, Team> repository;


    public TeamController(IRepository<TeamKey, Team> repository) {
        this.repository = repository;
    }


    @GetMapping(Routes.TEAM_ROUTE)
    @Operation(summary = "Get team by key", description = "Returns the team with the given key if found, otherwise returns 404")
    @ApiResponse(responseCode = "200", description = "Team found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Team.class)))
    @ApiResponse(responseCode = "404", description = "Team Not found", content = @Content(mediaType = "text/plain"))
    public TeamDTO getTeam(@PathVariable String teamName, @PathVariable String teamId) throws RepositoryException {
        Team team = repository.get(new TeamKey(teamName, teamId));
        return team == null ? null : new TeamDTO(team);
    }
    
}
