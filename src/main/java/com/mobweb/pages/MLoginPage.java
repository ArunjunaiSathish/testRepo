package com.mobweb.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import com.appln.pageobjects.GenericPageObjects;
import com.appln.reporting.Reporter;
import com.appln.utils.LoadDriverManager;

public class MLoginPage extends GenericPageObjects {
	protected RemoteWebDriver driver = null;

	@FindBy(how = How.XPATH, using = GenericPageObjects.MLoginPage_UserName_Tbox)
	private WebElement userName;

	@FindBy(how = How.XPATH, using = GenericPageObjects.MLoginPage_Password_Tbox)
	private WebElement passWord;
	
	@FindBy(how = How.XPATH, using = GenericPageObjects.MLoginPage_Password_Tbox)
	private WebElement signInButton;
	
	public MLoginPage(RemoteWebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
	}

	public MLoginPage enterUserName(String userNameValue){
		try{
			userName.clear();
			userName.sendKeys(userNameValue);
		}
		catch(Exception e){
			Reporter.reportStep("Entering UserName failed", "FAIL");
		}
		return this;
	}

	public MLoginPage enterPassword(String passWordValue){
		try{
			passWord.clear();
			passWord.sendKeys(passWordValue);
		}
		catch(Exception e){
			Reporter.reportStep("Entering Password failed", "FAIL");
		}
		return this;
	}
		
	public MHomePage clickSignInButton(){
		try{
			signInButton.click();
		}
		catch(Exception e){
			Reporter.reportStep("Clicking SignIn button failed", "FAIL");
		}
		return new MHomePage(LoadDriverManager.getWebdriver());
	}
	
	public MHomePage login(String userNameValue,String passWordValue){
		enterUserName(userNameValue);
		enterPassword(passWordValue);
		clickSignInButton();
		return new MHomePage(LoadDriverManager.getWebdriver());
	}
	
	
}
