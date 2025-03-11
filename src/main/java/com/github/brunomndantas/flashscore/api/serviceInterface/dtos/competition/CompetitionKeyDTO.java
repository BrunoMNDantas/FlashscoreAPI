package com.github.brunomndantas.flashscore.api.serviceInterface.dtos.competition;

import com.github.brunomndantas.flashscore.api.logic.domain.competition.CompetitionKey;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder
@AllArgsConstructor
@Schema(description = "Competition key")
public class CompetitionKeyDTO {

    @Schema(description = "Unique identifier for the sport", example = "football")
    private String sportId;

    @Schema(description = "Unique identifier for the region", example = "portugal")
    private String regionId;

    @Schema(description = "Unique identifier for the competition", example = "liga-portugal")
    private String competitionId;


    public CompetitionKeyDTO(CompetitionKey competitionKey) {
        this.sportId = competitionKey.getSportId();
        this.regionId = competitionKey.getRegionId();
        this.competitionId = competitionKey.getCompetitionId();
    }


    public CompetitionKey toDomainEntity() {
        return new CompetitionKey(sportId, regionId, competitionId);
    }

}