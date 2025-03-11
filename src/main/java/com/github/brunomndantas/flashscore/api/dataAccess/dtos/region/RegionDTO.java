package com.github.brunomndantas.flashscore.api.dataAccess.dtos.region;

import com.github.brunomndantas.flashscore.api.dataAccess.dtos.competition.CompetitionKeyDTO;
import com.github.brunomndantas.flashscore.api.logic.domain.region.Region;
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
public class RegionDTO {

    @NotNull
    @Valid
    private RegionKeyDTO key;

    @NotNull
    @NotEmpty
    @NotBlank
    private String name;

    @NotNull
    @NotEmpty
    @Valid
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