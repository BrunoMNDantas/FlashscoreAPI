package com.github.brunomndantas.flashscore.api.serviceInterface.dtos.match.event;

import com.github.brunomndantas.flashscore.api.logic.domain.match.event.Penalty;
import com.github.brunomndantas.flashscore.api.serviceInterface.dtos.player.PlayerKeyDTO;
import com.github.brunomndantas.flashscore.api.serviceInterface.dtos.team.TeamKeyDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@AllArgsConstructor
@Jacksonized
@Builder
@Schema(description = "Penalty")
public class PenaltyDTO {

    @Schema(description = "Time in which the event occurred", example = "{\"period\": \"FIRST_HALF\", \"minute\": 29, \"extraMinute\": 0}")
    private TimeDTO time;

    @Schema(description = "Unique identifier for the team", example = "{\"teamName\": \"sporting-lisbon\",\"teamId\": \"tljXuHBC\"}")
    private TeamKeyDTO teamKey;

    @Schema(description = "Penalty missedUnique", example = "false")
    private boolean missed;

    @Schema(description = "Unique identifier for the player", example = "{\"playerName\":\"catamo-geny\", \"playerId\":\"29eth6dF\"")
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


