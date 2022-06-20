package com.cucumberjunit.www.cucumber;

import com.cucumberjunit.www.managers.PageObjectManager;
import com.cucumberjunit.www.managers.WebDriverCustomManager;

public class TestContext {
	private WebDriverCustomManager webDriverManager;
	private PageObjectManager pageObjectManager;
	public ScenarioContext scenarioContext;
	 
	public TestContext() {
		webDriverManager = new WebDriverCustomManager();
		pageObjectManager = new PageObjectManager(webDriverManager.getDriver());
		scenarioContext = new ScenarioContext();
	}
	 
	public WebDriverCustomManager getWebDriverManager() {
		return webDriverManager;
	}
	 
	public PageObjectManager getPageObjectManager() {
		return pageObjectManager;
	}
	
	public ScenarioContext getScenarioContext() {
		return scenarioContext;
	}
	 
}