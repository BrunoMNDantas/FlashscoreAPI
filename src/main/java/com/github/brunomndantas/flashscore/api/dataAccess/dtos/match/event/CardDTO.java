package com.github.brunomndantas.flashscore.api.dataAccess.dtos.match.event;

import com.github.brunomndantas.flashscore.api.dataAccess.dtos.player.PlayerKeyDTO;
import com.github.brunomndantas.flashscore.api.dataAccess.dtos.team.TeamKeyDTO;
import com.github.brunomndantas.flashscore.api.logic.domain.match.event.Card;
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
public class CardDTO {

    public enum Color {
        YELLOW,
        RED
    }


    @NotNull
    @Valid
    private TimeDTO time;

    @NotNull
    @Valid
    private TeamKeyDTO teamKey;

    @Valid
    private PlayerKeyDTO playerKey;

    @NotNull
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