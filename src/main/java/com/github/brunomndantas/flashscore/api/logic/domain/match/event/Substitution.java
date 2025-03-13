package com.github.brunomndantas.flashscore.api.logic.domain.match.event;

import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class Substitution extends Event {

    private PlayerKey inPlayerKey;

    private PlayerKey outPlayerKey;


    public Substitution(Time time, TeamKey teamKey, PlayerKey inPlayerKey, PlayerKey outPlayerKey) {
        super(time, teamKey);
        this.inPlayerKey = inPlayerKey;
        this.outPlayerKey = outPlayerKey;
    }

}