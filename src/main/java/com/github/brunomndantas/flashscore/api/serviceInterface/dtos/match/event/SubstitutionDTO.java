package com.github.brunomndantas.flashscore.api.serviceInterface.dtos.match.event;

import com.github.brunomndantas.flashscore.api.logic.domain.match.event.Substitution;
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
@Schema(description = "Substitution")
public class SubstitutionDTO {

    @Schema(description = "Time in which the event occurred", example = "{\"period\": \"SECOND_HALF\", \"minute\": 72, \"extraMinute\": 0}")
    private TimeDTO time;

    @Schema(description = "Unique identifier for the team", example = "{\"teamName\": \"sporting-lisbon\",\"teamId\": \"tljXuHBC\"}")
    private TeamKeyDTO teamKey;

    @Schema(description = "Unique identifier for the player entering", example = "{\"playerName\": \"simoes-joao\",\"playerId\": \"4UYkqiMB\"},")
    private PlayerKeyDTO inPlayerKey;

    @Schema(description = "Unique identifier for the player leaving", example = "{\"playerName\": \"morita-hidemasa\",\"playerId\": \"Y3AIyFGA\"}")
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