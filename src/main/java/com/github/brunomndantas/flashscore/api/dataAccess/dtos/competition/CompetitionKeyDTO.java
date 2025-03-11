package com.github.brunomndantas.flashscore.api.dataAccess.dtos.competition;

import com.github.brunomndantas.flashscore.api.logic.domain.competition.CompetitionKey;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder
@AllArgsConstructor
public class CompetitionKeyDTO {

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


    public CompetitionKeyDTO(CompetitionKey competitionKey) {
        this.sportId = competitionKey.getSportId();
        this.regionId = competitionKey.getRegionId();
        this.competitionId = competitionKey.getCompetitionId();
    }


    public CompetitionKey toDomainEntity() {
        return new CompetitionKey(sportId, regionId, competitionId);
    }

}