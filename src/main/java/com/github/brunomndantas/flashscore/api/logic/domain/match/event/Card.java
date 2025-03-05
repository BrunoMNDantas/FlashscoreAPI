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
        @AttributeOverride(name = "type", column = @Column(name = "card_type")),
        @AttributeOverride(name = "minute", column = @Column(name = "card_minute")),
        @AttributeOverride(name = "extraMinute", column = @Column(name = "card_extra_minute")),
        @AttributeOverride(name = "team_id", column = @Column(name = "card_team_id")),
        @AttributeOverride(name = "team_name", column = @Column(name = "card_team_name"))
})
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Card")
public class Card extends Event {

    @NotNull
    @Valid
    @Schema(description = "Unique identifier for the player", example = "{\"playerName\": \"bah-alexander\", \"playerId\": \"Kn1rziON\"}")
    private PlayerKey playerKey;

    @NotNull
    @Schema(description = "Color of the card", example = "YELLOW")
    private CardColor color;

}