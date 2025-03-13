package com.github.brunomndantas.flashscore.api.logic.domain.season;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SeasonKey {

    private String sportId;

    private String regionId;

    private String competitionId;

    private String seasonId;

}