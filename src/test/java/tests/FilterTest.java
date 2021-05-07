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
		private static String reportPaht = System.getProperty("user.dir") + "\\test-output\\reportFilter.html";

		private WebDriver driver;
		private String baseUrl;
		//private String user;
		private static String email;
		private static String password;
		private static String adress;
		private static String userName;

		
		//pages
		private Main main;
		private Restaurant restaurant;
		private Login login;
		
		private static final Logger logger = LogManager.getLogger(FilterTest.class);

		
	

	@BeforeClass
	public void beforeClass() throws ParserConfigurationException, SAXException, IOException {
		PropertyConfigurator.configure(System.getProperty("user.dir") + "/log4j.properties");

		extent = new ExtentReports(reportPaht);
		extent.loadConfig(new File(System.getProperty("user.dir") + "\\resources\\extent-config.xml"));
		
		baseUrl = Utilities.getDataFromXML("info.xml", "baseUrl", 0);
		String browser =Utilities.getDataFromXML("info.xml", "browser", 0);
		email = Utilities.getDataFromXML("info.xml", "email", 0);
		password = Utilities.getDataFromXML("info.xml", "password", 0);
		userName = Utilities.getDataFromXML("info.xml", "userName", 0);
		adress = Utilities.getDataFromXML("info.xml", "adress", 0);
	   // user =Utilities.getDataFromXML("info.xml", "user", 0);
		
		//driver = GetDriver.getDriver(browser, baseUrl,user);
		driver = GetDriver.getDriver(browser, baseUrl);

		
		main = new Main(driver);
		restaurant = new Restaurant(driver);
		login = new Login(driver);
	}

	
	
	@BeforeMethod
	public void beforeMethod(Method method) throws IOException {
		myTest = extent.startTest(method.getName());
		myTest.log(LogStatus.INFO, "Starting test", "Start test");
	}
	
	@Test(priority = 1, enabled = true, description = "Login 10bis using Facebook")
	public void LoginUsingFacebook() throws InterruptedException, IOException, ParserConfigurationException, SAXException {

		logger.info("Going to connection page");
		main.login();
		logger.info("Going to login with facebook details");
		
		Assert.assertTrue(login.doLoginFacebook(email, password, userName, adress), "could not login with Facebook account, check logs");
		
		logger.info("Successfully Get into 10bis as registred user page (facebook login)");

	}
	

	
	/*  Prerequisite: getting into https://www.10bis.co.il/
	 * 		Given: Client is in site 
	 * 		When: Clicking in restaurant filter
	 *  	Then: Show the occurrences of filtered options in the page 
	 */
	
	@Test(priority = 2, enabled = true, description = "verify the occurrences of filter results")
	public void checkFilter() throws InterruptedException, IOException {
		
		logger.info("Going to 10Bis conected page");
		

		Assert.assertTrue(restaurant.useFilter());
		logger.info("Successfully verify the occurrences of filter results");

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
