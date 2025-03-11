package com.github.brunomndantas.flashscore.api.dataAccess.dtos.match.event;

import com.github.brunomndantas.flashscore.api.dataAccess.dtos.DTOTests;
import com.github.brunomndantas.flashscore.api.logic.domain.match.event.Card;
import com.github.brunomndantas.flashscore.api.logic.domain.match.event.Time;
import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CardDTOTests extends DTOTests<CardDTO, Card> {

    @Override
    protected Card getInitialEntity() {
        return new Card(
                new Time(Time.Period.FIRST_HALF, 2, 3),
                new TeamKey("A", "B"),
                new PlayerKey("C", "D"),
                Card.Color.RED
        );
    }

    @Override
    protected Card getNullSafeInitialEntity() {
        return new Card();
    }

    @Override
    protected CardDTO getDTO(Card domainEntity) {
        return new CardDTO(domainEntity);
    }

    @Override
    protected Card getDomainEntity(CardDTO cardDTO) {
        return cardDTO.toDomainEntity();
    }

}