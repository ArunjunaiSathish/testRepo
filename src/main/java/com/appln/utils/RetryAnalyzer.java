package com.appln.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

	int counter = 0;
	int retryLimit = 1;
	public boolean retry(ITestResult result) {
		if(counter<retryLimit){
			System.out.println("Retrying Test"+result.getName()+"with status"+getResultStatusName(result.getStatus())+ " for the " + (counter+1) + " time(s).");
		counter++;
		return true;
		}
		
		return false;
	}
  
	public String getResultStatusName(int status){
		String resultName = null;
    	if(status==1)
    		resultName = "SUCCESS";
    	if(status==2)
    		resultName = "FAILURE";
    	if(status==3)
    		resultName = "SKIP";
		return resultName;
    }
	
}

