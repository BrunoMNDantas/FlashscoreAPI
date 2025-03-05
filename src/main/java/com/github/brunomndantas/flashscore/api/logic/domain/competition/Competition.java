package com.github.brunomndantas.flashscore.api.logic.domain.competition;

import com.github.brunomndantas.flashscore.api.logic.domain.season.SeasonKey;
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
@Schema(description = "Competition")
public class Competition {

    @EmbeddedId
    @NotNull
    @Valid
    @Schema(description = "Unique identifier for the competition", example = "{\"sportId\": \"football\",\"regionId\": \"portugal\",\"competitionId\": \"liga-portugal\"}")
    private CompetitionKey key;

    @NotNull
    @NotEmpty
    @NotBlank
    @Schema(description = "Name of the competition", example = "Liga Portugal")
    private String name;

    @ElementCollection
    @NotNull
    @NotEmpty
    @Valid
    @Schema(
            description = "List of season keys for the competition",
            example = "[{\"sportId\": \"football\", \"regionId\": \"portugal\", \"competitionId\": \"liga-portugal\", \"seasonId\": \"2024-2025\"}]")
    private Collection<SeasonKey> seasonsKeys;

}