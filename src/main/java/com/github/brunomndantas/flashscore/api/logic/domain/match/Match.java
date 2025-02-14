package com.github.brunomndantas.flashscore.api.logic.domain.match;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
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
    private MatchId id;

    @NotNull
    @NotEmpty
    @NotBlank
    private String homeTeamId;

    @NotNull
    @NotEmpty
    @NotBlank
    private String awayTeamId;

    @Min(-1)
    @Max(200)
    private int homeTeamGoals;

    @Min(-1)
    @Max(200)
    private int awayTeamGoals;

    @NotNull
    private Date date;

}
