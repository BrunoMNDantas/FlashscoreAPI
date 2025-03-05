package com.github.brunomndantas.flashscore.api.serviceInterface.config;

import com.github.brunomndantas.flashscore.api.dataAccess.*;
import com.github.brunomndantas.flashscore.api.dataAccess.constraintViolationRepository.ConstraintViolationRepository;
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
import com.github.brunomndantas.flashscore.api.transversal.driverPool.IDriverPool;
import com.github.brunomndantas.repository4j.IRepository;
import com.github.brunomndantas.repository4j.cache.CacheRepository;
import com.github.brunomndantas.repository4j.memory.MemoryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

    @Value("${screenshots.directory}")
    protected String screenshotsDirectory;

    @PersistenceContext
    private EntityManager entityManager;


    @Bean
    public IRepository<SportKey, Sport> getSportRepository(IDriverPool driverPool) {
        IRepository<SportKey, Sport> sourceRepository = new SportScrapperRepository(driverPool, screenshotsDirectory);
        sourceRepository = new ConstraintViolationRepository<>(sourceRepository);
        IRepository<SportKey, Sport> cacheRepository = new MemoryRepository<>(Sport::getKey);

        return new CacheRepository<>(sourceRepository, cacheRepository, Sport::getKey);
    }

    @Bean
    public IRepository<RegionKey, Region> getRegionRepository(IDriverPool driverPool) {
        IRepository<RegionKey, Region> sourceRepository = new RegionScrapperRepository(driverPool, screenshotsDirectory);
        sourceRepository = new ConstraintViolationRepository<>(sourceRepository);
        IRepository<RegionKey, Region> cacheRepository = new MemoryRepository<>(Region::getKey);

        return new CacheRepository<>(sourceRepository, cacheRepository, Region::getKey);
    }

    @Bean
    public IRepository<CompetitionKey, Competition> getCompetitionRepository(IDriverPool driverPool) {
        IRepository<CompetitionKey, Competition> sourceRepository = new CompetitionScrapperRepository(driverPool, screenshotsDirectory);
        sourceRepository = new ConstraintViolationRepository<>(sourceRepository);
        IRepository<CompetitionKey, Competition> cacheRepository = new MemoryRepository<>(Competition::getKey);

        return new CacheRepository<>(sourceRepository, cacheRepository, Competition::getKey);
    }

    @Bean
    public IRepository<SeasonKey, Season> getSeasonRepository(IDriverPool driverPool) {
        IRepository<SeasonKey, Season> sourceRepository = new SeasonScrapperRepository(driverPool, screenshotsDirectory);
        sourceRepository = new ConstraintViolationRepository<>(sourceRepository);
        IRepository<SeasonKey, Season> cacheRepository = new MemoryRepository<>(Season::getKey);

        return new CacheRepository<>(sourceRepository, cacheRepository, Season::getKey);
    }

    @Bean
    public IRepository<MatchKey, Match> getMatchRepository(IDriverPool driverPool) {
        IRepository<MatchKey, Match> sourceRepository = new MatchScrapperRepository(driverPool, screenshotsDirectory);
        sourceRepository = new ConstraintViolationRepository<>(sourceRepository);
        IRepository<MatchKey, Match> cacheRepository = new MemoryRepository<>(Match::getKey);

        return new CacheRepository<>(sourceRepository, cacheRepository, Match::getKey);
    }

    @Bean
    public IRepository<TeamKey, Team> getTeamRepository(IDriverPool driverPool) {
        IRepository<TeamKey, Team> sourceRepository = new TeamScrapperRepository(driverPool, screenshotsDirectory);
        sourceRepository = new ConstraintViolationRepository<>(sourceRepository);
        IRepository<TeamKey, Team> cacheRepository = new MemoryRepository<>(Team::getKey);

        return new CacheRepository<>(sourceRepository, cacheRepository, Team::getKey);
    }

    @Bean
    public IRepository<PlayerKey, Player> getPlayerRepository(IDriverPool driverPool) {
        IRepository<PlayerKey, Player> sourceRepository = new PlayerScrapperRepository(driverPool, screenshotsDirectory);
        sourceRepository = new ConstraintViolationRepository<>(sourceRepository);
        IRepository<PlayerKey, Player> cacheRepository = new MemoryRepository<>(Player::getKey);

        return new CacheRepository<>(sourceRepository, cacheRepository, Player::getKey);
    }

}
