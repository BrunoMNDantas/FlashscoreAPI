package com.github.brunomndantas.flashscore.api.logic.domain.team;

import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Team {

    @NotNull
    @Valid
    @Schema(description = "Unique identifier for the team", example = "{\"teamName\": \"sporting-lisbon\",\"teamId\": \"tljXuHBC\"}")
    private TeamKey key;

    @NotNull
    @NotEmpty
    @NotBlank
    @Schema(description = "Name of the team", example = "Sporting CP")
    private String name;

    @Size(min = 1, message = "Must be null or non-empty")
    @Schema(description = "Name of the team's stadium", example = "Estádio José Alvalade (Lisbon)")
    private String stadium;

    @Min(-1)
    @Schema(description = "Capacity of the team's stadium", example = "50466")
    private int stadiumCapacity;

    @Valid
    @Schema(description = "Unique identifier for team's coach", example = "{\"playerName\": \"amorim-ruben\",\"playerId\": \"EiIHi8vU\"}")
    private PlayerKey coachKey;

    @NotNull
    @Valid
    @Schema(description = "List of player keys for the team", example = "[{\"playerName\": \"gyokeres-viktor\",\"playerId\": \"zaBZ1xIk\"}]")
    private Collection<PlayerKey> playersKeys;

}