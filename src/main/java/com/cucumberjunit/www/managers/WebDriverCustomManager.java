package com.cucumberjunit.www.managers;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.cucumberjunit.www.enums.DriverType;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverCustomManager {
	private WebDriver driver;
	private static DriverType driverType;

	private static Logger logger = Logger.getLogger(WebDriverCustomManager.class);

	public WebDriverCustomManager() {
		driverType = FileReaderManager.getInstance().getConfigReader().getBrowser();
	}

	public WebDriver getDriver() {
		if (driver == null)
			driver = createDriver();
		driver.manage().window().maximize();
		return driver;
	}

	private WebDriver createDriver() {
		try {
			switch (System.getProperty("execution")) {
			case "local":
				driver = createLocalDriver();
				break;
			case "remote":
				driver = createRemoteDriver();
				break;
			}
			return driver;
		} catch (Exception e) {
			logger.error("Error opening browser due to " + e.getMessage());
			Assert.fail("Error opening browser due to " + e.getMessage());
			return null;
		}
	}

	private WebDriver createLocalDriver() {
		if (driverType.equals(DriverType.CHROME)) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			return driver;
		}
		return driver;
	}

	private WebDriver createRemoteDriver() {
		return driver;
	}

	public void closeDriver() {
		if(driver != null) {
			driver.close();
			driver.quit();
		}
	}

}
