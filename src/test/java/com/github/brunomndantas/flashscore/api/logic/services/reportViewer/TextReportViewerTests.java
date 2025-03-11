package com.github.brunomndantas.flashscore.api.logic.services.reportViewer;

import com.github.brunomndantas.flashscore.api.dataAccess.constraintViolationRepository.ConstraintViolationException;
import com.github.brunomndantas.flashscore.api.logic.domain.competition.CompetitionKey;
import com.github.brunomndantas.flashscore.api.logic.domain.match.MatchKey;
import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import com.github.brunomndantas.flashscore.api.logic.domain.region.RegionKey;
import com.github.brunomndantas.flashscore.api.logic.domain.season.SeasonKey;
import com.github.brunomndantas.flashscore.api.logic.domain.sport.SportKey;
import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;
import com.github.brunomndantas.flashscore.api.logic.services.entityScrapper.EntityReport;
import com.github.brunomndantas.flashscore.api.logic.services.scrapService.Report;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.LinkedList;

@SpringBootTest
public class TextReportViewerTests {

    @Test
    public void shouldShowSportProgress() {
        SportKey key = new SportKey("A");
        Report report = new Report();
        String entityLabel = TextReportViewer.SPORT_LABEL;

        testEntityProgress(report, report.getSportReport(), entityLabel, key);
    }

    @Test
    public void shouldShowRegionProgress() {
        RegionKey key = new RegionKey("A", "A");
        Report report = new Report();
        String entityLabel = TextReportViewer.REGION_LABEL;

        testEntityProgress(report, report.getRegionReport(), entityLabel, key);
    }

    @Test
    public void shouldShowCompetitionProgress() {
        CompetitionKey key = new CompetitionKey("A", "A", "A");
        Report report = new Report();
        String entityLabel = TextReportViewer.COMPETITION_LABEL;

        testEntityProgress(report, report.getCompetitionReport(), entityLabel, key);
    }

    @Test
    public void shouldShowSeasonProgress() {
        SeasonKey key = new SeasonKey("A", "A", "A", "A");
        Report report = new Report();
        String entityLabel = TextReportViewer.SEASON_LABEL;

        testEntityProgress(report, report.getSeasonReport(), entityLabel, key);
    }

    @Test
    public void shouldShowMatchProgress() {
        MatchKey key = new MatchKey("A");
        Report report = new Report();
        String entityLabel = TextReportViewer.MATCH_LABEL;

        testEntityProgress(report, report.getMatchReport(), entityLabel, key);
    }

    @Test
    public void shouldShowTeamProgress() {
        TeamKey key = new TeamKey("A", "A");
        Report report = new Report();
        String entityLabel = TextReportViewer.TEAM_LABEL;

        testEntityProgress(report, report.getTeamReport(), entityLabel, key);
    }

    @Test
    public void shouldShowPlayerProgress() {
        PlayerKey key = new PlayerKey("A", "A");
        Report report = new Report();
        String entityLabel = TextReportViewer.PLAYER_LABEL;

        testEntityProgress(report, report.getPlayerReport(), entityLabel, key);
    }

    public <K,E> void testEntityProgress(Report report, EntityReport<K,E> entityReport, String entityLabel, K key) {
        Collection<String> messages = new LinkedList<>();
        TextReportViewer viewer = new TextReportViewer(messages::add);

        entityReport.addEntityToLoad(key);
        entityReport.addSucceededLoad(key, null);

        viewer.showProgress(report);

        Assertions.assertTrue(messages.stream().anyMatch(message -> message.contains(TextReportViewer.PROGRESS_LABEL)));
        Assertions.assertTrue(messages.stream().anyMatch(message -> message.contains(entityLabel)));
    }

    @Test
    public void shouldShowSportResult() {
        SportKey keyA = new SportKey("A");
        SportKey keyB = new SportKey("B");
        Report report = new Report();
        String entityLabel = TextReportViewer.SPORT_LABEL;

        testEntityResult(report, report.getSportReport(), entityLabel, keyA, keyB);
    }

    @Test
    public void shouldShowRegionResult() {
        RegionKey keyA = new RegionKey("A", "A");
        RegionKey keyB = new RegionKey("A", "B");
        Report report = new Report();
        String entityLabel = TextReportViewer.REGION_LABEL;

        testEntityResult(report, report.getRegionReport(), entityLabel, keyA, keyB);
    }

    @Test
    public void shouldShowCompetitionResult() {
        CompetitionKey keyA = new CompetitionKey("A", "A", "A");
        CompetitionKey keyB = new CompetitionKey("A", "A", "B");
        Report report = new Report();
        String entityLabel = TextReportViewer.COMPETITION_LABEL;

        testEntityResult(report, report.getCompetitionReport(), entityLabel, keyA, keyB);
    }

    @Test
    public void shouldShowSeasonResult() {
        SeasonKey keyA = new SeasonKey("A", "A", "A", "A");
        SeasonKey keyB = new SeasonKey("A", "A", "A", "B");
        Report report = new Report();
        String entityLabel = TextReportViewer.SEASON_LABEL;

        testEntityResult(report, report.getSeasonReport(), entityLabel, keyA, keyB);
    }

    @Test
    public void shouldShowMatchResult() {
        MatchKey keyA = new MatchKey("A");
        MatchKey keyB = new MatchKey("B");
        Report report = new Report();
        String entityLabel = TextReportViewer.MATCH_LABEL;

        testEntityResult(report, report.getMatchReport(), entityLabel, keyA, keyB);
    }

    @Test
    public void shouldShowTeamResult() {
        TeamKey keyA = new TeamKey("A", "A");
        TeamKey keyB = new TeamKey("A", "B");
        Report report = new Report();
        String entityLabel = TextReportViewer.TEAM_LABEL;

        testEntityResult(report, report.getTeamReport(), entityLabel, keyA, keyB);
    }

    @Test
    public void shouldShowPlayerResult() {
        PlayerKey keyA = new PlayerKey("A", "A");
        PlayerKey keyB = new PlayerKey("A", "B");
        Report report = new Report();
        String entityLabel = TextReportViewer.PLAYER_LABEL;

        testEntityResult(report, report.getPlayerReport(), entityLabel, keyA, keyB);
    }

    public <K,E> void testEntityResult(Report report, EntityReport<K,E> entityReport, String entityLabel, K keyA, K keyB) {
        Collection<String> messages = new LinkedList<>();
        TextReportViewer viewer = new TextReportViewer(messages::add);

        entityReport.addEntityToLoad(keyA);
        entityReport.addEntityToLoad(keyB);
        entityReport.addFailedLoad(keyA, new Exception("EXCEPTION A"));
        entityReport.addFailedLoad(keyB, new ConstraintViolationException("EXCEPTION B"));

        viewer.showResult(report);

        Assertions.assertTrue(messages.stream().anyMatch(message -> message.contains(TextReportViewer.RESULT_LABEL)));
        Assertions.assertTrue(messages.stream().anyMatch(message -> message.contains(entityLabel)));
    }

}