
/***************************
* Program Name: DeHuffman.java
* Description: Uncompress a file from compressed.txt.hh to uncompressed.txt
* Name: Nicolas Fornicola
* Date: 11/11/2020
* Course: CS 375.01 Software Engineering II 
* Compile Instructions: javac DeHuffman.java
* Execute: java DeHuffman a.txt.hh
*
***************************/
import java.io.File;
import java.io.IOException;


public class DeHuffman {

    private static final String sl = File.separator;

    // alphabet size of extended ASCII
    private static final int R = 256;
    public static boolean logging = true;

    // Huffman trie node
    private static class Node implements Comparable<Node> {
        private final char ch;
        private final int freq;
        private final Node left, right;

        Node(char ch, int freq, Node left, Node right) {
            this.ch    = ch;
            this.freq  = freq;
            this.left  = left;
            this.right = right;
        }

        // is the node a leaf node?
        private boolean isLeaf() {
            assert (left == null && right == null) || (left != null && right != null);
            return (left == null && right == null);
        }

        // compare, based on frequency
        public int compareTo(Node that) {
            return this.freq - that.freq;
        }
    }


    // expand Huffman-encoded input from standard input and write to standard output
    public static void expand(BinaryIn comIn, BinaryOut comOut) {

        // read in Huffman trie from input stream
        Node root = readTrie(comIn, comOut); 

        // number of bytes to write
        int length = comIn.readInt();

        // decode using the Huffman trie
        for (int i = 0; i < length; i++) {
            Node x = root;
            while (!x.isLeaf()) {
                boolean bit = comIn.readBoolean();
                if (bit) x = x.right;
                else     x = x.left;
            }
            comOut.write(x.ch);
        }
        comOut.flush();
    }


    private static Node readTrie(BinaryIn comIn, BinaryOut comOut) {

        boolean isLeaf = comIn.readBoolean();
        if (isLeaf) {
	    char x = comIn.readChar();
	       
            return new Node(x, -1, null, null);
        }
        else {
            return new Node('\0', -1, readTrie(comIn, comOut), readTrie(comIn, comOut));
        }
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
                    
        
                    //expand from a.txt.hh -> a.txt
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

