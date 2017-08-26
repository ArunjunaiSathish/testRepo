package com.appln.wrappers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appln.reporting.Reporter;
import com.appln.utils.LoadDriverManager;
/**
 * @author Team E
 *
 */
public class GenericWrappers {
	protected static Properties configProp;
	protected static Properties dataProp;
	protected static Properties testdataprop;
	protected static WebDriverWait wait;
	protected static DesiredCapabilities capabilites;
	protected Logger log;
	
	public GenericWrappers() {
		configProp = new Properties();
		dataProp = new Properties();
		try {
			configProp.load(new FileInputStream(new File("./src/main/resources/config.properties")));
			dataProp.load(new FileInputStream(new File("./src/main/resources/data.properties")));
		} catch (IOException e) {
			Reporter.reportStep("Not able to load config properties", "FAIL");
		}
	}

	public void initializeLogger(){
		this.log = log;
	}
	

	public static void loadtestData(String filename){
		testdataprop = new Properties();
		try {
			testdataprop.load(new FileInputStream(new File("./data/"+filename+".properties")));
		} catch (Exception e) {
			Reporter.reportStep("Test data file loading issue", "FAIL");
		}
	}
	
	public void waitUntilElementStatus(String locatorValue, String expectedValue) {
		wait = new WebDriverWait(LoadDriverManager.getWebdriver(), 40);
		try {
			if (expectedValue.equals("clickable")) {
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locatorValue)));
			} else if (expectedValue.equals("visible")) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorValue)));
			} else if (expectedValue.equals("invisible")) {
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(locatorValue)));
			} else if (expectedValue.equals("texttobepresent")) {
				wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(locatorValue), expectedValue));
			}

		} catch (Exception e) {
			Reporter.reportStep("Error in waiting for the element condition to be as expected", "FAIL");
		}
	}

	public boolean mouseOverbyXpath(String xpathValueSource, String xpathvalueDestination) {
		boolean successFlag = false;
		Actions action = new Actions(LoadDriverManager.getWebdriver());
		try {
			action.moveToElement(LoadDriverManager.getWebdriver().findElement(By.xpath(xpathValueSource)))
					.click(LoadDriverManager.getWebdriver().findElement(By.xpath(xpathvalueDestination))).build().perform();
			successFlag = true;
		} catch (Exception e) {
			successFlag = false;
			Reporter.reportStep("Mouse over for the element failed", "FAIL");
		}
		return successFlag;
	}

	

	public boolean validateText(String locator, String expectedvalue) {
		boolean successFlag = true;
		try {
			if (LoadDriverManager.getWebdriver().findElement(By.xpath(locator)).getText().equals(expectedvalue)) {
				successFlag = true;
			}
		} catch (Exception e) {
			successFlag = false;
			Reporter.reportStep("Validate text failed", "FAIL");
		}
		return successFlag;
	}

	public boolean verifyTitle(String expectedtitle) {
		boolean successFlag = true;
		try {
			if (LoadDriverManager.getWebdriver().getTitle().equals(expectedtitle)) {
				successFlag = true;
				Reporter.reportStep("Verify Title passed", "PASS");
			}
		} catch (Exception e) {
			successFlag = false;
			Reporter.reportStep("Verify Title passed", "FAIL");
		}
		return successFlag;
	}

	public boolean clickByXpath(String locator) {
		boolean successFlag = true;
		try {
			LoadDriverManager.getWebdriver().findElement(By.xpath(locator)).click();
		} catch (Exception e) {
		e.printStackTrace();
			successFlag = false;
			Reporter.reportStep("Element is not able to click", "FAIL");
		}
		return successFlag;
	}

	public boolean enterByXpath(String locator, String value) {
		boolean successFlag = true;
		try {
			LoadDriverManager.getWebdriver().findElement(By.xpath(locator)).clear();
			LoadDriverManager.getWebdriver().findElement(By.xpath(locator)).sendKeys(value);
		} catch (Exception e) {
			successFlag = false;
			Reporter.reportStep("Entering the value failed", "FAIL");
		}
		return successFlag;
	}

	public String getTextByXpath(String locator) {
		String fetchedTextValue = null;
		try {
			fetchedTextValue = LoadDriverManager.getWebdriver().findElement(By.xpath(locator)).getText();
		} catch (Exception e) {
			Reporter.reportStep("Value not able to fetch", "FAIL");
		}
		return fetchedTextValue;

	}

	public boolean captureScreenshot(String screenshotName){
		boolean successFlag = true;
				try {
					FileUtils.copyFile(((TakesScreenshot) LoadDriverManager.getWebdriver()).getScreenshotAs(OutputType.FILE) , new File("./reports/images/"+Reporter.currenttimedate+"/"+screenshotName+".jpg"));
				} catch (Exception e) {
					successFlag = false;	
					Reporter.reportStep("Screen capture failed", "FAIL");
				}
		return successFlag;
	}
	
	public boolean scrollthePage(){
		boolean successFlag = true;
		JavascriptExecutor js = (JavascriptExecutor)LoadDriverManager.getWebdriver();
		try{
			js.executeScript("window.scrollBy(0,480)", "");
		}
		catch(Exception e){
			successFlag = false;
		}
		return successFlag;
	}
	
	public boolean jseClick(WebElement element){
		boolean successFlag = true;
		JavascriptExecutor js = (JavascriptExecutor)LoadDriverManager.getWebdriver();
		try{
			js.executeScript("arguments[0].click();", element);
		}
		catch(Exception e){
			successFlag = false;
		}
		return successFlag;
	}
	
	
	
	
	
	public boolean pressKeyEnter(String locator){
		boolean successFlag = true;
		try{
			LoadDriverManager.getWebdriver().findElement(By.xpath(locator)).sendKeys(Keys.ENTER);
		}
		catch(Exception e){
			successFlag = false;
		}
		return successFlag;
	}
	
	public boolean selectByValue(WebElement element,String selectValue){
		boolean successFlag = true;
		try{
			Select select = new Select(element);
			select.selectByValue(selectValue);
		}
		catch(Exception e){
			successFlag = false;
			Reporter.reportStep("Selecting the value in element failed", "FAIL");
		}
		return successFlag;
		
	}
	
	public boolean enterByName(String locator, String value) {
		boolean successFlag = true;
		try {
			LoadDriverManager.getWebdriver().findElement(By.name(locator)).clear();
			LoadDriverManager.getWebdriver().findElement(By.name(locator)).sendKeys(value);
		} catch (Exception e) {
			successFlag = false;
			Reporter.reportStep("Value not entered", "FAIL");
		}
		return successFlag;
	}

	public boolean switchtoFrame(String frameName){
		boolean successFlag = true;
		try {
		LoadDriverManager.getWebdriver().switchTo().defaultContent();
		LoadDriverManager.getWebdriver().switchTo().frame(frameName);
		}
		catch(Exception e){
			successFlag = false;
			Reporter.reportStep("Switching to frame failed", "FAIL");
		}
		return successFlag;
	}

	public boolean switchtoDefault(){
		boolean successFlag = true;
		try {
		LoadDriverManager.getWebdriver().switchTo().defaultContent();
		}
		catch(Exception e){
			successFlag = false;
			Reporter.reportStep("Switching to default content from frame failed", "FAIL");
		}
		return successFlag;
	}
	public boolean clickByName(String locator) {
		boolean successFlag = true;
		try {
			LoadDriverManager.getWebdriver().findElement(By.name(locator)).click();
		} catch (Exception e) {
			successFlag = false;
			Reporter.reportStep("Element not able to click", "FAIL");
		}
		return successFlag;
	}
	
	public boolean checkisDisplayed(String locator){
		boolean successFlag = true;
		try{
			LoadDriverManager.getWebdriver().findElement(By.xpath(locator)).isDisplayed();
		}
		catch(Exception e){
			successFlag = false;
		}
		return successFlag;
	 
	}
	public boolean isAlertPresent(){
		try{
			LoadDriverManager.getWebdriver().switchTo().alert();
			return true;
		}
		catch(Exception e){
			return false;
		}
	}
	public boolean dismissAlert(){
		boolean successFlag = true;
		try{
			LoadDriverManager.getWebdriver().switchTo().alert().dismiss();
			LoadDriverManager.getWebdriver().switchTo().defaultContent();
		}
		catch(Exception e){
			successFlag = false;
		}
		return successFlag;
	 
	}
	
	
	public boolean validateCurrentURL(String URL){
		boolean successFlag = true;
		try{
			if(LoadDriverManager.getWebdriver().getCurrentUrl().equals(URL)){
				successFlag = true;
			}
			else{
				successFlag = false;
			}
		}
		catch(Exception e){
			successFlag = false;
		}
		return successFlag;
	}
}
