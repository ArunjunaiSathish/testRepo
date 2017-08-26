package com.mobweb.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.appln.pageobjects.GenericPageObjects;

public class MSignOutPage extends GenericPageObjects {
	protected RemoteWebDriver driver = null;

	@FindBy(how = How.XPATH, using = GenericPageObjects.MMainPage_MenuIcon_Button)
	private WebElement menuButton;

	@FindBy(how = How.XPATH, using = GenericPageObjects.MMainPage_SignIn_Link)
	private WebElement signInButton;


}
