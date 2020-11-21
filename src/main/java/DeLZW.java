/***************************
* Program Name: DeLZW.java
* Description: Uncompress a file from compressed.txt.ll to uncompressed.txt
* Name: Nicolas Fornicola
* Date: 11/11/2020
* Course: CS 375.01 Software Engineering II 
* Compile Instructions: javac DeLZW.java
* Execute: java DeLZW a.txt.ll
*
***************************/

import java.io.File;
import java.io.IOException;

public class DeLZW {
    private static final int R = 256;        // number of input chars
    private static final int L = 4096;       // number of codewords = 2^W
    private static final int W = 12;         // codeword width
    private static final String sl = File.separator;


    public static void expand(BinaryIn in, BinaryOut out) 
    {
        String[] str = new String[L];
        //next available codeword value
        int iWord; 

        //initialize symbol table with all 1-character strings
        for (iWord = 0; iWord < R; iWord++)
            str[iWord] = "" + (char) iWord;

        //(unused) lookahead for EOF
        str[iWord++] = "";                        

        int codeword = in.readInt(W);
        String val = str[codeword];

        while(true) 
        {
            out.write(val);
            codeword = in.readInt(W);

            if (codeword == R) 
                break;

            String prefix = str[codeword];

            //special case hack
            if (iWord == codeword) 
                prefix = val + val.charAt(0);  

            if (iWord < L) 
                str[iWord++] = val + prefix.charAt(0);

            val = prefix;
        }
        out.close();
    }

    public static void main(String[] args) throws IOException
    {
        if(args.length == 0)
        {
            System.err.println("Need at least one file to be uncompressed");
            return;
        }  

        for(int i = 0; i < args.length; i++)
        {
            //path to the output folder with the compressed and expanded version
            String inputpath = args[i];
            String outputpath = inputpath.substring(0,inputpath.length()-3);
    
            //make the folder
            File file = new File(args[i]);

            if(file.exists())
            {
                if(file.length() != 0)
                {
                    
                    //expand from a.txt.ll -> a.txt
                    //all within the output folder
                    BinaryIn comIn = new BinaryIn(inputpath);
                    BinaryOut comOut = new BinaryOut(outputpath);
                    
            
                    //a.txt.h -> a.txt
                    expand(comIn, comOut);
                }
                else
                    System.err.println("File to be uncompressed is empty.\n"); 
            }
            else
                System.err.println("File to be uncompressed does not exist.\n"); 

        }
        
        
    }

}

    

