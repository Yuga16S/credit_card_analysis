import java.io.IOException;
import java.util.List;
import java.util.Map;

import helpers.Constants;
import model.CreditCard;
import services.WebDriverService;
import services.CreditCardFetcher;
import services.WebCrawler;

public class MainApplication {
	
	public static void main(String[] args) throws IOException {
		
		WebDriverService webDriverService = new WebDriverService();
		
		System.out.println("Welcome to your personalized credit card analyser!");
		System.out.println("Here is a complete list of all the credit cards:");
		
		String allCreditCardsPageHtml = webDriverService.getPageContent(Constants.CREDIT_CARDS_URL);
		List<CreditCard> creditCards = CreditCardFetcher.getAllCreditCards(allCreditCardsPageHtml); 
		for (CreditCard creditCard : creditCards) {
			System.out.println(creditCard.getName());
		}
		
		Map<CreditCard, List<String>> creditCardPagesMap = WebCrawler.crawlAndDownload(creditCards);
		System.out.println("Done Crawling " + Constants.DOMAIN + " for credit card pages");
	}

}
