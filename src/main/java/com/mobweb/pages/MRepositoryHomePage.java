package com.mobweb.pages;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import com.appln.pageobjects.GenericPageObjects;
import com.appln.reporting.Reporter;

public class MRepositoryHomePage extends GenericPageObjects {
	protected RemoteWebDriver driver = null;

	@FindBy(how = How.XPATH, using = GenericPageObjects.MRepositoryHomePage_Menu_Links)
	private List<WebElement> menuLinks;

	@FindBy(how = How.XPATH, using = GenericPageObjects.MRepositoryHomePage_SearchResults_TopBanner_Links)
	private List<WebElement> bannerLinks;

	@FindBy(how = How.XPATH, using = GenericPageObjects.MRepositoryHomePage_IssueAdd_Button)
	private WebElement issueButton;
	@FindBy(how = How.XPATH, using = GenericPageObjects.MRepositoryHomePage_IssueTitle_Tbox)
	private WebElement issueTitle;
	@FindBy(how = How.XPATH, using = GenericPageObjects.MRepositoryHomePage_Issue_Tbox)
	private WebElement issueComment;
	@FindBy(how = How.XPATH, using = GenericPageObjects.MRepositoryHomePage_Star_Button)
	private WebElement starButton;
	@FindBy(how = How.XPATH, using = GenericPageObjects.MRepositoryHomePage_MenuIcon_Button)
	private WebElement topMenuIcon;
	@FindBy(how = How.XPATH, using = GenericPageObjects.MRepositoryHomePage_IssueSubmit_Button)
	private WebElement issueSubmit;

	public MRepositoryHomePage(RemoteWebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
	}

	public MRepositoryHomePage clickOnIssues() {
		try {
			for (WebElement link : menuLinks) {
				if (link.getText().equals("Issues")) {
					link.click();
					break;
				}
			}
		} catch (Exception e) {
			Reporter.reportStep("Unable to click on Issue Link", "FAIL");
		}
		return this;
	}

	public MRepositoryHomePage addIssue() {
		try {
			issueButton.click();
		} catch (Exception e) {
			Reporter.reportStep("Unable to add Issue", "FAIL");
		}
		return this;
	}

	public MRepositoryHomePage enterIssueFields(String issueTitleValue, String comment) {
		try {
			issueTitle.sendKeys(issueTitleValue);
			issueComment.sendKeys(comment);
			issueSubmit.click();
		} catch (Exception e) {
			Reporter.reportStep("Unable to enter the issue field values", "FAIL");
		}
		return this;
	}

	public MRepositoryHomePage navigateToVotingField() {
		try {
			for (WebElement link : menuLinks) {
				if (link.getText().equals("Code")) {
					link.click();
					break;
				}
			}
		} catch (Exception e) {
			Reporter.reportStep("Unable to navigate to voting field", "FAIL");
		}
		return this;
	}

	public MRepositoryHomePage clickStartoRepo() {
		try {
			starButton.click();
		} catch (Exception e) {
			Reporter.reportStep("Unable to provide star", "FAIL");
		}
		return this;
	}

	
	
	
}
