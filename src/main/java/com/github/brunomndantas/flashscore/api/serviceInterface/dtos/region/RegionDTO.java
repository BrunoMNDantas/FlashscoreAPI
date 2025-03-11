package com.github.brunomndantas.flashscore.api.serviceInterface.dtos.region;

import com.github.brunomndantas.flashscore.api.logic.domain.region.Region;
import com.github.brunomndantas.flashscore.api.serviceInterface.dtos.competition.CompetitionKeyDTO;
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
@Schema(description = "Region")
public class RegionDTO {

    @Schema(description = "Unique identifier for the region", example = "{\"sportId\": \"football\",\"regionId\": \"portugal\"}")
    private RegionKeyDTO key;

    @Schema(description = "Name of the region", example = "Portugal")
    private String name;

    @Schema(
            description = "List of competition keys for the region",
            example = "[{\"sportId\": \"football\",\"regionId\": \"portugal\",\"competitionId\": \"liga-portugal\"}]")
    private Collection<CompetitionKeyDTO> competitionsKeys;


    public RegionDTO(Region region) {
        this.key = region.getKey() == null ? null : new RegionKeyDTO(region.getKey());
        this.name = region.getName();
        this.competitionsKeys = region.getCompetitionsKeys() == null ? null : region.getCompetitionsKeys().stream().map(CompetitionKeyDTO::new).toList();
    }


    public Region toDomainEntity() {
        return new Region(
                key == null ? null : key.toDomainEntity(),
                name,
                competitionsKeys == null ? null : competitionsKeys.stream().map(CompetitionKeyDTO::toDomainEntity).toList()
        );
    }

}