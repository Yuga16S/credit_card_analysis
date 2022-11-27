package model;

import java.util.LinkedList;

/**
 * 
 * @author Riteesh
 *
 */
public class Posting implements Comparable<Posting> {
	// instance variables
	private final int file_index;
	private final LinkedList<Position> positionList;

	// class constructor
	public Posting(int file_index) {
		// initialize instance variables
		this.file_index = file_index;
		positionList = new LinkedList<Position>();
	}

	public int getFileIndex() {
		return file_index;
	}

	public int getWordCount() {
		return positionList.size();
	}

	public void addWordPosition(int line_num, int word_num) {
		positionList.add(new Position(line_num, word_num));
	}

	public String getPositionList() {
		String result = positionList.toString();
		return result.substring(1, result.length() - 1);
	}

	@Override
	public int compareTo(Posting other) {

		return other.getWordCount() - this.getWordCount();
	}

	@Override
	public boolean equals(Object o) {
		
		if (o instanceof Posting) {
			Posting other = (Posting) o;
			return other.file_index == this.file_index;
		}
		return false;
	}

	private class Position {
		// instance variables
		private final int line_num;
		private final int word_num;

		// class constructor
		public Position(int line_num, int word_num) {
			// initialize instance variables
			this.line_num = line_num;
			this.word_num = word_num;
		}

		@Override
		public String toString() {
			
			return "[" + line_num + "-" + word_num + "]";
		}
	}
}