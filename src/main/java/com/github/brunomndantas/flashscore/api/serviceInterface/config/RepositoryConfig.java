package com.github.brunomndantas.flashscore.api.serviceInterface.config;

import com.github.brunomndantas.flashscore.api.dataAccess.constraintViolationRepository.ConstraintViolationRepository;
import com.github.brunomndantas.flashscore.api.dataAccess.dtoRepository.DTORepository;
import com.github.brunomndantas.flashscore.api.dataAccess.dtos.competition.CompetitionDTO;
import com.github.brunomndantas.flashscore.api.dataAccess.dtos.competition.CompetitionKeyDTO;
import com.github.brunomndantas.flashscore.api.dataAccess.dtos.match.MatchDTO;
import com.github.brunomndantas.flashscore.api.dataAccess.dtos.match.MatchKeyDTO;
import com.github.brunomndantas.flashscore.api.dataAccess.dtos.player.PlayerDTO;
import com.github.brunomndantas.flashscore.api.dataAccess.dtos.player.PlayerKeyDTO;
import com.github.brunomndantas.flashscore.api.dataAccess.dtos.region.RegionDTO;
import com.github.brunomndantas.flashscore.api.dataAccess.dtos.region.RegionKeyDTO;
import com.github.brunomndantas.flashscore.api.dataAccess.dtos.season.SeasonDTO;
import com.github.brunomndantas.flashscore.api.dataAccess.dtos.season.SeasonKeyDTO;
import com.github.brunomndantas.flashscore.api.dataAccess.dtos.sport.SportDTO;
import com.github.brunomndantas.flashscore.api.dataAccess.dtos.sport.SportKeyDTO;
import com.github.brunomndantas.flashscore.api.dataAccess.dtos.team.TeamDTO;
import com.github.brunomndantas.flashscore.api.dataAccess.dtos.team.TeamKeyDTO;
import com.github.brunomndantas.flashscore.api.dataAccess.s3Repository.S3Repository;
import com.github.brunomndantas.flashscore.api.dataAccess.scrapperRepository.*;
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
        IRepository<SportKeyDTO, SportDTO> repository =
                new S3Repository<>(s3Client, s3BucketName, "Sport", SportDTO.class, SportDTO::getKey);
        return new DTORepository<>(repository, SportKeyDTO::new, SportDTO::new, SportDTO::toDomainEntity);
    }

    @Bean
    public IRepository<RegionKey, Region> regionCacheRepository(S3Client s3Client) {
        IRepository<RegionKeyDTO, RegionDTO> repository =
                new S3Repository<>(s3Client, s3BucketName, "Region", RegionDTO.class, RegionDTO::getKey);
        return new DTORepository<>(repository, RegionKeyDTO::new, RegionDTO::new, RegionDTO::toDomainEntity);
    }

    @Bean
    public IRepository<CompetitionKey, Competition> competitionCacheRepository(S3Client s3Client) {
        IRepository<CompetitionKeyDTO, CompetitionDTO> repository =
                new S3Repository<>(s3Client, s3BucketName, "Competition", CompetitionDTO.class, CompetitionDTO::getKey);
        return new DTORepository<>(repository, CompetitionKeyDTO::new, CompetitionDTO::new, CompetitionDTO::toDomainEntity);
    }

    @Bean
    public IRepository<SeasonKey, Season> seasonCacheRepository(S3Client s3Client) {
        IRepository<SeasonKeyDTO, SeasonDTO> repository =
                new S3Repository<>(s3Client, s3BucketName, "Season", SeasonDTO.class, SeasonDTO::getKey);
        return new DTORepository<>(repository, SeasonKeyDTO::new, SeasonDTO::new, SeasonDTO::toDomainEntity);
    }

    @Bean
    public IRepository<MatchKey, Match> matchCacheRepository(S3Client s3Client) {
        IRepository<MatchKeyDTO, MatchDTO> repository =
                new S3Repository<>(s3Client, s3BucketName, "Match", MatchDTO.class, MatchDTO::getKey);
        return new DTORepository<>(repository, MatchKeyDTO::new, MatchDTO::new, MatchDTO::toDomainEntity);
    }

    @Bean
    public IRepository<TeamKey, Team> teamCacheRepository(S3Client s3Client) {
        IRepository<TeamKeyDTO, TeamDTO> repository =
                new S3Repository<>(s3Client, s3BucketName, "Team", TeamDTO.class, TeamDTO::getKey);
        return new DTORepository<>(repository, TeamKeyDTO::new, TeamDTO::new, TeamDTO::toDomainEntity);
    }

    @Bean
    public IRepository<PlayerKey, Player> playerCacheRepository(S3Client s3Client) {
        IRepository<PlayerKeyDTO, PlayerDTO> repository =
                new S3Repository<>(s3Client, s3BucketName, "Player", PlayerDTO.class, PlayerDTO::getKey);
        return new DTORepository<>(repository, PlayerKeyDTO::new, PlayerDTO::new, PlayerDTO::toDomainEntity);
    }

    @Bean
    public IRepository<SportKey, Sport> sportSourceRepository(IDriverPool driverPool) {
        IRepository<SportKey, Sport> repository = new SportScrapperRepository(driverPool, screenshotsDirectory);
        return new ConstraintViolationRepository<>(repository, SportDTO::new);
    }

    @Bean
    public IRepository<RegionKey, Region> regionSourceRepository(IDriverPool driverPool) {
        IRepository<RegionKey, Region> repository = new RegionScrapperRepository(driverPool, screenshotsDirectory);
        return new ConstraintViolationRepository<>(repository, RegionDTO::new);
    }

    @Bean
    public IRepository<CompetitionKey, Competition> competitionSourceRepository(IDriverPool driverPool) {
        IRepository<CompetitionKey, Competition> repository = new CompetitionScrapperRepository(driverPool, screenshotsDirectory);
        return new ConstraintViolationRepository<>(repository, CompetitionDTO::new);
    }

    @Bean
    public IRepository<SeasonKey, Season> seasonSourceRepository(IDriverPool driverPool) {
        IRepository<SeasonKey, Season> repository = new SeasonScrapperRepository(driverPool, screenshotsDirectory);
        return new ConstraintViolationRepository<>(repository, SeasonDTO::new);
    }

    @Bean
    public IRepository<MatchKey, Match> matchSourceRepository(IDriverPool driverPool) {
        IRepository<MatchKey, Match> repository = new MatchScrapperRepository(driverPool, screenshotsDirectory);
        return new ConstraintViolationRepository<>(repository, MatchDTO::new);
    }

    @Bean
    public IRepository<TeamKey, Team> teamSourceRepository(IDriverPool driverPool) {
        IRepository<TeamKey, Team> repository = new TeamScrapperRepository(driverPool, screenshotsDirectory);
        return new ConstraintViolationRepository<>(repository, TeamDTO::new);

    }

    @Bean
    public IRepository<PlayerKey, Player> playerSourceRepository(IDriverPool driverPool) {
        IRepository<PlayerKey, Player> repository = new PlayerScrapperRepository(driverPool, screenshotsDirectory);
        return new ConstraintViolationRepository<>(repository, PlayerDTO::new);
    }

    @Bean
    @Primary
    public IRepository<SportKey, Sport> getSportRepository(
            @Qualifier("sportSourceRepository") IRepository<SportKey, Sport> sourceRepository,
            @Qualifier("sportCacheRepository") IRepository<SportKey, Sport> cacheRepository
    ) {
        return new CacheRepository<>(cacheRepository, sourceRepository, Sport::getKey);
    }

    @Bean
    @Primary
    public IRepository<RegionKey, Region> getRegionRepository(
            @Qualifier("regionSourceRepository") IRepository<RegionKey, Region> sourceRepository,
            @Qualifier("regionCacheRepository") IRepository<RegionKey, Region> cacheRepository
    ) {
        return new CacheRepository<>(cacheRepository, sourceRepository, Region::getKey);
    }

    @Bean
    @Primary
    public IRepository<CompetitionKey, Competition> getCompetitionRepository(
            @Qualifier("competitionSourceRepository") IRepository<CompetitionKey, Competition> sourceRepository,
            @Qualifier("competitionCacheRepository") IRepository<CompetitionKey, Competition> cacheRepository
    ) {
        return new CacheRepository<>(cacheRepository, sourceRepository, Competition::getKey);
    }

    @Bean
    @Primary
    public IRepository<SeasonKey, Season> getSeasonRepository(
            @Qualifier("seasonSourceRepository") IRepository<SeasonKey, Season> sourceRepository,
            @Qualifier("seasonCacheRepository") IRepository<SeasonKey, Season> cacheRepository
    ) {
        return new CacheRepository<>(cacheRepository, sourceRepository, Season::getKey);
    }

    @Bean
    @Primary
    public IRepository<MatchKey, Match> getMatchRepository(
            @Qualifier("matchSourceRepository") IRepository<MatchKey, Match> sourceRepository,
            @Qualifier("matchCacheRepository") IRepository<MatchKey, Match> cacheRepository
    ) {
        return new CacheRepository<>(cacheRepository, sourceRepository, Match::getKey);
    }

    @Bean
    @Primary
    public IRepository<TeamKey, Team> getTeamRepository(
            @Qualifier("teamSourceRepository") IRepository<TeamKey, Team> sourceRepository,
            @Qualifier("teamCacheRepository") IRepository<TeamKey, Team> cacheRepository
    ) {
        return new CacheRepository<>(cacheRepository, sourceRepository, Team::getKey);
    }

    @Bean
    @Primary
    public IRepository<PlayerKey, Player> getPlayerRepository(
            @Qualifier("playerSourceRepository") IRepository<PlayerKey, Player> sourceRepository,
            @Qualifier("playerCacheRepository") IRepository<PlayerKey, Player> cacheRepository
    ) {
        return new CacheRepository<>(cacheRepository, sourceRepository, Player::getKey);
    }

}
