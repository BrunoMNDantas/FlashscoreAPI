package com.github.brunomndantas.flashscore.api.serviceInterface.dtos.competition;

import com.github.brunomndantas.flashscore.api.logic.domain.competition.Competition;
import com.github.brunomndantas.flashscore.api.serviceInterface.dtos.season.SeasonKeyDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.Collection;

@Value
@AllArgsConstructor
@Jacksonized
@Builder
@Schema(description = "Competition")
public class CompetitionDTO {

    @Schema(description = "Unique identifier for the competition", example = "{\"sportId\": \"football\",\"regionId\": \"portugal\",\"competitionId\": \"liga-portugal\"}")
    private CompetitionKeyDTO key;

    @Schema(description = "Name of the competition", example = "Liga Portugal")
    private String name;

    @Schema(
            description = "List of season keys for the competition",
            example = "[{\"sportId\": \"football\", \"regionId\": \"portugal\", \"competitionId\": \"liga-portugal\", \"seasonId\": \"2024-2025\"}]")
    private Collection<SeasonKeyDTO> seasonsKeys;


    public CompetitionDTO(Competition competition) {
        this.key = competition.getKey() == null ? null : new CompetitionKeyDTO(competition.getKey());
        this.name = competition.getName();
        this.seasonsKeys = competition.getSeasonsKeys() == null ? null : competition.getSeasonsKeys().stream().map(SeasonKeyDTO::new).toList();
    }


    public Competition toDomainEntity() {
        return new Competition(
                key == null ? null : key.toDomainEntity(),
                name,
                seasonsKeys == null ? null : seasonsKeys.stream().map(SeasonKeyDTO::toDomainEntity).toList()
        );
    }

}