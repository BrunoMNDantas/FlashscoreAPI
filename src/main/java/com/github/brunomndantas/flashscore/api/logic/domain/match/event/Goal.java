package com.github.brunomndantas.flashscore.api.logic.domain.match.event;

import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class Goal extends Event {

    private PlayerKey playerKey;

    private PlayerKey assistPlayerKey;


    public Goal(Time time, TeamKey teamKey, PlayerKey playerKey, PlayerKey assistPlayerKey) {
        super(time, teamKey);
        this.playerKey = playerKey;
        this.assistPlayerKey = assistPlayerKey;
    }

}