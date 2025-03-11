package com.github.brunomndantas.flashscore.api.serviceInterface.dtos.match;

import com.github.brunomndantas.flashscore.api.logic.domain.match.Match;
import com.github.brunomndantas.flashscore.api.logic.domain.match.event.*;
import com.github.brunomndantas.flashscore.api.serviceInterface.dtos.match.event.CardDTO;
import com.github.brunomndantas.flashscore.api.serviceInterface.dtos.match.event.GoalDTO;
import com.github.brunomndantas.flashscore.api.serviceInterface.dtos.match.event.PenaltyDTO;
import com.github.brunomndantas.flashscore.api.serviceInterface.dtos.match.event.SubstitutionDTO;
import com.github.brunomndantas.flashscore.api.serviceInterface.dtos.player.PlayerKeyDTO;
import com.github.brunomndantas.flashscore.api.serviceInterface.dtos.team.TeamKeyDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

@Value
@AllArgsConstructor
@Jacksonized
@Builder
@Schema(description = "Match")
public class MatchDTO {

    @Schema(description = "Unique identifier for the match", example = "{\"matchId\": \"thRumiAF\"}")
    private MatchKeyDTO key;

    @Schema(description = "Unique identifier for the home team", example = "{\"teamName\": \"sporting-lisbon\",\"teamId\": \"tljXuHBC\"}")
    private TeamKeyDTO homeTeamKey;

    @Schema(description = "Unique identifier for the away team", example = "{\"teamName\": \"benfica\",\"teamId\": \"zBkyuyRI\"}")
    private TeamKeyDTO awayTeamKey;

    @Schema(description = "Number of goals scored by home team", example = "1")
    private int homeTeamGoals;

    @Schema(description = "Number of goals scored by away team", example = "0")
    private int awayTeamGoals;

    @Schema(description = "Date of the match", example = "2024-12-29T20:30:00.000+00:00")
    private Date date;

    @Schema(description = "Unique identifier for home team's coach", example = "{\"playerName\": \"borges-rui\",\"playerId\": \"vocqEUBt\"}")
    private PlayerKeyDTO homeCoachPlayerKey;

    @Schema(description = "Unique identifier for away team's coach", example = "{\"playerName\": \"lage-bruno\",\"playerId\": \"6DEnR4qe\"}")
    private PlayerKeyDTO awayCoachPlayerKey;

    @Schema(description = "List of player keys for home team's lineup", example = "[{\"playerName\": \"gyokeres-viktor\",\"playerId\": \"zaBZ1xIk\"}]")
    private Collection<PlayerKeyDTO> homeLineupPlayersKeys;

    @Schema(description = "List of player keys for away team's lineup", example = "[{\"playerName\": \"fernandez-alvaro\",\"playerId\": \"ALGEzlma\"}]")
    private Collection<PlayerKeyDTO> awayLineupPlayersKeys;

    @Schema(description = "List of player keys for home team's bench", example = "[{\"playerName\": \"harder-conrad\",\"playerId\": \"KWNBSZBE\"}]")
    private Collection<PlayerKeyDTO> homeBenchPlayersKeys;

    @Schema(description = "List of player keys for away team's bench", example = "[{\"playerName\": \"silva-antonio\",\"playerId\": \"0xBtwxKs\"}]")
    private Collection<PlayerKeyDTO> awayBenchPlayersKeys;

    @Schema(
            description = "List of cards",
            example = "[{\"time\": {\"period\": \"SECOND_HALF\",\"minute\": 58,\"extraMinute\": 0},\"teamKey\": {\"teamName\": \"benfica\",\"teamId\": \"zBkyuyRI\"},\"playerKey\": {\"playerName\": \"bah-alexander\",\"playerId\": \"Kn1rziON\"},\"color\": \"YELLOW\"}]")
    private Collection<CardDTO> cards;

    @Schema(
            description = "List of goals",
            example = "[{\"time\": {\"period\": \"FIRST_HALF\",\"minute\": 29,\"extraMinute\": 0},\"teamKey\": {\"teamName\": \"sporting-lisbon\",\"teamId\": \"tljXuHBC\"},\"playerKey\": {\"playerName\": \"catamo-geny\",\"playerId\": \"29eth6dF\"},\"assistPlayerKey\": {\"playerName\": \"gyokeres-viktor\",\"playerId\": \"zaBZ1xIk\"}}]")
    private Collection<GoalDTO> goals;

    @Schema(
            description = "List of penalties",
            example = "[  {\"time\": {\"period\": \"FIRST_HALF\",\"minute\": 29,\"extraMinute\": 0},\"teamKey\": {\"teamName\": \"sporting-lisbon\",\"teamId\": \"tljXuHBC\"},\"missed\": false,\"playerKey\": {\"playerName\": \"catamo-geny\",\"playerId\": \"29eth6dF\"}}]")
    private Collection<PenaltyDTO> penalties;

    @Schema(
            description = "List of substitutions",
            example = "[{\"time\": {\"period\": \"SECOND_HALF\",\"minute\": 72,\"extraMinute\": 0},\"teamKey\": {\"teamName\": \"sporting-lisbon\",\"teamId\": \"tljXuHBC\"},\"inPlayerKey\": {\"playerName\": \"simoes-joao\",\"playerId\": \"4UYkqiMB\"},\"outPlayerKey\": {\"playerName\": \"morita-hidemasa\",\"playerId\": \"Y3AIyFGA\"}}]")
    private Collection<SubstitutionDTO> substitutions;


    public MatchDTO(Match match) {
        this.key = match.getKey() == null ? null : new MatchKeyDTO(match.getKey());
        this.homeTeamKey = match.getHomeTeamKey() == null ? null : new TeamKeyDTO(match.getHomeTeamKey());
        this.awayTeamKey = match.getAwayTeamKey() == null ? null : new TeamKeyDTO(match.getAwayTeamKey());
        this.homeTeamGoals = match.getHomeTeamGoals();
        this.awayTeamGoals = match.getAwayTeamGoals();
        this.date = match.getDate();
        this.homeCoachPlayerKey = match.getHomeCoachPlayerKey() == null ?
                null : new PlayerKeyDTO(match.getHomeCoachPlayerKey());
        this.awayCoachPlayerKey = match.getAwayCoachPlayerKey() == null ?
                null : new PlayerKeyDTO(match.getAwayCoachPlayerKey());
        this.homeLineupPlayersKeys = match.getHomeLineupPlayersKeys() == null ?
                null : match.getHomeLineupPlayersKeys().stream().map(PlayerKeyDTO::new).toList();
        this.awayLineupPlayersKeys = match.getAwayLineupPlayersKeys() == null ?
                null : match.getAwayLineupPlayersKeys().stream().map(PlayerKeyDTO::new).toList();
        this.homeBenchPlayersKeys = match.getHomeBenchPlayersKeys() == null ?
                null : match.getHomeBenchPlayersKeys().stream().map(PlayerKeyDTO::new).toList();
        this.awayBenchPlayersKeys = match.getAwayBenchPlayersKeys() == null ?
                null : match.getAwayBenchPlayersKeys().stream().map(PlayerKeyDTO::new).toList();
        this.cards = match.getEvents() == null ? null : getCards(match).stream().map(CardDTO::new).toList();
        this.goals = match.getEvents() == null ? null : getGoals(match).stream().map(GoalDTO::new).toList();
        this.penalties = match.getEvents() == null ? null : getPenalties(match).stream().map(PenaltyDTO::new).toList();
        this.substitutions = match.getEvents() == null ? null : getSubstitutions(match).stream().map(SubstitutionDTO::new).toList();
    }

    private Collection<Card> getCards(Match match) {
        return match.getEvents().stream()
                .filter(Card.class::isInstance)
                .map(Card.class::cast)
                .toList();
    }

    private Collection<Goal> getGoals(Match match) {
        return match.getEvents().stream()
                .filter(Goal.class::isInstance)
                .map(Goal.class::cast)
                .toList();
    }

    private Collection<Penalty> getPenalties(Match match) {
        return match.getEvents().stream()
                .filter(Penalty.class::isInstance)
                .map(Penalty.class::cast)
                .toList();
    }

    private Collection<Substitution> getSubstitutions(Match match) {
        return match.getEvents().stream()
                .filter(Substitution.class::isInstance)
                .map(Substitution.class::cast)
                .toList();
    }


    public Match toDomainEntity() {
        return new Match(
                key == null ? null : key.toDomainEntity(),
                homeTeamKey == null ? null : homeTeamKey.toDomainEntity(),
                awayTeamKey == null ? null : awayTeamKey.toDomainEntity(),
                homeTeamGoals,
                awayTeamGoals,
                date,
                homeCoachPlayerKey == null ? null : homeCoachPlayerKey.toDomainEntity(),
                awayCoachPlayerKey == null ? null : awayCoachPlayerKey.toDomainEntity(),
                homeLineupPlayersKeys == null ? null : homeLineupPlayersKeys.stream().map(PlayerKeyDTO::toDomainEntity).toList(),
                awayLineupPlayersKeys == null ? null : awayLineupPlayersKeys.stream().map(PlayerKeyDTO::toDomainEntity).toList(),
                homeBenchPlayersKeys == null ? null : homeBenchPlayersKeys.stream().map(PlayerKeyDTO::toDomainEntity).toList(),
                awayBenchPlayersKeys == null ? null : awayBenchPlayersKeys.stream().map(PlayerKeyDTO::toDomainEntity).toList(),
                getEvents()
        );
    }

    private Collection<Event> getEvents() {
        if(cards == null) {
            return null;
        }

        Collection<Event> events = new LinkedList<>();

        events.addAll(cards.stream().map(CardDTO::toDomainEntity).toList());
        events.addAll(goals.stream().map(GoalDTO::toDomainEntity).toList());
        events.addAll(penalties.stream().map(PenaltyDTO::toDomainEntity).toList());
        events.addAll(substitutions.stream().map(SubstitutionDTO::toDomainEntity).toList());

        return events;
    }
}