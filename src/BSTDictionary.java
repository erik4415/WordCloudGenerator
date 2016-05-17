import java.util.Iterator;

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

public class BSTDictionary<K extends Comparable<K>> implements DictionaryADT<K> {
	
    private BSTnode<K> root;  // the root node
    private int numItems;     // the number of items in the dictionary
    
    
    /**
     * 
     * Constructor: Makes an empty bstdictionary
     * 
     */
    public BSTDictionary(){   	
    	root = null;
    	numItems = 0;   	
    }

    
    
    /**
     * 
     * Inserts the new key if it is not a duplicate
     * 
     * @param the key to add to the bst
     */
    public void insert(K key) throws DuplicateException {

    	if(key==null){
    		throw new IllegalArgumentException();
    	}

    	//If empty make a new node
    	if(numItems == 0){
    		root = new BSTnode<K>(key);
    		numItems++;
    	}

    	else { 
    		try{
    			insert(root,key);
    			numItems++;  	
    		}
    		catch(DuplicateException e){
    			throw new DuplicateException();
    		}
    	}
    }


/**
 * 
 * Helper method to insert a key into the tree using recurssion.
 *
 * @param n the node to start looking from
 * @param key the key to insert
 * @return the next node to use with the recurssive call
 */
    private BSTnode<K> insert(BSTnode<K> n, K key) throws DuplicateException {
    	if (n == null) {
    		return new BSTnode<K>(key, null, null);
    	}

    	if (n.getKey().equals(key)) {
    		throw new DuplicateException();
    	}

    	if (key.compareTo(n.getKey()) < 0) {
    		
    		// add key to the left subtree
    		n.setLeft(insert(n.getLeft(), key) );
    		return n;
    	}
        
        else {
            // add key to the right subtree
            n.setRight(insert(n.getRight(), key) );
            return n;
        }
    }
   


    /**
     * method for deleting a node.  Uses auxiliary method to do node changing
     * and actual deletion.
     * 
     * @return true if it was deleted
     */
    public boolean delete(K key) {

    	BSTnode<K> node = root;

    	if(isEmpty()){
    		return false;
    	}

    	else{
    		node = delete(node,key);
    		
    		if (node == null)
    			return false;
    		else
    			return true;
    			
    		}
    	}
    	
    
    
    /**
     * 
     * Auxiliary method to do the actual deleting of the node
     *
     * @param n node to to be looking at
     * @param key to compare to
     * @return node to look at
     */
    private BSTnode<K> delete (BSTnode<K> n, K key){
    	
    	
    	 if (n == null) {
             return null;
         }
    	 
         if (key.equals(n.getKey())) {
        	 
             // n is the node to be removed
             if (n.getLeft() == null && n.getRight() == null) {
                 return null;
             }
             if (n.getLeft() == null){
            	 return n.getRight();
             }
             if (n.getRight() == null){
            	 return n.getLeft();
             }
             
             
             
             K smallVal;	//holds the smaller of the two values if having delete
             
             //Find the smaller of the two values to replace
             if(n.getRight().getKey().compareTo(n.getLeft().getKey()) >  0)
            	 smallVal = n.getRight().getKey();
             else
            	 smallVal = n.getLeft().getKey();

             n.setKey(smallVal);

             n.setRight( delete(n.getRight(), smallVal) );
             
             numItems--;

             return n;
         }

         else if (key.compareTo(n.getKey()) < 0) {
        	 n.setLeft(delete(n.getLeft(), key));
        	 return n;
         }
         else {
        	 n.setRight( delete(n.getRight(), key) );
        	 return n;
         } 
    }
    
    
    
    /**
     * Look up a specific key in the BST
     * 
     * 
     * @param key the key to search for
     * @return K the key if found
     */
    public K lookup(K key) {
    	
    	return lookup(root,key);
    }
    
    
    
    /**
     * 
     * Auxiliary method to help with lookup of node, uses recurssive call
     *
     * @param n The node to continue looking from
     * @param k The key to be looking for
     * @return the key to continue to look for
     */
    private K lookup(BSTnode<K> n, K key) {
        if (n == null) {
            return null;
        }
        
        if (n.getKey().equals(key)) {
            return n.getKey();
        }
        
        if (key.compareTo(n.getKey()) < 0) {
            // key < this node's key; look in left subtree
            return lookup(n.getLeft(), key);
        }
        
        else {
            // key > this node's key; look in right subtree
            return lookup(n.getRight(), key);
        }
    }
    



    /**
     * Finds if the tree is empty
     * 
     * @return true if it is empty, otherwise false
     */
    public boolean isEmpty() {
        return root == null;  
    }

    
    /**
     * Finds the size of the BST
     * 
     * @return number of items in the tree
     */
    public int size() {
        return numItems;  
    }
    
    
    
   /**
    * Uses an auxiliary method to find the total path length of all node in the 
    * tree and returns the sum.
    * 
    * 
    * @return the sum of all the path lengths in the tree
    */
    public int totalPathLength() {
    	
    	if(size() == 0){
    		return 0;
    	}
    	else{
    		
    		int sum = 0;
    		 return pathLength(root, sum);
    		
    	}
    }
    
    
    
   /**
    * 
    * Auxiliary method to help find the length of all nodes in the tree
    * and return the total sum.
    *
    * @param n Bst node to count
    * @param sum int to hold the path length
    * @return An int of the total sum of all path lengths
    * 
    */
   private int pathLength(BSTnode<K> n, int pathLength){

	   int sum = 0;
	   
	   if (n != null){
		   pathLength++;
		   
		
		   sum+=pathLength;


		   if(n.getRight() != null){
			   sum += pathLength(n.getRight(),pathLength);
		   }
		   if(n.getLeft() != null){
			   sum += pathLength(n.getLeft(),pathLength);
		   }
	   }

	   return sum;
	   
	   
	   
   }
    
    
    /**
     * 
     * Creates and iterator for the bst
     * 
     * @return Iterator for the bst
     * 
     */
    public Iterator<K> iterator() {
        return new BSTDictionaryIterator<K>(root);  // replace this stub with your code
    }
}
