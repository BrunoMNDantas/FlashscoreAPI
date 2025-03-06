package com.github.brunomndantas.flashscore.api;

import com.github.brunomndantas.flashscore.api.logic.services.reportViewer.IReportViewer;
import com.github.brunomndantas.flashscore.api.logic.services.scrapService.IScrapService;
import com.github.brunomndantas.flashscore.api.logic.services.scrapService.Report;
import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.task.factory.TaskFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class FlashscoreScrapper implements CommandLineRunner {

	private boolean run;
	private long reportInterval;
	private IScrapService scrapService;
	private IReportViewer reportViewer;


	public FlashscoreScrapper(
			@Value("${scrap.run}") boolean run,
			@Value("${scrap.reportInterval}") long reportInterval,
			IScrapService scrapService,
			IReportViewer reportViewer
	) {
		this.run = run;
		this.reportInterval = reportInterval;
		this.scrapService = scrapService;
		this.reportViewer = reportViewer;
	}


	@Override
	public void run(String... args) throws Exception {
		if(run) {
			run();
		}
	}

	private void run() throws InterruptedException {
		Report report = new Report();
		Task<Void> scrapTask = TaskFactory.createAndStart(() -> scrapService.scrap(report));

		while(!scrapTask.getFinishedEvent().hasFired()) {
			reportViewer.showProgress(report);
			Thread.sleep(reportInterval);
		}

		reportViewer.showProgress(report);
		reportViewer.showResult(report);
	}

}