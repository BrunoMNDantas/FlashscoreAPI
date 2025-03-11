package com.github.brunomndantas.flashscore.api.serviceInterface.dtos.sport;

import com.github.brunomndantas.flashscore.api.logic.domain.sport.SportKey;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@AllArgsConstructor
@Jacksonized
@Builder
@Schema(description = "Sport key")
public class SportKeyDTO {

    @Schema(description = "Unique identifier for the sport", example = "football")
    private String sportId;


    public SportKeyDTO(SportKey sportKey) {
        this.sportId = sportKey.getSportId();
    }


    public SportKey toDomainEntity() {
        return new SportKey(sportId);
    }

}