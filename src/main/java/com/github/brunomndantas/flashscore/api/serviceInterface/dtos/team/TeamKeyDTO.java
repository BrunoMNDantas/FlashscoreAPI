package com.github.brunomndantas.flashscore.api.serviceInterface.dtos.team;

import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@AllArgsConstructor
@Jacksonized
@Builder
@Schema(description = "Team key")
public class TeamKeyDTO {

    @Schema(description = "Name identifier for the team", example = "sporting-cp")
    private String teamName;

    @Schema(description = "Unique identifier for the team", example = "tljXuHBC")
    private String teamId;


    public TeamKeyDTO(TeamKey teamKey) {
        this.teamName = teamKey.getTeamName();
        this.teamId = teamKey.getTeamId();
    }


    public TeamKey toDomainEntity() {
        return new TeamKey(teamName, teamId);
    }

}