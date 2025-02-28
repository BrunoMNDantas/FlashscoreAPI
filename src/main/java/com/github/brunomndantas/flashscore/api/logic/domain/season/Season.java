package com.github.brunomndantas.flashscore.api.logic.domain.season;

import com.github.brunomndantas.flashscore.api.logic.domain.match.MatchKey;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Season")
public class Season {

    @NotNull
    @Valid
    @Schema(
            description = "Unique identifier for the season",
            example = "{\"sportId\": \"football\", \"regionId\": \"portugal\", \"competitionId\": \"liga-portugal\", \"seasonId\": \"2024-2025\"}")
    private SeasonKey key;

    @Min(1900)
    @Max(2100)
    @Schema(description = "Year in which the season started", example = "2024")
    private int initYear;

    @Min(1900)
    @Max(2100)
    @Schema(description = "Year in which the season finished", example = "2025")
    private int endYear;

    @NotNull
    @NotEmpty
    @Valid
    @Schema(description = "List of match keys for the season", example = "[{\"matchId\": \"thRumiAF\"}]")
    private Collection<MatchKey> matchesKeys;

}