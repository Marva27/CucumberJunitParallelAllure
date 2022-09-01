package com.cucumberjunit.www;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.runner.RunWith;

import com.cucumberjunit.www.util.ReportGenerator;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/features", 
		glue = "com.cucumberjunit.www.steps", 
		dryRun = false,
		tags = "",
		plugin = {"io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
				"json:target/cucumber-reports/cucumber.json"})
public class JunitTest {

	@AfterClass
	public static void generateReport() throws IOException {
		System.out.println("Generating report....");
		ReportGenerator report = new ReportGenerator();
		report.generateReport();
		System.out.println("Done!!!!");
	}
}
