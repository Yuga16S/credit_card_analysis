package helpers;

/**
 * Helper class to store all the constants used in this project
 * @author yugapriya
 */
public class Constants {

	public static final String PROJECT_PATH = System.getProperty("user.dir");
	
	public static final String DOMAIN = "https://www.bmo.com";
	
	public static final String CREDIT_CARDS_URL = DOMAIN + "/main/personal/credit-cards/all-cards/";
	
	public static final String CHROME_DRIVER_PATH = Constants.PROJECT_PATH + "/resources/bin/chromedriver-kartik.exe";
	
	public static final String FILE_WRITER_PATH = Constants.PROJECT_PATH + "/resources/keywords.txt/";
}