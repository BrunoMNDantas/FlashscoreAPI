package com.github.brunomndantas.flashscore.api.serviceInterface.dtos.sport;

import com.github.brunomndantas.flashscore.api.logic.domain.sport.Sport;
import com.github.brunomndantas.flashscore.api.serviceInterface.dtos.region.RegionKeyDTO;
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
@Schema(description = "Sport")
public class SportDTO {

    @Schema(description = "Unique identifier for the sport", example = "{\"sportId\": \"football\"}")
    private SportKeyDTO key;

    @Schema(description = "Name of the sport", example = "Football")
    private String name;

    @Schema(description = "List of region keys for the sport", example = "[{\"sportId\": \"football\",\"regionId\": \"portugal\"}]")
    private Collection<RegionKeyDTO> regionsKeys;


    public SportDTO(Sport sport) {
        this.key = sport.getKey() == null ? null : new SportKeyDTO(sport.getKey());
        this.name = sport.getName();
        this.regionsKeys = sport.getRegionsKeys() == null ? null : sport.getRegionsKeys().stream().map(RegionKeyDTO::new).toList();
    }


    public Sport toDomainEntity() {
        return new Sport(
                key == null ? null : key.toDomainEntity(),
                name,
                regionsKeys == null ? null : regionsKeys.stream().map(RegionKeyDTO::toDomainEntity).toList()
        );
    }

}