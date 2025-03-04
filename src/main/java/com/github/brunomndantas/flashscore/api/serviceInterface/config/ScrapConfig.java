package com.github.brunomndantas.flashscore.api.serviceInterface.config;

import com.github.brunomndantas.flashscore.api.logic.domain.competition.Competition;
import com.github.brunomndantas.flashscore.api.logic.domain.competition.CompetitionKey;
import com.github.brunomndantas.flashscore.api.logic.domain.match.Match;
import com.github.brunomndantas.flashscore.api.logic.domain.match.MatchKey;
import com.github.brunomndantas.flashscore.api.logic.domain.player.Player;
import com.github.brunomndantas.flashscore.api.logic.domain.player.PlayerKey;
import com.github.brunomndantas.flashscore.api.logic.domain.region.Region;
import com.github.brunomndantas.flashscore.api.logic.domain.region.RegionKey;
import com.github.brunomndantas.flashscore.api.logic.domain.season.Season;
import com.github.brunomndantas.flashscore.api.logic.domain.season.SeasonKey;
import com.github.brunomndantas.flashscore.api.logic.domain.sport.Sport;
import com.github.brunomndantas.flashscore.api.logic.domain.sport.SportKey;
import com.github.brunomndantas.flashscore.api.logic.domain.team.Team;
import com.github.brunomndantas.flashscore.api.logic.domain.team.TeamKey;
import com.github.brunomndantas.flashscore.api.logic.services.entityScrapper.EntityScrapper;
import com.github.brunomndantas.flashscore.api.logic.services.reportViewer.IReportViewer;
import com.github.brunomndantas.flashscore.api.logic.services.reportViewer.TextReportViewer;
import com.github.brunomndantas.flashscore.api.logic.services.scrapService.*;
import com.github.brunomndantas.repository4j.IRepository;
import com.github.brunomndantas.tpl4j.task.pool.TaskPool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import java.util.List;

@Configuration
public class ScrapConfig {

    private static final TaskPool TASK_POOL = new TaskPool();


    @Value("${sportsIds}")
    protected List<String> sportIds;

    @Value("${scrap.maxSportsToLoad}")
    protected int maxSportsToLoad;
    @Value("${scrap.maxRegionsPerSport}")
    protected int maxRegionsPerSport;
    @Value("${scrap.maxCompetitionsPerRegion}")
    protected int maxCompetitionsPerRegion;
    @Value("${scrap.maxSeasonsPerCompetition}")
    protected int maxSeasonsPerCompetition;
    @Value("${scrap.maxMatchesPerSeason}")
    protected int maxMatchesPerSeason;
    @Value("${scrap.maxTeamsPerSeason}")
    protected int maxTeamsPerSeason;
    @Value("${scrap.maxPlayersPerMatch}")
    protected int maxPlayersPerMatch;
    @Value("${scrap.maxPlayersPerTeam}")
    protected int maxPlayersPerTeam;


    @PreDestroy
    public void cleanUp() {
        TASK_POOL.close();
    }

    @Bean
    public TaskPool getTaskPool() {
        return TASK_POOL;
    }

    @Bean
    public IScrapService getScrapService(
            IRepository<SportKey, Sport> sportRepository,
            IRepository<RegionKey, Region> regionRepository,
            IRepository<CompetitionKey, Competition> competitionRepository,
            IRepository<SeasonKey, Season> seasonRepository,
            IRepository<MatchKey, Match> matchRepository,
            IRepository<TeamKey, Team> teamRepository,
            IRepository<PlayerKey, Player> playerRepository,
            TaskPool taskPool
    ) {
        IScrapService sportScrapService = new SportScrapService(new EntityScrapper<>(sportRepository, taskPool), sportIds, maxSportsToLoad);
        IScrapService regionScrapService = new RegionScrapService(new EntityScrapper<>(regionRepository, taskPool), maxRegionsPerSport);
        IScrapService competitionScrapService = new CompetitionScrapService(new EntityScrapper<>(competitionRepository, taskPool), maxCompetitionsPerRegion);
        IScrapService seasonScrapService = new SeasonScrapService(new EntityScrapper<>(seasonRepository, taskPool), maxSeasonsPerCompetition);
        IScrapService matchScrapService = new MatchScrapService(new EntityScrapper<>(matchRepository, taskPool), maxMatchesPerSeason);
        IScrapService teamScrapService = new TeamScrapService(new EntityScrapper<>(teamRepository, taskPool), maxTeamsPerSeason);
        IScrapService playerScrapService = new PlayerScrapService(new EntityScrapper<>(playerRepository, taskPool), maxPlayersPerMatch, maxPlayersPerTeam);

        return new ScrapService(
                sportScrapService,
                regionScrapService,
                competitionScrapService,
                seasonScrapService,
                matchScrapService,
                teamScrapService,
                playerScrapService
        );
    }

    @Bean
    public IReportViewer getReportViewer() {
        return new TextReportViewer(System.out::println);
    }

}
