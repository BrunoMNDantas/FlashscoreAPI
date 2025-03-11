package com.github.brunomndantas.flashscore.api.serviceInterface.dtos.player;

import com.github.brunomndantas.flashscore.api.logic.domain.player.Player;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.Date;

@Value
@AllArgsConstructor
@Jacksonized
@Builder
@Schema(description = "Player")
public class PlayerDTO {

    @Schema(description = "Unique identifier for the player", example = "{\"playerName\": \"gyokeres-viktor\",\"playerId\": \"zaBZ1xIk\"}")
    private PlayerKeyDTO key;

    @Schema(description = "Name of the player", example = "Viktor Gyokeres")
    private String name;

    @Schema(description = "Birthdate of the player", example = "1998-06-03T23:00:00.000+00:00")
    private Date birthDate;

    @Schema(description = "Role of the player", example = "Forward")
    private String role;


    public PlayerDTO(Player player) {
        this.key = player.getKey() == null ? null : new PlayerKeyDTO(player.getKey());
        this.name = player.getName();
        this.birthDate = player.getBirthDate();
        this.role = player.getRole();
    }


    public Player toDomainEntity() {
        return new Player(
                key == null ? null : key.toDomainEntity(),
                name,
                birthDate,
                role
        );
    }

}