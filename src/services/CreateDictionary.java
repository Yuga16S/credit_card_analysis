package services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import helpers.Constants;
/**
 * 
 * @author Khushi Paul
 *
 */

/*
 *  this class creates dictionary using trie data structure
 *  createDictionary function creates dictionary
 *  getDictionaryWordsFromTokens function generates token form each line of text file
 *  getDictionaryWordsFromTokens function creates string word from tokens generated from each line of text file
 */
public class CreateDictionary {

	
	// created dictionary using trie data structure
    public static ArrayList<String> createDictionary() throws FileNotFoundException{
    	
    	// store words array of dictionary
    	ArrayList<String> dictionaryArray=new ArrayList<String>();
  
		// folder path for text files
		String pathToFiles =  Constants.PROJECT_PATH + "/resources/textFiles/";;	
		
		// folder path
		File folderPath = new File(pathToFiles);		

		// text files
		File[] textFiles = folderPath.listFiles();
	
		// looping over all files in user entered folder
		for (File textFile : textFiles) {
			
			Scanner fileScanning;
			
			// only text files are allowed
			if(textFile.getName().endsWith(".txt")) {
						try {
							
							// reading file
							fileScanning = new Scanner(textFile);
							
							String wordString = "";
							
							// concatenate all string words from file
							while (fileScanning.hasNextLine()) {
								wordString += fileScanning.nextLine();

							}

							fileScanning.close();
							
							// passing string to filter chars based on ASCII values and then get token from each string to add in dictionary
							dictionaryArray = CreateDictionary.getDictionaryWordsFromTokens(CreateDictionary.getStringTokenFromTextFile(wordString));

						} catch (FileNotFoundException e) {
							
							System.out.print(e);
					        e.printStackTrace();
						}
				
					}
		}
		return dictionaryArray;
	}
    
    
 //function generates words from tokens
 public static ArrayList<String> getDictionaryWordsFromTokens(ArrayList<String> stringTokens) {
 			
 	// store words
 	ArrayList<String> wordsArray = new ArrayList<String>();
 		
 	// traverse string tokens
 	for(String word: stringTokens) {
 				
 		boolean removeSpace = false;
 				
 		// traverse string
 	    for(char c:word.toCharArray()) {
 					
 		// ASCII value of single char 
 	    int asciiCharValue = (int)c;
 					
 		// traversing char array
 		if(((asciiCharValue > 96 && asciiCharValue < 123) )||(asciiCharValue > 47  && asciiCharValue < 58)) {
 			continue;
 		}

 		// need to skip token, break out of the loop
 		if(asciiCharValue < 48) {
 			removeSpace = true;
 			break;
 		}
 					
 		removeSpace = true;
 			break;
 		}
 				
 		// add word to final array
 		if(!removeSpace) {
 			wordsArray.add(word);
 		}
 				
 	}
 	    // return 
 	    return wordsArray;
 }
 
 // getting tokens from text file from  each line
 public static ArrayList<String> getStringTokenFromTextFile(String stringFromEachLine) {
		
		// store tokens
		ArrayList<String> finalTokensArray = new ArrayList<String>();
		
		// getting single tokens
		StringTokenizer singleToken = new StringTokenizer(stringFromEachLine);
		
		while (singleToken.hasMoreTokens()) {
			finalTokensArray.add(singleToken.nextToken().toLowerCase());
		}
		return finalTokensArray;
		}

}