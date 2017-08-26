package com.appln.utils;

import org.openqa.selenium.remote.RemoteWebDriver;

public class LoadDriverManager {

	public static ThreadLocal<RemoteWebDriver> webdriver = new ThreadLocal<RemoteWebDriver>();

	public static RemoteWebDriver getWebdriver() {
		return webdriver.get();
	}
	

	public static void setWebdriver(RemoteWebDriver driver) {
		webdriver.set(driver);
		}
	
	
	
	
	
}
