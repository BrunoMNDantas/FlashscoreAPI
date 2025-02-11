package com.github.brunomndantas.flashscore.api.logic.domain.region;

import com.github.brunomndantas.flashscore.api.logic.domain.competition.CompetitionId;
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
public class Region {

    @NotNull
    @Valid
    private RegionId id;

    @NotNull
    @NotEmpty
    @NotBlank
    private String name;

    @NotNull
    @NotEmpty
    @Valid
    private Collection<CompetitionId> competitionsIds;

}
