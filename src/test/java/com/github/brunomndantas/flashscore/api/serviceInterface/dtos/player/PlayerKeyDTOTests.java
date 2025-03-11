package com.github.brunomndantas.flashscore.api.serviceInterface.dtos.player;

import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import com.github.brunomndantas.flashscore.api.serviceInterface.dtos.DTOTests;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PlayerKeyDTOTests extends DTOTests<PlayerKeyDTO, PlayerKey> {

    @Override
    protected PlayerKey getInitialEntity() {
        return new PlayerKey("A", "B");
    }

    @Override
    protected PlayerKey getNullSafeInitialEntity() {
        return new PlayerKey();
    }

    @Override
    protected PlayerKeyDTO getDTO(PlayerKey domainEntity) {
        return new PlayerKeyDTO(domainEntity);
    }

    @Override
    protected PlayerKey getDomainEntity(PlayerKeyDTO playerKeyDTO) {
        return playerKeyDTO.toDomainEntity();
    }

}