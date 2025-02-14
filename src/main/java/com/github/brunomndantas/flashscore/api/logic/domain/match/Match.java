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
    private MatchKey key;

    @NotNull
    @NotEmpty
    @NotBlank
    private String homeTeamKey;

    @NotNull
    @NotEmpty
    @NotBlank
    private String awayTeamKey;

    @Min(-1)
    @Max(200)
    private int homeTeamGoals;

    @Min(-1)
    @Max(200)
    private int awayTeamGoals;

    @NotNull
    private Date date;

}
