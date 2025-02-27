package com.github.brunomndantas.flashscore.api.logic.domain.sport;

import com.github.brunomndantas.flashscore.api.logic.domain.region.RegionKey;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Unique identifier for the sport", example = "{\"sportId\": \"football\"}")
    private SportKey key;

    @NotNull
    @NotEmpty
    @NotBlank
    @Schema(description = "Name of the sport", example = "Football")
    private String name;

    @NotNull
    @NotEmpty
    @Valid
    @Schema(description = "List of region keys for the sport", example = "[{\"sportId\": \"football\",\"regionId\": \"portugal\"}]")
    private Collection<RegionKey> regionsKeys;

}