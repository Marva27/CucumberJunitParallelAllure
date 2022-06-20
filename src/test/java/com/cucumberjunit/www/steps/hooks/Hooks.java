package com.cucumberjunit.www.steps.hooks;

import org.openqa.selenium.WebDriver;

import com.cucumberjunit.www.cucumber.TestContext;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {
	
	private WebDriver driver;
	
	TestContext testContext;
	
	public Hooks(TestContext context) {
		testContext = context;
	}
	

    @Before
    public void setUp(Scenario scenario) {
    	testContext.scenarioContext.setContext("currentScenario", scenario);
    	setDriver();
    }
	
	private void setDriver() {
		driver = testContext.getWebDriverManager().getDriver();
	}
	
	public WebDriver getDriver() {
        return driver;
    }
	
	@After
	public void tearDown() {
		if(driver != null) {
			driver.close();
			driver.quit();
		}
	}

}
