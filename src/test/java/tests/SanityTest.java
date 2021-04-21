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


public class SanityTest {

	// Global variables 
	// Add extent reports
	private ExtentReports extent;
	private ExtentTest myTest;
	private static String reportPaht = System.getProperty("user.dir") + "\\test-output\\reportSanity.html";

	private WebDriver driver;

	//pages
	private Main main;
	private Login login;
	private Restaurant restaurant;
	
	private static final Logger logger = LogManager.getLogger(SanityTest.class);
	private static String email;
	private static String password;
	private static String browser;
	private static String baseUrl;
	//private static String user;
	private static String userName;
	private static String adress;
	private static String restaurantName;
	
	
	//Restaurant search and order details:
		private final String paymentText = "אמצעי תשלום";;


	@BeforeClass
	public void beforeClass() throws ParserConfigurationException, SAXException, IOException, InterruptedException {
		PropertyConfigurator.configure(System.getProperty("user.dir") + "/log4j.properties");

		extent = new ExtentReports(reportPaht);
		extent.loadConfig(new File(System.getProperty("user.dir") + "\\resources\\extent-config.xml"));
		
		baseUrl = Utilities.getDataFromXML("info.xml", "baseUrl", 0);
		browser = Utilities.getDataFromXML("info.xml", "browser", 0);
		//user = Utilities.getDataFromXML("info.xml", "user", 0);
		email = Utilities.getDataFromXML("info.xml", "email", 0);
		password = Utilities.getDataFromXML("info.xml", "password", 0);
		userName = Utilities.getDataFromXML("info.xml", "userName", 0);
		adress = Utilities.getDataFromXML("info.xml", "adress", 0);
		restaurantName = Utilities.getDataFromXML("info.xml", "restaurantName", 0);

        Thread.sleep(2000);
		
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
	 * 		Given: Client click connection and  
	 * 		When: give Facebook login details and click login
	 *  	Then: Getting into 10bis as registered user
	 */
	
	
	@Test(priority = 1, enabled = true, description = "Login 10bis using Facebook")
	public void LoginUsingFacebook() throws InterruptedException, IOException, ParserConfigurationException, SAXException {

		logger.info("Going to connection page");
		main.login();
		logger.info("Going to login with facebook details");
		
		Assert.assertTrue(login.doLoginFacebook(email, password, userName, adress), "could not login with Facebook account, check logs");
		
		logger.info("Successfully Get into 10bis as registred user page (facebook login)");

	}
	
	/*   	Given: Client click in search field  
	 * 		When: give restaurant name and click search
	 *  	Then: Getting into the restaurant page
	 */
	
	@Test(priority = 2, enabled = true, description = "Do restaurant search in 10bis site")
	public void searchForRestaurant() throws InterruptedException, IOException, ParserConfigurationException, SAXException {

		logger.info("Going to search field");
		logger.info("Type restaurant name to search");
		
		Assert.assertTrue(restaurant.RestaurantSearch(restaurantName), "could not find the restaurant you searched, check logs");
		
		logger.info("Successfully Get into restaurante page");

	}
	
	/*   	Given: Client is in restaurant page  
	 * 		When: click on dishes to order and payment
	 *  	Then: Getting into the payment page
	 */
	
	@Test(priority = 3, enabled = true, description = "Order dishes and go to payment page ")
	public void orderFromRestaurant() throws InterruptedException, IOException, ParserConfigurationException, SAXException {

		logger.info("Going to dishes options in restaurant page");
		
		logger.info("Click on dishes to order");
		
		Assert.assertTrue(restaurant.RestaurantOrder(paymentText), "could not order the dishes, check logs");
		
		logger.info("Successfully Get into payment page");

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
	
		//return to base URL 
		//driver.get(baseUrl);
	}

	@AfterClass
	public void afterClass() {
		extent.flush();
		extent.close();
		driver.quit();

	}
	
}