package com.github.brunomndantas.flashscore.api.dataAccess.dtos.season;

import com.github.brunomndantas.flashscore.api.logic.domain.season.SeasonKey;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@AllArgsConstructor
@Jacksonized
@Builder
public class SeasonKeyDTO {

    @NotNull
    @NotEmpty
    @NotBlank
    private String sportId;

    @NotNull
    @NotEmpty
    @NotBlank
    private String regionId;

    @NotNull
    @NotEmpty
    @NotBlank
    private String competitionId;

    @NotNull
    @NotEmpty
    @NotBlank
    @Pattern(regexp = "^(19\\d{2}|20\\d{2})(?:-(19\\d{2}|20\\d{2}))?$", message = "Invalid season format. Must be 'YYYY' or 'YYYY-YYYY' with years ≥ 1900.")
    private String seasonId;


    public SeasonKeyDTO(SeasonKey seasonKey) {
        this.sportId = seasonKey.getSportId();
        this.regionId = seasonKey.getRegionId();
        this.competitionId = seasonKey.getCompetitionId();
        this.seasonId = seasonKey.getSeasonId();
    }


    public SeasonKey toDomainEntity() {
        return new SeasonKey(sportId, regionId, competitionId, seasonId);
    }

}