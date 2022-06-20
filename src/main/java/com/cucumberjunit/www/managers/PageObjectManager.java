package com.cucumberjunit.www.managers;

import org.openqa.selenium.WebDriver;

import com.cucumberjunit.www.pages.BingPage;
import com.cucumberjunit.www.pages.GooglePage;

public class PageObjectManager {

	private WebDriver driver;
		
	//Google Page Objects
	private GooglePage googlePage;
	private BingPage bingPage;
		
	public PageObjectManager(WebDriver driver) {
		this.driver = driver;
	}
	
	public GooglePage getGooglePage() {
		return (googlePage == null) ? googlePage = new GooglePage(driver) : googlePage;
	}

	public BingPage getBingPage() {
		return (bingPage == null) ? bingPage = new BingPage(driver) : bingPage;
	}

}