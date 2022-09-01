package com.cucumberjunit.www.steps.google;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Assert;

import com.cucumberjunit.www.cucumber.TestContext;
import com.cucumberjunit.www.managers.FileReaderManager;
import com.cucumberjunit.www.pages.GooglePage;
import com.cucumberjunit.www.steps.hooks.Hooks;
import com.cucumberjunit.www.util.Utility;

import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class GoogleSteps {

	Hooks hooks;

	Scenario currentScenario;
	TestContext testContext;

	GooglePage googlePage;
	Map<String, String> record = new HashMap<>();

	private static Logger logger;

	public GoogleSteps(TestContext context, Hooks hooks) {
		this.testContext = context;
		this.hooks = hooks;
		googlePage = testContext.getPageObjectManager().getGooglePage();
		logger = Logger.getLogger(GoogleSteps.class);
		record = new HashMap<String, String>();
		currentScenario = (Scenario) context.getScenarioContext().getContext("currentScenario");
	}

	@Given("I connect to https:\\/\\/www.google.com")
	public void i_connect_to_https_www_google_com() throws InterruptedException {
		logger.info("Connecting " + FileReaderManager.getInstance().getConfigReader().getGoogleURL());
		hooks.getDriver().get(FileReaderManager.getInstance().getConfigReader().getGoogleURL());
		Thread.sleep(3000);
	}

	@Then("I should see Google Home page")
	public void i_should_see_google_home_page() throws IOException {
		logger.info("Verify if Google Home page is displayed");
		currentScenario.attach(Files.readAllBytes(Utility.captureScreenshot(hooks.getDriver(), false).toPath()), "image/png",
				"Google Page");
		Assert.assertTrue("Is Google HomePage displayed?", googlePage.isGooglePaeDisplayed());
	}

	@When("I search for {string}")
	public void i_search_for(String searchText) throws IOException {
		logger.info("Calling googleSearch");
		googlePage.googleSearch(searchText, currentScenario);
	}

	@Then("I should see the search results for {string}")
	public void i_should_see_the_search_results_for(String searchResults) throws IOException {
		logger.info("Calling areGoogleSearchResultsDisplayed");
		currentScenario.attach(Files.readAllBytes(Utility.captureScreenshot(hooks.getDriver(), false).toPath()), "image/png",
				"Google Search Results");
		Assert.fail("No results found!!!");
//		Assert.assertTrue("Are Google Search Results displayed?",
//				googlePage.areGoogleSearchResultsDisplayed(searchResults, currentScenario));
	}
}
