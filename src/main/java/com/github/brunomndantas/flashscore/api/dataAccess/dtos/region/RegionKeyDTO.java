package com.github.brunomndantas.flashscore.api.dataAccess.dtos.region;

import com.github.brunomndantas.flashscore.api.logic.domain.region.RegionKey;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@AllArgsConstructor
@Jacksonized
@Builder
public class RegionKeyDTO {

    @NotNull
    @NotEmpty
    @NotBlank
    private String sportId;

    @NotNull
    @NotEmpty
    @NotBlank
    private String regionId;


    public RegionKeyDTO(RegionKey regionKey) {
        this.sportId = regionKey.getSportId();
        this.regionId = regionKey.getRegionId();
    }


    public RegionKey toDomainEntity() {
        return new RegionKey(sportId, regionId);
    }

}