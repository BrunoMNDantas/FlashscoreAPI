package com.github.brunomndantas.flashscore.api.serviceInterface.config;

import com.github.brunomndantas.flashscore.api.dataAccess.*;
import com.github.brunomndantas.flashscore.api.dataAccess.constraintViolationRepository.ConstraintViolationRepository;
import com.github.brunomndantas.flashscore.api.dataAccess.s3Repository.S3Repository;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class RepositoryConfig {

    @Value("${screenshots.directory}")
    protected String screenshotsDirectory;

    @Value("${s3.bucket-name}")
    private String s3BucketName;

    @Value("${s3.region}")
    private String s3Region;

    @Value("${s3.access-key}")
    private String s3AccessKey;

    @Value("${s3.secret-key}")
    private String s3SecretKey;


    @Bean
    public S3Client getS3Client() {
        return S3Client.builder()
                .region(software.amazon.awssdk.regions.Region.of(s3Region))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(s3AccessKey, s3SecretKey)
                ))
                .build();
    }

    @Bean
    public IRepository<SportKey, Sport> sportCacheRepository(S3Client s3Client) {
        return new S3Repository<>(s3Client, s3BucketName, "Sport", Sport.class, Sport::getKey);
    }

    @Bean
    public IRepository<RegionKey, Region> regionCacheRepository(S3Client s3Client) {
        return new S3Repository<>(s3Client, s3BucketName, "Region", Region.class, Region::getKey);
    }

    @Bean
    public IRepository<CompetitionKey, Competition> competitionCacheRepository(S3Client s3Client) {
        return new S3Repository<>(s3Client, s3BucketName, "Competition", Competition.class, Competition::getKey);
    }

    @Bean
    public IRepository<SeasonKey, Season> seasonCacheRepository(S3Client s3Client) {
        return new S3Repository<>(s3Client, s3BucketName, "Season", Season.class, Season::getKey);
    }

    @Bean
    public IRepository<MatchKey, Match> matchCacheRepository(S3Client s3Client) {
        return new S3Repository<>(s3Client, s3BucketName, "Match", Match.class, Match::getKey);
    }

    @Bean
    public IRepository<TeamKey, Team> teamCacheRepository(S3Client s3Client) {
        return new S3Repository<>(s3Client, s3BucketName, "Team", Team.class, Team::getKey);
    }

    @Bean
    public IRepository<PlayerKey, Player> playerCacheRepository(S3Client s3Client) {
        return new S3Repository<>(s3Client, s3BucketName, "Player", Player.class, Player::getKey);
    }

    @Bean
    public IRepository<SportKey, Sport> sportSourceRepository(IDriverPool driverPool) {
        return new SportScrapperRepository(driverPool, screenshotsDirectory);
    }

    @Bean
    public IRepository<RegionKey, Region> regionSourceRepository(IDriverPool driverPool) {
        return new RegionScrapperRepository(driverPool, screenshotsDirectory);
    }

    @Bean
    public IRepository<CompetitionKey, Competition> competitionSourceRepository(IDriverPool driverPool) {
        return new CompetitionScrapperRepository(driverPool, screenshotsDirectory);
    }

    @Bean
    public IRepository<SeasonKey, Season> seasonSourceRepository(IDriverPool driverPool) {
        return new SeasonScrapperRepository(driverPool, screenshotsDirectory);
    }

    @Bean
    public IRepository<MatchKey, Match> matchSourceRepository(IDriverPool driverPool) {
        return new MatchScrapperRepository(driverPool, screenshotsDirectory);
    }

    @Bean
    public IRepository<TeamKey, Team> teamSourceRepository(IDriverPool driverPool) {
        return new TeamScrapperRepository(driverPool, screenshotsDirectory);
    }

    @Bean
    public IRepository<PlayerKey, Player> playerSourceRepository(IDriverPool driverPool) {
        return new PlayerScrapperRepository(driverPool, screenshotsDirectory);
    }

    @Bean
    @Primary
    public IRepository<SportKey, Sport> getSportRepository(
            @Qualifier("sportSourceRepository") IRepository<SportKey, Sport> sourceRepository,
            @Qualifier("sportCacheRepository") IRepository<SportKey, Sport> cacheRepository
    ) {
        sourceRepository = new ConstraintViolationRepository<>(sourceRepository);
        return new CacheRepository<>(cacheRepository, sourceRepository, Sport::getKey);
    }

    @Bean
    @Primary
    public IRepository<RegionKey, Region> getRegionRepository(
            @Qualifier("regionSourceRepository") IRepository<RegionKey, Region> sourceRepository,
            @Qualifier("regionCacheRepository") IRepository<RegionKey, Region> cacheRepository
    ) {
        sourceRepository = new ConstraintViolationRepository<>(sourceRepository);
        return new CacheRepository<>(cacheRepository, sourceRepository, Region::getKey);
    }

    @Bean
    @Primary
    public IRepository<CompetitionKey, Competition> getCompetitionRepository(
            @Qualifier("competitionSourceRepository") IRepository<CompetitionKey, Competition> sourceRepository,
            @Qualifier("competitionCacheRepository") IRepository<CompetitionKey, Competition> cacheRepository
    ) {
        sourceRepository = new ConstraintViolationRepository<>(sourceRepository);
        return new CacheRepository<>(cacheRepository, sourceRepository, Competition::getKey);
    }

    @Bean
    @Primary
    public IRepository<SeasonKey, Season> getSeasonRepository(
            @Qualifier("seasonSourceRepository") IRepository<SeasonKey, Season> sourceRepository,
            @Qualifier("seasonCacheRepository") IRepository<SeasonKey, Season> cacheRepository
    ) {
        sourceRepository = new ConstraintViolationRepository<>(sourceRepository);
        return new CacheRepository<>(cacheRepository, sourceRepository, Season::getKey);
    }

    @Bean
    @Primary
    public IRepository<MatchKey, Match> getMatchRepository(
            @Qualifier("matchSourceRepository") IRepository<MatchKey, Match> sourceRepository,
            @Qualifier("matchCacheRepository") IRepository<MatchKey, Match> cacheRepository
    ) {
        sourceRepository = new ConstraintViolationRepository<>(sourceRepository);
        return new CacheRepository<>(cacheRepository, sourceRepository, Match::getKey);
    }

    @Bean
    @Primary
    public IRepository<TeamKey, Team> getTeamRepository(
            @Qualifier("teamSourceRepository") IRepository<TeamKey, Team> sourceRepository,
            @Qualifier("teamCacheRepository") IRepository<TeamKey, Team> cacheRepository
    ) {
        sourceRepository = new ConstraintViolationRepository<>(sourceRepository);
        return new CacheRepository<>(cacheRepository, sourceRepository, Team::getKey);
    }

    @Bean
    @Primary
    public IRepository<PlayerKey, Player> getPlayerRepository(
            @Qualifier("playerSourceRepository") IRepository<PlayerKey, Player> sourceRepository,
            @Qualifier("playerCacheRepository") IRepository<PlayerKey, Player> cacheRepository
    ) {
        sourceRepository = new ConstraintViolationRepository<>(sourceRepository);
        return new CacheRepository<>(cacheRepository, sourceRepository, Player::getKey);
    }

}
