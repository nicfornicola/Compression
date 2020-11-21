
/***************************
* Program Name: UnTars.java
* Description: get stuff out of tars
* Name: Nicolas Fornicola
* Date: 11/5/2020
* Course: CS 375.01 Software Engineering II 
* Compile Instructions: javac UnTars.java
* Execute: java UnTars file.tar
*
***************************/

import java.io.File;
import java.io.IOException;

public class UnTars
{

	public static void UnTarIt(String tarFile) {

		char sep = (char) 255; // all ones 11111111
	
		// lengthoffilename, sep, filename, sep, lengthoffile, sep, bits
		File tar = new File(tarFile);
		BinaryIn in = null;
		BinaryOut out = null;
	

		//checks if the tar is exists and is a file
		if(!tar.exists() || !tar.isFile())
		{
			System.out.println("Tar does not exist");
			return;
		}	

		//set in to the tarFile
		in = new BinaryIn(tarFile);

		//stop if its empty
		if(in.isEmpty())
		{
			System.out.println("Tars is empty");
			return;
		}

		while (!in.isEmpty()) 
		{

			// read the nameLength
			int nameLength = in.readInt();

			// read serperator 11111111
			sep = in.readChar();
			// read the fileName
			String fileName = "";

			for (int i = 0; i < nameLength; i++) 
			{
				// concatenate characters to string
				fileName += in.readChar();
			}

			
			// read serperator 11111111
			sep = in.readChar();
			// read fileSize
			long fileSize = in.readLong();

			// read serperator 11111111
			sep = in.readChar();


			// set out to output the contents of the file to its own file
			String str = "";
			for (int i = 0; i < fileSize; i++) 
			{
				// concatenate characters to string
				char con = in.readChar();
				str += con;
			}

			//write the stuff to the new file
			out = new BinaryOut(fileName);
			out.write(str);
			out.close();

			sep = in.readChar();
			sep = in.readChar();


		}


	}

	public static void main(String[] args) throws IOException {

		if(args.length != 1)
		{
			System.out.println("UnTars accepts only 1 argument, recieved '" + args.length + "'");
			return;
		}	

		UnTarIt(args[0]);
    }
}
