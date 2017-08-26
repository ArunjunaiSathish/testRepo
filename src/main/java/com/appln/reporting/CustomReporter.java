package com.appln.reporting;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Date;

import org.testng.IInvokedMethod;
import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.collections.Lists;
import org.testng.xml.XmlSuite;

public class CustomReporter implements IReporter {

	private PrintWriter m_out;
	private int m_row;
	private Integer m_testIndex;
	String formattedDate = "";
	

	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites,
			String outputDirectory) {
		new File(outputDirectory).mkdirs();
		try {
			java.util.Date todayDate = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			 formattedDate = formatter.format(todayDate).replace("-", "_");
			 print("Date is: "+formattedDate);
			m_out = new PrintWriter(new BufferedWriter(new FileWriter(new File(
					outputDirectory, "custom-report.html"))));
		} catch (IOException e) {
			System.out.println("Error in creating writer: " + e);
		}
		startHtml();
		generateSummaryReport(suites);
		generateSuiteSummaryReport(suites);
		endHtml();
		m_out.flush();
		m_out.close();
	}

	private void print(String text) {
		System.out.println(text);
		//m_out.println(text + "<br/>");
	}

	protected void startHtml() {
		m_out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
		m_out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
		m_out.println("<head>");
		m_out.println("<title>Automation Report</title>");
		m_out.println("<style type=\"text/css\">");
		m_out.println("h3 {text-align: center;}");
		m_out.println("table {margin-bottom:10px;border-collapse:collapse;empty-cells:show;margin: auto;width :100%}");
		m_out.println("th {background-color: silver;color: #020202;border:1px solid #009;padding:.25em .5em}");
		m_out.println("td {border:1px solid #009;padding:.25em .5em}");
		m_out.println(".specialRow {font-weight:bold;text-align: center;background-color:#AFCADA;color: #FFFFFF}");
		m_out.println(".result th {vertical-align:bottom}");
		m_out.println(".param th {padding-left:1em;padding-right:1em}");
		m_out.println(".param td {padding-left:.5em;padding-right:2em}");
		m_out.println(".stripe td,.stripe th {background-color: #E6EBF9}");
		m_out.println(".numi,.numi_attn {text-align:center}");
		m_out.println(".statusPassed,.numipassed {background-color: #A7C699;text-align:center}");
		m_out.println(".statusSkipped,.numiskipped {background-color: #FAF4BE;text-align:center}");
		m_out.println(".statusFailed,.numifailed {background-color: #F9B5B1;text-align:center}");
		m_out.println(".statusFailed,.stripe {background-color: #F9B5B1;}");
		m_out.println(".stacktrace {white-space:pre;font-family:monospace}");
		m_out.println(".totop {font-size:85%;text-align:center;border-bottom:2px solid #000}");
		m_out.println("</style>");
		m_out.println("</head>");
		m_out.println("<body>");
		m_out.println("<h3>Automation Report</h3><br>");
	}

	protected void endHtml() {
		m_out.println("</body></html>");
	}

	private Collection<ITestNGMethod> getMethodSet(IResultMap tests,
			ISuite suite) {
		List<IInvokedMethod> r = Lists.newArrayList();
		List<IInvokedMethod> invokedMethods = suite.getAllInvokedMethods();
		for (IInvokedMethod im : invokedMethods) {
			if (tests.getAllMethods().contains(im.getTestMethod())) {
				r.add(im);
			}
		}
		
		List<ITestNGMethod> result = Lists.newArrayList();

		for (IInvokedMethod m : r) {
			result.add(m.getTestMethod());
		}

		for (ITestNGMethod m : tests.getAllMethods()) {
			if (!result.contains(m)) {
				result.add(m);
			}
		}
		return result;
	}
	
	public void generateSummaryReport(List<ISuite> suites) {
		
		m_out.println("<h3 style='margintop:10%'>Summary</h3><br>");
		tableStart("Overview", null);
		m_out.print("<tr>");
		//tableColumnStart("Total Flows");
		tableColumnStart("Total<br/>Scenarios");
		tableColumnStart("Passed<br/>(Pass %)");
		tableColumnStart("Skipped");
		tableColumnStart("Failed");
		tableColumnStart("Total Time<br/>in Minutes");
		tableColumnStart("Date");
		
		m_out.println("</tr>");
		NumberFormat formatter = new DecimalFormat("#,##0.0");
		int qty_tests = 0;
		int qty_total = 0;
		int qty_pass_m = 0;
		int qty_skip = 0;
		int qty_fail = 0;
		int flows_nbr = 0;
		long time_start = Long.MAX_VALUE;
		long time_end = Long.MIN_VALUE;
		m_testIndex = 1;
		for (ISuite suite : suites) {
			/*if (suites.size() > 1) {
				titleRow(suite.getName(), 8);
			}*/
			Map<String, ISuiteResult> tests = suite.getResults();
			flows_nbr = tests.size();
			//print("Number of flows are-----------------------"+tests.size());
			for (ISuiteResult r : tests.values()) {
				
				qty_tests += 1;
				ITestContext overview = r.getTestContext();
				//startSummaryRow(overview.getName());

				int q = getMethodSet(overview.getPassedTests(), suite).size()
						+ getMethodSet(overview.getSkippedTests(), suite)
						.size()
						+ getMethodSet(overview.getFailedTests(), suite).size();
				
				String passPercentage = formatter.format((getMethodSet(overview.getPassedTests(), suite).size() / Double.valueOf(q)) * 100);
				
				qty_total += q;
				//summaryCell(q, Integer.MAX_VALUE);
				q = getMethodSet(overview.getPassedTests(), suite).size();
				qty_pass_m += q;
				//summaryCell(q+" ("+passPercentage+"%)", (q<= Integer.MAX_VALUE));
				q = getMethodSet(overview.getSkippedTests(), suite).size();
				qty_skip += q;
				//summaryCell(q, 0);
				q = getMethodSet(overview.getFailedTests(), suite).size();
				qty_fail += q;
				//summaryCell(q, 0);

				time_start = Math.min(overview.getStartDate().getTime(),
						time_start);
				time_end = Math.max(overview.getEndDate().getTime(), time_end);
				//summaryCell(
						//formatter.format(((overview.getEndDate().getTime() - overview
						//		.getStartDate().getTime()) / 1000.) / 60.)
						//		+ " Mins", true);
				//m_out.println("</tr>");
				m_testIndex++;
			}
		}
		if (qty_tests > 1) {
			
			//m_out.println("<tr class=\"total\" border-bottom:1pt solid black;><td style=\"text-align:center;color:#641e16;font-weight:bold;\">"+flows_nbr+"</td>");
			m_out.println("<td style=\"text-align:center;color:#641e16;font-weight:bold;\">"+qty_total+"</td>");
			String passPercentage = formatter.format((Double.valueOf(qty_pass_m)/Double.valueOf(qty_total))*100);
			m_out.println("<td style=\"text-align:center;background-color: #A7C699;color:#641e16;font-weight:bold;\">"+qty_pass_m + " (" + passPercentage + "%)"+"</td>");
			m_out.println("<td style=\"text-align:center;background-color: #FAF4BE;color:#641e16;font-weight:bold;\">"+qty_skip+"</td>");
			m_out.println("<td style=\"text-align:center;background-color: #FF514D;color:#641e16;font-weight:bold;\">"+qty_fail+"</td>");
			m_out.println("<td style=\"text-align:center;color:#641e16;font-weight:bold;\">"+formatter.format(((time_end - time_start) / 1000.) / 60.)+ " Mins"+"</td>");
			m_out.println("<td style=\"text-align:center;color:#641e16;font-weight:bold;\">"+formattedDate+"</td>");
			m_out.println("</tr>");
		}
		m_out.println("</table>");
		
	}
	
	public void generateSuiteSummaryReport(List<ISuite> suites) {
		m_out.println("</br>");
		m_out.println("</br>");
		m_out.println("<h3 style='margintop:10%'>Detailed Report</h3><br>");
		tableStart("testOverview", null);
		m_out.print("<tr>");
		tableColumnStart("Flow Name");
		tableColumnStart("Total<br/>Testcases");
		tableColumnStart("Passed<br/>(Pass %)");
		tableColumnStart("Skipped");
		tableColumnStart("Failed");
		tableColumnStart("Total Time<br/>in Minutes");

		m_out.println("</tr>");
		NumberFormat formatter = new DecimalFormat("#,##0.0");
		int qty_tests = 0;
		int qty_total = 0;
		int qty_pass_m = 0;
		int qty_skip = 0;
		int qty_fail = 0;
		long time_start = Long.MAX_VALUE;
		long time_end = Long.MIN_VALUE;
		m_testIndex = 1;
		for (ISuite suite : suites) {
			if (suites.size() > 1) {
				titleRow(suite.getName(), 8);
			}
			Map<String, ISuiteResult> tests = suite.getResults();
			//print("Number of flows are-----------------------"+tests.size());
			for (ISuiteResult r : tests.values()) {
				
				qty_tests += 1;
				ITestContext overview = r.getTestContext();
				startSummaryRow(overview.getName());

				int q = getMethodSet(overview.getPassedTests(), suite).size()
						+ getMethodSet(overview.getSkippedTests(), suite)
						.size()
						+ getMethodSet(overview.getFailedTests(), suite).size();
				
				String passPercentage = formatter.format((getMethodSet(overview.getPassedTests(), suite).size() / Double.valueOf(q)) * 100);
				
				qty_total += q;
				summaryCell(q, Integer.MAX_VALUE);
				q = getMethodSet(overview.getPassedTests(), suite).size();
				qty_pass_m += q;
				summaryCell(q+" ("+passPercentage+"%)", (q<= Integer.MAX_VALUE));
				q = getMethodSet(overview.getSkippedTests(), suite).size();
				qty_skip += q;
				summaryCell(q, 0);
				q = getMethodSet(overview.getFailedTests(), suite).size();
				qty_fail += q;
				summaryCell(q, 0);

				time_start = Math.min(overview.getStartDate().getTime(),
						time_start);
				time_end = Math.max(overview.getEndDate().getTime(), time_end);
				summaryCell(
						formatter.format(((overview.getEndDate().getTime() - overview
								.getStartDate().getTime()) / 1000.) / 60.)
								+ " Mins", true);
				m_out.println("</tr>");
				m_testIndex++;
			}
		}
		if (qty_tests > 1) {
			
			m_out.println("<tr class=\"total\" border-bottom:1pt solid black;><td style=\"text-align:center;color:#641e16;font-weight:bold;\">Total</td>");
			m_out.println("<td style=\"text-align:center;color:#641e16;font-weight:bold;\">"+qty_total+"</td>");
			String passPercentage = formatter.format((Double.valueOf(qty_pass_m)/Double.valueOf(qty_total))*100);
			m_out.println("<td style=\"text-align:center;background-color: #A7C699;color:#641e16;font-weight:bold;\">"+qty_pass_m + " (" + passPercentage + "%)"+"</td>");
			m_out.println("<td style=\"text-align:center;background-color: #FAF4BE;color:#641e16;font-weight:bold;\">"+qty_skip+"</td>");
			m_out.println("<td style=\"text-align:center;background-color: #FF514D;color:#641e16;font-weight:bold;\">"+qty_fail+"</td>");
			m_out.println("<td style=\"text-align:center;color:#641e16;font-weight:bold;\">"+formatter.format(((time_end - time_start) / 1000.) / 60.)+ " Mins"+"</td>");
			
			m_out.println("</tr>");
		}
		m_out.println("</table>");
	}

	private void summaryCell(String v, boolean isgood) {
		m_out.print("<td class=\"numi" + (isgood ? "" : "_attn") + "\">" + v
				+ "</td>");
	}

	private void summaryCell(String v, boolean isgood, String status) {
		m_out.print("<td class=\"numi" + status + " style=\"text-align:center;color:#000000;font-weight:bold;\">" + v + "</td>");
	}

	private void startSummaryRow(String label) {
		m_row += 1;
		m_out.print("<tr"
				+ (m_row % 2 == 0 ? " class=\"stripe\"" : "")
				+ "><td style=\"text-align:center;padding-right:2em\">" + label + "</td>");
	}

	private void summaryCell(int v, int maxexpected) {
		summaryCell(String.valueOf(v), v <= maxexpected);
	}

	private void summaryCell(int v, int maxexpected, String status) {
		summaryCell(String.valueOf(v), v <= maxexpected, status);
	}

	private void tableStart(String cssclass, String id) {
		m_out.println("<table cellspacing=\"0\" cellpadding=\"0\""
				+ (cssclass != null ? " class=\"" + cssclass + "\""
						: " style=\"padding-bottom:2em\"")
				+ (id != null ? " id=\"" + id + "\"" : "") + ">");
		m_row = 0;
	}

	private void tableColumnStart(String label) {
		m_out.print("<th>" + label + "</th>");
	}

	private void titleRow(String label, int cq) {
		titleRow(label, cq, null);
	}

	private void titleRow(String label, int cq, String id) {
		m_out.print("<tr");
		if (id != null) {
			m_out.print(" id=\"" + id + "\" class=\"specialRow\"");
		}
		m_out.println("><td style=\"text-align:center;color:#7d3c98 ;font-weight:bold;padding-right:2em\" colspan=\"" + cq + "\"><i>" + label + "</></td></tr>");
		m_row = 0;
	}
}