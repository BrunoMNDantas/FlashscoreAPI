package com.github.brunomndantas.flashscore.api.serviceInterface.dtos.season;

import com.github.brunomndantas.flashscore.api.logic.domain.season.SeasonKey;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@AllArgsConstructor
@Jacksonized
@Builder
@Schema(description = "Season key")
public class SeasonKeyDTO {

    @Schema(description = "Unique identifier for the sport", example = "football")
    private String sportId;

    @Schema(description = "Unique identifier for the region", example = "portugal")
    private String regionId;

    @Schema(description = "Unique identifier for the competition", example = "liga-portugal")
    private String competitionId;

    @Schema(description = "Unique identifier for the season", example = "2024-2025")
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