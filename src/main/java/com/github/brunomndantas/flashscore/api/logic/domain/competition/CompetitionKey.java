package com.github.brunomndantas.flashscore.api.logic.domain.competition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CompetitionKey {

    private String sportId;

    private String regionId;

    private String competitionId;

}