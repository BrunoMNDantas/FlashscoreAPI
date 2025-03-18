package com.github.brunomndantas.flashscore.api.dataAccess.dtos.sport;

import com.github.brunomndantas.flashscore.api.dataAccess.dtos.region.RegionKeyDTO;
import com.github.brunomndantas.flashscore.api.logic.domain.sport.Sport;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.Collection;

@Value
@AllArgsConstructor
@Jacksonized
@Builder
public class SportDTO {

    @NotNull
    @Valid
    private SportKeyDTO key;

    @NotNull
    @NotEmpty
    @NotBlank
    private String name;

    @NotNull
    @NotEmpty
    private Collection<@NotNull @Valid RegionKeyDTO> regionsKeys;


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