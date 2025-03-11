package com.github.brunomndantas.flashscore.api.serviceInterface.dtos.match.event;

import com.github.brunomndantas.flashscore.api.logic.domain.match.event.Card;
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
@Schema(description = "Card")
public class CardDTO {

    @Schema(description = "Card color")
    public enum Color {
        YELLOW,
        RED
    }


    @Schema(description = "Time in which the event occurred", example = "{\"period\": \"SECOND_HALF\", \"minute\": 58, \"extraMinute\": 0}")
    private TimeDTO time;

    @Schema(description = "Unique identifier for the team", example = "{\"teamName\": \"benfica\",\"teamId\": \"zBkyuyRI\"}")
    private TeamKeyDTO teamKey;

    @Schema(description = "Unique identifier for the player", example = "{\"playerName\": \"bah-alexander\", \"playerId\": \"Kn1rziON\"}")
    private PlayerKeyDTO playerKey;

    @Schema(description = "Color of the card", example = "YELLOW")
    private Color color;


    public CardDTO(Card card) {
        this.time = card.getTime() == null ? null : new TimeDTO(card.getTime());
        this.teamKey = card.getTeamKey() == null ? null : new TeamKeyDTO(card.getTeamKey());
        this.playerKey = card.getPlayerKey() == null ? null : new PlayerKeyDTO(card.getPlayerKey());
        this.color = card.getColor() == null ? null : Color.valueOf(card.getColor().toString());
    }


    public Card toDomainEntity() {
        return new Card(
                time == null ? null : time.toDomainEntity(),
                teamKey == null ? null : teamKey.toDomainEntity(),
                playerKey == null ? null : playerKey.toDomainEntity(),
                color == null ? null : Card.Color.valueOf(color.toString())
        );
    }

}