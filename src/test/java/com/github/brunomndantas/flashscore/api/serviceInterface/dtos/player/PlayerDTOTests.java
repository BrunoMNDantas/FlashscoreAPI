package com.github.brunomndantas.flashscore.api.serviceInterface.dtos.player;

import com.github.brunomndantas.flashscore.api.logic.domain.player.Player;
import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import com.github.brunomndantas.flashscore.api.serviceInterface.dtos.DTOTests;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class PlayerDTOTests extends DTOTests<PlayerDTO, Player> {

    @Override
    protected Player getInitialEntity() {
        return new Player(
                new PlayerKey("A", "B"),
                "C",
                new Date(),
                "D"
        );
    }

    @Override
    protected Player getNullSafeInitialEntity() {
        return new Player();
    }

    @Override
    protected PlayerDTO getDTO(Player domainEntity) {
        return new PlayerDTO(domainEntity);
    }

    @Override
    protected Player getDomainEntity(PlayerDTO playerDTO) {
        return playerDTO.toDomainEntity();
    }

}