package com.github.brunomndantas.flashscore.api.logic.domain.season;

import com.github.brunomndantas.flashscore.api.logic.domain.match.MatchKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Season {

    private SeasonKey key;

    private int initYear;

    private int endYear;

    private Collection<MatchKey> matchesKeys;

}