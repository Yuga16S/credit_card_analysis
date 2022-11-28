package model;

/**
 * 
 * @author Khushi Paul
 *
 */
public class TrieNodeForDictionary {

	// storing chars of word
	public TrieNodeForDictionary Trie[];

	// variable to mark end of word in trie
	public boolean isEnd;

	// initialization using constructor
	public TrieNodeForDictionary()
	{
		Trie = new TrieNodeForDictionary[256];
		for(int i = 0; i < 256; i++)
		{
			Trie[i] = null;
		}
		isEnd = false;
	}
}