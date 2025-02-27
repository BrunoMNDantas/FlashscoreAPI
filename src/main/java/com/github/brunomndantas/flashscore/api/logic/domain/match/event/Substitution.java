package com.github.brunomndantas.flashscore.api.logic.domain.match.event;

import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Substitution extends Event {

    @NotNull
    @Valid
    @Schema(description = "Unique identifier for the player entering", example = "{\"playerName\": \"simoes-joao\",\"playerId\": \"4UYkqiMB\"},")
    private PlayerKey inPlayerKey;

    @NotNull
    @Valid
    @Schema(description = "Unique identifier for the player leaving", example = "{\"playerName\": \"morita-hidemasa\",\"playerId\": \"Y3AIyFGA\"}")
    private PlayerKey outPlayerKey;

}