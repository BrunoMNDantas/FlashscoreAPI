package com.github.brunomndantas.flashscore.api.logic.domain.competition;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Competition key")
public class CompetitionKey {

    @NotNull
    @NotEmpty
    @NotBlank
    @Schema(description = "Unique identifier for the sport", example = "football")
    private String sportId;

    @NotNull
    @NotEmpty
    @NotBlank
    @Schema(description = "Unique identifier for the region", example = "portugal")
    private String regionId;

    @NotNull
    @NotEmpty
    @NotBlank
    @Schema(description = "Unique identifier for the competition", example = "liga-portugal")
    private String competitionId;

}