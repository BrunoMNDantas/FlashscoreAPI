package com.github.brunomndantas.flashscore.api.logic.domain.season;

import com.github.brunomndantas.flashscore.api.logic.domain.match.MatchKey;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Season {

    @NotNull
    @Valid
    private SeasonKey key;

    @Min(1900)
    @Max(2100)
    private int initYear;

    @Min(1900)
    @Max(2100)
    private int endYear;

    @NotNull
    @NotEmpty
    @Valid
    private Collection<MatchKey> matchesKeys;

}
