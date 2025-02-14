package com.github.brunomndantas.flashscore.api.logic.domain.region;

import com.github.brunomndantas.flashscore.api.logic.domain.competition.CompetitionKey;
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
public class Region {

    @NotNull
    @Valid
    private RegionKey key;

    @NotNull
    @NotEmpty
    @NotBlank
    private String name;

    @NotNull
    @NotEmpty
    @Valid
    private Collection<CompetitionKey> competitionsKeys;

}
