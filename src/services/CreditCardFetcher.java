package services;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import helpers.Constants;
import model.CreditCard;

import org.jsoup.nodes.Document;

/**
 * This service scrapes the "all credit cards" page
 * @author yugapriya
 */
public class CreditCardFetcher {
	
	public static List<CreditCard> getAllCreditCards(String htmlContent) {
		List<CreditCard> creditCards = new ArrayList<>();
		Document document = Jsoup.parse(htmlContent); // parses the input HTML into a new Document
		Elements elements = document.select("a.cc-product-title"); //Find elements that match the Selector CSS query, returns an Elements list containing elements that match the query (empty if none match
		for(Element element : elements) {
			String hyperLink = element.attr("href");
			if (! hyperLink.startsWith("http")) { // The site uses relative url hence we are making it complete.
				hyperLink = (Constants.DOMAIN + hyperLink);
			}
			CreditCard card = new CreditCard(element.text(), hyperLink);
			creditCards.add(card);
		}
		return creditCards;
	}

}
