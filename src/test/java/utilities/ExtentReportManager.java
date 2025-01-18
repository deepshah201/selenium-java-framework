package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.DataSourceResolver;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener {
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;
	String repName;

	public void onStart(ITestContext testContext) {
		String timeStemp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		repName = "test-report-" + timeStemp + ".html";
		sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName);
		sparkReporter.config().setReportName("OpenCart Automation Test Report");
		sparkReporter.config().setDocumentTitle("OpenCart Automation Test Results");
		sparkReporter.config().setTheme(Theme.STANDARD);

		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Tester", "Deep Shah");
		extent.setSystemInfo("Application", "OpenCart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub Module", "Customers");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Environment", "QA");
		String css = " .nav-logo {display: none; }";
		sparkReporter.config().setCss(css);

		String os = testContext.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("Operating System", os);

		String browser = testContext.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser", browser);

		List<String> includeGroup = testContext.getCurrentXmlTest().getIncludedGroups();

		if (!includeGroup.isEmpty()) {
			extent.setSystemInfo("Groups", includeGroup.toString());
		}

	}

	public void onTestSuccess(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.PASS, result.getName() + " got successfully executed");
	}

	public void onTestFailure(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.FAIL, result.getName() + " got failed");
		test.log(Status.INFO, result.getThrowable().getMessage());

		try {
			String imgPath = new BaseClass().captureScreen(result.getName());
			test.addScreenCaptureFromPath(imgPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void onTestSkipped(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP, result.getName() + " got skipped");
		test.log(Status.INFO, result.getThrowable().getMessage());
	}

	public void onFinish(ITestContext context) {
		extent.flush();
		String pathOfExtentReport = System.getProperty("user.dir") + "\\reports\\" + repName;
		File extentReport = new File(pathOfExtentReport);
		String subject = "OpenCart Automation Test Report: " + System.getProperty("user.name");
		String messageBody = "<html>" + "<body>" + "<p style='font-size: 16px;'>Dear Team,</p>"
				+ "<p style='font-size: 16px;'>I hope this message finds you well. Please find attached the Extent Report generated from the recent automation tests conducted on the OpenCart application.</p>"
				+ "<p style='font-size: 16px;'>If you have any questions or require further information, please do not hesitate to reach out.</p>"
				+ "<p style='font-size: 16px;'>Best regards,<br>Deep Shah</p>" + "</body>" + "</html>";

		try {
			Desktop.getDesktop().browse(extentReport.toURI());
		} catch (Exception e) {
			e.printStackTrace();
		}

//		try {
//			URL url = new URL("file:///" + System.getProperty("user.dir") + "\\reports\\" + repName);
//			ImageHtmlEmail email = new ImageHtmlEmail();
//			email.setDataSourceResolver(new DataSourceUrlResolver(url));
//			email.setHostName("smtp.gmail.com");
//			email.setSmtpPort(465);
//			email.setAuthenticator(new DefaultAuthenticator("xyz@gmail.com", "<app password>"));
//			email.setSSLOnConnect(true);
//			email.setFrom("xyz@gmail.com");
//			email.setSubject(subject);
//			email.setMsg(messageBody);
//			email.addTo("xyz@gmail.com");
//			email.attach(url, "extent report", "Please check report...");
//			email.send();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

}
