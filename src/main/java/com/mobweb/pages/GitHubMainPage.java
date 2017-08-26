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

import repackage.Repackage;

public class GitHubMainPage extends GenericPageObjects {

	protected RemoteWebDriver driver = null;

	@FindBy(how = How.XPATH, using = GenericPageObjects.MMainPage_MenuIcon_Button)
	private WebElement menuButton;

	@FindBy(how = How.XPATH, using = GenericPageObjects.MMainPage_SignIn_Link)
	private WebElement signInLink;

	public GitHubMainPage(RemoteWebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
	}

	public GitHubMainPage clickMenu() {
		try {
			menuButton.click();
		} catch (Exception e) {
			Reporter.reportStep("Not able to Click Menu icon", "FAIL");
		}
		return this;
	}

	public MLoginPage navigateToSignInPage() {
		try {
			signInLink.click();
		} catch (Exception e) {
			Reporter.reportStep("Not able to Click Menu icon", "FAIL");
		}
		return new MLoginPage(LoadDriverManager.getWebdriver());
	}

}
