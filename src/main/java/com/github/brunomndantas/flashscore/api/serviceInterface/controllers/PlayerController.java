package com.github.brunomndantas.flashscore.api.serviceInterface.controllers;

import com.github.brunomndantas.flashscore.api.logic.domain.player.Player;
import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import com.github.brunomndantas.flashscore.api.serviceInterface.config.Routes;
import com.github.brunomndantas.flashscore.api.serviceInterface.dtos.player.PlayerDTO;
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

@Tag(name = "07. Players", description = "Operations related to Players")
@RestController
public class PlayerController {

    private IRepository<PlayerKey, Player> repository;


    public PlayerController(IRepository<PlayerKey, Player> repository) {
        this.repository = repository;
    }


    @GetMapping(Routes.PLAYER_ROUTE)
    @Operation(summary = "Get player by key", description = "Returns the player with the given key if found, otherwise returns 404")
    @ApiResponse(responseCode = "200", description = "Player found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Player.class)))
    @ApiResponse(responseCode = "404", description = "Player not found", content = @Content(mediaType = "text/plain"))
    public PlayerDTO getPlayer(@PathVariable String playerName, @PathVariable String playerId) throws RepositoryException {
        Player player = repository.get(new PlayerKey(playerName, playerId));
        return player == null ? null : new PlayerDTO(player);
    }
    
}
