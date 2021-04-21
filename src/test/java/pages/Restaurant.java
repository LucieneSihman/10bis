package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Restaurant extends Base {

	public Restaurant(WebDriver driver) {
		super(driver);
	}

	// Go to restaurant page
	public boolean RestaurantSearch(String restaurantName) throws InterruptedException {

		//click on search field and type restaurant name
		click(By.xpath("//*[@id=\"root\"]/div[2]/header//input"));
		driver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/header//input")).sendKeys(restaurantName);
		Thread.sleep(2000);
		
		//click on the result of restaurant found
		click(By.xpath("//*[@id=\"searchResults\"]/div/a/img"));

		
		if (restaurantName.equals(getText(By.tagName("h1"))))
			return true;
		else
			return false;

	}

	// Select dishes to order
	public boolean RestaurantOrder(String paymentText) throws InterruptedException {

		// select and add the first dish
		click(By.xpath("//*[@id=\"menu-body-wrapper\"]//section[1]//div[2]/div[1]"));
		click(By.xpath("//*[@id='modals']//div[4]/div/button/div"));

		// select and add the second dish
		click(By.xpath("//*[@id=\"menu-body-wrapper\"]//section[1]//div[2]/div[2]"));
		click(By.xpath("//*[@id='modals']//div[4]/div/button/div"));

		// select and add the third dish
		click(By.xpath("//*[@id=\"menu-body-wrapper\"]//section[1]//div[2]/div[3]"));
		click(By.xpath("//*[@id='modals']//div[4]/div/button/div"));
		
		Thread.sleep(3000);

		//click on payment button
		click(By.xpath("//*[@id=\"menu-body-wrapper\"]/div[2]/aside/div/div[2]/div[1]/button/div"));

		
		if (paymentText.equals(getText(By.xpath("//*[@id=\"root\"]//div[2]/h2"))))
			return true;
		else
			return false;

	}
	
	// use site's restaurant filter
	public boolean useFilter() throws InterruptedException {
		
		//Click connection
		click(By.xpath("//*[@id=\"root\"]//aside//fieldset//div[6]/button"));
		
		if (isExist(By.xpath("//a[@data-test=\"restaurant-item\"]")))
			return true;
		else
			return false;

	}

}