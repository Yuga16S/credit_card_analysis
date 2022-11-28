package services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import helpers.Constants;
import model.Posting;
import model.WordFrequency;

/**
 * 
 * @author Riteesh
 *
 */
//Creates InvertedIndex map
public class CreateInvertedIndex {
	// creating regex constant
	public static final String REGEX = "[^a-zA-Z0-9']+";

	// instance variables
	private final File[] fileArray; // File Array
	private final HashMap<String, LinkedList<Posting>> hashMap; // hashmap declaration with string (word) as key and
																// linked list of posting object which contains word
																// location

	// class constructor
	public CreateInvertedIndex(ArrayList<String> fileNameArray) throws IOException {
		// initializing class instance variables
		fileArray = new File[fileNameArray.size()];
		hashMap = new HashMap<String, LinkedList<Posting>>();

		for (int file_index = 0; file_index < fileArray.length; file_index++) {

			fileArray[file_index] = new File(fileNameArray.get(file_index)); // creates a new file and adds it to
																				// fileArray
			createFileindex(file_index); // calls indexFile() method by passing File Index as parameter
		}
	}

	 private void createFileindex(int file_index) throws IOException
	    {
	        BufferedReader reader = new BufferedReader(new FileReader(fileArray[file_index]));  // open reader

	        
	        int line_num = 0;  // line number in which word is present
	        int word_num;// word number

	        

	        for (String line = reader.readLine(); line != null; line = reader.readLine())   // reads file by line by line
	        {
	            line_num++;
	            word_num = 0;

	            String local = "";
	            for (String word : line.split(REGEX))   // split lines by word by word
	            {
	                word = word.trim().toLowerCase();    // removes whitespaces and converts it into lower case

	                if (word.length() > 1)            // removes whitespaces and converts it into lower case
	                {
	                    word_num++;

	                    if (word.equalsIgnoreCase("no") || word.equalsIgnoreCase("low")) {
							local = word;
							continue;
						} else if ((word.equalsIgnoreCase("fee") && local.equalsIgnoreCase("no"))|| (word.equalsIgnoreCase("interest") && local.equalsIgnoreCase("low"))) {
							local += word;
						}
	                 // checks for existing word in hashMap
	                    LinkedList<Posting> value = hashMap.get(word);
	                    if (value == null)
	                    {
	                      // if word does not exist in hashMap
	                        value = new LinkedList<Posting>();  /// create a new word
	                        if (word.equalsIgnoreCase("fee") || word.equalsIgnoreCase("interest")) {
								hashMap.put(local, value); // add to hashMap
							} else {
								hashMap.put(word, value); // add to hashMap
							}


	                    }

	                 // checks for existing posting in word
	                    int index = value.indexOf(new Posting(file_index));
	                    Posting posting;
	                    if (index == -1) // if posting does not exist in word
	                    {                                       
	                        posting = new Posting(file_index);     // create a new posting
	                        value.add(posting);                // adds posting to word
	                    }
	                    else
	                        posting = value.get(index);

	                    
	                    posting.addWordPosition(line_num, word_num);
	                }
	            }
	        }

	        reader.close();     // close reader

	        //if you want you can file names
	        //System.out.println("\t File: \"" + fileArray[file_index].getName() + "\"");
	    }


	 //Searches given input string
	public void findIndex(String word) {

		HashMap<String, ArrayList<WordFrequency>>  finalFrequencyMap = new HashMap<String, ArrayList<WordFrequency>>();

		
		word = word.trim().toLowerCase(); // removes whitespaces between words and converts them into lower case


		LinkedList<Posting> value = hashMap.get(word); // checks word in hashMap
		if (value != null) // word found in hashMap
		{
			LinkedList<Posting> postingList = new LinkedList<Posting>(value);
			Collections.sort(postingList); // sorting postingList by relevance
			
			int num=0;
			for (Posting posting : postingList) {
				// output results
				String fileName = fileArray[posting.getFileIndex()].getName();
				if (finalFrequencyMap.containsKey(fileName)) {

					ArrayList<WordFrequency> currList = finalFrequencyMap.get(fileName);

					for (WordFrequency currentWord : currList) {
						if (currentWord.getWord().equalsIgnoreCase(word)) {
							currentWord.setFrequency(currentWord.getFrequency() + 1);
						} else {
							currList.add(new WordFrequency(word, posting.getWordCount()));
						}
					}
				} else {
					WordFrequency frequencyWord = new WordFrequency(word, posting.getWordCount());
					ArrayList<WordFrequency> arrayList = new ArrayList<WordFrequency>();
					arrayList.add(frequencyWord);
					finalFrequencyMap.put(fileName, arrayList);
				}

			}
			for (String key : finalFrequencyMap.keySet()) {
				CreditCardRanking.invertedIndexFrequencyMap.put(key, finalFrequencyMap.get(key));
				// search for value
				ArrayList<WordFrequency> freqList = finalFrequencyMap.get(key);
				
//				 printing of map can be used for debugging
//				for (int i = 0; i < freqList.size(); i++) {
//					System.out.print("file name = " + key + "\n");
//					System.out.println("word = " + freqList.get(i).getWord() + " ------ " + "frequency = "
//							+ freqList.get(i).getFrequency() + "-------- " +freqList.size());
//
//				}
			}

			return;
		}

		System.out.println("Word not found"); // print when no word found
		
	}

}
