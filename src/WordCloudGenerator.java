import java.util.*;
import java.io.*;

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

public class WordCloudGenerator {
    /**
     * The main method generates a word cloud.
     * 
     * @param args the command-line arguments that determine where input and 
     * output is done:
     * <ul>
     *   <li>args[0] is the name of the input file</li>
     *   <li>args[1] is the name of the output file</li>
     *   <li>args[2] is the name of the file containing the words to ignore 
     *       when generating the word cloud</li>
     *   <li>args[3] is the maximum number of words to include in the word 
     *       cloud</li> 
     * </ul>
     * @throws FileNotFoundException 
     */
    public static void main(String[] args){
    	Scanner in = new Scanner(System.in);;         // for input from text file
        PrintStream out = null;    // for output to html file
        Scanner inIgnore = null;   // for input from ignore file
        DictionaryADT<KeyWord> dictionary = new BSTDictionary<KeyWord>();  

        
        
        // Check the command-line arguments and set up the input and output     
        if(args.length != 4){
        	System.out.println("Four arguments required: inputFileName "
        			+ "outputFileName ignoreFileName maxWords");
        	return;
        }
        
        
        //Make files out of the ingore and input and see if they are readable
        File file0 = new File(args[0]);
        File file2 = new File(args[2]);   
        
        if(!file0.exists() || !file0.canRead() || !file2.exists() || !file2.canRead()){
        		
        	if(!file0.exists() || !file0.canRead())
        		System.out.println("Error: cannot access file " + args[0]);
        	if(!file2.exists() || !file2.canRead())
        		System.out.println("Error: cannot access file " + args[2]);
        	
        	return;
        }
        
        
        //Check to see if the length paramater is a positive ingeter.
        int max = Integer.parseInt(args[3]);
        
        if(max < 1){
        	System.out.println("Error: maxWords must be a positive integer");
        	return;
        }


        //Make dictionary of words to ignore
        try{
        	inIgnore = new Scanner(file2);
        }
        catch(FileNotFoundException e){       	
        	//File isn't there
        }
        
        
        // Create the dictionary of words to ignore
        // You do not need to change this code.
        DictionaryADT<String> ignore = new BSTDictionary<String>();
        while (inIgnore.hasNext()) {
        	
            try {
                ignore.insert(inIgnore.next().toLowerCase());
               
            } catch (DuplicateException e) {
                // if there is a duplicate, we'll just ignore it
            	
            }
        }
        
      
            
        // Process the input file line by line and create a dictionary bst
        try{
        	 in = new Scanner(file0);
        }
        catch(FileNotFoundException e){
        	
        	//File isn't there
        }
         
        while (in.hasNext()) {
            String line = in.nextLine();
            List<String> words = parseLine(line);

          //Insert the words into the dictionary
            for (String word : words){
            	
            	try{
            		word = word.toLowerCase();
            	
            		if(ignore.lookup(word) == null)
            			dictionary.insert(new KeyWord(word));
            		
            	}
            	catch(DuplicateException e){           		
            		//Catching a duplicate then incrementing 
            		KeyWord find = new KeyWord(word);           		
            		KeyWord a = dictionary.lookup(find);
            		a.increment();
            		
            		          	
            	}            	               
            } //end for loop
          

        } // end while

      
       

        //Print out information about the dictionary 
        System.out.println("# keys: " + dictionary.size());

        //figure out the average path length
        double avgLength = dictionary.totalPathLength()/(double)dictionary.size(); 
        System.out.println("avg path length: " + avgLength);


        //Figure out the linear average
        double avgPath = (1.0 + dictionary.totalPathLength())/2.0;
        System.out.println("linear avg path: " + avgPath);



        //Put the dictionary of key words into a priority queue.
        PriorityQueueADT<KeyWord> heap = new ArrayHeap<KeyWord>(max);
        Iterator<KeyWord> iter = dictionary.iterator();     
        while(iter.hasNext()){       	
      	  heap.insert(iter.next());	      	 
        }
        
        
      
       //Make a dictionary from the heap by adding everything to a new dictionary       
        DictionaryADT<KeyWord> priorityDictionary = new BSTDictionary<KeyWord>();  
        while(!heap.isEmpty()){
        	try{
        		priorityDictionary.insert(heap.removeMax());      			       		
        	}
        	catch(DuplicateException e){
        		//nothing to do
        	}
        	catch(IllegalArgumentException e){
        		//nothing to do
        	}
        }
      
      

        //Save to file
        File file = new File(args[1]);

        // warn about overwriting existing file
        if (file.exists()) {
        	System.out.print("warning: file already exists, ");
        	System.out.println("will be overwritten");
        }

        // give message if not able to write to file
        if (file.exists() && !file.canWrite()) {
        	System.out.println("unable to save");
        	return;
        }

        // print each message to the file
        try {
        	out = new PrintStream(file);
        	generateHtml(priorityDictionary, out);


        } catch (FileNotFoundException e) {
        	System.out.println("unable to save");
        }
   


        // Close everything
        if (in != null) 
            in.close();
        if (inIgnore != null) 
            inIgnore.close();
        if (out != null) 
            out.close();
    }
    
    /**
     * Parses the given line into an array of words.
     * 
     * @param line a line of input to parse
     * @return a list of words extracted from the line of input in the order
     *         they appear in the line
     *         
     * DO NOT CHANGE THIS METHOD.
     */
    private static List<String> parseLine(String line) {
        String[] tokens = line.split("[ ]+");
        ArrayList<String> words = new ArrayList<String>();
        for (int i = 0; i < tokens.length; i++) {  // for each word
            
            // find index of first digit/letter
              boolean done = false; 
              int first = 0;
            String word = tokens[i];
            while (first < word.length() && !done) {
                if (Character.isDigit(word.charAt(first)) ||
                    Character.isLetter(word.charAt(first)))
                    done = true;
                else first++;
            }
            
            // find index of last digit/letter
            int last = word.length()-1;
            done = false;
            while (last > first && !done) {
                if (Character.isDigit(word.charAt(last)) ||
                        Character.isLetter(word.charAt(last)))
                        done = true;
                    else last--;
            }
            
            // trim from beginning and end of string so that is starts and
            // ends with a letter or digit
            word = word.substring(first, last+1);
  
            // make sure there is at least one letter in the word
            done = false;
            first = 0;
            while (first < word.length() && !done)
                if (Character.isLetter(word.charAt(first)))
                    done = true;
                else first++;           
            if (done)
                words.add(word);
        }
        
        return words;
    }
    
    /**
     * Generates the html file using the given list of words.  The html file
     * is printed to the provided PrintStream.
     * 
     * @param words a list of KeyWords
     * @param out the PrintStream to print the html file to
     * 
     * DO NOT CHANGE THIS METHOD
     */
    private static void generateHtml(DictionaryADT<KeyWord> words, 
                                     PrintStream out) {
           String[] colors = { 
                "6F", "6A", "65", "60",
                "5F", "5A", "55", "50",
                "4F", "4A", "45", "40",
                "3F", "3A", "35", "30",
                "2F", "2A", "25", "20",
                "1F", "1A", "15", "10",        
                "0F", "0A", "05", "00" 
                };
           int initFontSize = 80;
           
          
           
        // Print the header information including the styles
        out.println("<head>\n<title>Word Cloud</title>");
        out.println("<style type=\"text/css\">");
        out.println("body { font-family: Arial }");
        
        // Each style is of the form:
        // .styleN {
        //      font-size: X%;
        //      color: #YYAA;
        // }
        // where N and X are integers and Y is two hexadecimal digits
        for (int i = 0; i < colors.length; i++)
            out.println(".style" + i + 
                    " {\n    font-size: " + (initFontSize + i*20)
                    + "%;\n    color: #" + colors[i] + colors[i]+ "AA;\n}");
        
        out.println("</style>\n</head>\n<body><p>");        
        
        // Find the minimum and maximum values in the collection of words
        int min = Integer.MAX_VALUE, max = 0;
        for (KeyWord word : words) {
            int occur = word.getOccurrences();
            if (occur > max)
                max = occur;
            else if (occur < min)
                min = occur;
        }

        double slope = (colors.length - 1.0)/(max - min);
        
        for (KeyWord word : words) {
            out.print("<span class=\"style");
            
            // Determine the appropriate style for this value using
            // linear interpolation
            // y = slope *(x - min) (rounded to nearest integer)
            // where y = the style number
            // and x = number of occurrences
            int index = (int)Math.round(slope*(word.getOccurrences() - min));
            
            out.println(index + "\">" + word.getWord() + "</span>&nbsp;");
        }
        
        // Print the closing tags
        out.println("</p></body>\n</html>");
    }
 }
