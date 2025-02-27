package com.github.brunomndantas.flashscore.api.dataAccess;

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

@SpringBootTest
public class MatchScrapperRepositoryTests extends ScrapperRepositoryTests<MatchKey, Match> {

    @Override
    protected ScrapperRepository<MatchKey, Match> createRepository() {
        return createRepository(DRIVER_POOL);
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
        ScrapperRepository<MatchKey, Match> repository = createRepository(DRIVER_POOL);

        Match match = repository.get(key);

        Assertions.assertNull(getConstraintViolation(match));

        Assertions.assertEquals(key, match.getKey());
        Assertions.assertEquals(new TeamKey("exeter", "ve14a3l4"), match.getHomeTeamKey());
        Assertions.assertEquals(new TeamKey("nottingham", "UsushcZr"), match.getAwayTeamKey());
        Assertions.assertEquals(2, match.getHomeTeamGoals());
        Assertions.assertEquals(3, match.getAwayTeamGoals());
        Assertions.assertEquals(MatchScrapperRepository.DATE_FORMAT.parse("11.02.2025 20:00"), match.getDate());

        Collection<Event> firstHalfEvents = match.getFirstHalfEvents();
        Assertions.assertEquals(4, firstHalfEvents.size());
        Assertions.assertTrue(firstHalfEvents.contains(getFirstGoal()));
        Assertions.assertTrue(firstHalfEvents.contains(getSecondGoal()));
        Assertions.assertTrue(firstHalfEvents.contains(getThirdGoal()));
        Assertions.assertTrue(firstHalfEvents.contains(getFirstYellowCard()));

        Collection<Event> secondHalfEvents = match.getSecondHalfEvents();
        Assertions.assertEquals(13, secondHalfEvents.size());
        Assertions.assertTrue(secondHalfEvents.contains(getFourtGoal()));
        Assertions.assertTrue(secondHalfEvents.contains(getFirstSubstitution()));
        Assertions.assertTrue(secondHalfEvents.contains(getSecondSubstitution()));
        Assertions.assertTrue(secondHalfEvents.contains(getThirdSubstitution()));
        Assertions.assertTrue(secondHalfEvents.contains(getFourthSubstitution()));
        Assertions.assertTrue(secondHalfEvents.contains(getFirstRedCard()));

        Collection<Event> extraTimeEvents = match.getExtraTimeEvents();
        Assertions.assertEquals(3, extraTimeEvents.size());
        Assertions.assertTrue(extraTimeEvents.contains(getSecondYellowCard()));
        Assertions.assertTrue(extraTimeEvents.contains(getThirdYellowCard()));

        Collection<Penalty> penalties = match.getPenalties();
        Assertions.assertEquals(8, penalties.size());
        Assertions.assertTrue(penalties.contains(getFirstPenalty()));
        Assertions.assertTrue(penalties.contains(getSecondPenalty()));
        Assertions.assertTrue(penalties.contains(getThirdPenalty()));
    }

    private Goal getFirstGoal() {
        Goal goal = new Goal();

        goal.setType(EventType.GOAL);
        goal.setMinute(5);
        goal.setExtraMinute(0);
        goal.setTeamKey(new TeamKey("exeter", "ve14a3l4"));
        goal.setPlayerKey(new PlayerKey("magennis-josh", "IXUzpyWo"));
        goal.setAssistPlayerKey(null);

        return goal;
    }

    private Goal getSecondGoal() {
        Goal goal = new Goal();

        goal.setType(EventType.GOAL);
        goal.setMinute(15);
        goal.setExtraMinute(0);
        goal.setTeamKey(new TeamKey("nottingham", "UsushcZr"));
        goal.setPlayerKey(new PlayerKey("sosa-ramon", "GzkUujEi"));
        goal.setAssistPlayerKey(new PlayerKey("sangare-ibrahim", "0zOKXsla"));

        return goal;
    }

    private Goal getThirdGoal() {
        Goal goal = new Goal();

        goal.setType(EventType.GOAL);
        goal.setMinute(37);
        goal.setExtraMinute(0);
        goal.setTeamKey(new TeamKey("nottingham", "UsushcZr"));
        goal.setPlayerKey(new PlayerKey("awoniyi-taiwo", "OWFf8WFm"));
        goal.setAssistPlayerKey(new PlayerKey("sangare-ibrahim", "0zOKXsla"));

        return goal;
    }

    private Goal getFourtGoal() {
        Goal goal = new Goal();

        goal.setType(EventType.GOAL);
        goal.setMinute(50);
        goal.setExtraMinute(0);
        goal.setTeamKey(new TeamKey("exeter", "ve14a3l4"));
        goal.setPlayerKey(new PlayerKey("magennis-josh", "IXUzpyWo"));
        goal.setAssistPlayerKey(null);

        return goal;
    }

    private Card getFirstYellowCard() {
        Card card = new Card();

        card.setType(EventType.CARD);
        card.setMinute(38);
        card.setExtraMinute(0);
        card.setTeamKey(new TeamKey("exeter", "ve14a3l4"));
        card.setColor(CardColor.YELLOW);
        card.setPlayerKey(new PlayerKey("mcmillan-jack", "YVrybbkq"));

        return card;
    }

    private Card getSecondYellowCard() {
        Card card = new Card();

        card.setType(EventType.CARD);
        card.setMinute(105);
        card.setExtraMinute(1);
        card.setTeamKey(new TeamKey("nottingham", "UsushcZr"));
        card.setColor(CardColor.YELLOW);
        card.setPlayerKey(new PlayerKey("williams-neco", "YVxu86Hs"));

        return card;
    }

    private Card getThirdYellowCard() {
        Card card = new Card();

        card.setType(EventType.CARD);
        card.setMinute(115);
        card.setExtraMinute(0);
        card.setTeamKey(new TeamKey("exeter", "ve14a3l4"));
        card.setColor(CardColor.YELLOW);
        card.setPlayerKey(new PlayerKey("yogane-tony", "fP6cbjth"));

        return card;
    }

    private Card getFirstRedCard() {
        Card card = new Card();

        card.setType(EventType.CARD);
        card.setMinute(87);
        card.setExtraMinute(0);
        card.setTeamKey(new TeamKey("exeter", "ve14a3l4"));
        card.setColor(CardColor.RED);
        card.setPlayerKey(new PlayerKey("turns-ed", "Kraw6tR1"));

        return card;
    }

    private Substitution getFirstSubstitution() {
        Substitution substitution = new Substitution();

        substitution.setType(EventType.SUBSTITUTION);
        substitution.setMinute(59);
        substitution.setExtraMinute(0);
        substitution.setTeamKey(new TeamKey("nottingham", "UsushcZr"));
        substitution.setInPlayerKey(new PlayerKey("sels-matz", "Qqo54dB7"));
        substitution.setOutPlayerKey(new PlayerKey("carlos-miguel", "GhFZjle9"));

        return substitution;
    }

    private Substitution getSecondSubstitution() {
        Substitution substitution = new Substitution();

        substitution.setType(EventType.SUBSTITUTION);
        substitution.setMinute(59);
        substitution.setExtraMinute(0);
        substitution.setTeamKey(new TeamKey("nottingham", "UsushcZr"));
        substitution.setInPlayerKey(new PlayerKey("dominguez-nicolas", "A7jPNAQT"));
        substitution.setOutPlayerKey(new PlayerKey("danilo", "rsMiiNaj"));

        return substitution;
    }

    private Substitution getThirdSubstitution() {
        Substitution substitution = new Substitution();

        substitution.setType(EventType.SUBSTITUTION);
        substitution.setMinute(71);
        substitution.setExtraMinute(0);
        substitution.setTeamKey(new TeamKey("exeter", "ve14a3l4"));
        substitution.setInPlayerKey(new PlayerKey("watts-caleb", "fZSnywKF"));
        substitution.setOutPlayerKey(new PlayerKey("trevitt-ryan", "IDtPdIPE"));

        return substitution;
    }

    private Substitution getFourthSubstitution() {
        Substitution substitution = new Substitution();

        substitution.setType(EventType.SUBSTITUTION);
        substitution.setMinute(71);
        substitution.setExtraMinute(0);
        substitution.setTeamKey(new TeamKey("nottingham", "UsushcZr"));
        substitution.setInPlayerKey(new PlayerKey("anderson-elliott", "KdYCbsbE"));
        substitution.setOutPlayerKey(new PlayerKey("yates-ryan", "IwFLkXYE"));

        return substitution;
    }

    private Penalty getFirstPenalty() {
        Penalty penalty = new Penalty();

        penalty.setType(EventType.PENALTY);
        penalty.setMinute(1);
        penalty.setExtraMinute(0);
        penalty.setTeamKey(new TeamKey("exeter", "ve14a3l4"));
        penalty.setMissed(false);
        penalty.setPlayerKey(new PlayerKey("magennis-josh", "IXUzpyWo"));

        return penalty;
    }

    private Penalty getSecondPenalty() {
        Penalty penalty = new Penalty();

        penalty.setType(EventType.PENALTY);
        penalty.setMinute(1);
        penalty.setExtraMinute(0);
        penalty.setTeamKey(new TeamKey("nottingham", "UsushcZr"));
        penalty.setMissed(false);
        penalty.setPlayerKey(new PlayerKey("wood-chris", "Cp7L7Gro"));

        return penalty;
    }

    private Penalty getThirdPenalty() {
        Penalty penalty = new Penalty();

        penalty.setType(EventType.PENALTY);
        penalty.setMinute(2);
        penalty.setExtraMinute(0);
        penalty.setTeamKey(new TeamKey("exeter", "ve14a3l4"));
        penalty.setMissed(true);
        penalty.setPlayerKey(new PlayerKey("cole-reece", "WCqq6BwA"));

        return penalty;
    }

}