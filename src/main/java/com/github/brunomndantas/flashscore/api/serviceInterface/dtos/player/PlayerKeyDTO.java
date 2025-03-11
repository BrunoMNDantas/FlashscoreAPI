package com.github.brunomndantas.flashscore.api.serviceInterface.dtos.player;

import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@AllArgsConstructor
@Jacksonized
@Builder
@Schema(description = "Player key")
public class PlayerKeyDTO {

    @Schema(description = "Name identifier for the player", example = "gyokeres-viktor")
    private String playerName;

    @Schema(description = "Unique identifier for the player", example = "zaBZ1xIk")
    private String playerId;


    public PlayerKeyDTO(PlayerKey playerKey) {
        this.playerName = playerKey.getPlayerName();
        this.playerId = playerKey.getPlayerId();
    }


    public PlayerKey toDomainEntity() {
        return new PlayerKey(playerName, playerId);
    }

}