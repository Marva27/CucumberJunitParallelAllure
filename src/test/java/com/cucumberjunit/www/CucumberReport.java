package com.cucumberjunit.www;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.presentation.PresentationMode;
import net.masterthought.cucumber.sorting.SortingMethod;

public class CucumberReport {

	@Test
	public void generateReport() {
		File reportOutputDirectory = new File("target/advnced-cucumber-report");

		List<String> jsonFiles = new ArrayList<>();
		jsonFiles.add(".\\target\\cucumber-reports\\cucumber.json");

		String buildNumber = "1";
		String projectName = "Transistion FI";
		Configuration configuration = new Configuration(reportOutputDirectory, projectName);
		configuration.setBuildNumber(buildNumber);
		configuration.addClassifications("OS", "Windows");
		if (System.getProperty("environment").equals("Stage") || System.getProperty("environment").equals("stage"))
			configuration.addClassifications("Environment", "Stage");
		else if (System.getProperty("environment").equals("Test") || System.getProperty("environment").equals("test"))
			configuration.addClassifications("Environment", "Test");
		configuration.setSortingMethod(SortingMethod.NATURAL);
		configuration.addPresentationModes(PresentationMode.EXPAND_ALL_STEPS);
		configuration.setTrendsStatsFile(new File("target/test-classes/demo-trends.json"));

		ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
		reportBuilder.generateReports();
	}

}
