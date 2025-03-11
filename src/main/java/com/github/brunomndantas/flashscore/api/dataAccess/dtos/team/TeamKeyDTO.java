package com.github.brunomndantas.flashscore.api.dataAccess.dtos.team;

import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;
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
public class TeamKeyDTO {

    @NotNull
    @NotEmpty
    @NotBlank
    private String teamName;

    @NotNull
    @NotEmpty
    @NotBlank
    private String teamId;


    public TeamKeyDTO(TeamKey teamKey) {
        this.teamName = teamKey.getTeamName();
        this.teamId = teamKey.getTeamId();
    }


    public TeamKey toDomainEntity() {
        return new TeamKey(teamName, teamId);
    }

}