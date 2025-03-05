package com.github.brunomndantas.flashscore.api.logic.domain.match;

import com.github.brunomndantas.flashscore.api.logic.domain.match.event.Event;
import com.github.brunomndantas.flashscore.api.logic.domain.match.event.Penalty;
import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Collection;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Schema(description = "Match")
public class Match {

    @NotNull
    @Valid
    @Schema(description = "Unique identifier for the match", example = "{\"matchId\": \"thRumiAF\"}")
    private MatchKey key;

    @NotNull
    @Valid
    @Schema(description = "Unique identifier for the home team", example = "{\"teamName\": \"sporting-lisbon\",\"teamId\": \"tljXuHBC\"}")
    private TeamKey homeTeamKey;

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

    @Valid
    @Schema(description = "Unique identifier for home team's coach", example = "{\"playerName\": \"borges-rui\",\"playerId\": \"vocqEUBt\"}")
    private PlayerKey homeCoachPlayerKey;

    @Valid
    @Schema(description = "Unique identifier for away team's coach", example = "{\"playerName\": \"lage-bruno\",\"playerId\": \"6DEnR4qe\"}")
    private PlayerKey awayCoachPlayerKey;

    @NotNull
    @Valid
    @Size(max=11)
    @Schema(description = "List of player keys for home team's lineup", example = "[{\"playerName\": \"gyokeres-viktor\",\"playerId\": \"zaBZ1xIk\"}]")
    private Collection<PlayerKey> homeLineupPlayersKeys;

    @NotNull
    @Valid
    @Size(max=11)
    @Schema(description = "List of player keys for away team's lineup", example = "[{\"playerName\": \"fernandez-alvaro\",\"playerId\": \"ALGEzlma\"}]")
    private Collection<PlayerKey> awayLineupPlayersKeys;

    @NotNull
    @Valid
    @Size(max=20)
    @Schema(description = "List of player keys for home team's bench", example = "[{\"playerName\": \"harder-conrad\",\"playerId\": \"KWNBSZBE\"}]")
    private Collection<PlayerKey> homeBenchPlayersKeys;

    @NotNull
    @Valid
    @Size(max=20)
    @Schema(description = "List of player keys for away team's bench", example = "[{\"playerName\": \"silva-antonio\",\"playerId\": \"0xBtwxKs\"}]")
    private Collection<PlayerKey> awayBenchPlayersKeys;

    @NotNull
    @Valid
    @Schema(
            description = "List of events during the first half of the match",
            example = "[{\"type\":\"GOAL\", \"minute\":29, \"extraMinute\":0, \"teamKey\":{\"teamName\":\"sporting-lisbon\", \"teamId\":\"tljXuHBC\"},\"playerKey\":{\"playerName\":\"catamo-geny\", \"playerId\":\"29eth6dF\"},\"assistPlayerKey\":{\"playerName\":\"gyokeres-viktor\", \"playerId\":\"zaBZ1xIk\"}}]")
    private Collection<Event> firstHalfEvents;

    @NotNull
    @Valid
    @Schema(
            description = "List of events during the second half of the match",
            example = "[{\"type\": \"CARD\", \"minute\": 58, \"extraMinute\": 0, \"teamKey\": {\"teamName\": \"benfica\", \"teamId\": \"zBkyuyRI\"},\"playerKey\": {\"playerName\": \"bah-alexander\", \"playerId\": \"Kn1rziON\"},\"color\": \"YELLOW\"}]")
    private Collection<Event> secondHalfEvents;

    @NotNull
    @Valid
    @Schema(description = "List of events during the extra time of the match", example = "[]")
    private Collection<Event> extraTimeEvents;

    @NotNull
    @Valid
    @Schema(description = "Penalties after extra time of the match", example = "[]")
    private Collection<Penalty> penalties;

}