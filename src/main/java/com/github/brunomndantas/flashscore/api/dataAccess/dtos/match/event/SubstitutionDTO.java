package com.github.brunomndantas.flashscore.api.dataAccess.dtos.match.event;

import com.github.brunomndantas.flashscore.api.dataAccess.dtos.player.PlayerKeyDTO;
import com.github.brunomndantas.flashscore.api.dataAccess.dtos.team.TeamKeyDTO;
import com.github.brunomndantas.flashscore.api.logic.domain.match.event.Substitution;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@AllArgsConstructor
@Jacksonized
@Builder
public class SubstitutionDTO {

    @NotNull
    @Valid
    private TimeDTO time;

    @NotNull
    @Valid
    private TeamKeyDTO teamKey;

    @NotNull
    @Valid
    private PlayerKeyDTO inPlayerKey;

    @NotNull
    @Valid
    private PlayerKeyDTO outPlayerKey;


    public SubstitutionDTO(Substitution substitution) {
        this.time = substitution.getTime() == null ? null : new TimeDTO(substitution.getTime());
        this.teamKey = substitution.getTeamKey() == null ? null : new TeamKeyDTO(substitution.getTeamKey());
        this.inPlayerKey = substitution.getInPlayerKey() == null ? null : new PlayerKeyDTO(substitution.getInPlayerKey());
        this.outPlayerKey = substitution.getOutPlayerKey() == null ? null : new PlayerKeyDTO(substitution.getOutPlayerKey());
    }


    public Substitution toDomainEntity() {
        return new Substitution(
                time == null ? null : time.toDomainEntity(),
                teamKey == null ? null : teamKey.toDomainEntity(),
                inPlayerKey == null ? null : inPlayerKey.toDomainEntity(),
                outPlayerKey == null ? null : outPlayerKey.toDomainEntity()
        );
    }

}