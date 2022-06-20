package com.cucumberjunit.www.pages;

import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.util.Map;

import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cucumberjunit.www.util.Utility;

import io.cucumber.java.Scenario;

public class BasePage {
	
	public WebDriver browser;
	
	public BasePage(WebDriver browser) {
		this.browser = browser;
	}
	
	/*
	 * Function Name: isAlertPresent
	 * Objective: To verify if an alert box is displayed
	 * Date Created: 01/28/2020 Date Modified: 01/28/2020
	 * Changes Made: Initial version
	 */
	public boolean isAlertPresent() {
		try {
			browser.switchTo().alert();
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	public boolean verifyPartialLabelText(WebElement label, String expectedValue, Scenario currentScenario) throws IOException {
		try {
			if(label.getText().replaceAll("\\s", "").contains(expectedValue.replaceAll("\\s", ""))) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			currentScenario.attach(Files.readAllBytes(Utility.captureScreenshot(browser, false).toPath()), "image/png", "");
			Assert.fail("Error in locating element due to " + e.getMessage());
			return false;
		}
	}
	
	/*
	 * Function Name: selectDropDown
	 * Objective: To select a value from drop down
	 * Date Created: 03/14/2019
	 * Date Modified: 01/27/2020
	 * Changes Made: Stop execution if a value is NOT present in the dropdown
	 */
	public void selectDropDown(WebElement element, String optionValue, Scenario currentScenario) throws IOException {
		waitForAnElementToBeVisible(element, 5, currentScenario);
		Select select = new Select(element);
		try {
			select.selectByVisibleText(optionValue);
		}catch(Exception e) {
			scrollToElement(element, currentScenario);
			element.click();
			Assert.fail(optionValue + " was NOT found in dropdown");
		}
	}
	
	/*
	 * Function Name: getTextBoxValue
	 * Objective: To get the value from a text box
	 * Date Created: 04/12/2020 Date Modified: 04/12/2020
	 * Changes Made: Initial version
	 */
	
	public String getTextBoxValue(WebElement element, Scenario currentScenario) throws IOException {
		try {
			return element.getAttribute("value");
		} catch (Exception e) {
			currentScenario.attach(Files.readAllBytes(Utility.captureScreenshot(browser, false).toPath()), "image/png", "");
			Assert.fail("Error in locating element due to " + e.getMessage());
		}
		return null;
	}
	
	/*
	 * Function Name: verifyTextBoxContents
	 * Objective: To verify the contents of a text field
	 * Date Created: 01/22/2020 Date Modified: 01/22/2020
	 * Changes Made: Initial version
	 */
	
	public void verifyTextBoxContents(WebElement element, String expectedValue, Scenario currentScenario) throws IOException {
		try {
			if(expectedValue.trim().equals("")) {
				Assert.assertEquals(element.getAttribute("value"), "", "Is textfield value set to " + expectedValue + "?");
			} else {
				Assert.assertEquals(element.getAttribute("value"), expectedValue, "Is textfield value set to " + expectedValue + "?");
			}
		} catch(Exception e) {
			currentScenario.attach(Files.readAllBytes(Utility.captureScreenshot(browser, false).toPath()), "image/png", "");
			Assert.fail("Error in locating element. Please contact automation engineer.");
		}
	}
	
	/*
	 * Function Name: verifyDropDownValue
	 * Objective: To verify the active selected value in dropdown
	 * Date Created: 01/22/2020 Date Modified: 01/22/2020
	 * Changes Made: Initial version
	 */
	
	public void verifyDropDownValue(WebElement element, String expectedValue, Scenario currentScenario) throws IOException {
		try {
			Select select = new Select(element);
			Assert.assertEquals(select.getFirstSelectedOption().getText(), expectedValue, "Is dropdown set to " + expectedValue + "?");	
		} catch(Exception e) {
			currentScenario.attach(Files.readAllBytes(Utility.captureScreenshot(browser, false).toPath()), "image/png", "");
			Assert.fail("Error in locating element. Please contact automation engineer. Reason is: " + e.getMessage());
		}
	}
	
	/*
	 * Function Name: verifyPartialDropDownValue
	 * Objective: To verify if the active selected value partially match with the expected value
	 * Date Created: 04/09/2020 Date Modified: 04/09/2020
	 * Changes Made: Initial version
	 */
	
	public void verifyPartialDropDownValue(WebElement element, String expectedValue, Scenario currentScenario) throws IOException {
		try {
			Select select = new Select(element);
			Assert.assertTrue("Is dropdown contains " + expectedValue + "?", select.getFirstSelectedOption().getText().contains(expectedValue));	
		} catch(Exception e) {
			currentScenario.attach(Files.readAllBytes(Utility.captureScreenshot(browser, false).toPath()), "image/png", "");
			Assert.fail("Error in locating element. Please contact automation engineer. Reason is: " + e.getMessage());
		}
		
	}

	
	/*
	 * Function Name: clickUsingActionsClass
	 * Objective: To click a web element using Actions class
	 * Date Created: 04/17/2020 Date Modified: 04/17/2020
	 * Changes Made: Initial version
	 */
	public void clickElementUsingActionsClass(WebElement element, Scenario currentScenario) throws IOException {
		try	{
			Actions actions = new Actions(browser);
			actions.moveToElement(element).click().build().perform();
		}catch(Exception e) {
			currentScenario.attach(Files.readAllBytes(Utility.captureScreenshot(browser, false).toPath()), "image/png", "");
			Assert.fail("Error in locating element. Please contact automation developer. Reason is: " + e.getMessage());
		}
	}
	
	/*
	 * Function Name: getFormattedValue
	 * Objective: To remove $ and , symbols from a value
	 * Date Created: 04/10/2020 Date Modified: 04/10/2020
	 * Changes Made: Initial version
	 */
	public String getFormattedValue(String inputText) {
		inputText = inputText.replaceAll("\\$", "").replaceAll("\\,", "");
		double value = Double.parseDouble(inputText);
		value = Math.round(value);
		inputText = String.valueOf(value);
		if(inputText.contains("."))
			return inputText.substring(0, inputText.indexOf("."));
		else
			return inputText;
	}
	
	public boolean waitForAnElementToBeVisibleIgnoringErrors(WebElement element, Duration seconds, Scenario currentScenario) {
		int i = 0;
		boolean staleElement = true;
		while (staleElement && i < 2) {
			try {
				//browser.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
				WebDriverWait wait = new WebDriverWait(browser, seconds);
				wait.until(ExpectedConditions.visibilityOf(element));
				return true;
			}catch(Exception e) {
				i++;
				staleElement = true;
				if(i >= 2) {
					return false;
				}
			}
		}
		return false;
	}
	
	/*
	 * Function Name: enterValue
	 * Objective: To enter value in a text field
	 * Date Created: 03/07/2019
	 * Date Modified: 03/07/2019
	 * Changes Made: Initial version
	 */
	public void enterValue(WebElement element, String value, Scenario currentScenario) throws IOException {
		try {
			if(!element.getAttribute("value").equals(""))
				element.clear();
			element.sendKeys(value);
		}catch(Exception e) {
			if(browser != null) {
				currentScenario.attach(Files.readAllBytes(Utility.captureScreenshot(browser, false).toPath()), "image/png", "");
				Assert.fail("Error in locating element. Please contact automation developer.");
			} else {
				Assert.fail("Browser was crashed!!!");
			}		
		}
	}
	
	/*
	 * Function Name: verifyLabelText
	 * Objective: To match the label text
	 * Date Created: 01/15/2020
	 * Date Modified: 01/15/2020
	 * Changes Made: Initial version
	 */
	public boolean verifyLabelText(WebElement element, String labelText, Scenario currentScenario) throws IOException {
		try {
			if(element.getText().replaceAll("\\s", "").equals(labelText.replaceAll("\\s", ""))) return true;
			else return false;
		}catch(Exception e) {
			currentScenario.attach(Files.readAllBytes(Utility.captureScreenshot(browser, false).toPath()), "image/png", "");
			Assert.fail("Error in locating element. Please contact automation developer. Reason is : " + e.getMessage());
			return false;
		}
	}
	
	/*
	 * Function Name: isElementDisappeared
	 * Objective: To wait for an element to disappear after specified seconds
	 * Date Created: 01/16/2020 Date Modified: 01/16/2020
	 * Changes Made: Initial version
	 */
	
	public boolean isElementDisappeared(WebElement element, Duration seconds) {
		try {
			browser.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
			WebDriverWait wait = new WebDriverWait(browser, seconds);
			wait.until(ExpectedConditions.invisibilityOf(element));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/*
	 * Function Name: selectDropDownUsingJS
	 * Objective: To select a value from drop down using Javascript
	 * Date Created: 01/28/2020
	 * Date Modified: 01/28/2020
	 * Changes Made: Initial version
	 */
	public void selectDropDownUsingJS(WebElement element, String optionValue, Map<String, String> map, Scenario currentScenario) throws IOException {
		Select select = new Select(element);
		try {
			((JavascriptExecutor) browser).executeScript("arguments[0].value='" + map.get(optionValue) + "';",select);
		}catch(Exception e) {
			scrollToElement(element, currentScenario);
			element.click();
			currentScenario.attach(Files.readAllBytes(Utility.captureScreenshot(browser, false).toPath()), "image/png", "");
			Assert.fail(optionValue + " was NOT found in dropdown");
		}
	}
	
	/*
	 * Function Name: isElementDisplayed
	 * Objective: To check if a WebElement is visible
	 * Date Created: 03/08/2019
	 * Date Modified: 03/08/2019
	 * Changes Made: Initial version
	 */
	public boolean isElementDisplayed(WebElement element) {
		browser.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		try {
			if(element.isDisplayed()){
				return true;
			}else {
				return false;
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	/*
	 * Function Name: enterValueUsingJS
	 * Objective: To enter value in a text field using Java script
	 * Date Created: 01/15/2020
	 * Date Modified: 01/15/2020
	 * Changes Made: Initial version
	 */
	public void enterValueUsingJS(WebElement element, String value) throws IOException {
		try {
			JavascriptExecutor runJS= ((JavascriptExecutor) browser);
			runJS.executeScript("arguments[0].value = '" + value + "'", element);
		}catch(Exception e) {
			Assert.fail("Error in locating element. Please contact automation developer. Reason is: " + e.getMessage());
		}
	}
	
	/*
	 * Function Name: getActiveSelected
	 * Objective: To get a currently selected value from a drop down
	 * Date Created: 03/19/2019
	 * Date Modified: 03/19/2019
	 * Changes Made: Initial version
	 */
	public String getActiveSelected(WebElement element) {
		Select select = new Select(element);
		return select.getFirstSelectedOption().getText();	
	}
	
	/*
	 * Function Name: clickElementUsingJS
	 * Objective: To click a web element using Java Script Executor
	 * Date Created: 03/07/2019
	 * Date Modified: 03/07/2019
	 * Changes Made: Initial version
	 */
	public void clickElementUsingJS(WebElement element, Scenario currentScenario) throws IOException {
		try {
			JavascriptExecutor js = (JavascriptExecutor) browser;
			js.executeScript("arguments[0].click()", element);
		}catch(Exception e) {
			currentScenario.attach(Files.readAllBytes(Utility.captureScreenshot(browser, false).toPath()), "image/png", "");
			Assert.fail("Error in locating element. Please contact automation developer due to reason " + e.getMessage());
		}
	}
	
	/*
	 * Function Name: selectDropDownByValue
	 * Objective: To select an option from drop down by its value
	 * Date Created: 02/03/2020 Date Modified: 02/03/2020
	 * Changes Made: Initial version
	 */
	
	public void selectDropDownByValue(WebElement element, String optionValue, Scenario currentScenario) throws IOException {
		Select select = new Select(element);
		try {
			select.selectByValue(optionValue);
		}catch(Exception e) {
			currentScenario.attach(Files.readAllBytes(Utility.captureScreenshot(browser, false).toPath()), "image/png", "");
			Assert.fail(optionValue + " was NOT found in dropdown");
		}	
	}
	
	/*
	 * Function Name: clickElement
	 * Objective: To click a web element
	 * Date Created: 03/07/2019
	 * Date Modified: 03/07/2019
	 * Changes Made: Initial version
	 */
	public void clickElement(WebElement element, Scenario currentScenario) throws IOException {
		try {
			WebDriverWait wait = new WebDriverWait(browser, Duration.ofSeconds(5));
			wait.until(ExpectedConditions.elementToBeClickable(element));
			//scrollToElement(element, currentScenario);
			element.click();
		}catch(Exception e) {
			if(e instanceof StaleElementReferenceException) {
				WebDriverWait wait = new WebDriverWait(browser, Duration.ofSeconds(2));
				wait.until(ExpectedConditions.elementToBeClickable(element));
				element.click();
			} else {
				currentScenario.attach(Files.readAllBytes(Utility.captureScreenshot(browser, false).toPath()), "image/png", "");
				Assert.fail("Error in locating element. Please contact automation developer. Reason is: " + e.getMessage());
			}
		}
	}
	
	/*
	 * Function Name: waitForAnElementToBeVisible
	 * Objective: To wait for an element to be visible
	 * Date Created: 03/19/2019
	 * Date Modified: 03/19/2019
	 * Changes Made: Initial version
	 */
	public boolean waitForAnElementToBeVisible(WebElement element, long seconds, Scenario currentScenario) throws IOException {
		int i = 0;
		boolean staleElement = true;
		while (staleElement && i < 2) {
			try {
				browser.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
				WebDriverWait wait = new WebDriverWait(browser, Duration.ofSeconds(seconds));
				wait.until(ExpectedConditions.visibilityOf(element));
				return true;
			}catch(Exception e) {
				i++;
				staleElement = true;
				if(i >= 2) {
					currentScenario.attach(Files.readAllBytes(Utility.captureScreenshot(browser, false).toPath()), "image/png", "");
					return false;
				}
			}
		}
		return false;
	}
	
	/*
	 * Function Name: scrollToElement
	 * Objective: To scroll to a web element
	 * Date Created: 01/24/2020 Date Modified: 01/24/2020
	 * Changes Made: Initial version
	 */
	public void scrollToElement(WebElement element, Scenario currentScenario) throws IOException {
		try {
			JavascriptExecutor js = (JavascriptExecutor) browser;
			js.executeScript("arguments[0].scrollIntoView(true);", element);
		} catch(Exception e) {
			currentScenario.attach(Files.readAllBytes(Utility.captureScreenshot(browser, false).toPath()), "image/png", "");
			Assert.fail("Error in location element. Please contact automation developer. Reason is: " + e.getMessage());
		}
	
	}
	
	
	
	
	
	/*
	 * VJ's reusable action methods
	 */

	public boolean  isCheckBoxSelected (WebElement element) {		
		try {
			if (element.isSelected()) {
				return true;	
			}else {
				return false;
			}
		} catch (Exception e) {
	      System.out.println(e.getMessage());
	      return false;
		}		
		
	}	
	
	public void selectCheckBox (WebElement element,Scenario currentScenario) throws IOException {		
		try {
			if (!isCheckBoxSelected(element))
				element.click();
			
		} catch (Exception e) {
			currentScenario.attach(Files.readAllBytes(Utility.captureScreenshot(browser, false).toPath()), "image/png", "");
			Assert.fail("Error in locating element. Please contact automation developer.");
		}
		
					
	}
	
	//verify an element is displayed on the page
	
	public boolean  isDisplayed (WebElement element,Scenario currentScenario) throws IOException {		
		try {
			if (element.isDisplayed()) {
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			return false;
			//currentScenario.attach(Files.readAllBytes(Utility.captureScreenshot(browser, false).toPath()), "image/png", "");
			//Assert.fail("Error in locating element. Please contact automation developer.");
		}
		//return false;	
		
	}
}
