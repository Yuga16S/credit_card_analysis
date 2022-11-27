package services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import helpers.Constants;
/*
 * @author Shalini Shah
 * */
public class SearchWord {

	private static final String TEXTFILEDIR = Constants.PROJECT_PATH + "/resources/textfiles/";
	
	public HashMap<String, Integer> numbers = new HashMap<String, Integer>();
	public ArrayList<String> key = new ArrayList<String>();
	public String matchedWord = new String();
	public void suggestAltWord(String wordToSearch) {
		String line = " ";
		String regex = "[a-zA-Z_ ]+";

		// Create a Pattern object
		Pattern pattern = Pattern.compile(regex);
		// Now create matcher object.
		Matcher matcher = pattern.matcher(line);
		int fileNumber = 0;

		File dir = new File(TEXTFILEDIR);
		File[] fileArray = dir.listFiles();
		for (int i = 0; i < fileArray.length; i++) {
			try {
				findWord(fileArray[i], fileNumber, matcher, wordToSearch);
				fileNumber++;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		Integer allowedDistance = 1; // Edit distance allowed
		boolean matchFound = false; // set to true if word found with edit distance equal to allowedDistance

		
		int i = 0;
		for (Map.Entry entry : numbers.entrySet()) {
			if (allowedDistance == entry.getValue()) {
				
				i++;
				
				if(i==1)
				System.out.println("Did you mean? " + " " + entry.getKey());
				
				matchFound = true;
				matchedWord = (String) entry.getKey();
				break;
			}
		}
	}
	
	private void findWord(File sourceFile, int fileNumber, Matcher match, String str)
			throws FileNotFoundException, ArrayIndexOutOfBoundsException {
		try {
			BufferedReader my_rederObject = new BufferedReader(new FileReader(sourceFile));
			String line = null;

			while ((line = my_rederObject.readLine()) != null) {
				match.reset(line);
				while (match.find()) {
					key.add(match.group());
				}
			}

			my_rederObject.close();
			for (int p = 0; p < key.size(); p++) {
				numbers.put(key.get(p), editDistance(str.toLowerCase(), key.get(p).toLowerCase()));
			}
		} catch (Exception e) {
			System.out.println("Exception:" + e);
		}

	}
	
	private int editDistance(String str1, String str2) {
		int len1 = str1.length();
		int len2 = str2.length();

		int[][] my_array = new int[len1 + 1][len2 + 1];

		for (int i = 0; i <= len1; i++) {
			my_array[i][0] = i;
		}

		for (int j = 0; j <= len2; j++) {
			my_array[0][j] = j;
		}

		// iterate though, and check last char
		for (int i = 0; i < len1; i++) {
			char c1 = str1.charAt(i);
			for (int j = 0; j < len2; j++) {
				char c2 = str2.charAt(j);

				if (c1 == c2) {
					my_array[i + 1][j + 1] = my_array[i][j];
				} else {
					int replace = my_array[i][j] + 1;
					int insert = my_array[i][j + 1] + 1;
					int delete = my_array[i + 1][j] + 1;

					int min = replace > insert ? insert : replace;
					min = delete > min ? min : delete;
					my_array[i + 1][j + 1] = min;
				}
			}
		}

		return my_array[len1][len2];
	}
}