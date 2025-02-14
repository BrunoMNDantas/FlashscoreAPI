package com.github.brunomndantas.flashscore.api.logic.domain.sport;

import com.github.brunomndantas.flashscore.api.logic.domain.region.RegionId;
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
public class Sport {

    @NotNull
    @Valid
    private SportId id;

    @NotNull
    @NotEmpty
    @NotBlank
    private String name;

    @NotNull
    @NotEmpty
    @Valid
    private Collection<RegionId> regionsIds;

}
