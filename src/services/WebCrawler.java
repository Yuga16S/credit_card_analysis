package services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
		final int INITIAL_CRAWL_DEPTH = 0;
		
		Map<CreditCard, List<String>> crawlResult = new HashMap<>();
		
		// Deletes all the old files.
		deleteFilesInDirectory(WEBPAGES_DIR);
		
		for (CreditCard creditCard : creditCards) {
			HashSet<String> visitedUrls = new HashSet<>();
			String specifiCreditCardLink = creditCard.getLink();
			crawlAndDownload(creditCard, specifiCreditCardLink, crawlResult, visitedUrls, INITIAL_CRAWL_DEPTH);
		}
		return crawlResult;
	}
	
	public static void crawlAndDownload(CreditCard creditCard, String sourceLink, Map<CreditCard, List<String>> crawlResult, HashSet<String> visitedUrls, int crawledDepth) throws IOException {
		if (visitedUrls.contains(sourceLink)) {
			return; // this url has already been visited for this credit card
		}
		
		String fileName = downloadPage(webDriverService, sourceLink); // downloads page content of sourceLink and puts it in a file whose name is returned
		String htmlFileName = fileName + ".html";
		
		List<String> fileNames = crawlResult.get(creditCard);
		if (fileNames == null) {
			fileNames = new ArrayList<String>();
		}
		fileNames.add(fileName);
		crawlResult.put(creditCard, fileNames);
		visitedUrls.add(sourceLink);
		
		crawledDepth++; // incrementing crawled depth after downloading the file
		
		if (Constants.CRAWLING_DEPTH.equals(crawledDepth)) {
			return; // achieved the required crawl depth. 
		}
		
		List<String> linksInFile = extractLinks(new File(WEBPAGES_DIR + htmlFileName));
		for (String linkInFile : linksInFile) {
			try {
				crawlAndDownload(creditCard, linkInFile, crawlResult, visitedUrls, crawledDepth);
			} catch (Exception e) {
				// ignore invalid links (the page may contain some href tags for telephone numbers or email id)
			}
		}
	}
	
	private static List<String> extractLinks(File htmlFile) throws IOException {
		List<String> links = new ArrayList<>();
		Document document = Jsoup.parse(htmlFile); // parses the input HTML into a new Document
		Elements anchorTagElements = document.select("a");
		for(Element element : anchorTagElements) {
			String url = element.attr("href");
			if (! url.startsWith("http")) { // The site uses relative url hence we are making it complete.
				url = (Constants.DOMAIN + url);
			}
			links.add(url);
		}
		return links;
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