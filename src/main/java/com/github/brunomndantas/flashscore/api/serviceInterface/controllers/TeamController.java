package com.github.brunomndantas.flashscore.api.serviceInterface.controllers;

import com.github.brunomndantas.flashscore.api.logic.domain.team.Team;
import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;
import com.github.brunomndantas.flashscore.api.serviceInterface.config.Routes;
import com.github.brunomndantas.repository4j.IRepository;
import com.github.brunomndantas.repository4j.exception.RepositoryException;
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
    public Team getTeam(@PathVariable String teamName, @PathVariable String teamId) throws RepositoryException {
        return repository.get(new TeamKey(teamName, teamId));
    }
    
}
