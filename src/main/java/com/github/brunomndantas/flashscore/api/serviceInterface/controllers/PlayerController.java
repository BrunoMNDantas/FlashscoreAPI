package com.github.brunomndantas.flashscore.api.serviceInterface.controllers;

import com.github.brunomndantas.flashscore.api.logic.domain.player.Player;
import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import com.github.brunomndantas.flashscore.api.serviceInterface.config.Routes;
import com.github.brunomndantas.repository4j.IRepository;
import com.github.brunomndantas.repository4j.exception.RepositoryException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "07. Players", description = "Operations related to Players")
@RestController
public class PlayerController {

    private IRepository<PlayerKey, Player> repository;


    public PlayerController(IRepository<PlayerKey, Player> repository) {
        this.repository = repository;
    }


    @GetMapping(Routes.PLAYER_ROUTE)
    public Player getPlayer(@PathVariable String playerName, @PathVariable String playerId) throws RepositoryException {
        return repository.get(new PlayerKey(playerName, playerId));
    }
    
}
