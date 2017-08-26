package com.appln.utils;

import java.util.Set;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

public class TestListener implements ITestListener {
  
public void onTestStart(ITestResult result) {
	// TODO Auto-generated method stub
	
}

public void onTestSuccess(ITestResult result) {
	// TODO Auto-generated method stub
	
}

public void onTestFailure(ITestResult result) {
	}

public void onTestSkipped(ITestResult result) {
	// TODO Auto-generated method stub
	
}

public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	// TODO Auto-generated method stub
	
}

public void onStart(ITestContext context) {
	// TODO Auto-generated method stub
	
}

public void onFinish(ITestContext context) {
		/*Set<ITestResult> failedTests = context.getFailedTests().getAllResults();
		Set<ITestResult> skippedTests = context.getSkippedTests().getAllResults();
		for (ITestResult temp : failedTests) {
			ITestNGMethod method = temp.getMethod();
			if (context.getFailedTests().getResults(method).size() > 1) {
				failedTests.remove(temp);
			} else {
				if (context.getPassedTests().getResults(method).size() > 0) {
					failedTests.remove(temp);
				}
			}
		}
		for (ITestResult temp : skippedTests) {
			ITestNGMethod method = temp.getMethod();
			if (context.getSkippedTests().getResults(method).size() > 1) {
				skippedTests.remove(temp);
			} else {
				if (context.getPassedTests().getResults(method).size() > 0) {
					skippedTests.remove(temp);
				}
			}
		}*/
	}
}
