package services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import external.SplayBST;
import helpers.Constants;
import model.KeyWord;

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
		List<String> lines = new ArrayList<>();
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			lines.add(0, line);
		}
		for (String line : lines) {
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
		
		FileWriter fileWriter = new FileWriter(Constants.FILE_WRITER_PATH, false);
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
		int frequency = keywordFrequencyMap.get(lastSearchedKeyWord);
		return lastSearchedKeyWord + "(" + frequency + ")";
	}
	
} 
