package com.github.brunomndantas.flashscore.api.logic.domain.sport;

import com.github.brunomndantas.flashscore.api.logic.domain.region.RegionKey;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Sport {

    @NotNull
    @Valid
    private SportKey key;

    @NotNull
    @NotEmpty
    @NotBlank
    private String name;

    @NotNull
    @NotEmpty
    @Valid
    private Collection<RegionKey> regionsKeys;

}