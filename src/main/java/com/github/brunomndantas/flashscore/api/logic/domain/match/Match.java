package com.github.brunomndantas.flashscore.api.logic.domain.match;

import com.github.brunomndantas.flashscore.api.logic.domain.match.event.Event;
import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;
import lombok.*;

import java.util.Collection;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Match {

    private MatchKey key;

    private TeamKey homeTeamKey;

    private TeamKey awayTeamKey;

    private int homeTeamGoals;

    private int awayTeamGoals;

    private Date date;

    private PlayerKey homeCoachPlayerKey;

    private PlayerKey awayCoachPlayerKey;

    private Collection<PlayerKey> homeLineupPlayersKeys;

    private Collection<PlayerKey> awayLineupPlayersKeys;

    private Collection<PlayerKey> homeBenchPlayersKeys;

    private Collection<PlayerKey> awayBenchPlayersKeys;

    private Collection<Event> events;

}