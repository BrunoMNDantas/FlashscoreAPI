package com.github.brunomndantas.flashscore.api.dataAccess.dtos.match;

import com.github.brunomndantas.flashscore.api.dataAccess.dtos.match.event.CardDTO;
import com.github.brunomndantas.flashscore.api.dataAccess.dtos.match.event.GoalDTO;
import com.github.brunomndantas.flashscore.api.dataAccess.dtos.match.event.PenaltyDTO;
import com.github.brunomndantas.flashscore.api.dataAccess.dtos.match.event.SubstitutionDTO;
import com.github.brunomndantas.flashscore.api.dataAccess.dtos.player.PlayerKeyDTO;
import com.github.brunomndantas.flashscore.api.dataAccess.dtos.team.TeamKeyDTO;
import com.github.brunomndantas.flashscore.api.logic.domain.match.Match;
import com.github.brunomndantas.flashscore.api.logic.domain.match.event.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class MatchDTO {

    @NotNull
    @Valid
    private MatchKeyDTO key;

    @NotNull
    @Valid
    private TeamKeyDTO homeTeamKey;

    @NotNull
    @Valid
    private TeamKeyDTO awayTeamKey;

    @Min(-1)
    @Max(200)
    private int homeTeamGoals;

    @Min(-1)
    @Max(200)
    private int awayTeamGoals;

    @NotNull
    private Date date;

    @Valid
    private PlayerKeyDTO homeCoachPlayerKey;

    @Valid
    private PlayerKeyDTO awayCoachPlayerKey;

    @NotNull
    @Size(max=11)
    private Collection<@NotNull @Valid PlayerKeyDTO> homeLineupPlayersKeys;

    @NotNull
    @Size(max=11)
    private Collection<@NotNull @Valid PlayerKeyDTO> awayLineupPlayersKeys;

    @NotNull
    @Size(max=20)
    private Collection<@NotNull @Valid PlayerKeyDTO> homeBenchPlayersKeys;

    @NotNull
    @Size(max=20)
    private Collection<@NotNull @Valid PlayerKeyDTO> awayBenchPlayersKeys;

    @NotNull
    private Collection<@NotNull @Valid CardDTO> cards;

    @NotNull
    private Collection<@NotNull @Valid GoalDTO> goals;

    @NotNull
    private Collection<@NotNull @Valid PenaltyDTO> penalties;

    @NotNull
    private Collection<@NotNull @Valid SubstitutionDTO> substitutions;


    public MatchDTO(Match match) {
        this.key = match.getKey() == null ? null : new MatchKeyDTO(match.getKey());
        this.homeTeamKey = match.getHomeTeamKey() == null ? null : new TeamKeyDTO(match.getHomeTeamKey());
        this.awayTeamKey = match.getAwayTeamKey() == null ? null : new TeamKeyDTO(match.getAwayTeamKey());
        this.homeTeamGoals = match.getHomeTeamGoals();
        this.awayTeamGoals = match.getAwayTeamGoals();
        this.date = match.getDate();
        this.homeCoachPlayerKey = match.getHomeCoachPlayerKey() == null ? null : new PlayerKeyDTO(match.getHomeCoachPlayerKey());
        this.awayCoachPlayerKey = match.getAwayCoachPlayerKey() == null ? null : new PlayerKeyDTO(match.getAwayCoachPlayerKey());
        this.homeLineupPlayersKeys = match.getHomeLineupPlayersKeys() == null ? null : match.getHomeLineupPlayersKeys().stream().map(PlayerKeyDTO::new).toList();
        this.awayLineupPlayersKeys = match.getAwayLineupPlayersKeys() == null ? null : match.getAwayLineupPlayersKeys().stream().map(PlayerKeyDTO::new).toList();
        this.homeBenchPlayersKeys = match.getHomeBenchPlayersKeys() == null ? null : match.getHomeBenchPlayersKeys().stream().map(PlayerKeyDTO::new).toList();
        this.awayBenchPlayersKeys = match.getAwayBenchPlayersKeys() == null ? null : match.getAwayBenchPlayersKeys().stream().map(PlayerKeyDTO::new).toList();
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