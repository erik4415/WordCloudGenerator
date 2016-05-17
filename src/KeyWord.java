///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            Program 3
// Files:           KeyWord.java,BSTDictionary.java,BSTDictionaryIterator.java,ArrayHeap.java,WordCloudGenerator.java
// Semester:         CS 367 Fall 2015
//
// Author:           Erik Hough
// Email:            ehough@wisc.edu
// CS Login:         ehough
// Lecturer's Name:  Charles N. Fischer
// Lab Section:      Lecture 3

public class KeyWord implements java.lang.Comparable<KeyWord>, Prioritizable {
	
	private int score = 0;     //holds the occurrences of the word
	private String word = "";  //holds the word of the keyword
	
	
	
	/**
	 * 
	 * Constructor: Requires a word to create the object
	 * @param word the word to be used
	 * 
	 */
	public KeyWord(String word){
		this.word = word;
	}
	
	
	
	/**
	 * 
	 *Increases the score for this keyword object by one.
	 *
	 *
	 */
	public void increment(){
		
		score++;	
	}
	
	
	/**
	 * 
	 * Returns the number of ocurrences for this object
	 * 
	 * @return number of ocurrences for this object
	 */
	public int getOccurrences(){
		
		return score;
		
	}
	
	/**
	 * 
	 * Returns the word for this keyword object
	 * 
	 * @return word of this keyword object
	 */
	public String getWord(){
		
		return word;
	}
	
	
	

	/**
	 * Gets the priotrity of the keyword
	 * 
	 * @return the occurnces of the keyword
	 */
	@Override
	public int getPriority() {
		
		return score;
	}
	
	
	

	/** 
	 * Compares the keyword to a given word
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(KeyWord o) {
		
		String compareWord = o.getWord().toLowerCase();
		
		String word = getWord().toLowerCase();
		
		return word.compareTo(compareWord);
	}
	
	
	
	/**
	 * Compares the KeyWord object word with this keywords objects word
	 * 
	 * 
	 * @param compare the keyword object to compare to this keyword
	 */
	@Override
	public boolean equals(Object compare){
		
		KeyWord compareWord = (KeyWord)compare;
		
		if (compareWord.getWord().toLowerCase().equals(getWord().toLowerCase())){
			return true;
		}
		
		
		return false;
	}

}
