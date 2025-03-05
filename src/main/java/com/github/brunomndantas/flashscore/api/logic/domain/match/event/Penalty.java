package com.github.brunomndantas.flashscore.api.logic.domain.match.event;

import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@AttributeOverrides({
        @AttributeOverride(name = "type", column = @Column(name = "penalty_type")),
        @AttributeOverride(name = "minute", column = @Column(name = "penalty_minute")),
        @AttributeOverride(name = "extraMinute", column = @Column(name = "penalty_extra_minute")),
        @AttributeOverride(name = "team_id", column = @Column(name = "penalty_team_id")),
        @AttributeOverride(name = "team_name", column = @Column(name = "penalty_team_name"))
})
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Penalty")
public class Penalty extends Event {

    @Schema(description = "Penalty missedUnique", example = "false")
    private boolean missed;

    @NotNull
    @Valid
    @Schema(description = "Unique identifier for the player", example = "{\"playerName\":\"catamo-geny\", \"playerId\":\"29eth6dF\"")
    private PlayerKey playerKey;

}