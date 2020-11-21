
/***************************
* Program Name: Tarsn.java
* Description: Compress n files into a single Tar
* Name: Nicolas Fornicola
* Date: 11/11/2020
* Course: CS 375.01 Software Engineering II 
* Compile Instructions: javac Tarsn.java
* Execute: java Tarsn archive .txt
*
* Note: If the archive given is not type .tar it will be made a .tar
***************************/

import java.io.File;
import java.io.FileNotFoundException;  
import java.io.FileInputStream;
import java.io.IOException;

import javax.lang.model.util.ElementScanner6;


public class Tarsn
{
	public static void main(String[] args) throws IOException
	{
		if(args.length < 2)
		{
			System.out.println("Must have a archive and atleast one input file");
			return;
		}

		Boolean ll = false, hh = false;
		
		//checks which .ending the first file has
		if(args[1].substring(args[1].length()-3, args[1].length()).equals(".ll"))
			ll = true;

		
		if(args[1].substring(args[1].length()-3, args[1].length()).equals(".hh"))
			hh = true;


		//adds the appropiate .ending .zl .zh or .tar
		if(ll && !args[0].substring(args[0].length()-3, args[0].length()).equals(".zl"))
			args[0] += ".zl";
		else if(hh && !args[0].substring(args[0].length()-3, args[0].length()).equals(".zh"))
			args[0] += ".zh";
		else if(!ll && !hh && !(args[0].substring(args[0].length()-4, args[0].length()).equals(".tar")))
			args[0] += ".tar";
		

		//archive name, input1, input2
		File inputFile = null;
		File outputFile = null;
		BinaryIn  binIn = null;
		BinaryOut binOut = null;
	
		char separator =  (char) 255;  // all ones 11111111

		try 
		{
			outputFile = new File(args[0]);

			//outputfile checks
			if(!outputFile.exists()) 
				outputFile.createNewFile();
		

			if(!outputFile.isFile())
			{
				System.out.println("Archive given is not a file");
				return;	
			}
				

			binOut = new BinaryOut(args[0]);
			
			//start at one because we never want to access the tar at 0, only go through the files 1-n
			for(int i = 1; i < args.length ; i++)
			{
				//make input file somthing 
				inputFile = new File(args[i]);

				//if it doesnt exist return, if it is not a file return
				if (inputFile.exists() && inputFile.isFile()) 
				{

					//get content length and name length
					long fileLength = inputFile.length();
					int fileNameLength = args[i].length();  

					//Write the stuff
					binOut.write(fileNameLength);
					binOut.write(separator);
			
					binOut.write(args[i]);
					binOut.write(separator);
			
					binOut.write(fileLength);
					binOut.write(separator);
						
					//copy the file one character at a time then put it in Binout
					FileInputStream inStream = new FileInputStream(args[i]);
					int byteIn = 0; 
					
					
					byteIn = inStream.read();
					while(byteIn != -1 && (fileLength != 0))
					{
						//cast in to a char makes it 8 bytes instead of 32 :)
						binOut.write((char)byteIn);
						byteIn = inStream.read();
			
					}
					inStream.close();
					
					binOut.write(separator);
					binOut.write(separator);
				}
				else
				{
					System.out.println(inputFile.getName() + " was skipped, not a file or does not exist");
				}

			}		

		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		finally 
		{
			if (binOut != null)
			{
				binOut.close();
			}	
		}

		
    }

}