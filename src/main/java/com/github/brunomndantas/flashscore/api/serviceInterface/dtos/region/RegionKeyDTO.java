package com.github.brunomndantas.flashscore.api.serviceInterface.dtos.region;

import com.github.brunomndantas.flashscore.api.logic.domain.region.RegionKey;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@AllArgsConstructor
@Jacksonized
@Builder
@Schema(description = "Region key")
public class RegionKeyDTO {

    @Schema(description = "Unique identifier for the sport", example = "football")
    private String sportId;

    @Schema(description = "Unique identifier for the region", example = "portugal")
    private String regionId;


    public RegionKeyDTO(RegionKey regionKey) {
        this.sportId = regionKey.getSportId();
        this.regionId = regionKey.getRegionId();
    }


    public RegionKey toDomainEntity() {
        return new RegionKey(sportId, regionId);
    }

}