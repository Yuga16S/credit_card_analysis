package services;

import java.util.ArrayList;

import model.TrieNodeForDictionary;

/**
 * 
 * @author Khushi Paul
 *
 */
/*
 * this class creates words dictionary using trie data structure
 */
public class TrieDictionary {
	
	//creating dictionary using trie
	static void insertDictionaryWord(TrieNodeForDictionary root, String s)
	{
		TrieNodeForDictionary dictionaryWordTemporary = root;

	  // inserting string each character
		for(int i = 0; i < s.length(); i++)
		{
			if (dictionaryWordTemporary.Trie[s.charAt(i)] == null)
			{
			   // create new node
				dictionaryWordTemporary.Trie[s.charAt(i)] = new TrieNodeForDictionary();
			}

			// Update dictionaryWordTemporary
			dictionaryWordTemporary = dictionaryWordTemporary.Trie[s.charAt(i)];
		}

	
		// last char of string should be true to define its end of dictionary word
		dictionaryWordTemporary.isEnd = true;
	}

	//Function to print suggestions of the string
	static void getSuugestionsForKeyword(TrieNodeForDictionary root, String keyword)
	{

		// checking for last char
		if (root.isEnd == true)
		{
			System.out.print(keyword + "\n");
		}

		// looping for possible words 
		for(int stringChar = 0; stringChar < 256; stringChar++)
		{
			
			// current char is in dictionary,then
			if (root.Trie[stringChar] != null)
			{
				// creating suggestion
				keyword = keyword+(char)(stringChar);
				getSuugestionsForKeyword(root.Trie[stringChar], keyword);
			}
		}
	}

	//Function to check if the string
	//is present in Trie or not
	static boolean isKeywordInDictionary(TrieNodeForDictionary root, String keyword)
	{

		// looping over keyword
		for(int i = 0; i < keyword.length(); i++)
		{
			// checking if char is in trie
			if (root.Trie[keyword.charAt(i)] == null)
			{
				// if not present in trie get suggestion for that keyword
				System.out.print("Are you searching for :-\n");
				getSuugestionsForKeyword(root, keyword.substring(0, i));
				return false;
			}

			// Update root
			root = root.Trie[keyword.charAt(i)];
		}
		// checSking end of word
		if (root.isEnd == true)
		{
			return true;
		}
		
		// printing suggestion if word spelled wrong or not present in dictionary
		System.out.print("Are you searching for :-\n");
		getSuugestionsForKeyword(root, keyword);

		return false;
	}

	public static boolean spellcheck(ArrayList<String> dictionary, ArrayList<String> keywords)
	{
        // creating root for trie
		TrieNodeForDictionary root = new TrieNodeForDictionary();

		// Insert words to trie
		for(int i = 0; i < dictionary.size(); i++)
		{
			insertDictionaryWord(root, dictionary.get(i));
		}

		// variable to check if all keywords spelled correctly
		boolean areKeywordsCorrect = false;
		
		// traversing user entered keywords and checking spelling of each keyword
		for(String keyword: keywords) {
			if (isKeywordInDictionary(root, keyword))
			{
				areKeywordsCorrect = true;
				
			}else {
				areKeywordsCorrect = false;	
			}
		}
		
		// return 
		return areKeywordsCorrect;
	}

}