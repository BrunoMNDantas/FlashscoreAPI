package com.github.brunomndantas.flashscore.api.logic.domain.match.event;

import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@Schema(description = "Penalty")
public class Penalty extends Event {

    @Schema(description = "Penalty missedUnique", example = "false")
    private boolean missed;

    @NotNull
    @Valid
    @Schema(description = "Unique identifier for the player", example = "{\"playerName\":\"catamo-geny\", \"playerId\":\"29eth6dF\"")
    private PlayerKey playerKey;


    public Penalty(Time time, TeamKey teamKey, boolean missed, PlayerKey playerKey) {
        super(time, teamKey);
        this.missed = missed;
        this.playerKey = playerKey;
    }

}