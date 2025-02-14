package com.github.brunomndantas.flashscore.api.logic.domain.match;

import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Match {

    @NotNull
    @Valid
    private MatchKey key;

    @NotNull
    @Valid
    private TeamKey homeTeamKey;

    @NotNull
    @Valid
    private TeamKey awayTeamKey;

    @Min(-1)
    @Max(200)
    private int homeTeamGoals;

    @Min(-1)
    @Max(200)
    private int awayTeamGoals;

    @NotNull
    private Date date;

}
