/*package com.appln.testcases;

import java.io.IOException;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.appln.logger.TestLogging;
import com.appln.pageobjects.GenericPageObjects;
import com.appln.pages.BookingDetailsPage;
import com.appln.pages.HomePage;
import com.appln.pages.SearchFlightsPage;
import com.appln.pages.SearchResultsPage;
import com.appln.reporting.Reporter;
import com.appln.utils.EnvironmentSetup;
import com.appln.utils.LoadDriverManager;

public class VerifyBookingFunctionality extends EnvironmentSetup {

	protected RemoteWebDriver ldriver = null;

	@BeforeClass
	@Parameters({ "executiontype", "browser", "platform", "connectionAddress", "DeviceName" })

	public void setUp(String executionType, String browserName, String platform, String connectAddress,
			String deviceName) throws IOException, InterruptedException {
		testcaseName = "Verify Booking Functionality";
		testCaseDescription = "To validate a booking functionality";
		dataSheetName = "testdata";
		loadtestData(this.getClass().getSimpleName());
		log = TestLogging.getLogger(this.getClass().getSimpleName());
		ldriver = setUpEnvironment(executionType, browserName, platform, connectAddress, deviceName);
		ldriver.manage().window().maximize();
		LoadDriverManager.setWebdriver(ldriver);
		
		 * System.out.println("ThreadID" +Thread.currentThread().getId());
		 * System.out.println("Hashcode of webDriver instance = "
		 * +LoadDriverManager.getWebdriver().hashCode());
		 
	}

	@Test(description = "To validate a user is able to book a plan - Happy path flow", priority = 0)
	public void validateBookingSearch() throws InterruptedException {
		log.info("Testcase started");
		LoadDriverManager.getWebdriver().get(configProp.getProperty("APP_URL"));
		HomePage homepage = new HomePage(LoadDriverManager.getWebdriver());
		SearchFlightsPage searchflightspage = homepage.clickOnFlights();
		searchflightspage.selectTripType(testdataprop.getProperty("TRIP_TYPE"));
		searchflightspage.selectFromToCity(testdataprop.getProperty("ORIGIN"), testdataprop.getProperty("DESTINATION"));
		searchflightspage.selectDepartDate(testdataprop.getProperty("MONTH"), testdataprop.getProperty("DATE"),
				dataProp.getProperty("YEAR"));
		searchflightspage.selectAdults(testdataprop.getProperty("NUMBEROFPASSENGERS"));
		log.info("Search flights step yet to start");	
		searchflightspage.clickOnSearchFlights();
		Assert.assertTrue(LoadDriverManager.getWebdriver().getTitle().contains(testdataprop.getProperty("ORIGIN")));
		SearchResultsPage searchresultspage = new SearchResultsPage(ldriver);
		BookingDetailsPage bookdetailspage = searchresultspage.bookAPackage(4);
		bookdetailspage.enterItenary();
		bookdetailspage.clickContinueBookingInItenary();
		bookdetailspage.enterEmailID(testdataprop.getProperty("EMAIL_ID"));
		bookdetailspage.enterTravellerNameDetails(testdataprop.getProperty("TITLE"),
				testdataprop.getProperty("FIRST_NAME"), testdataprop.getProperty("LAST_NAME"),
				testdataprop.getProperty("MOBILE_NUMBER"));
		Assert.assertTrue(checkisDisplayed(GenericPageObjects.BookingDetailsPage_paymentbutton));
		LoadDriverManager.getWebdriver().close();
		log.info("Testcase completed");
	}

	@AfterClass
	public void tearDown() {
		try {
			LoadDriverManager.getWebdriver().quit();
		} catch (Exception e) {
			Reporter.reportStep("Error in cleaning the Web browser session", "FAIL");
		}
	}

}
*/