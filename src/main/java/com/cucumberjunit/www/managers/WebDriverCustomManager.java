package com.cucumberjunit.www.managers;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverCustomManager {
	private WebDriver driver;

	private static Logger logger = Logger.getLogger(WebDriverCustomManager.class);

	public WebDriver getDriver() {
		if (driver == null)
			driver = createDriver();
		driver.manage().window().maximize();
		return driver;
	}

	private WebDriver createDriver() {
		try {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			return driver;
		} catch (Exception e) {
			logger.error("Error opening browser due to " + e.getMessage());
			Assert.fail("Error opening browser due to " + e.getMessage());
			return null;
		}
	}

	public void closeDriver() {
		if(driver != null) {
			driver.close();
			driver.quit();
		}
	}

}
