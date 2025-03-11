package com.github.brunomndantas.flashscore.api.dataAccess.dtos.sport;

import com.github.brunomndantas.flashscore.api.logic.domain.sport.SportKey;
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
public class SportKeyDTO {

    @NotNull
    @NotEmpty
    @NotBlank
    private String sportId;


    public SportKeyDTO(SportKey sportKey) {
        this.sportId = sportKey.getSportId();
    }


    public SportKey toDomainEntity() {
        return new SportKey(sportId);
    }

}