package com.mobileweb.testcases;

import java.io.IOException;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.appln.logger.TestLogging;
import com.appln.pageobjects.GenericPageObjects;
import com.appln.reporting.Reporter;
import com.appln.utils.EnvironmentSetup;
import com.appln.utils.LoadDriverManager;
import com.mobweb.pages.GitHubMainPage;
import com.mobweb.pages.MHomePage;
import com.mobweb.pages.MLoginPage;
import com.mobweb.pages.MRepositoryHomePage;

public class VerifyMobileFlow extends EnvironmentSetup {

	protected RemoteWebDriver ldriver = null;

	@BeforeClass
	@Parameters({ "executiontype", "browser", "platform", "connectionAddress", "DeviceName" })
	public void setUp(String executionType, String browserName, String platform, String connectAddress,
			String deviceName) throws IOException, InterruptedException {
		startServerService();
		testcaseName = "Validate search Repository in mobile";
		testCaseDescription = "To Validate search Repository in mobile";
		dataSheetName = "testdata";
		loadtestData(this.getClass().getSimpleName());
		log = TestLogging.getLogger(this.getClass().getSimpleName());
		ldriver = setUpEnvironment(executionType, browserName, platform, connectAddress, deviceName);
		LoadDriverManager.setWebdriver(ldriver);
	}

	@Test(description = "To validate a user is able to search the repository", priority = 0)
	public void validateSearchRepository() throws InterruptedException {
		LoadDriverManager.getWebdriver().get(configProp.getProperty("APP_URL"));
		GitHubMainPage gmainpage = new GitHubMainPage(ldriver);
		gmainpage.clickMenu();
		MLoginPage mloginpage = gmainpage.navigateToSignInPage();
		mloginpage.login(testdataprop.getProperty("USERNAME"),testdataprop.getProperty("PASSWORD"));
		
		Assert.assertTrue(checkisDisplayed(
				GenericPageObjects.MHomePage_Search_Tbox));
		
		MHomePage mhomepage =new MHomePage(ldriver);
		mhomepage.searchRepository(testdataprop.getProperty("REPOSITORYNAME"));
		mhomepage.selectRepository();
		MRepositoryHomePage mrepositoryHomePage = new MRepositoryHomePage(ldriver);
		mrepositoryHomePage.clickOnIssues();
		mrepositoryHomePage.addIssue();
		mrepositoryHomePage.enterIssueFields(testdataprop.getProperty("ISSUETITLE"), testdataprop.getProperty("ISSUECOMMENT"));
		
	}

	@AfterClass
	public void tearDown() {
		try{
			LoadDriverManager.getWebdriver().quit();
			stopServerService();
		}
		catch(Exception e){
			Reporter.reportStep("Error in cleaning the appium session", "FAIL");
		}
	}

}
