package services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import external.SplayBST;
import helpers.Constants;

/**
 * Service to get/save keywords from "keywords.txt" and to get the last searched keyword with frequency.
 * @author yugapriya
 */
public class KeyWordService {
	
	// reads keywords.txt and returns a map of words by their frequency
	public static SplayBST<String, Integer> getKeywords() {
		SplayBST<String, Integer> keywordFrequencyMap = new SplayBST<>();
		File keywordsFile = new File(Constants.PROJECT_PATH + "/resources/keywords.txt/");
		
		Scanner scanner = null;
		try {
			scanner = new Scanner(keywordsFile);
		} catch (FileNotFoundException e) {
			System.out.println("File not found " + keywordsFile.getName());
			return keywordFrequencyMap;
		}
		
		Stack<String> lines = new Stack<>();
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			lines.add(line);
		}
		while (! lines.isEmpty()) {
			String line = lines.pop();
			String[] lineParts = line.split(":");
			String word = lineParts[1];
			int frequency = Integer.parseInt(lineParts[0]);
			keywordFrequencyMap.put(word, frequency);
		}
		
		scanner.close();
		
		return keywordFrequencyMap;
	}
	
	
	public static void saveKeywords(List<String> keywords) throws IOException {
		SplayBST<String, Integer> keywordFrequencyMap = getKeywords();
		
		for (String keyword : keywords) {
			Integer value = keywordFrequencyMap.get(keyword);
			if (value == null) {
				value = 0;
			}
			keywordFrequencyMap.put(keyword, value+1);
			
		}
		
		FileWriter fileWriter = new FileWriter(Constants.FILE_WRITER_PATH, false); // append = false
		while (keywordFrequencyMap.getRootKey() != null) {
			String rootKey = keywordFrequencyMap.getRootKey();
			Integer rootValue = keywordFrequencyMap.get(rootKey);
			keywordFrequencyMap.remove(rootKey);
			
			fileWriter.write(rootValue + ":" + rootKey + "\n");
		}
		
		fileWriter.close();
	}
	
	public static String getLastSearchedKeywordWithFrequency() {
		SplayBST<String, Integer> keywordFrequencyMap = getKeywords();
		String lastSearchedKeyWord = keywordFrequencyMap.getRootKey();
		if (keywordFrequencyMap.isEmpty()) {
			return "";
		}
		int frequency = keywordFrequencyMap.get(lastSearchedKeyWord);
		return lastSearchedKeyWord + "(" + frequency + ")";
	}
	
} 
