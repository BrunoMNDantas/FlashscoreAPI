package com.github.brunomndantas.flashscore.api.serviceInterface.config;

import com.github.brunomndantas.flashscore.api.dataAccess.*;
import com.github.brunomndantas.flashscore.api.logic.domain.competition.Competition;
import com.github.brunomndantas.flashscore.api.logic.domain.competition.CompetitionId;
import com.github.brunomndantas.flashscore.api.logic.domain.match.Match;
import com.github.brunomndantas.flashscore.api.logic.domain.match.MatchId;
import com.github.brunomndantas.flashscore.api.logic.domain.player.Player;
import com.github.brunomndantas.flashscore.api.logic.domain.region.Region;
import com.github.brunomndantas.flashscore.api.logic.domain.region.RegionId;
import com.github.brunomndantas.flashscore.api.logic.domain.season.Season;
import com.github.brunomndantas.flashscore.api.logic.domain.season.SeasonId;
import com.github.brunomndantas.flashscore.api.logic.domain.sport.Sport;
import com.github.brunomndantas.flashscore.api.logic.domain.sport.SportId;
import com.github.brunomndantas.flashscore.api.logic.domain.team.Team;
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import com.github.brunomndantas.repository4j.IRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

    @Value("${screenshots.directory}")
    protected String screenshotsDirectory;


    @Bean
    public IRepository<SportId, Sport> getSportRepository(IDriverPool driverPool) {
        return new SportScrapperRepository(driverPool, screenshotsDirectory);
    }

    @Bean
    public IRepository<RegionId, Region> getRegionRepository(IDriverPool driverPool) {
        return new RegionScrapperRepository(driverPool, screenshotsDirectory);
    }

    @Bean
    public IRepository<CompetitionId, Competition> getCompetitionRepository(IDriverPool driverPool) {
        return new CompetitionScrapperRepository(driverPool, screenshotsDirectory);
    }

    @Bean
    public IRepository<SeasonId, Season> getSeasonRepository(IDriverPool driverPool) {
        return new SeasonScrapperRepository(driverPool, screenshotsDirectory);
    }

    @Bean
    public IRepository<MatchId, Match> getMatchRepository(IDriverPool driverPool) {
        return new MatchScrapperRepository(driverPool, screenshotsDirectory);
    }

    @Bean
    public IRepository<String, Team> getTeamRepository(IDriverPool driverPool) {
        return new TeamScrapperRepository(driverPool, screenshotsDirectory);
    }

    @Bean
    public IRepository<String, Player> getPlayerRepository(IDriverPool driverPool) {
        return new PlayerScrapperRepository(driverPool, screenshotsDirectory);
    }

}
