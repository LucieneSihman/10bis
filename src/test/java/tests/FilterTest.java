package tests;

import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import pages.Login;
import pages.Main;
import pages.Restaurant;
import utilites.GetDriver;
import utilites.Utilities;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;


public class FilterTest {

	// Global variables 
	// Add extent reports
	private ExtentReports extent;
	private ExtentTest myTest;
	private static String reportPaht = System.getProperty("user.dir") + "\\test-output\\FilterReport.html";

	private WebDriver driver;
	
	
	//pages
	private Main main;
	private Login login;
	private Restaurant restaurant;
	
	private static String email;
	private static String password;
	private static String browser;
	private static String baseUrl;
	private static String userName;
	private static String adress;
	
	
	private static final Logger logger = LogManager.getLogger(FilterTest.class);

	

	@BeforeClass
	public void beforeClass() throws ParserConfigurationException, SAXException, IOException {
		PropertyConfigurator.configure(System.getProperty("user.dir") + "/log4j.properties");

		extent = new ExtentReports(reportPaht);
		extent.loadConfig(new File(System.getProperty("user.dir") + "\\resources\\extent-config.xml"));
		
		baseUrl = Utilities.getDataFromXML("info.xml", "baseUrl", 0);
		browser = Utilities.getDataFromXML("info.xml", "browser", 0);
		email = Utilities.getDataFromXML("info.xml", "email", 0);
		password = Utilities.getDataFromXML("info.xml", "password", 0);
		userName = Utilities.getDataFromXML("info.xml", "userName", 0);
		adress = Utilities.getDataFromXML("info.xml", "adress", 0);
		
		driver = GetDriver.getDriver(browser, baseUrl);
		
		main = new Main(driver);
		login = new Login(driver);
        restaurant = new Restaurant(driver);
	}

	
	
	@BeforeMethod
	public void beforeMethod(Method method) throws IOException {
		myTest = extent.startTest(method.getName());
		myTest.log(LogStatus.INFO, "Starting test", "Start test");
	}
	

	
	/*  Prerequisite: getting into https://www.10bis.co.il/
	 * 		Given: Client is in site 
	 * 		When: click register link
	 *  	Then: Getting into Registration page
	 */
	
	@Test(priority = 1, enabled = true, description = "verify filter results")
	public void checkFilter() throws InterruptedException, IOException {
		
		logger.info("Going to registration page");
		main.login();
		login.doLoginFacebook(email, password, userName, adress);
		Assert.assertTrue(restaurant.useFilter());
		logger.info("Successfully Get Register page");

	}
	
	
	
	@AfterMethod
	public void afterMethod(ITestResult result) throws IOException {

		if (result.getStatus() == ITestResult.FAILURE) {
			myTest.log(LogStatus.FAIL, "Test failed: " + result.getName());
			myTest.log(LogStatus.FAIL, "Test failed reason: " + result.getThrowable());
			myTest.log(LogStatus.FAIL, myTest.addScreenCapture(Utilities.takeScreenShot(driver)));
		}
		else {
			myTest.log(LogStatus.PASS, result.getName(), "Verify successful ");
			myTest.log(LogStatus.PASS, myTest.addScreenCapture(Utilities.takeScreenShot(driver)));

		}

		myTest.log(LogStatus.INFO, "Finish test", "Finish test ");
		extent.endTest(myTest);
	
		
	}

	@AfterClass
	public void afterClass() {
		extent.flush();
		extent.close();
		driver.quit();

	}
	
}
