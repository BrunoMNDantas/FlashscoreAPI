package com.github.brunomndantas.flashscore.api.logic.domain.sport;

import com.github.brunomndantas.flashscore.api.logic.domain.region.RegionKey;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Sport")
public class Sport {

    @EmbeddedId
    @NotNull
    @Valid
    @Schema(description = "Unique identifier for the sport", example = "{\"sportId\": \"football\"}")
    private SportKey key;

    @NotNull
    @NotEmpty
    @NotBlank
    @Schema(description = "Name of the sport", example = "Football")
    private String name;

    @ElementCollection
    @NotNull
    @NotEmpty
    @Valid
    @Schema(description = "List of region keys for the sport", example = "[{\"sportId\": \"football\",\"regionId\": \"portugal\"}]")
    private Collection<RegionKey> regionsKeys;

}