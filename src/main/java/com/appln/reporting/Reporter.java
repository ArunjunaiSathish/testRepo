package com.appln.reporting;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.appln.utils.LoadDriverManager;
import com.appln.wrappers.GenericWrappers;
import com.relevantcodes.extentreports.LogStatus;

public class Reporter extends GenericWrappers {

	public static String currenttimedate;
	static {
		DateFormat df = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
		Date dateobj = new Date();
		currenttimedate = df.format(dateobj);
		currenttimedate = currenttimedate.replace(':', '-');
	}

	public static void reportStep(String desc, String status) {
		long number = (long) Math.floor(Math.random() * 900000000L) + 10000000L;
		try {
			FileUtils.copyFile(((TakesScreenshot) LoadDriverManager.getWebdriver()).getScreenshotAs(OutputType.FILE),
					new File("./reports/images/" + currenttimedate + "/" + number + ".jpg"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Write if it is successful or failure or information
		if (status.toUpperCase().equals("PASS")) {
			ExtentTestManager.getTest().log(LogStatus.PASS, desc);
		} else if (status.toUpperCase().equals("FAIL")) {
			ExtentTestManager.getTest().log(LogStatus.FAIL, desc + ExtentTestManager.getTest()
					.addScreenCapture("./../reports/images/" + currenttimedate + "/" + number + ".jpg"));
			throw new RuntimeException("FAILED");
		} else if (status.toUpperCase().equals("INFO")) {
			ExtentTestManager.getTest().log(LogStatus.INFO, desc);
		}
	}

}
