package com.github.brunomndantas.flashscore.api.logic.domain.region;

import com.github.brunomndantas.flashscore.api.logic.domain.competition.CompetitionKey;
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
@Schema(description = "Region")
public class Region {

    @EmbeddedId
    @NotNull
    @Valid
    @Schema(description = "Unique identifier for the region", example = "{\"sportId\": \"football\",\"regionId\": \"portugal\"}")
    private RegionKey key;

    @NotNull
    @NotEmpty
    @NotBlank
    @Schema(description = "Name of the region", example = "Portugal")
    private String name;

    @ElementCollection
    @NotNull
    @NotEmpty
    @Valid
    @Schema(
            description = "List of competition keys for the region",
            example = "[{\"sportId\": \"football\",\"regionId\": \"portugal\",\"competitionId\": \"liga-portugal\"}]")
    private Collection<CompetitionKey> competitionsKeys;

}