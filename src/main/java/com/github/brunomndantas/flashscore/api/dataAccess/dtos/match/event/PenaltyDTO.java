package com.github.brunomndantas.flashscore.api.dataAccess.dtos.match.event;

import com.github.brunomndantas.flashscore.api.dataAccess.dtos.player.PlayerKeyDTO;
import com.github.brunomndantas.flashscore.api.dataAccess.dtos.team.TeamKeyDTO;
import com.github.brunomndantas.flashscore.api.logic.domain.match.event.Penalty;
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
public class PenaltyDTO {

    @NotNull
    @Valid
    private TimeDTO time;

    @NotNull
    @Valid
    private TeamKeyDTO teamKey;

    private boolean missed;

    @NotNull
    @Valid
    private PlayerKeyDTO playerKey;


    public PenaltyDTO(Penalty penalty) {
        this.time = penalty.getTime() == null ? null : new TimeDTO(penalty.getTime());
        this.teamKey = penalty.getTeamKey() == null ? null : new TeamKeyDTO(penalty.getTeamKey());
        this.missed = penalty.isMissed();
        this.playerKey = penalty.getPlayerKey() == null ? null : new PlayerKeyDTO(penalty.getPlayerKey());
    }


    public Penalty toDomainEntity() {
        return new Penalty(
                time  == null ? null :  time.toDomainEntity(),
                teamKey == null ? null : teamKey.toDomainEntity(),
                missed,
                playerKey == null ? null : playerKey.toDomainEntity()
        );
    }

}


