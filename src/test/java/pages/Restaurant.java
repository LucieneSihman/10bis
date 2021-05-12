package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Restaurant extends Base {

	public Restaurant(WebDriver driver) {
		super(driver);
	}

	// Go to restaurant page
	public boolean RestaurantSearch(String restaurantName) throws InterruptedException {

		// click on search field and type restaurant name
		click(By.xpath("//*[@id=\"root\"]/div[2]/header//input"));
		driver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/header//input")).sendKeys(restaurantName);
		Thread.sleep(2000);

		click(By.xpath("//*[@id=\"searchResults\"]/div/a/div/div[2]"));

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

		// click on payment button
		click(By.xpath("//*[@id=\"menu-body-wrapper\"]/div[2]/aside/div/div[2]/div[1]/button/div"));

		if ((getText(By.xpath("//*[@id=\"root\"]/div[2]/div[1]/div/div[1]/div/h1")).contains(paymentText)))
			return true;
		else
			return false;

	}

	// use site's restaurant filter
	public boolean useFilter() throws InterruptedException {

		String occurrence = getText(By.xpath("//fieldset/div[2]/div[4]/div[6]/button/div[1]/div[1]/div"));

		System.out.println("1" + occurrence);

		String str = occurrence.replaceAll("\\D", "");

		System.out.println("2-" + str);

		int i = Integer.parseInt(str);

		Thread.sleep(3000);

        //Click on American restaurant filter
		click(By.xpath("//div[2]//fieldset//div[6]/button/div[1]/div[text()='אמריקאי ']"));

		Thread.sleep(3000);

		// Count occurrences of restaurants in the page
		List<WebElement> options = driver.findElements(By.xpath("//a[@data-test]"));

		System.out.println("3-" + options.size());

		if (i == options.size())
			return true;
		else
			return false;

	}

}
