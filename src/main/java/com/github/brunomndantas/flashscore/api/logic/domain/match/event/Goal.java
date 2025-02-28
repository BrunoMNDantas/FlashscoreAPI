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
@Schema(description = "Goal")
public class Goal extends Event {

    @NotNull
    @Valid
    @Schema(description = "Unique identifier for the player", example = "{\"playerName\":\"catamo-geny\", \"playerId\":\"29eth6dF\"")
    private PlayerKey playerKey;

    @Valid
    @Schema(description = "Unique identifier for the assist player", example = "{\"playerName\":\"gyokeres-viktor\", \"playerId\":\"zaBZ1xIk\"}")
    private PlayerKey assistPlayerKey;

}