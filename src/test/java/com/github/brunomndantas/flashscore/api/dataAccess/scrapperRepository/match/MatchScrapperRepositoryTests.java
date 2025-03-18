package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.match;

import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.ScrapperRepository;
import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.ScrapperRepositoryTests;
import com.github.brunomndantas.flashscore.api.logic.domain.match.Match;
import com.github.brunomndantas.flashscore.api.logic.domain.match.MatchKey;
import com.github.brunomndantas.flashscore.api.logic.domain.match.event.Event;
import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.Collections;

@SpringBootTest
public class MatchScrapperRepositoryTests extends ScrapperRepositoryTests<MatchKey, Match, MatchPage> {

    @Override
    protected ScrapperRepository<MatchKey, Match, MatchPage> createRepository() {
        return createRepository(driverPool);
    }

    @Override
    protected ScrapperRepository<MatchKey, Match, MatchPage> createRepository(IDriverPool driverPool) {
        return new MatchScrapperRepository(driverPool, screenshotsDirectory);
    }

    @Override
    protected MatchKey getExistentKey() {
        return new MatchKey("G29j2xY9");
    }

    @Override
    protected MatchKey getNonExistentKey() {
        return new MatchKey("non_existent_id");
    }


    @Test
    public void shouldScrapData() throws Exception {
        MatchKey key = new MatchKey("vekYdzTa");
        ScrapperRepository<MatchKey, Match, MatchPage> repository = createRepository(driverPool);

        Match match = repository.get(key);

        Assertions.assertNull(getConstraintViolation(match));

        Assertions.assertEquals(key, match.getKey());
        Assertions.assertEquals(new TeamKey("exeter", "ve14a3l4"), match.getHomeTeamKey());
        Assertions.assertEquals(new TeamKey("nottingham", "UsushcZr"), match.getAwayTeamKey());
        Assertions.assertEquals(2, match.getHomeTeamGoals());
        Assertions.assertEquals(3, match.getAwayTeamGoals());
        Assertions.assertEquals(MatchPage.DATE_FORMAT.parse("11.02.2025 20:00"), match.getDate());
        Assertions.assertEquals(new PlayerKey("caldwell-gary", "prPkSBdq"), match.getHomeCoachPlayerKey());
        Assertions.assertEquals(new PlayerKey("espirito-santo", "IoJ2QpOs"), match.getAwayCoachPlayerKey());
        Assertions.assertEquals(11, match.getHomeLineupPlayersKeys().size());
        Assertions.assertEquals(11, match.getAwayLineupPlayersKeys().size());
        Assertions.assertTrue(Collections.disjoint(match.getHomeLineupPlayersKeys(), match.getAwayLineupPlayersKeys()));
        Assertions.assertEquals(9, match.getHomeBenchPlayersKeys().size());
        Assertions.assertEquals(9, match.getAwayBenchPlayersKeys().size());
        Assertions.assertTrue(Collections.disjoint(match.getHomeBenchPlayersKeys(), match.getAwayBenchPlayersKeys()));

        Collection<Event> events = match.getEvents();
        Assertions.assertEquals(28, events.size());
    }

}