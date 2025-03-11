package com.github.brunomndantas.flashscore.api.dataAccess.dtos.player;

import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@AllArgsConstructor
@Jacksonized
@Builder
public class PlayerKeyDTO {

    @NotNull
    @NotEmpty
    @NotBlank
    private String playerName;

    @NotNull
    @NotEmpty
    @NotBlank
    private String playerId;


    public PlayerKeyDTO(PlayerKey playerKey) {
        this.playerName = playerKey.getPlayerName();
        this.playerId = playerKey.getPlayerId();
    }


    public PlayerKey toDomainEntity() {
        return new PlayerKey(playerName, playerId);
    }

}