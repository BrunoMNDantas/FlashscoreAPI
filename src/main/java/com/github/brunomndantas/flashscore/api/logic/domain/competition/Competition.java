package com.github.brunomndantas.flashscore.api.logic.domain.competition;

import com.github.brunomndantas.flashscore.api.logic.domain.season.SeasonKey;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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
public class Competition {

    @NotNull
    @Valid
    private CompetitionKey key;

    @NotNull
    @NotEmpty
    @NotBlank
    private String name;

    @NotNull
    @NotEmpty
    @Valid
    private Collection<SeasonKey> seasonsKeys;

}
