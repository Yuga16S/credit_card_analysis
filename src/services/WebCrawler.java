package services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import helpers.Constants;
import model.CreditCard;

/**
 * Service to crawl and download credit card web pages.
 * @author yugapriya
 */
public class WebCrawler {
	
	private static final String WEBPAGES_DIR = Constants.PROJECT_PATH + "/resources/webpages/";
	
	private static WebDriverService webDriverService = new WebDriverService();

	public static Map<CreditCard, List<String>> crawlAndDownload(List<CreditCard> creditCards) throws IOException {
		Map<CreditCard, List<String>> result = new HashMap<>();
		
		// Deletes all the old files.
		deleteFilesInDirectory(WEBPAGES_DIR);
		
		for (CreditCard creditCard : creditCards) {
			String specifiCreditCardLink = creditCard.getLink();
			String fileName = downloadPage(webDriverService, specifiCreditCardLink);
			List<String> fileNames = new ArrayList<>();
			fileNames.add(fileName);
			
			//TODO: DEEP SEARCH
			result.put(creditCard, fileNames);
		}
		return result;
	}
	
	private static String downloadPage(WebDriverService webDriverService, String url) throws IOException {
		String fileName = null;
		try {
			String pageContent = webDriverService.getPageContent(url);
			fileName = randomFileNameGenerator();
			FileWriter fileWriter = new FileWriter(WEBPAGES_DIR + fileName + ".html");
			fileWriter.write(pageContent);
			fileWriter.close();
		} catch (IOException e) {
			System.out.println("Failed to download from - " + url);
		}
		return fileName;
	}
	
	private static String randomFileNameGenerator() {
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
				+ "0123456789"
				+ "abcdefghijklmnopqrstuvxyz";
		
		StringBuilder str = new StringBuilder();
		int stringLength = 8;
		for (int s = 0; s < stringLength; s++) {
			int randomIndex = (int)(AlphaNumericString.length() * Math.random());
			str.append(AlphaNumericString.charAt(randomIndex));
		}
		String constructedFileName = str.toString();
		return constructedFileName;
	}
	
	private static void deleteFilesInDirectory(String directory) {
		File directoryFile = new File(directory);
		if (!directoryFile.isDirectory()) {
			throw new RuntimeException("Not a directory: " + directory);
		}
		for (File fileInsideDirectory : directoryFile.listFiles()) {
			fileInsideDirectory.delete();
		}
	}

}