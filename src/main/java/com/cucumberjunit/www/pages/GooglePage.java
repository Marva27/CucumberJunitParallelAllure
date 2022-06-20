package com.cucumberjunit.www.pages;

import java.io.IOException;
import java.nio.file.Files;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cucumberjunit.www.util.Utility;

import io.cucumber.java.Scenario;

public class GooglePage extends BasePage {
	
	private static Logger logger = Logger.getLogger(GooglePage.class);

	public GooglePage(WebDriver browser) {
		super(browser);
		PageFactory.initElements(browser, this);
	}
	
	public boolean isGooglePaeDisplayed() {
		if(browser.getTitle().contains("Google"))
			return true;
		else
			return false;
	}
	
	@FindBy(css="input[name='q']")
	WebElement txtSearchBox;
	
	@FindBy(css="input[value='Google Search']")
	WebElement btnGoogleSearch;
	
	public void googleSearch(String searchText, Scenario currentScenario) throws IOException {
		logger.info("Entered googleSearch");
		enterValue(txtSearchBox, searchText, currentScenario);
		currentScenario.attach(Files.readAllBytes(Utility.captureScreenshot(browser, false).toPath()), "image/png", searchText);
		clickElement(btnGoogleSearch, currentScenario);
		logger.info("Exited googleSearch");
	}
	
	public boolean areGoogleSearchResultsDisplayed(String searchResults, Scenario currentScenario) {
		logger.info("Entered areGoogleSearchResultsDisplayed");
		if(browser.getTitle().contains(searchResults))
			return true;
		else
			return false;
	}
}
