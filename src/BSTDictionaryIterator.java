import java.util.*;

///////////////////////////////////////////////////////////////////////////////
//ALL STUDENTS COMPLETE THESE SECTIONS
//Title:            Program 3
//Files:           KeyWord.java,BSTDictionary.java,BSTDictionaryIterator.java,ArrayHeap.java,WordCloudGenerator.java
//Semester:         CS 367 Fall 2015
//
//Author:           Erik Hough
//Email:            ehough@wisc.edu
//CS Login:         ehough
//Lecturer's Name:  Charles N. Fischer
//Lab Section:      Lecture 3

/**
 * BSTDictionaryIterator implements an iterator for a binary search tree (BST)
 * implementation of a Dictionary.  The iterator iterates over the tree in 
 * order of the key values (from smallest to largest).
 */
public class BSTDictionaryIterator<K> implements Iterator<K> {

	private Stack<BSTnode<K>> stack;  // the root node
	 
	 /**
	  * 
	  * Constructor: will preload the way left path to start returning
	  * @param BSTnode to use as the root
	  *
	  */
	 public BSTDictionaryIterator(BSTnode<K> root){
		 stack = new Stack<BSTnode<K>>();
			while (root != null) {
				stack.push(root);
				root = root.getLeft();
			}
	 }
	 
	 
	 
	 /**
	  * Checks to see if there is another node, then will return true.
	  * 
	  * 
	  * @return true if there are still nodes
	  */
    public boolean hasNext() {
    	
    	return !stack.empty();
       
    }

    
    /**
     * 
     * Gets the next element, then after getting it finds the next to return
     * by pushing more on the stack.
     * 
     * 
     * @return K the next element
     */
    public K next() {

    	//take the first off of the top of the stack
    	BSTnode<K> returnNode = stack.pop();
    	
    	if(returnNode == null){
    		throw new NoSuchElementException();
    	}
  	
    	
    	//check if anything is to right, if so get everything to the left from that
    	BSTnode<K> next = returnNode.getRight();  
    	
    	if(next!= null){
    		
    		stack.push(next);
    		next = next.getLeft();
    
    		while (next != null ){
    			stack.push(next);
    			next = next.getLeft();   			
    		}   		
    	}    	
    	return returnNode.getKey();  
    }
        
    

    public void remove() {
        // DO NOT CHANGE: you do not need to implement this method
        throw new UnsupportedOperationException();
    }    
}
