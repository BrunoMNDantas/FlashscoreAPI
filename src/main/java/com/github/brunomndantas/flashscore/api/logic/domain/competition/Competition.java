package com.github.brunomndantas.flashscore.api.logic.domain.competition;

import com.github.brunomndantas.flashscore.api.logic.domain.season.SeasonId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Competition {

    @NotNull
    @Valid
    private CompetitionId id;

    @NotNull
    @NotEmpty
    @NotBlank
    private String name;

    @NotNull
    @NotEmpty
    @Valid
    private Collection<SeasonId> seasonsIds;

}
