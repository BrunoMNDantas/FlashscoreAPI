package com.github.brunomndantas.flashscore.api.serviceInterface.controllers;

import com.github.brunomndantas.flashscore.api.logic.domain.player.Player;
import com.github.brunomndantas.flashscore.api.serviceInterface.config.Routes;
import com.github.brunomndantas.repository4j.IRepository;
import com.github.brunomndantas.repository4j.exception.RepositoryException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerController {

    private IRepository<String, Player> repository;


    public PlayerController(IRepository<String, Player> repository) {
        this.repository = repository;
    }


    @GetMapping(Routes.PLAYER_ROUTE)
    public Player getPlayer(@PathVariable String playerId) throws RepositoryException {
        return repository.get(playerId);
    }
    
}
