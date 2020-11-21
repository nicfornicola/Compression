/*************************************************************************
 *  Compilation:  javac LZW.java
 *  Execution:    java LZW - < input.txt   (compress)
 *  Execution:    java LZW + < input.txt   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *
 *  Compress or expand binary input from standard input using LZW.
 *
 *
 *************************************************************************/

import java.io.File;
import java.io.IOException;

public class LZW {
    private static final int R = 256;        // number of input chars
    private static final int L = 4096;       // number of codewords = 2^W
    private static final int W = 12;         // codeword width
    private static final String sl = File.separator;


    public static void compress(BinaryIn in, BinaryOut out) 
    { 
        String input = in.readString();
        TST<Integer> str = new TST<Integer>();

        for (int i = 0; i < R; i++)
            str.put("" + (char) i, i);

        // R is codeword for EOF
        int EoFcode = R+1; 

        while (input.length() > 0) 
        {
            // Find max prefix match prefix.
            String prefix = str.longestPrefixOf(input);  
            // Print prefix'prefix encoding.
            out.write(str.get(prefix), W);     
            int prefixLength = prefix.length();

            // Add prefix to symbol table.
            if (prefixLength < input.length() && EoFcode < L)    
                str.put(input.substring(0, prefixLength + 1), EoFcode++);

            // Scan past prefix in input.
            input = input.substring(prefixLength);            
        }

        out.write(R, W);
        out.close();
    } 

    public static void main(String[] args) 
    {

        if(args.length == 0)
        {
            System.err.println("Need at least one file to be compressed");
            return;
        }  

        for(int i = 0; i < args.length; i++)
        {
            //path to the output folder with the compressed and expanded version
            String outputpath = args[i];
    
            //make the folder 
            File file = new File(args[i]);

            if(file.exists())
            {
                //make and input stream from a.txt -> a.txt.hh
                BinaryIn  in  = new BinaryIn(args[i]);
                BinaryOut out = new BinaryOut(outputpath + ".ll");

                if(file.length() != 0)
                {
                    //compress from a.txt -> a.txt.hh
                    compress(in, out);
        
                }
                else
                    System.err.println("File to be compressed is empty.\n");   

                //close the output stream inbetween files so it can be swapped from a -> b
                out.close();
            }
            else
                System.err.println("File to be compressed does not exist.\n"); 

            
        }
        
        
    }

    

}