package com.appln.reporting;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.relevantcodes.extentreports.ExtentReports;

public class ExtentManager {
	private static ExtentReports instance;
	public static String currenttimedate;
	static{
		DateFormat df = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
		Date dateobj = new Date();
		currenttimedate = df.format(dateobj);
		currenttimedate = currenttimedate.replace(':', '-');
	}
	public static synchronized ExtentReports getInstance() {
		if (instance == null) {
			String resultfileName = "result"+ currenttimedate +".html";
			instance = new ExtentReports("./reports/"+ resultfileName, false);
			instance.loadConfig(new File("./extent-config.xml"));
		}
		
		return instance;
	}
}
