import java.util.NoSuchElementException;

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
public class ArrayHeap<E extends Prioritizable> implements PriorityQueueADT<E> {

    // default number of items the heap can hold before expanding
    private static final int INIT_SIZE = 100;
 
    private E[] heap;     	//Underlying array for the heap
    private int total = 0;  //Holds the number of slots filled in heap
  

 
    /**
     * 
     * Constructor: Constructs a heap whose underlying
     * array has enough space to store INIT_SIZE items before needing to 
     * expand.
     */
    public ArrayHeap(){
    	
    	
    	heap=(E[])(new Prioritizable[INIT_SIZE]);
    	
    }
    
    
    /**
     * 
     * Constructor: Constructs a heap whose underlying
     * array has enough space to store size items before needing to 
     * expand.
     * 
     *@param size of the underlying array to make
     */
    public ArrayHeap(int size){

    	if(size < 0)
    		throw new IllegalArgumentException();

    	else
    		heap=(E[])(new Prioritizable[size]);
 
    }


    
    
    
/**
 * Method to check if the heap is empty
 * 
 * 
 * @return boolean true if it is empty, otherwise false
 */
    public boolean isEmpty() {
    	if(size() == 0)
    		return true;

    	else
    		return false;  
    }


    
    
 /**
  * Inserts an item into the heap if there is size or if it is bigger than the
  * smallest value.
  *    
  *@param item the item to insert into the priority queue
  */
    public void insert(E item) {
    	
    	int index = -1;   //varaible to hold an open index, set ot iindex not possible in array

    	
    	//if array still has space, find the open index
    	if(total < heap.length){
    		for(int i = 0; i < heap.length; i++){

    			if (heap[i] == null){
    				index=i;

    				
    				
    		    		heap[index] = item;
    		    		total++;
    		    		break;
    			}	   		
    		}
    	}

    	//Find the index of the lowest value and set it to that if item is bigger
    	else{
    		int min = getMax().getPriority();

    		for(int i = 0; i < heap.length; i++){
    			
    			if(heap[i].getPriority() < min){
    				min = heap[i].getPriority();
    				if(item.getPriority() >= min){
    					index=i;
    				}
    			}

    		}
    		//Set the item to the index
    		if (index !=-1){
        		heap[index] = item;
        		
    	}

    	

    	}
    	
    	
    }
    
    
    
    
/**
 * Remove the max element from the heap and return it
 * 
 * @return E the max element
 */
    public E removeMax() {
  	
    	if(isEmpty()){
    		throw new NoSuchElementException();
    	}
    	
    	int max = -1; 		//var to hold the max priority
    	int index = -1;		//variable to hold the index of the max
    	
    	for(int i=0; i<heap.length; i++){
    		
    		if(heap[i] != null && (heap[i].getPriority() >= max)){
    			max = heap[i].getPriority();
    			index = i;
    		}
    	}
    	
    	
    	if(index != -1 && heap[index] != null){
    		E maxReturn = heap[index];
    		heap[index] = null;
    		total--;
    		return maxReturn;  
    	}
 	
    	return null;
    }
    
    
    
    
    
/**
 * Finds the max element in the heap and returns it
 * 
 * 
 * @return the max element
 */
    public E getMax() {
    	
    	
    	if(isEmpty()){
    		throw new NoSuchElementException();	
    	}
    	

    	int max = -1;		//variable to hold the max priority
    	int index = -1;		//Holds the index of the max priority
    	
    	for(int i=0; i<heap.length; i++){
    		
    		if(heap[i] != null && heap[i].getPriority() > max){
    			max = heap[i].getPriority();
    			index = i;
    		}
    	}
     	if(index != -1)
     		return heap[index]; 
     	else
     		return null;
    }
    
    
/**
 * Finds the size of the heap in terms of how many objects are in it.
 * 
 * @return the amount of objects in the heap
 */
    public int size() { 	
        return total;  
    }
}
