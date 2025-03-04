package com.github.brunomndantas.flashscore.api.logic.services.reportViewer;

import com.github.brunomndantas.flashscore.api.logic.services.scrapService.Report;

public interface IReportViewer {

    public void showProgress(Report report);

    public void showResult(Report report);

}
