package com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.match;

import com.github.brunomndantas.flashscore.api.logic.domain.match.MatchKey;
import com.github.brunomndantas.flashscore.api.logic.domain.match.event.*;
import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.DriverPool;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

@SpringBootTest
public class MatchPageTests {
    private static final MatchKey KEY = new MatchKey("vekYdzTa");
    private static final MatchKey NON_EXISTENT_KEY = new MatchKey("nonExistentKey");


    @Autowired
    private DriverPool driverPool;

    private WebDriver driver;


    @BeforeEach
    public void init() throws Exception {
        this.driver = driverPool.getDriver();
    }

    @AfterEach
    public void end() throws Exception {
        driverPool.returnDriver(driver);
    }


    @Test
    public void shouldReturnHomeTeamKeyIfPresent() {
        MatchPage page = new MatchPage(driver, KEY);

        page.load();

        Assertions.assertEquals(new TeamKey("exeter", "ve14a3l4"), page.getHomeTeamKey());
    }

    @Test
    public void shouldReturnDefaultHomeTeamKeyIfNotPresent() {
        MatchPage page = new MatchPage(driver, NON_EXISTENT_KEY);

        page.load();

        Assertions.assertNull(page.getHomeTeamKey());
    }

    @Test
    public void shouldReturnAwayTeamKeyIfPresent() {
        MatchPage page = new MatchPage(driver, KEY);

        page.load();

        Assertions.assertEquals(new TeamKey("nottingham", "UsushcZr"), page.getAwayTeamKey());
    }

    @Test
    public void shouldReturnDefaultAwayTeamKeyIfNotPresent() {
        MatchPage page = new MatchPage(driver, NON_EXISTENT_KEY);

        page.load();

        Assertions.assertNull(page.getAwayTeamKey());
    }

    @Test
    public void shouldReturnHomeTeamGoalsIfPresent() {
        MatchPage page = new MatchPage(driver, KEY);

        page.load();

        Assertions.assertEquals(2, page.getHomeTeamGoals());
    }

    @Test
    public void shouldReturnDefaultHomeTeamGoalsIfNotPresent() {
        MatchPage page = new MatchPage(driver, NON_EXISTENT_KEY);

        page.load();

        Assertions.assertEquals(-1, page.getHomeTeamGoals());
    }

    @Test
    public void shouldReturnAwayTeamGoalsIfPresent() {
        MatchPage page = new MatchPage(driver, KEY);

        page.load();

        Assertions.assertEquals(3, page.getAwayTeamGoals());
    }

    @Test
    public void shouldReturnDefaultAwayTeamGoalsIfNotPresent() {
        MatchPage page = new MatchPage(driver, NON_EXISTENT_KEY);

        page.load();

        Assertions.assertEquals(-1, page.getAwayTeamGoals());
    }

    @Test
    public void shouldReturnDateIfPresent() throws Exception {
        MatchPage page = new MatchPage(driver, KEY);

        page.load();

        Assertions.assertEquals(MatchPage.DATE_FORMAT.parse("11.02.2025 20:00"), page.getDate());
    }

    @Test
    public void shouldReturnDefaultDateIfNotPresent() {
        MatchPage page = new MatchPage(driver, NON_EXISTENT_KEY);

        page.load();

        Assertions.assertNull(page.getDate());
    }

    @Test
    public void shouldWrapDateParsingException() {
        WebDriver mockDriver = Mockito.spy(driver);
        WebElement mockElement = Mockito.mock(WebElement.class);

        Mockito.doReturn(Arrays.asList(mockElement)).when(mockDriver).findElements(MatchPage.DATE_SELECTOR);
        Mockito.doReturn(mockElement).when(mockDriver).findElement(MatchPage.DATE_SELECTOR);
        Mockito.when(mockElement.getText()).thenReturn("invalid date");

        MatchPage page = new MatchPage(mockDriver, KEY);

        page.load();

        Exception exception = Assertions.assertThrows(RuntimeException.class, page::getDate);
        Assertions.assertNotNull(exception.getCause(), "Exception cause should not be null");
        Assertions.assertInstanceOf(ParseException.class, exception.getCause());
    }

    @Test
    public void shouldReturnFirstHalfEventsIfPresent() {
        MatchPage page = new MatchPage(driver, KEY);

        page.load();

        Collection<Event> events = page.getFirstHalfEvents();
        Assertions.assertNotNull(events);
        Assertions.assertEquals(4, events.size());
        Assertions.assertTrue(events.contains(getFirstFirstHalfEvent()));
        Assertions.assertTrue(events.contains(getSecondFirstHalfEvent()));
        Assertions.assertTrue(events.contains(getThirdFirstHalfEvent()));
    }

    private Event getFirstFirstHalfEvent() {
        Goal goal = new Goal();

        goal.setTime(new Time(Time.Period.FIRST_HALF, 5, 0));
        goal.setTeamKey(new TeamKey("exeter", "ve14a3l4"));
        goal.setPlayerKey(new PlayerKey("magennis-josh", "IXUzpyWo"));
        goal.setAssistPlayerKey(null);

        return goal;
    }

    private Event getSecondFirstHalfEvent() {
        Goal goal = new Goal();

        goal.setTime(new Time(Time.Period.FIRST_HALF, 15, 0));
        goal.setTeamKey(new TeamKey("nottingham", "UsushcZr"));
        goal.setPlayerKey(new PlayerKey("sosa-ramon", "GzkUujEi"));
        goal.setAssistPlayerKey(new PlayerKey("sangare-ibrahim", "0zOKXsla"));

        return goal;
    }

    private Event getThirdFirstHalfEvent() {
        Card card = new Card();

        card.setTime(new Time(Time.Period.FIRST_HALF, 38, 0));
        card.setTeamKey(new TeamKey("exeter", "ve14a3l4"));
        card.setColor(Card.Color.YELLOW);
        card.setPlayerKey(new PlayerKey("mcmillan-jack", "YVrybbkq"));

        return card;
    }

    @Test
    public void shouldReturnDefaultFirstHalfEventsIfNotPresent() {
        MatchPage page = new MatchPage(driver, NON_EXISTENT_KEY);

        page.load();

        Collection<Event> events = page.getFirstHalfEvents();
        Assertions.assertNotNull(events);
        Assertions.assertTrue(events.isEmpty());
    }

    @Test
    public void shouldReturnSecondHalfEventsIfPresent() {
        MatchPage page = new MatchPage(driver, KEY);

        page.load();

        Collection<Event> events = page.getSecondHalfEvents();
        Assertions.assertNotNull(events);
        Assertions.assertEquals(13, events.size());
        Assertions.assertTrue(events.contains(getFirstSecondHalfEvent()));
        Assertions.assertTrue(events.contains(getSecondSecondHalfEvent()));
        Assertions.assertTrue(events.contains(getThirdSecondHalfEvent()));
        Assertions.assertTrue(events.contains(getFourthSecondHalfEvent()));
        Assertions.assertTrue(events.contains(getFifthSecondHalfEvent()));
    }

    private Event getFirstSecondHalfEvent() {
        Goal goal = new Goal();

        goal.setTime(new Time(Time.Period.SECOND_HALF, 50, 0));
        goal.setTeamKey(new TeamKey("exeter", "ve14a3l4"));
        goal.setPlayerKey(new PlayerKey("magennis-josh", "IXUzpyWo"));
        goal.setAssistPlayerKey(null);

        return goal;
    }

    private Event getSecondSecondHalfEvent() {
        Substitution substitution = new Substitution();

        substitution.setTime(new Time(Time.Period.SECOND_HALF, 59, 0));
        substitution.setTeamKey(new TeamKey("nottingham", "UsushcZr"));
        substitution.setInPlayerKey(new PlayerKey("sels-matz", "Qqo54dB7"));
        substitution.setOutPlayerKey(new PlayerKey("carlos-miguel", "GhFZjle9"));

        return substitution;
    }

    private Event getThirdSecondHalfEvent() {
        Substitution substitution = new Substitution();

        substitution.setTime(new Time(Time.Period.SECOND_HALF, 59, 0));
        substitution.setTeamKey(new TeamKey("nottingham", "UsushcZr"));
        substitution.setInPlayerKey(new PlayerKey("dominguez-nicolas", "A7jPNAQT"));
        substitution.setOutPlayerKey(new PlayerKey("danilo", "rsMiiNaj"));

        return substitution;
    }

    private Event getFourthSecondHalfEvent() {
        Substitution substitution = new Substitution();

        substitution.setTime(new Time(Time.Period.SECOND_HALF, 71, 0));
        substitution.setTeamKey(new TeamKey("nottingham", "UsushcZr"));
        substitution.setInPlayerKey(new PlayerKey("anderson-elliott", "KdYCbsbE"));
        substitution.setOutPlayerKey(new PlayerKey("yates-ryan", "IwFLkXYE"));

        return substitution;
    }

    private Event getFifthSecondHalfEvent() {
        Card card = new Card();

        card.setTime(new Time(Time.Period.SECOND_HALF, 87, 0));
        card.setTeamKey(new TeamKey("exeter", "ve14a3l4"));
        card.setColor(Card.Color.RED);
        card.setPlayerKey(new PlayerKey("turns-ed", "Kraw6tR1"));

        return card;
    }

    @Test
    public void shouldReturnDefaultSecondHalfEventsIfNotPresent() {
        MatchPage page = new MatchPage(driver, NON_EXISTENT_KEY);

        page.load();

        Collection<Event> events = page.getSecondHalfEvents();
        Assertions.assertNotNull(events);
        Assertions.assertTrue(events.isEmpty());
    }

    @Test
    public void shouldReturnExtraTimeEventsIfPresent() {
        MatchPage page = new MatchPage(driver, KEY);

        page.load();

        Collection<Event> events = page.getExtraTimeEvents();
        Assertions.assertNotNull(events);
        Assertions.assertEquals(3, events.size());
        Assertions.assertTrue(events.contains(getFirstExtraTimeEvent()));
        Assertions.assertTrue(events.contains(getSecondExtraTimeEvent()));
    }

    private Event getFirstExtraTimeEvent() {
        Card card = new Card();

        card.setTime(new Time(Time.Period.EXTRA_TIME, 105, 1));
        card.setTeamKey(new TeamKey("nottingham", "UsushcZr"));
        card.setColor(Card.Color.YELLOW);
        card.setPlayerKey(new PlayerKey("williams-neco", "YVxu86Hs"));

        return card;
    }

    private Event getSecondExtraTimeEvent() {
        Card card = new Card();

        card.setTime(new Time(Time.Period.EXTRA_TIME, 115, 0));
        card.setTeamKey(new TeamKey("exeter", "ve14a3l4"));
        card.setColor(Card.Color.YELLOW);
        card.setPlayerKey(new PlayerKey("yogane-tony", "fP6cbjth"));

        return card;
    }

    @Test
    public void shouldReturnDefaultExtraTimeEventsIfNotPresent() {
        MatchPage page = new MatchPage(driver, NON_EXISTENT_KEY);

        page.load();

        Collection<Event> events = page.getExtraTimeEvents();
        Assertions.assertNotNull(events);
        Assertions.assertTrue(events.isEmpty());
    }

    @Test
    public void shouldReturnPenaltiesEventsIfPresent() {
        MatchPage page = new MatchPage(driver, KEY);

        page.load();

        Collection<Event> events = page.getPenaltiesEvents();
        Assertions.assertNotNull(events);
        Assertions.assertEquals(8, events.size());
        Assertions.assertTrue(events.contains(getFirstPenaltiesEvent()));
        Assertions.assertTrue(events.contains(getSecondPenaltiesEvent()));
    }

    private Event getFirstPenaltiesEvent() {
        Penalty penalty = new Penalty();

        penalty.setTime(new Time(Time.Period.PENALTIES, 1, 0));
        penalty.setTeamKey(new TeamKey("exeter", "ve14a3l4"));
        penalty.setMissed(false);
        penalty.setPlayerKey(new PlayerKey("magennis-josh", "IXUzpyWo"));

        return penalty;
    }

    private Event getSecondPenaltiesEvent() {
        Penalty penalty = new Penalty();

        penalty.setTime(new Time(Time.Period.PENALTIES, 1, 0));
        penalty.setTeamKey(new TeamKey("nottingham", "UsushcZr"));
        penalty.setMissed(false);
        penalty.setPlayerKey(new PlayerKey("wood-chris", "Cp7L7Gro"));

        return penalty;
    }

    @Test
    public void shouldReturnDefaultPenaltiesEventsIfNotPresent() {
        MatchPage page = new MatchPage(driver, NON_EXISTENT_KEY);

        page.load();

        Collection<Event> events = page.getPenaltiesEvents();
        Assertions.assertNotNull(events);
        Assertions.assertTrue(events.isEmpty());
    }

    @Test
    public void shouldReturnNullIfCannotDefineTypeOfEvent() {
        WebDriver mockDriver = Mockito.spy(driver);
        WebElement mockLabelElement = Mockito.mock(WebElement.class);
        WebElement mockEventElement = Mockito.mock(WebElement.class);

        Mockito.doReturn(Arrays.asList(mockLabelElement, mockEventElement)).when(mockDriver).findElements(MatchPage.EVENTS_SELECTOR);

        Mockito.when(mockLabelElement.getAttribute("class")).thenReturn("");
        Mockito.when(mockLabelElement.getText()).thenReturn(MatchPage.PENALTIES_LABEL);

        Mockito.when(mockEventElement.getAttribute("class")).thenReturn(MatchPage.EVENT_CLASS);
        Mockito.when(mockEventElement.getText()).thenReturn("");
        Mockito.doReturn(new LinkedList<>()).when(mockEventElement).findElements(Mockito.any());

        MatchPage page = new MatchPage(mockDriver, KEY);

        page.load();

        Collection<Event> events = page.getPenaltiesEvents();
        Assertions.assertNotNull(events);
        Assertions.assertTrue(events.contains(null));
    }

}