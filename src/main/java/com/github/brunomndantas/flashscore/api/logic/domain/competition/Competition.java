package com.github.brunomndantas.flashscore.api.logic.domain.competition;

import com.github.brunomndantas.flashscore.api.logic.domain.season.SeasonKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Competition {

    private CompetitionKey key;

    private String name;

    private Collection<SeasonKey> seasonsKeys;

}