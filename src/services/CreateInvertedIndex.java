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

	public static void createInvertedIndexMap() throws IOException {
		ArrayList<String> FileNameArray = new ArrayList<String>();

		String textFilesDirectoryPath = Constants.PROJECT_PATH + "/resources/textFiles/"; // directory path to text
																							// files

		File textFilesDirectory = new File(textFilesDirectoryPath);// Creating file object

		File[] listOfAllHtmlFiles = textFilesDirectory.listFiles(); // returns files in directory
		int i = 0;
		// iterates through list of files in the directory
		for (File file : listOfAllHtmlFiles) {
			String fileNameArray2 = file.getPath();
			FileNameArray.add(fileNameArray2.toString());
			i = i + 1;

		}
		CreateInvertedIndex index = new CreateInvertedIndex(FileNameArray); // Creates CreateInvertedIndex object
		String[] inpArray = { "cashback", "Low interest", "Airmiles", "mastercard" };
		for (String inp : inpArray) {
			index.findIndex(inp);
		}
		// index.findIndexForTwoStrings("student", "mastercard");
	}

	private void createFileindex(int file_index) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileArray[file_index])); // open reader

		int line_num = 0; // line number in which word is present
		int word_num; // word number

		for (String line = br.readLine(); line != null; line = br.readLine()) // read file, line by line
		{
			line_num++;
			word_num = 0;
			String local = "";
			for (String word : line.split(REGEX)) // split line, word by word
			{
				word = word.trim().toLowerCase(); // removes whitespaces and converts it into lower case

				if (word.length() > 1) // ignores any whitespaces, words with single characters
				{
					word_num++;

					if (word.equalsIgnoreCase("no") || word.equalsIgnoreCase("low")) {
						local = word;
						continue;
					} else if ((word.equalsIgnoreCase("fee") && local.equalsIgnoreCase("no"))
							|| (word.equalsIgnoreCase("interest") && local.equalsIgnoreCase("low"))) {
						local += word;
					}
					// checks for existing word in hashMap
					LinkedList<Posting> value = word.equalsIgnoreCase("fee") || word.equalsIgnoreCase("interest")
							? hashMap.get(local)
							: hashMap.get(word);

					if (value == null) {
						// if word does not exist in hashMap:
						value = new LinkedList<Posting>(); // create a new word
						if (word.equalsIgnoreCase("fee") || word.equalsIgnoreCase("interest")) {
							hashMap.put(local, value); // and add it to hashMap
						} else {
							hashMap.put(word, value); // and add it to hashMap
						}

						// checks for existing posting in word
						int index = value.indexOf(new Posting(file_index));
						Posting posting;
						if (index == -1) { // if posting does not exist in word:
							posting = new Posting(file_index); // create a new posting
							value.add(posting); // adds posting to word
						} else
							posting = value.get(index);

						// adds position i.e line number and word number to posting
						posting.addWordPosition(line_num, word_num);
					}
				}
			}
		}
		br.close();

	}

	public void findIndex(String word) {

		word = word.trim().toLowerCase(); // removes whitespaces between words and converts them into lower case

		HashMap<String, ArrayList<WordFrequency>> finalFrequencyMap = new HashMap<String, ArrayList<WordFrequency>>();

		LinkedList<Posting> value = hashMap.get(word); // checks word in hashMap
		if (value != null) // word found in hashMap
		{
			LinkedList<Posting> postingList = new LinkedList<Posting>(value);
			Collections.sort(postingList); // sorting postingList by relevance

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
				// search for value
				ArrayList<WordFrequency> freqList = finalFrequencyMap.get(key);
				for (int i = 0; i < freqList.size(); i++) {
					System.out.print("file name = " + key + "\n");
					System.out.println("word = " + freqList.get(i).getWord() + " ------ " + "frequency = "
							+ freqList.get(i).getFrequency());

				}
			}

			return;
		}

		System.out.println("Word not found"); // no result
	}

	public void findIndexForTwoStrings(String word1, String word2) {
		System.out.println("Searched: \"" + word1 + "\"" + " AND \"" + word2 + "\"");

		word1 = word1.trim().toLowerCase(); // removes whitespaces between words and converts them into lower case

		HashMap<String, ArrayList<WordFrequency>> finalMap = new HashMap<String, ArrayList<WordFrequency>>();
		// Iterator
		Iterator<Entry<String, ArrayList<WordFrequency>>> new_Iterator = finalMap.entrySet().iterator();

		LinkedList<Posting> value1 = hashMap.get(word1); // lookup word1 in hashMap
		if (value1 != null) // word1 found in hashMap
		{
			word2 = word2.trim().toLowerCase(); // normalize word2

			LinkedList<Posting> value2 = hashMap.get(word2); // lookup word2 in hashMap
			if (value2 != null) // word2 found in hashMap
			{
				LinkedList<Posting> maxValue = (value1.size() >= value2.size()) ? value1 : value2;
				LinkedList<Posting> minValue = (value1.size() < value2.size()) ? value1 : value2;
				LinkedList<Posting> postingList = new LinkedList<Posting>(maxValue);

				if ((maxValue.size() - minValue.size()) > 0) {
					LinkedList<Posting> temp = new LinkedList<Posting>(maxValue);
					for (Posting posting : minValue)
						temp.remove(posting);

					for (Posting posting : temp)
						postingList.remove(posting); // AND
				}

				int num = 0;

				for (Posting posting : postingList) {

					System.out.println("\t" + (++num) + "\t File: \"" + fileArray[posting.getFileIndex()] + "\"\n");
				}

				return;
			}
		}

		System.out.println("Word not found"); // prints when word is not found
	}
}
