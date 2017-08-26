package com.mobweb.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import com.appln.pageobjects.GenericPageObjects;
import com.appln.reporting.Reporter;
import com.appln.utils.LoadDriverManager;

public class MHomePage extends GenericPageObjects {
	protected RemoteWebDriver driver = null;

	@FindBy(how = How.XPATH, using = GenericPageObjects.MHomePage_Search_Tbox)
	private WebElement searchRepoBox;

	@FindBy(how = How.XPATH, using = GenericPageObjects.MHomePage_SearchResults_List)
	private WebElement selectRepositoryItem;

	
	public MHomePage(RemoteWebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
	}

	public MHomePage searchRepository(String repositoryName){
		try{
			searchRepoBox.clear();
			searchRepoBox.sendKeys(repositoryName);
			searchRepoBox.sendKeys(Keys.ENTER);	
		}
		catch(Exception e){
			Reporter.reportStep("Entering Repository name failed", "FAIL");
		}
		return this;
	}
	
	public MRepositoryHomePage selectRepository(){
		try{
			if(selectRepositoryItem.getText().equals("funkfan82/Autothon")){
				selectRepositoryItem.click();
			}
		}
		catch(Exception e){
			Reporter.reportStep("Failed in selecting the repository", "FAIL");
		}
		return new MRepositoryHomePage(LoadDriverManager.getWebdriver());
	}
	
	
}
