package com.github.brunomndantas.flashscore.api.logic.domain.match.event;

import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class Penalty extends Event {

    private boolean missed;

    @NotNull
    @Valid
    private PlayerKey playerKey;


    public Penalty(Time time, TeamKey teamKey, boolean missed, PlayerKey playerKey) {
        super(time, teamKey);
        this.missed = missed;
        this.playerKey = playerKey;
    }

}