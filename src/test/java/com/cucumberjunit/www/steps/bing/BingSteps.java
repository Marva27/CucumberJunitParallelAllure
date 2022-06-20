package com.cucumberjunit.www.steps.bing;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Assert;

import com.cucumberjunit.www.cucumber.TestContext;
import com.cucumberjunit.www.managers.FileReaderManager;
import com.cucumberjunit.www.pages.BingPage;
import com.cucumberjunit.www.steps.hooks.Hooks;
import com.cucumberjunit.www.util.Utility;

import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class BingSteps {
	
	private Hooks hooks;

	Scenario currentScenario;
	TestContext testContext;

	BingPage bingPage;
	Map<String, String> record = new HashMap<>();

	private static Logger logger;

	public BingSteps(TestContext context, Hooks hooks) {
		this.hooks = hooks;
		this.testContext = context;
		bingPage = testContext.getPageObjectManager().getBingPage();
		logger = Logger.getLogger(BingSteps.class);
		record = new HashMap<String, String>();
		currentScenario = (Scenario) context.getScenarioContext().getContext("currentScenario");
	}
	
	@Given("I connect to https:\\/\\/www.bing.com")
	public void i_connect_to_https_www_bing_com() {
		logger.info("Connecting " + FileReaderManager.getInstance().getConfigReader().getBingURL());
		hooks.getDriver().get(FileReaderManager.getInstance().getConfigReader().getBingURL());
	}
	
	@Then("I should see Bing website")
	public void i_should_see_bing_website() throws IOException {
	    logger.info("Calling isBingPageDisplayed");
	    currentScenario.attach(Files.readAllBytes(Utility.captureScreenshot(hooks.getDriver(), false).toPath()), "image/png", "");
	    Assert.assertTrue("Is Bing HomePage displayed?", bingPage.isBingPageDisplayed());
	}
}
