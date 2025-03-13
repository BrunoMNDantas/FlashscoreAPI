package com.github.brunomndantas.flashscore.api.logic.domain.team;

import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Team {

    private TeamKey key;

    private String name;

    private String stadium;

    private int stadiumCapacity;

    private PlayerKey coachKey;

    private Collection<PlayerKey> playersKeys;

}