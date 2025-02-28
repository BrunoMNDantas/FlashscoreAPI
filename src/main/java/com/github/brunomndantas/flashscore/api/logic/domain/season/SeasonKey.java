package com.github.brunomndantas.flashscore.api.logic.domain.season;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Season key")
public class SeasonKey {

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

    @NotNull
    @NotEmpty
    @NotBlank
    @Pattern(regexp = "^(19\\d{2}|20\\d{2})(?:-(19\\d{2}|20\\d{2}))?$", message = "Invalid season format. Must be 'YYYY' or 'YYYY-YYYY' with years ≥ 1900.")
    @Schema(description = "Unique identifier for the season", example = "2024-2025")
    private String seasonId;

}