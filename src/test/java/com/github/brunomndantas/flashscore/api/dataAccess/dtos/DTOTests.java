package com.github.brunomndantas.flashscore.api.dataAccess.dtos;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public abstract class DTOTests<DTO, DE> {

    @Test
    public void shouldMapToDomainEntity() {
        DE initialEntity = getInitialEntity();
        DTO dto = getDTO(initialEntity);
        DE domainEntity = getDomainEntity(dto);

        Assertions.assertEquals(initialEntity, domainEntity);
    }

    @Test
    public void shouldBeNullSafe() {
        DE initialEntity = getNullSafeInitialEntity();
        DTO dto = getDTO(initialEntity);
        DE domainEntity = getDomainEntity(dto);

        Assertions.assertEquals(initialEntity, domainEntity);
    }


    protected abstract DE getInitialEntity();

    protected abstract DE getNullSafeInitialEntity();

    protected abstract DTO getDTO(DE domainEntity);

    protected abstract DE getDomainEntity(DTO dto);

}
