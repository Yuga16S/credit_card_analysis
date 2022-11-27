package model;

/**
 * 
 * @author Riteesh
 *
 */
//This class is to store the details of words from the file/url and its frequency
public class WordFrequency {
	private String word;
	private int frequency;

	// Class constructor to initalize instance variables
	public WordFrequency(String word, int frequency) {
		this.word = word;
		this.frequency = frequency;
	}

	// Class getters and setters
	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

}