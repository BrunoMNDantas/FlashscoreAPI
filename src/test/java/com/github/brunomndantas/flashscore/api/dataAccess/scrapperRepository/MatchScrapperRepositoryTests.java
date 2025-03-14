package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository;

import com.github.brunomndantas.flashscore.api.logic.domain.match.Match;
import com.github.brunomndantas.flashscore.api.logic.domain.match.MatchKey;
import com.github.brunomndantas.flashscore.api.logic.domain.match.event.*;
import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.Collections;

@SpringBootTest
public class MatchScrapperRepositoryTests extends ScrapperRepositoryTests<MatchKey, Match> {

    @Override
    protected ScrapperRepository<MatchKey, Match> createRepository() {
        return createRepository(driverPool);
    }

    @Override
    protected ScrapperRepository<MatchKey, Match> createRepository(IDriverPool driverPool) {
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
        ScrapperRepository<MatchKey, Match> repository = createRepository(driverPool);

        Match match = repository.get(key);

        Assertions.assertNull(getConstraintViolation(match));

        Assertions.assertEquals(key, match.getKey());
        Assertions.assertEquals(new TeamKey("exeter", "ve14a3l4"), match.getHomeTeamKey());
        Assertions.assertEquals(new TeamKey("nottingham", "UsushcZr"), match.getAwayTeamKey());
        Assertions.assertEquals(2, match.getHomeTeamGoals());
        Assertions.assertEquals(3, match.getAwayTeamGoals());
        Assertions.assertEquals(MatchScrapperRepository.DATE_FORMAT.parse("11.02.2025 20:00"), match.getDate());
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

        Assertions.assertTrue(events.contains(getFirstGoal()));
        Assertions.assertTrue(events.contains(getSecondGoal()));
        Assertions.assertTrue(events.contains(getFirstYellowCard()));
        Assertions.assertTrue(events.contains(getFourtGoal()));
        Assertions.assertTrue(events.contains(getFirstSubstitution()));
        Assertions.assertTrue(events.contains(getSecondSubstitution()));
        Assertions.assertTrue(events.contains(getFourthSubstitution()));
        Assertions.assertTrue(events.contains(getFirstRedCard()));
        Assertions.assertTrue(events.contains(getSecondYellowCard()));
        Assertions.assertTrue(events.contains(getThirdYellowCard()));
        Assertions.assertTrue(events.contains(getFirstPenalty()));
        Assertions.assertTrue(events.contains(getSecondPenalty()));
    }

    private Goal getFirstGoal() {
        Goal goal = new Goal();

        goal.setTime(new Time(Time.Period.FIRST_HALF, 5, 0));
        goal.setTeamKey(new TeamKey("exeter", "ve14a3l4"));
        goal.setPlayerKey(new PlayerKey("magennis-josh", "IXUzpyWo"));
        goal.setAssistPlayerKey(null);

        return goal;
    }

    private Goal getSecondGoal() {
        Goal goal = new Goal();

        goal.setTime(new Time(Time.Period.FIRST_HALF, 15, 0));
        goal.setTeamKey(new TeamKey("nottingham", "UsushcZr"));
        goal.setPlayerKey(new PlayerKey("sosa-ramon", "GzkUujEi"));
        goal.setAssistPlayerKey(new PlayerKey("sangare-ibrahim", "0zOKXsla"));

        return goal;
    }

    private Goal getFourtGoal() {
        Goal goal = new Goal();

        goal.setTime(new Time(Time.Period.SECOND_HALF, 50, 0));
        goal.setTeamKey(new TeamKey("exeter", "ve14a3l4"));
        goal.setPlayerKey(new PlayerKey("magennis-josh", "IXUzpyWo"));
        goal.setAssistPlayerKey(null);

        return goal;
    }

    private Card getFirstYellowCard() {
        Card card = new Card();

        card.setTime(new Time(Time.Period.FIRST_HALF, 38, 0));
        card.setTeamKey(new TeamKey("exeter", "ve14a3l4"));
        card.setColor(Card.Color.YELLOW);
        card.setPlayerKey(new PlayerKey("mcmillan-jack", "YVrybbkq"));

        return card;
    }

    private Card getSecondYellowCard() {
        Card card = new Card();

        card.setTime(new Time(Time.Period.EXTRA_TIME, 105, 1));
        card.setTeamKey(new TeamKey("nottingham", "UsushcZr"));
        card.setColor(Card.Color.YELLOW);
        card.setPlayerKey(new PlayerKey("williams-neco", "YVxu86Hs"));

        return card;
    }

    private Card getThirdYellowCard() {
        Card card = new Card();

        card.setTime(new Time(Time.Period.EXTRA_TIME, 115, 0));
        card.setTeamKey(new TeamKey("exeter", "ve14a3l4"));
        card.setColor(Card.Color.YELLOW);
        card.setPlayerKey(new PlayerKey("yogane-tony", "fP6cbjth"));

        return card;
    }

    private Card getFirstRedCard() {
        Card card = new Card();

        card.setTime(new Time(Time.Period.SECOND_HALF, 87, 0));
        card.setTeamKey(new TeamKey("exeter", "ve14a3l4"));
        card.setColor(Card.Color.RED);
        card.setPlayerKey(new PlayerKey("turns-ed", "Kraw6tR1"));

        return card;
    }

    private Substitution getFirstSubstitution() {
        Substitution substitution = new Substitution();

        substitution.setTime(new Time(Time.Period.SECOND_HALF, 59, 0));
        substitution.setTeamKey(new TeamKey("nottingham", "UsushcZr"));
        substitution.setInPlayerKey(new PlayerKey("sels-matz", "Qqo54dB7"));
        substitution.setOutPlayerKey(new PlayerKey("carlos-miguel", "GhFZjle9"));

        return substitution;
    }

    private Substitution getSecondSubstitution() {
        Substitution substitution = new Substitution();

        substitution.setTime(new Time(Time.Period.SECOND_HALF, 59, 0));
        substitution.setTeamKey(new TeamKey("nottingham", "UsushcZr"));
        substitution.setInPlayerKey(new PlayerKey("dominguez-nicolas", "A7jPNAQT"));
        substitution.setOutPlayerKey(new PlayerKey("danilo", "rsMiiNaj"));

        return substitution;
    }

    private Substitution getFourthSubstitution() {
        Substitution substitution = new Substitution();

        substitution.setTime(new Time(Time.Period.SECOND_HALF, 71, 0));
        substitution.setTeamKey(new TeamKey("nottingham", "UsushcZr"));
        substitution.setInPlayerKey(new PlayerKey("anderson-elliott", "KdYCbsbE"));
        substitution.setOutPlayerKey(new PlayerKey("yates-ryan", "IwFLkXYE"));

        return substitution;
    }

    private Penalty getFirstPenalty() {
        Penalty penalty = new Penalty();

        penalty.setTime(new Time(Time.Period.PENALTIES, 1, 0));
        penalty.setTeamKey(new TeamKey("exeter", "ve14a3l4"));
        penalty.setMissed(false);
        penalty.setPlayerKey(new PlayerKey("magennis-josh", "IXUzpyWo"));

        return penalty;
    }

    private Penalty getSecondPenalty() {
        Penalty penalty = new Penalty();

        penalty.setTime(new Time(Time.Period.PENALTIES, 1, 0));
        penalty.setTeamKey(new TeamKey("nottingham", "UsushcZr"));
        penalty.setMissed(false);
        penalty.setPlayerKey(new PlayerKey("wood-chris", "Cp7L7Gro"));

        return penalty;
    }

}