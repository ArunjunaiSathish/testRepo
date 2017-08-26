package com.appln.utils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;

import com.appln.logger.TestLogging;
import com.appln.reporting.ExtentManager;
import com.appln.reporting.ExtentTestManager;
import com.appln.reporting.Reporter;
import com.appln.wrappers.GenericWrappers;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class EnvironmentSetup extends GenericWrappers {

	protected static String testcaseName;
	protected static String testCaseDescription;
	protected static String dataSheetName;
	protected static TestLogging logger;
	protected Logger log;
	protected RemoteWebDriver driver = null;
	public ExtentTest testReporter;
	private AppiumDriverLocalService service;
	private AppiumServiceBuilder builder;

	@BeforeMethod
	public void beforeMethod(Method caller) {
		ExtentTestManager.startTest(caller.getName(), this.getClass().getName());
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
		if (result.isSuccess()) {
			ExtentTestManager.getTest().log(LogStatus.PASS, "Testcase passed");
		} else if (result.getStatus() == ITestResult.FAILURE) {
			ExtentTestManager.getTest().log(LogStatus.FAIL, "Testcase failed with error"+result.getThrowable().getMessage());
		} else if (result.getStatus() == ITestResult.SKIP) {
			ExtentTestManager.getTest().log(LogStatus.SKIP, "Testcase skipped");
		}

		ExtentTestManager.endTest();
		ExtentManager.getInstance().flush();
	}

	@AfterSuite
	public void afterSuite() {
		ExtentManager.getInstance().flush();
	}

	public RemoteWebDriver setUpEnvironment(String executionType, String browserName, String platform,
			String connectAddress, String deviceName) throws IOException, InterruptedException {
		if (executionType.equalsIgnoreCase("localenvironment")) {
			setCapabilities(browserName, platform, connectAddress, deviceName);
			if (browserName.equalsIgnoreCase("chrome")) {
				return new ChromeDriver(capabilites);
			} else if (browserName.equalsIgnoreCase("firefox")) {
				return new FirefoxDriver(capabilites);
			} else if (browserName.equalsIgnoreCase("ie")) {
				return new InternetExplorerDriver(capabilites);
			}
		} else if (executionType.equalsIgnoreCase("gridenvironment")) {
			setCapabilities(browserName, platform, connectAddress, deviceName);
			return new RemoteWebDriver(new URL("http://" + connectAddress + "/wd/hub"), capabilites);
		} else if (executionType.equalsIgnoreCase("localmobileenvironment")) {
			setCapabilities(browserName, platform, connectAddress, deviceName);
			return new AndroidDriver<WebElement>(capabilites);
		} else {
			Reporter.reportStep("Configuration failure", "FAIL");
		}
		return driver;
	}

	public void startServerService() {
		// Set Capabilities
		capabilites = new DesiredCapabilities();
		capabilites.setCapability("noReset", "false");

		// Build the Appium service
		builder = new AppiumServiceBuilder();
		builder.withIPAddress("127.0.0.1");
		builder.usingAnyFreePort();
		builder.withCapabilities(capabilites);
		builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
		builder.withArgument(GeneralServerFlag.LOG_LEVEL, "error");

		// Start the server with the builder
		service = AppiumDriverLocalService.buildService(builder);
		service.start();
	}

	public void stopServerService() {
		service.stop();
	}

	public void setCapabilities(String browserName, String platform, String connectAddress, String deviceName)
			throws IOException, InterruptedException {
		if (platform.equalsIgnoreCase("windows")) {
			if (browserName.equals("chrome")) {
				
				System.setProperty("webdriver.chrome.driver", "./src/main/resources/chromedriver.exe");
				capabilites = DesiredCapabilities.chrome();
				ChromeOptions options = new ChromeOptions();
				options.addArguments("start-maximized");
				options.addArguments("--disable-extensions");
				capabilites.setBrowserName(browserName);
				capabilites.setPlatform(Platform.WINDOWS);
				capabilites.setCapability(ChromeOptions.CAPABILITY, options);
			} else if (browserName.equalsIgnoreCase("firefox")) {
				System.setProperty("webdriver.gecko.driver", "./src/main/resources/geckodriver.exe");
				FirefoxProfile profile = new FirefoxProfile();
				profile.setAcceptUntrustedCertificates(false);
				profile.setAssumeUntrustedCertificateIssuer(true);
				capabilites = DesiredCapabilities.firefox();
				FirefoxOptions options = new FirefoxOptions();
				options.setBinary(configProp.getProperty("FIREFOX_PATH"));
				capabilites.setCapability("marionette", true);
				capabilites.setAcceptInsecureCerts(false);
				capabilites.setCapability("moz:firefoxOptions", options);
				capabilites.setPlatform(Platform.WINDOWS);
			} else if (browserName.equalsIgnoreCase("ie")) {
				System.setProperty("webdriver.ie.driver", "./src/main/resources/IEDriverServer.exe");
				capabilites = DesiredCapabilities.internetExplorer();
				capabilites.setBrowserName("internet explorer");
				capabilites.setPlatform(Platform.WINDOWS);
				capabilites.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
						true);
			} 
			else {
				Reporter.reportStep("Invalid configuration & browser selection", "FAIL");
			}

		}
		else if (platform.equalsIgnoreCase("linux")) {
			if (browserName.equals("chrome")) {
				System.setProperty("webdriver.chrome.driver", "./src/main/resources/chromedriver.exe");
				capabilites = DesiredCapabilities.chrome();
				ChromeOptions options = new ChromeOptions();
				options.addArguments("start-maximized");
				options.addArguments("--disable-extensions");
				capabilites.setBrowserName(browserName);
				capabilites.setPlatform(Platform.LINUX);
				capabilites.setCapability(ChromeOptions.CAPABILITY, options);
			} else if (browserName.equalsIgnoreCase("firefox")) {
				System.setProperty("webdriver.gecko.driver", "./src/main/resources/geckodriver.exe");
				FirefoxProfile profile = new FirefoxProfile();
				profile.setAcceptUntrustedCertificates(false);
				profile.setAssumeUntrustedCertificateIssuer(true);
				capabilites = DesiredCapabilities.firefox();
				FirefoxOptions options = new FirefoxOptions();
				options.setBinary(configProp.getProperty("FIREFOX_PATH"));
				capabilites.setCapability("marionette", true);
				capabilites.setAcceptInsecureCerts(false);
				capabilites.setCapability("moz:firefoxOptions", options);
				capabilites.setPlatform(Platform.LINUX);
			} else if (browserName.equalsIgnoreCase("ie")) {
				System.setProperty("webdriver.ie.driver", "./src/main/resources/IEDriverServer.exe");
				capabilites = DesiredCapabilities.internetExplorer();
				capabilites.setBrowserName("internet explorer");
				capabilites.setPlatform(Platform.LINUX);
				capabilites.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
						true);
			} 
			else {
				Reporter.reportStep("Invalid configuration & browser selection", "FAIL");	
				}

		}
		else if (platform.equalsIgnoreCase("android")) {
			if (browserName.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver", "./src/main/resources/chromedriver.exe");
				capabilites = DesiredCapabilities.chrome();
				ChromeOptions options = new ChromeOptions();
				options.addArguments("start-maximized");
				options.addArguments("--disable-popup-blocking");
				options.addArguments("--disable-extensions");
				capabilites.setBrowserName(browserName);
				capabilites.setCapability(ChromeOptions.CAPABILITY, options);
				capabilites.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
				capabilites.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
				capabilites.setCapability("autoAcceptAlerts", true);
			} else {
				Reporter.reportStep("Invalid configuration", "FAIL");
			}
		}
		else {
			Reporter.reportStep("Invalid Platform selection", "FAIL");	
		}
	}

}
