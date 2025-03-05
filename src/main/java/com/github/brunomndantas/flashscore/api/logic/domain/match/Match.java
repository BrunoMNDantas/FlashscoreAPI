package com.github.brunomndantas.flashscore.api.logic.domain.match;

import com.github.brunomndantas.flashscore.api.logic.domain.match.event.Event;
import com.github.brunomndantas.flashscore.api.logic.domain.match.event.Penalty;
import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Collection;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Schema(description = "Match")
public class Match {

    @EmbeddedId
    @NotNull
    @Valid
    @Schema(description = "Unique identifier for the match", example = "{\"matchId\": \"thRumiAF\"}")
    private MatchKey key;

    @AttributeOverrides({
            @AttributeOverride(name = "teamId", column = @Column(name = "home_team_id")),
            @AttributeOverride(name = "teamName", column = @Column(name = "home_team_name"))
    })
    @NotNull
    @Valid
    @Schema(description = "Unique identifier for the home team", example = "{\"teamName\": \"sporting-lisbon\",\"teamId\": \"tljXuHBC\"}")
    private TeamKey homeTeamKey;

    @AttributeOverrides({
            @AttributeOverride(name = "teamId", column = @Column(name = "away_team_id")),
            @AttributeOverride(name = "teamName", column = @Column(name = "away_team_name"))
    })
    @NotNull
    @Valid
    @Schema(description = "Unique identifier for the awayt team", example = "{\"teamName\": \"benfica\",\"teamId\": \"zBkyuyRI\"}")
    private TeamKey awayTeamKey;

    @Min(-1)
    @Max(200)
    @Schema(description = "Number of goals scored by home team", example = "1")
    private int homeTeamGoals;

    @Min(-1)
    @Max(200)
    @Schema(description = "Number of goals scored by away team", example = "0")
    private int awayTeamGoals;

    @NotNull
    @Schema(description = "Date of the match", example = "2024-12-29T20:30:00.000+00:00")
    private Date date;

    @AttributeOverrides({
            @AttributeOverride(name = "playerId", column = @Column(name = "home_coach_player_id")),
            @AttributeOverride(name = "playerName", column = @Column(name = "home_coach_player_name"))
    })
    @Valid
    @Schema(description = "Unique identifier for home team's coach", example = "{\"playerName\": \"borges-rui\",\"playerId\": \"vocqEUBt\"}")
    private PlayerKey homeCoachPlayerKey;

    @AttributeOverrides({
            @AttributeOverride(name = "playerId", column = @Column(name = "away_coach_player_id")),
            @AttributeOverride(name = "playerName", column = @Column(name = "away_coach_player_name"))
    })
    @Valid
    @Schema(description = "Unique identifier for away team's coach", example = "{\"playerName\": \"lage-bruno\",\"playerId\": \"6DEnR4qe\"}")
    private PlayerKey awayCoachPlayerKey;

    @ElementCollection
    @CollectionTable(name = "match_home_lineup", joinColumns = @JoinColumn(name = "match_id"))
    @NotNull
    @Valid
    @Size(max=11)
    @Schema(description = "List of player keys for home team's lineup", example = "[{\"playerName\": \"gyokeres-viktor\",\"playerId\": \"zaBZ1xIk\"}]")
    private Collection<PlayerKey> homeLineupPlayersKeys;

    @ElementCollection
    @CollectionTable(name = "match_away_lineup", joinColumns = @JoinColumn(name = "match_id"))
    @NotNull
    @Valid
    @Size(max=11)
    @Schema(description = "List of player keys for away team's lineup", example = "[{\"playerName\": \"fernandez-alvaro\",\"playerId\": \"ALGEzlma\"}]")
    private Collection<PlayerKey> awayLineupPlayersKeys;

    @ElementCollection
    @CollectionTable(name = "match_home_bench", joinColumns = @JoinColumn(name = "match_id"))
    @NotNull
    @Valid
    @Size(max=20)
    @Schema(description = "List of player keys for home team's bench", example = "[{\"playerName\": \"harder-conrad\",\"playerId\": \"KWNBSZBE\"}]")
    private Collection<PlayerKey> homeBenchPlayersKeys;

    @ElementCollection
    @CollectionTable(name = "match_away_bench", joinColumns = @JoinColumn(name = "match_id"))
    @NotNull
    @Valid
    @Size(max=20)
    @Schema(description = "List of player keys for away team's bench", example = "[{\"playerName\": \"silva-antonio\",\"playerId\": \"0xBtwxKs\"}]")
    private Collection<PlayerKey> awayBenchPlayersKeys;

    @ElementCollection
    @CollectionTable(name = "match_events", joinColumns = @JoinColumn(name = "match_id"))
    @NotNull
    @Valid
    @Schema(
            description = "List of events during the first half of the match",
            example = "[{\"type\":\"GOAL\", \"minute\":29, \"extraMinute\":0, \"teamKey\":{\"teamName\":\"sporting-lisbon\", \"teamId\":\"tljXuHBC\"},\"playerKey\":{\"playerName\":\"catamo-geny\", \"playerId\":\"29eth6dF\"},\"assistPlayerKey\":{\"playerName\":\"gyokeres-viktor\", \"playerId\":\"zaBZ1xIk\"}}]")
    private Collection<Event> firstHalfEvents;

    @ElementCollection
    @CollectionTable(name = "match_events", joinColumns = @JoinColumn(name = "match_id"))
    @NotNull
    @Valid
    @Schema(
            description = "List of events during the second half of the match",
            example = "[{\"type\": \"CARD\", \"minute\": 58, \"extraMinute\": 0, \"teamKey\": {\"teamName\": \"benfica\", \"teamId\": \"zBkyuyRI\"},\"playerKey\": {\"playerName\": \"bah-alexander\", \"playerId\": \"Kn1rziON\"},\"color\": \"YELLOW\"}]")
    private Collection<Event> secondHalfEvents;

    @ElementCollection
    @CollectionTable(name = "match_events", joinColumns = @JoinColumn(name = "match_id"))
    @NotNull
    @Valid
    @Schema(description = "List of events during the extra time of the match", example = "[]")
    private Collection<Event> extraTimeEvents;

    @ElementCollection
    @CollectionTable(name = "match_events", joinColumns = @JoinColumn(name = "match_id"))
    @NotNull
    @Valid
    @Schema(description = "Penalties after extra time of the match", example = "[]")
    private Collection<Penalty> penalties;

}