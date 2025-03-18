package com.github.brunomndantas.flashscore.api.dataAccess.dtos.competition;

import com.github.brunomndantas.flashscore.api.dataAccess.dtos.season.SeasonKeyDTO;
import com.github.brunomndantas.flashscore.api.logic.domain.competition.Competition;
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
public class CompetitionDTO {

    @NotNull
    @Valid
    private CompetitionKeyDTO key;

    @NotNull
    @NotEmpty
    @NotBlank
    private String name;

    @NotNull
    @NotEmpty
    private Collection<@NotNull @Valid SeasonKeyDTO> seasonsKeys;


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