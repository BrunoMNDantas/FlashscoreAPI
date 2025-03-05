package com.github.brunomndantas.flashscore.api.logic.domain.match.event;

import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@AttributeOverrides({
        @AttributeOverride(name = "type", column = @Column(name = "goal_type")),
        @AttributeOverride(name = "minute", column = @Column(name = "goal_minute")),
        @AttributeOverride(name = "extraMinute", column = @Column(name = "goal_extra_minute")),
        @AttributeOverride(name = "team_id", column = @Column(name = "goal_team_id")),
        @AttributeOverride(name = "team_name", column = @Column(name = "goal_team_name"))
})
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Goal")
public class Goal extends Event {

    @NotNull
    @Valid
    @Schema(description = "Unique identifier for the player", example = "{\"playerName\":\"catamo-geny\", \"playerId\":\"29eth6dF\"")
    private PlayerKey playerKey;

    @Valid
    @Schema(description = "Unique identifier for the assist player", example = "{\"playerName\":\"gyokeres-viktor\", \"playerId\":\"zaBZ1xIk\"}")
    private PlayerKey assistPlayerKey;

}