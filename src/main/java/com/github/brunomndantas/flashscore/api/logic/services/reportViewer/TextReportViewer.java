package com.github.brunomndantas.flashscore.api.logic.services.reportViewer;

import com.github.brunomndantas.flashscore.api.dataAccess.constraintViolationRepository.ConstraintViolationException;
import com.github.brunomndantas.flashscore.api.logic.services.entityScrapper.EntityReport;
import com.github.brunomndantas.flashscore.api.logic.services.scrapService.Report;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.function.Consumer;

public class TextReportViewer implements IReportViewer {

    public static final String PROGRESS_LABEL = "PROGRESS";
    public static final String RESULT_LABEL = "RESULT";

    public static final String SPORT_LABEL = "SPORT";
    public static final String REGION_LABEL = "REGION";
    public static final String COMPETITION_LABEL = "COMPETITION";
    public static final String SEASON_LABEL = "SEASON";
    public static final String MATCH_LABEL = "MATCH";
    public static final String TEAM_LABEL = "TEAM";
    public static final String PLAYER_LABEL = "PLAYER";


    protected Consumer<String> writer;


    public TextReportViewer(Consumer<String> writer) {
        this.writer = writer;
    }


    @Override
    public void showProgress(Report report) {
        printOutDivider();
        printLabel(PROGRESS_LABEL);

        showEntityReport(SPORT_LABEL, report.getSportReport());
        showEntityReport(REGION_LABEL, report.getRegionReport());
        showEntityReport(COMPETITION_LABEL, report.getCompetitionReport());
        showEntityReport(SEASON_LABEL, report.getSeasonReport());
        showEntityReport(MATCH_LABEL, report.getMatchReport());
        showEntityReport(TEAM_LABEL, report.getTeamReport());
        showEntityReport(PLAYER_LABEL, report.getPlayerReport());

        printOutDivider();
    }

    protected void showEntityReport(String entity, EntityReport<?,?> report) {
        int entitiesToLoad = report.getEntitiesToLoad().size();

        if(entitiesToLoad != 0) {
            int succeededLoads = report.getSucceededLoads().size();
            int failedLoads = report.getFailedLoads().size();
            int totalLoads = succeededLoads + failedLoads;

            String entityName = StringUtils.rightPad(entity, 15);
            String percentage = StringUtils.rightPad(((totalLoads*100)/entitiesToLoad) + "%", 5);
            String loads = StringUtils.rightPad(totalLoads + "", 6) + " of " + StringUtils.leftPad(entitiesToLoad + "", 6);
            String details = StringUtils.rightPad(succeededLoads + " S", 10) + " - " + StringUtils.leftPad(failedLoads + " F", 10);

            print(entityName + " | " + percentage + " | " + loads + " | " + details);
        } else {
            print(entity);
        }
    }

    @Override
    public void showResult(Report report) {
        printOutDivider();
        printLabel(RESULT_LABEL);

        showFinalEntityReport(SPORT_LABEL, report.getSportReport());
        printInDivider();
        showFinalEntityReport(REGION_LABEL, report.getRegionReport());
        printInDivider();
        showFinalEntityReport(COMPETITION_LABEL, report.getCompetitionReport());
        printInDivider();
        showFinalEntityReport(SEASON_LABEL, report.getSeasonReport());
        printInDivider();
        showFinalEntityReport(MATCH_LABEL, report.getMatchReport());
        printInDivider();
        showFinalEntityReport(TEAM_LABEL, report.getTeamReport());
        printInDivider();
        showFinalEntityReport(PLAYER_LABEL, report.getPlayerReport());

        printOutDivider();
    }

    protected <K,E> void showFinalEntityReport(String name, EntityReport<K,E> report) {
        print(name);

        print(1, "Entities to Load: " + report.getEntitiesToLoad().size());
        print(1, "Entities Succeeded: " + report.getSucceededLoads().size());
        print(1, "Entities Failed: " + report.getFailedLoads().size());

        print(1, "Failures:");
        Map<K,Exception> failures = report.getFailedLoads();
        Exception e;
        for(K key : failures.keySet()) {
            e = failures.get(key);

            if(!(e instanceof ConstraintViolationException)) {
                print(2, key.toString());
                print(2, e.getMessage());
            }
        }

        print(1, "Constraint Violations:");
        for(K key : failures.keySet()) {
            e = failures.get(key);

            if(e instanceof ConstraintViolationException) {
                print(2, key.toString());
                print(2, e.getMessage());
            }
        }
    }

    protected void printOutDivider() {
        print("===============================================================================================================================");
    }

    protected void printInDivider() {
        print("-------------------------------------------------------------------------------------------------------------------------------");
    }

    protected void printLabel(String label) {
        label = StringUtils.leftPad(label, label.length()+50, " ");
        print(label);
    }

    protected void print(String message) {
        print(0, message);
    }

    protected void print(int indentation, String message) {
        message = StringUtils.leftPad(message, message.length()+indentation, "\t");
        writer.accept(message);
    }

}