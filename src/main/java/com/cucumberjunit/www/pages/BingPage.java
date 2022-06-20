package com.cucumberjunit.www.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BingPage extends BasePage {
	
	private static Logger logger = Logger.getLogger(BingPage.class);

	public BingPage(WebDriver browser) {
		super(browser);
		PageFactory.initElements(browser, this);
	}
	
	public boolean isBingPageDisplayed() {
		logger.info("Entered isBingPageDisplayed");
		if(browser.getTitle().contains("Bing"))
			return true;
		else
			return false;
	}

}
