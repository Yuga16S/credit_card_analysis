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
import services.CreateInvertedIndex;
import services.CreateTextFiles;

public class MainApplication {
	
	public static void main(String[] args) throws IOException {
		
		WebDriverService webDriverService = new WebDriverService();
		
		// Welcome message
		System.out.println("Welcome to your personalized credit card analyser!");
		System.out.println("Please wait while we fetch the information ...");
		
		String allCreditCardsPageHtml = webDriverService.getPageContent(Constants.CREDIT_CARDS_URL);
		List<CreditCard> creditCards = CreditCardFetcher.getAllCreditCards(allCreditCardsPageHtml); 
		
		@SuppressWarnings("unused") // To be used for page ranking
		Map<CreditCard, List<String>> creditCardPagesMap = WebCrawler.crawlAndDownload(creditCards);
		
		Scanner scanner = new Scanner(System.in);
		boolean runAgain = true;
		while (runAgain) {
			System.out.println("Here is a complete list of all the credit cards:");
			for (CreditCard creditCard : creditCards) {
				System.out.println(creditCard.getName());
			}
			
			StringBuilder sb = new StringBuilder();
			sb.append("\n\n\nNot sure what to choose ?");
			sb.append("\nYou can type in what you are looking for and we'll help you find the credit card for you.\n\n");
			sb.append("\n _____________________SOME COMMON KEYWORDS_________________");
			sb.append("\n|                                                          |");
			sb.append("\n| Cash Back                                                |");
			sb.append("\n| Groceries                                                |");
			sb.append("\n| No Fee                                                   |");
			sb.append("\n| Low Interest                                             |");
			sb.append("\n| AIR MILES                                                |");
			sb.append("\n| Student                                                  |");
			sb.append("\n| Lifestyle                                                |");
			sb.append("\n| Travel                                                   |");
			sb.append("\n|__________________________________________________________|");
			sb.append("\n\nRecently searched keyword: " + KeyWordService.getLastSearchedKeywordWithFrequency());
			System.out.println(sb.toString());
			
			
			// temp test code for reading input
			System.out.println("Please enter your keywords (comma separated) and hit the return key to proceed ...");
			
			String inputLine = scanner.nextLine();
			String[] inputKeyWords = inputLine.split(",");
			KeyWordService.saveKeywords(Util.listify(inputKeyWords));
			
			// Simulating the pending work below
//			System.out.println("Based on your search, we recommend you " + creditCards.get(0).getName());
			
			
			CreateTextFiles.createFiles(); // function to create text files
			CreateTextFiles.createInvertedIndexMap(); // function to create inverted frequency map
			
			System.out.println("Do you want to start over? (Please press y to continue)");
			String response = scanner.nextLine();
			runAgain = "y".equals(response);
		}
		
		scanner.close();
		System.out.println("Thank you for your interest. Have a nice day!");
	}
	
}
