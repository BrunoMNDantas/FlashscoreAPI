package com.github.brunomndantas.flashscore.api.logic.domain.region;

import com.github.brunomndantas.flashscore.api.logic.domain.competition.CompetitionKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Region {

    private RegionKey key;

    private String name;

    private Collection<CompetitionKey> competitionsKeys;

}