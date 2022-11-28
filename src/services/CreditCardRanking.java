package services;

import java.util.ArrayList;
import java.util.HashMap;

import model.WordFrequency;

public class CreditCardRanking {
	
	public static HashMap<String, ArrayList<WordFrequency>>  invertedIndexFrequencyMap = new HashMap<String, ArrayList<WordFrequency>>();
	
    
	 public static ArrayList<String> getCreditCard() {
		 
		ArrayList<String> finalCreditCard = new ArrayList<String>();
		int minFrequency = 100;
		for (String key : invertedIndexFrequencyMap.keySet()) {
			
			ArrayList<WordFrequency> freqList = invertedIndexFrequencyMap.get(key);
			
			if(freqList.get(0).getFrequency() <= minFrequency &&finalCreditCard.size()<2 ) {
				minFrequency = freqList.get(0).getFrequency();
				finalCreditCard.add(key);
			}
	}

		return finalCreditCard;
	}
}
