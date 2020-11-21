/***************************
* Program Name: Deschubs.java
* Description: Calls DeLZW, DeHuffman, or Untars depending on what file is given
* Name: Nicolas Fornicola
* Date: 11/21/2020
* Course: CS 375.01 Software Engineering II 
* Compile Instructions: javac Deschubs.java
* Execute: java Deschubs file.txt.ll
*
***************************/

import java.io.IOException;

public class Deschubs {
    
    public static void main(String[] args) throws IOException
    {
        if(args[0].substring(args[0].length()-3).equals(".ll"))
            DeLZW.main(args);

        if(args[0].substring(args[0].length()-3).equals(".hh"))
            DeHuffman.main(args);
            
        if(args[0].substring(args[0].length()-3).equals(".zl"))
            UnTars.main(args);
        
        if(args[0].substring(args[0].length()-3).equals(".zh"))
            UnTars.main(args);

    }  
}
