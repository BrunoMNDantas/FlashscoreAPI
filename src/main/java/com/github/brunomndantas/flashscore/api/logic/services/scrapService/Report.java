package com.github.brunomndantas.flashscore.api.logic.services.scrapService;

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
import com.github.brunomndantas.flashscore.api.logic.services.entityScrapper.EntityReport;
import lombok.Getter;

@Getter
public class Report {

    private final EntityReport<SportKey, Sport> sportReport = new EntityReport<>();

    private final EntityReport<RegionKey, Region> regionReport = new EntityReport<>();

    private final EntityReport<CompetitionKey, Competition> competitionReport = new EntityReport<>();

    private final EntityReport<SeasonKey, Season> seasonReport = new EntityReport<>();

    private final EntityReport<MatchKey, Match> matchReport = new EntityReport<>();

    private final EntityReport<TeamKey, Team> teamReport = new EntityReport<>();

    private final EntityReport<PlayerKey, Player> playerReport = new EntityReport<>();

}