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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    public IRepository<SportKey, Sport> getSportRepository(IDriverPool driverPool, S3Client s3Client) {
        IRepository<SportKey, Sport> sourceRepository = new SportScrapperRepository(driverPool, screenshotsDirectory);
        sourceRepository = new ConstraintViolationRepository<>(sourceRepository);
        IRepository<SportKey, Sport> cacheRepository = new S3Repository<>(s3Client, s3BucketName, "Sport", Sport.class, Sport::getKey);

        return new CacheRepository<>(cacheRepository, sourceRepository, Sport::getKey);
    }

    @Bean
    public IRepository<RegionKey, Region> getRegionRepository(IDriverPool driverPool, S3Client s3Client) {
        IRepository<RegionKey, Region> sourceRepository = new RegionScrapperRepository(driverPool, screenshotsDirectory);
        sourceRepository = new ConstraintViolationRepository<>(sourceRepository);
        IRepository<RegionKey, Region> cacheRepository = new S3Repository<>(s3Client, s3BucketName, "Region", Region.class, Region::getKey);

        return new CacheRepository<>(cacheRepository, sourceRepository, Region::getKey);
    }

    @Bean
    public IRepository<CompetitionKey, Competition> getCompetitionRepository(IDriverPool driverPool, S3Client s3Client) {
        IRepository<CompetitionKey, Competition> sourceRepository = new CompetitionScrapperRepository(driverPool, screenshotsDirectory);
        sourceRepository = new ConstraintViolationRepository<>(sourceRepository);
        IRepository<CompetitionKey, Competition> cacheRepository = new S3Repository<>(s3Client, s3BucketName, "Competition", Competition.class, Competition::getKey);

        return new CacheRepository<>(cacheRepository, sourceRepository, Competition::getKey);
    }

    @Bean
    public IRepository<SeasonKey, Season> getSeasonRepository(IDriverPool driverPool, S3Client s3Client) {
        IRepository<SeasonKey, Season> sourceRepository = new SeasonScrapperRepository(driverPool, screenshotsDirectory);
        sourceRepository = new ConstraintViolationRepository<>(sourceRepository);
        IRepository<SeasonKey, Season> cacheRepository = new S3Repository<>(s3Client, s3BucketName, "Season", Season.class, Season::getKey);

        return new CacheRepository<>(cacheRepository, sourceRepository, Season::getKey);
    }

    @Bean
    public IRepository<MatchKey, Match> getMatchRepository(IDriverPool driverPool, S3Client s3Client) {
        IRepository<MatchKey, Match> sourceRepository = new MatchScrapperRepository(driverPool, screenshotsDirectory);
        sourceRepository = new ConstraintViolationRepository<>(sourceRepository);
        IRepository<MatchKey, Match> cacheRepository = new S3Repository<>(s3Client, s3BucketName, "Match", Match.class, Match::getKey);

        return new CacheRepository<>(cacheRepository, sourceRepository, Match::getKey);
    }

    @Bean
    public IRepository<TeamKey, Team> getTeamRepository(IDriverPool driverPool, S3Client s3Client) {
        IRepository<TeamKey, Team> sourceRepository = new TeamScrapperRepository(driverPool, screenshotsDirectory);
        sourceRepository = new ConstraintViolationRepository<>(sourceRepository);
        IRepository<TeamKey, Team> cacheRepository = new S3Repository<>(s3Client, s3BucketName, "Team", Team.class, Team::getKey);

        return new CacheRepository<>(cacheRepository, sourceRepository, Team::getKey);
    }

    @Bean
    public IRepository<PlayerKey, Player> getPlayerRepository(IDriverPool driverPool, S3Client s3Client) {
        IRepository<PlayerKey, Player> sourceRepository = new PlayerScrapperRepository(driverPool, screenshotsDirectory);
        sourceRepository = new ConstraintViolationRepository<>(sourceRepository);
        IRepository<PlayerKey, Player> cacheRepository = new S3Repository<>(s3Client, s3BucketName, "Player", Player.class, Player::getKey);

        return new CacheRepository<>(cacheRepository, sourceRepository, Player::getKey);
    }

}
