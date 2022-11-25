package services;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import helpers.Constants;

/**
 * Service that uses a headless webdriver to load the page first and get it's contents.
 * @author yugapriya
 */
public class WebDriverService {
	
	WebDriver driver = null; //Interface 

	public WebDriverService() {
		System.setProperty("webdriver.chrome.driver", Constants.CHROME_DRIVER_PATH); //sets the path for chrome driver in the given sys property name
		System.setProperty("webdriver.chrome.silentOutput", "true");

		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setHeadless(true); // It won't show the browser window(Hide the head)
		driver = new ChromeDriver(chromeOptions);
	}
	
	public String getPageContent(String url) {
		driver.get(url);
		return driver.getPageSource();
	}
}
