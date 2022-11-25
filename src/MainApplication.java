import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import helpers.Constants;
import helpers.Util;
import model.CreditCard;
import services.WebDriverService;
import services.CreditCardFetcher;
import services.KeyWordService;
import services.WebCrawler;

public class MainApplication {
	
	public static void main(String[] args) throws IOException {
		
		WebDriverService webDriverService = new WebDriverService();
		
		System.out.println("Welcome to your personalized credit card analyser!");
		System.out.println("Here is a complete list of all the credit cards:");
		
		String allCreditCardsPageHtml = webDriverService.getPageContent(Constants.CREDIT_CARDS_URL);
		List<CreditCard> creditCards = CreditCardFetcher.getAllCreditCards(allCreditCardsPageHtml); 
		Map<CreditCard, List<String>> creditCardPagesMap = WebCrawler.crawlAndDownload(creditCards);

		for (CreditCard creditCard : creditCards) {
			System.out.println(creditCard.getName());
		}
		
		System.out.println("");
		System.out.println("");
		System.out.println("Not sure what to choose ?");
		System.out.println("You can type in what you are looking for and we'll help you find the credit card for you.");
		System.out.println("");
		System.out.println("");
		System.out.println(" _____________________SOME COMMON KEYWORDS_________________");
		System.out.println("|                                                          |");
		System.out.println("| Cash Back                                                |");
		System.out.println("| Groceries                                                |");
		System.out.println("| No Fee                                                   |");
		System.out.println("| Low Interest                                             |");
		System.out.println("| AIR MILES                                                |");
		System.out.println("| Student                                                  |");
		System.out.println("| Lifestyle & Travel                                       |");
		System.out.println("|__________________________________________________________|");
		System.out.println("");
		System.out.println("");
		System.out.println("Recently searched keyword: " + KeyWordService.getLastSearchedKeywordWithFrequency());
		
		
		// temp test code for reading input
		System.out.println("Please enter your keywords (comma separated) and hit the return key to proceed ...");
		
		Scanner scanner = new Scanner(System.in);
		String inputLine = scanner.nextLine();
		String[] inputKeyWords = inputLine.split(",");
		KeyWordService.saveKeywords(Util.listify(inputKeyWords));
		
		// Simulating the pending work below
		System.out.println("Based on your search, we recommend you " + creditCards.get(0).getName());
		System.out.println("Thank you for your interest. Have a nice day!");
		
		scanner.close();
	}

}
