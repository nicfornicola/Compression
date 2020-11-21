/***************************
* Program Name: TarsnTest.java
* Description: Test Tarsn tarring things
* Name: Nicolas Fornicola
* Date: 11/09/2020
* Course: CS 375.01 Software Engineering II 
* Compile Instructions: mvn test
* Execute: mvn test
*
***************************/

//A is good to go

//huffman lzw compare pros and cons
//download the whole thing
//mvn clean test
//how to run from ajava
//describe the thing
//put everythingin the REadme on gethub

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;  

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;

import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;  



public class FinalTest 
{
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
	private final PrintStream originalErr = System.err;
	private static final String sl = File.separator;
	private static final RandString rand = new RandString();
	private static final String testPath = ("src" + sl + "Files" + sl + "testOutput" + sl + "log.txt");
	private static final String Huffmanpath = ("src" + sl + "Files" + sl + "Huffman" + sl);
	private static final String LZWpath = ("src" + sl + "Files" + sl + "LZW" + sl);
	private static final String Tarspath = ("src" + sl + "Files" + sl + "Tars" + sl);
	private static final String UnTarspath = ("src" + sl + "Files" + sl + "UnTars" + sl);
	

	//settings System.out 
    
	public void setUpStreams() 
	{
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}
	

    @After
	public void restoreStreams() 
	{
		System.setOut(originalOut);
		System.setErr(originalErr);
	}


	@BeforeClass
	/**
     * Overwrites everything in the output Filelog with the current run time
     * 
     */
	public static void flushTestWriter() throws IOException
	{
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy" + sl + "MM" + sl + "dd HH:mm:ss");  
   		LocalDateTime now = LocalDateTime.now();  
   		System.out.println(dtf.format(now));

		FileWriter testWriter = new FileWriter(testPath, false);
		testWriter.write(dtf.format(now) + "\n\n");
		testWriter.close();
	}

	/**
     * Writes to the output Filelog without overwritting other tests
     * 
     */
	public void TestWriter(String str) throws IOException
	{
		FileWriter testWriter = new FileWriter(testPath, true);
		testWriter.write(str + "\n\n");
		testWriter.close();
	}

	//-----------------------------------------------//
	//-----All Huffman are for the C level grade-----//
	//-----------------Huffman TESTS-----------------//
	//-----------------------------------------------//
	
	@Test
	public void EmptyFileCompressionHuffmanTest() throws IOException
	{
		System.err.println("EmptyFileCompressionHuffmanTest()");

		String[] args = {Huffmanpath + "uncompressed.txt"};

		//write nothing to the file
		FileWriter writer = new FileWriter(args[0]);
		writer.write("");
		writer.close();

		//read the file before
		File file1 = new File(args[0]);
		String content1 = Files.readString(file1.toPath()); 

		Huffman.main(args);

		//read the file after compression and expansion
		File file2 = new File(args[0]);
		String content2 = Files.readString(file2.toPath());  

		//there should be nothing in both
		assertEquals(content1, content2, "");
	}

	@Test
	public void GlobsHuffmanTest() throws IOException
	{
		System.err.println("GlobsHuffmanTest()");

		String[] args = {Huffmanpath + "glob1.txt", Huffmanpath + "glob2.txt", Huffmanpath + "glob3.txt"};
		String[] compargs = {Huffmanpath + "glob1.txt.hh", Huffmanpath + "glob2.txt.hh", Huffmanpath + "glob3.txt.hh"};

		//make some .txt files
		File uncomp1 = new File(args[0]);
		File uncomp2 = new File(args[1]);
		File uncomp3 = new File(args[2]);
		uncomp1.createNewFile();
		uncomp2.createNewFile();
		uncomp3.createNewFile();

		//get random strings
		String str1 = (rand.r("upper", "5") + rand.r("lower", "7")); 
		String str2 = (rand.r("upper", "3") + rand.r("lower", "10")); 
		String str3 = (rand.r("upper", "2") + rand.r("lower", "9")); 

		//put different random strings inside each file
		FileWriter writer = new FileWriter(args[0]);
		writer.write(str1);
		writer.close();
		writer = new FileWriter(args[1]);
		writer.write(str2);
		writer.close();
		writer = new FileWriter(args[2]);
		writer.write(str3);
		writer.close();

		//writes to the testwriter file
		TestWriter("GlobsHuffmanTest : " + str1 + "\n" + str2 + "\n" + str3 + "\n");

		
		//read the files before Huffman and DeHuffman
		File file = new File(args[0]);
		String content1 = Files.readString(file.toPath()); 
		file = new File(args[1]);
		String content2 = Files.readString(file.toPath());
		file = new File(args[2]);
		String content3 = Files.readString(file.toPath());

		Huffman.main(args);
		DeHuffman.main(compargs);

		//read the files after Huffman and DeHuffman
		file = new File(args[0]);
		String contentEx1 = Files.readString(file.toPath()); 
		file = new File(args[1]);
		String contentEx2 = Files.readString(file.toPath());
		file = new File(args[2]);
		String contentEx3 = Files.readString(file.toPath());

		//the before and after are the same
		assertEquals(content1, contentEx1);
		assertEquals(content2, contentEx2);
		assertEquals(content3, contentEx3);
	
	}

	@Test
	public void GlobsHuffmanTest2() throws IOException
	{
		System.err.println("GlobsHuffmanTest2()");

		String[] args = {Huffmanpath + "glob.bin", Huffmanpath + "glob.tar", Huffmanpath + "glob.txt"};
		String[] compargs = {Huffmanpath + "glob.bin.hh", Huffmanpath + "glob.tar.hh", Huffmanpath + "glob.txt.hh"};

		//make .bin .tar .txt files
		File uncomp1 = new File(args[0]);
		File uncomp2 = new File(args[1]);
		File uncomp3 = new File(args[2]);
		uncomp1.createNewFile();
		uncomp2.createNewFile();
		uncomp3.createNewFile();

		//random strings
		String str1 = (rand.r("upper", "5") + rand.r("lower", "7")); 
		String str2 = (rand.r("upper", "3") + rand.r("lower", "10")); 
		String str3 = (rand.r("upper", "2") + rand.r("lower", "9")); 

		//write random string
		FileWriter writer = new FileWriter(args[0]);
		writer.write(str1);
		writer.close();
		
		writer = new FileWriter(args[1]);
		writer.write(str2);
		writer.close();

		writer = new FileWriter(args[2]);
		writer.write(str3);
		writer.close();

		//write strings to testoutput
		TestWriter("GlobsHuffmanTest2 : " + str1 + "\n" + str2 + "\n" + str3 + "\n");

		//read the files we just wrote too
		File file = new File(args[0]);
		String content1 = Files.readString(file.toPath()); 

		file = new File(args[1]);
		String content2 = Files.readString(file.toPath());

		file = new File(args[2]);
		String content3 = Files.readString(file.toPath());

		//compress then decompress
		Huffman.main(args);
		DeHuffman.main(compargs);

		//read the files after Huffman Dehuffman
		file = new File(args[0]);
		String contentEx1 = Files.readString(file.toPath()); 

		file = new File(args[1]);
		String contentEx2 = Files.readString(file.toPath());

		file = new File(args[2]);
		String contentEx3 = Files.readString(file.toPath());

		//they should be the same from before and after
		assertEquals(content1, contentEx1);
		assertEquals(content2, contentEx2);
		assertEquals(content3, contentEx3);
	
	}

	@Test
	public void NormalCaseHuffmanTest() throws IOException
	{
		System.err.println("NormalCaseHuffmanTest()");

		String[] args = {Huffmanpath + "uncompressed.txt"};
		String[] compargs = {Huffmanpath + "uncompressed.txt.hh"};
		
		//make a file
		File uncomp = new File(args[0]);
		uncomp.createNewFile();

		//put some normalish random text
		String str = (rand.r("upper", "1") + rand.r("lower", "5") + " " 
					+ rand.r("upper", "1") + rand.r("lower", "6") + " " 
					+ rand.r("upper", "1") + rand.r("lower", "7") + " " 
					+ rand.r("upper", "1") + rand.r("lower", "9"));

		//write it to the file
		FileWriter writer = new FileWriter(args[0]);
		writer.write(str);
		writer.close();

		//write the strings to our testoutputfile
		TestWriter("NormalCaseHuffmanTest : " + str);

		//read before compression and expansion
		File file = new File(args[0]);
		String content = Files.readString(file.toPath()); 

		//compress then decompress
		Huffman.main(args);
		DeHuffman.main(compargs);

		//read the file after compression and expansion
		File fileEx = new File(args[0]);
		String contentEx = Files.readString(fileEx.toPath());  

		assertEquals(content, contentEx);
		
	}

	@Test
	public void ManyThingsHuffmanTest() throws IOException
	{
		System.err.println("ManyThingsHuffmanTest");

		String[] args = {Huffmanpath + "uncompressed.txt"};
		String[] compargs = {Huffmanpath + "uncompressed.txt.hh"};
		File uncomp = new File(args[0]);

		//make a file
		uncomp.createNewFile();
		
		String str = rand.r("rubbish", 100);

		FileWriter runWriter = new FileWriter(args[0]);
		runWriter.write(str);
		runWriter.close();


		//write the str to output test log
		TestWriter("ManyThingsHuffmanTest : " + str);

		//read before compression and expansion
		File file1 = new File(args[0]);
		String content1 = Files.readString(file1.toPath());    

		//compress then decompress
		Huffman.main(args);
		DeHuffman.main(compargs);

		//read the file after compression and expansion
		File file2 = new File(args[0]);
		String content2 = Files.readString(file2.toPath());  

		assertEquals(content1, content2);
	}

	@Test
	public void FileDoesNotExistHuffmanTest() throws IOException
	{
		System.err.println("FileDoesNotExistHuffmanTest()");

		String[] args = {Huffmanpath + "ThisDoesNotExist.txt"};
		File file1 = new File(args[0]);

		//check if the file exists... it doesnt
		assertFalse(file1.exists());		
		
		Huffman.main(args);

		// the file still doesnt exist 
		assertFalse(file1.exists());
	}

	@Test
	public void WrongAmountOfArgsHuffmanTest() throws IOException
	{
		System.err.println("WrongAmountOfArgsHuffmanTest()");

		String[] args = {};
		
		//pass in 0 args
		Huffman.main(args);

		//there are still 0 args and no crash
		assertEquals(args.length, 0);
		

	}

	@Test
	public void LongWordHuffmanTest() throws IOException
	{
		System.err.println("LongWordHuffmanTest()");

		String[] args = {Huffmanpath + "uncompressed.txt"};
		String[] compargs = {Huffmanpath + "uncompressed.txt.hh"};
		File uncomp = new File(args[0]);

		//make a file
		uncomp.createNewFile();


		String str = rand.r("upper", 50) + rand.r("lower", 50) + rand.r("upper", 50) + rand.r("lower", 50);

		FileWriter runWriter = new FileWriter(args[0]);
		runWriter.write(str);
		runWriter.close();

		//write the strings to our testoutputfile
		TestWriter("LongWordHuffmanTest   : " + str);

		//read before compression and expansion
		File file1 = new File(args[0]);
		String content1 = Files.readString(file1.toPath());  

		Huffman.main(args);		  
		DeHuffman.main(compargs);		  

		//read the file after compression and expansion
		File file2 = new File(args[0]);
		String content2 = Files.readString(file2.toPath());  

		assertEquals(content1, content2);
	}

	@Test
	public void UpperCaseHuffmanTest() throws IOException
	{
		System.err.println("UpperCaseHuffmanTest()");

		String[] args = {Huffmanpath + "uncompressed.txt"};
		String[] compargs = {Huffmanpath + "uncompressed.txt.hh"};
		File uncomp = new File(args[0]);

		//make a file
		uncomp.createNewFile();

		String str = rand.r("upper", 100);

		//write the random string to the file
		FileWriter runWriter = new FileWriter(args[0]);
		runWriter.write(str);
		runWriter.close();

		//write the strings to our testoutputfile
		TestWriter("UpperCaseHuffmanTest  : " + str);

		//read before compression and expansion
		File file1 = new File(args[0]);
		String content1 = Files.readString(file1.toPath());    

		Huffman.main(args);
		DeHuffman.main(compargs);

		//read the file after compression and expansion
		File file2 = new File(args[0]);
		String content2 = Files.readString(file2.toPath());  

		assertEquals(content1, content2);
	}

	@Test
	public void LowerCaseHuffmanTest() throws IOException
	{
		System.err.println("LowerCaseHuffmanTest()");

		String[] args = {Huffmanpath + "uncompressed.txt"};
		String[] compargs = {Huffmanpath + "uncompressed.txt.hh"};
		File uncomp = new File(args[0]);

		//make a file
		uncomp.createNewFile();

		String str = rand.r("lower", "100");

		// write the random string to the file
		FileWriter runWriter = new FileWriter(args[0]);
		runWriter.write(str);
		runWriter.close();

		//write the strings to our testoutputfile
		TestWriter("LowerCaseHuffmanTest  : " + str);

		//read before compression and expansion
		File file1 = new File(args[0]);
		String content1 = Files.readString(file1.toPath());
	
		//compress then uncompress
		Huffman.main(args);
		DeHuffman.main(compargs);

		//read the file after compression and expansion
		File file2 = new File(args[0]);
		String content2 = Files.readString(file2.toPath());  

		assertEquals(content1, content2);
	}

	@Test
	public void JustSpacesHuffmanTest() throws IOException
	{
		System.err.println("JustSpacesHuffmanTest()");

		String[] args = {Huffmanpath + "uncompressed.txt"};
		String[] compargs = {Huffmanpath + "uncompressed.txt.hh"};
		File uncomp = new File(args[0]);

		//make a file
		uncomp.createNewFile();

		//string has just spaces
		String str = "               ";

		//write the just spaces
		FileWriter writer = new FileWriter(args[0]);
		writer.write(str);
		writer.close();

		//read before compression and expansion
		File file1 = new File(args[0]);
		String content1 = Files.readString(file1.toPath());

		Huffman.main(args);
		DeHuffman.main(compargs);

		//read the file after compression and expansion
		File file2 = new File(args[0]);
		String content2 = Files.readString(file2.toPath());  

		//both should equal just spaces
		assertEquals(content1, content2, "               ");
	}



	//-----------------------------------------------//
	//-------All LZW are for the B level grade-------//
	//-------------------LZW TESTS-------------------//
	//-----------------------------------------------//

	
	@Test
	public void NormalCaseLZWTest() throws IOException
	{
		System.err.println("NormalCaseLZWTest()");

		String[] args = {LZWpath + "uncompressed.txt"};
		String[] compargs = {LZWpath + "uncompressed.txt.ll"};
		File uncomp = new File(args[0]);

		//make file
		uncomp.createNewFile();

		String str = (rand.r("upper", "1") + rand.r("lower", "5") + " " 
					+ rand.r("upper", "1") + rand.r("lower", "6") + " " 
					+ rand.r("upper", "1") + rand.r("lower", "7") + " " 
					+ rand.r("upper", "1") + rand.r("lower", "9"));

		//write the strings to the file
		FileWriter writer = new FileWriter(args[0]);
		writer.write(str);
		writer.close();

		//write the strings to our testoutputfile
		TestWriter("NormalCaseLZWTest : " + str);

		//read before compression and expansion
		File file = new File(args[0]);
		String content = Files.readString(file.toPath()); 

		//compress and decompress
		LZW.main(args);
		DeLZW.main(compargs);

		//read the file after compression and expansion
		File fileEx = new File(args[0]);
		String contentEx = Files.readString(fileEx.toPath());  

		//they will be equal
		assertEquals(content, contentEx);
		
	}

	@Test
	public void EmptyFileCompressionLZWTest() throws IOException
	{
		System.err.println("EmptyFileCompressionLZWTest()");

		String[] args = {LZWpath + "uncompressed.txt"};

		//write nothing to the file to make sure there is nothing
		FileWriter writer = new FileWriter(args[0]);
		writer.write("");
		writer.close();

		//read before compression and expansion
		File file1 = new File(args[0]);
		String content1 = Files.readString(file1.toPath()); 

		//compress 
		LZW.main(args);

		//read the file after compression and expansion
		File file2 = new File(args[0]);
		String content2 = Files.readString(file2.toPath());  

		// there is still nothing because the LZW said there needs to be stuff to be compressed
		assertEquals(content1, content2, "");
	}

	@Test
	public void ManyThingsLZWTest() throws IOException
	{
		System.err.println("ManyThingsLZWTest");

		String[] args = {LZWpath + "uncompressed.txt"};
		String[] compargs = {LZWpath + "uncompressed.txt.ll"};

		//random string
		String str = rand.r("rubbish", 100);
		
		//write the random string to file
		FileWriter runWriter = new FileWriter(args[0]);
		runWriter.write(str);
		runWriter.close();

		//write the strings to our testoutputfile
		TestWriter("ManyThingsLZWTest : " + str);

		//read before compression and expansion
		File file1 = new File(args[0]);
		String content1 = Files.readString(file1.toPath());    

		//compress and decompress
		LZW.main(args);
		DeLZW.main(compargs);

		//read the file after compression and expansion
		File file2 = new File(args[0]);
		String content2 = Files.readString(file2.toPath());  

		//they will be equal before and after compression/expansion
		assertEquals(content1, content2);
	}


	@Test
	public void FileDoesNotExistLZWTest() throws IOException
	{
		System.err.println("FileDoesNotLZWExist()");

		String[] args = {LZWpath + "ThisDoesNotExist.txt"};

		//check if the file exists... it doesnt
		File file1 = new File(args[0]);
		assertFalse(file1.exists());	

		LZW.main(args);
		
		//still doesnt
		assertFalse(file1.exists());
	}

	@Test
	public void WrongAmountOfArgsLZWTest() throws IOException
	{
		System.err.println("WrongAmountOfArgsLZWTest()");

		String[] args = {};
		//pass in zero args
		LZW.main(args);

		//still 0 args and no crash because LZW catches it
		assertEquals(args.length, 0);
	}

	@Test
	public void LongWordLZWTest() throws IOException
	{
		System.err.println("LongWordLZWTest()");

		String[] args = {LZWpath + "uncompressed.txt"};
		String[] compargs = {LZWpath + "uncompressed.txt.ll"};

		//make random string
		String str = rand.r("upper", 50) + rand.r("lower", 50) + rand.r("upper", 50) + rand.r("lower", 50);

		//write random string
		FileWriter runWriter = new FileWriter(args[0]);
		runWriter.write(str);
		runWriter.close();

		//write the strings to our testoutputfile
		TestWriter("LongWordLZWTest   : " + str);
	
		//read before compression and expansion
		File file1 = new File(args[0]);
		String content1 = Files.readString(file1.toPath());  

		//compress and decompress
		LZW.main(args);	
		DeLZW.main(compargs);
	  
		//read the file after compression and expansion
		File file2 = new File(args[0]);
		String content2 = Files.readString(file2.toPath());  

		//the same before and after compression/expansion
		assertEquals(content1, content2);
	}

	@Test
	public void UpperCaseLZWTest() throws IOException
	{
		System.err.println("UpperCaseLZWTest()");

		String[] args = {LZWpath + "uncompressed.txt"};
		String[] compargs = {LZWpath + "uncompressed.txt.ll"};

		//random string
		String str = rand.r("upper", 100);

		//write the string to the file
		FileWriter runWriter = new FileWriter(args[0]);
		runWriter.write(str);
		runWriter.close();

		//write the strings to our testoutputfile
		TestWriter("UpperCaseLZWTest  : " + str);
	
		//read before compression and expansion
		File file1 = new File(args[0]);
		String content1 = Files.readString(file1.toPath());    

		//compress/decompress
		LZW.main(args);
		DeLZW.main(compargs);

		//read the file after compression and expansion
		File file2 = new File(args[0]);
		String content2 = Files.readString(file2.toPath());  

		//the same before and after compression/expansion
		assertEquals(content1, content2);
	}

	@Test
	public void LowerCaseLZWTest() throws IOException
	{
		System.err.println("LowerCaseLZWTest()");

		String[] args = {LZWpath + "uncompressed.txt"};
		String[] compargs = {LZWpath + "uncompressed.txt.ll"};

		//random string
		String str = rand.r("lower", "100");

		//write the random string
		FileWriter runWriter = new FileWriter(args[0]);
		runWriter.write(str);
		runWriter.close();

		//write the strings to our testoutputfile
		TestWriter("LowerCaseLZWTest  : " + str);

		//read before compression and expansion
		File file1 = new File(args[0]);
		String content1 = Files.readString(file1.toPath()); 
  
		//compression/expansion
		LZW.main(args);
		DeLZW.main(compargs);

		
		//read the file after compression and expansion
		File file2 = new File(args[0]);
		String content2 = Files.readString(file2.toPath());  

		//the same before and after compression/expansion
		assertEquals(content1, content2);
	}

	@Test
	public void JustSpacesLZWTest() throws IOException
	{
		System.err.println("JustSpacesLZWTest()");

		String[] args = {LZWpath + "uncompressed.txt"};
		String[] compargs = {LZWpath + "uncompressed.txt.ll"};

		//just spaces
		String str = "               ";
	
		//write the spaces
		FileWriter writer = new FileWriter(args[0]);
		writer.write(str);
		writer.close();

		//read before compression and expansion
		File file1 = new File(args[0]);
		String content1 = Files.readString(file1.toPath());

		//compression/expansion
		LZW.main(args);
		DeLZW.main(compargs);

		//read the file after compression and expansion
		File file2 = new File(args[0]);
		String content2 = Files.readString(file2.toPath());  

		//the same before and after compression/expansion
		assertEquals(content1, content2);
	}

	@Test
	public void GlobsLZWTest1() throws IOException
	{
		System.err.println("GlobsLZWTest1()");

		String[] args = {LZWpath + "glob1.txt", LZWpath + "glob2.txt", LZWpath + "glob3.txt"};
		String[] compargs = {LZWpath + "glob1.txt.ll", LZWpath + "glob2.txt.ll", LZWpath + "glob3.txt.ll"};

		//make the files
		File uncomp1 = new File(args[0]);
		File uncomp2 = new File(args[1]);
		File uncomp3 = new File(args[2]);
		uncomp1.createNewFile();
		uncomp2.createNewFile();
		uncomp3.createNewFile();

		//random strings
		String str1 = (rand.r("upper", "5") + rand.r("lower", "7")); 
		String str2 = (rand.r("upper", "3") + rand.r("lower", "10")); 
		String str3 = (rand.r("upper", "2") + rand.r("lower", "9")); 

		//write to the files		
		FileWriter writer = new FileWriter(args[0]);
		writer.write(str1);
		writer.close();
		
		writer = new FileWriter(args[1]);
		writer.write(str2);
		writer.close();

		writer = new FileWriter(args[2]);
		writer.write(str3);
		writer.close();

		//write the strings to our testoutputfile
		TestWriter("GlobsLZWTest1 : " + str1 + "\n" + str2 + "\n" + str3 + "\n");

		//read before compression and expansion
		File file = new File(args[0]);
		String content1 = Files.readString(file.toPath()); 

		//read the files after expansion
		file = new File(args[1]);
		String content2 = Files.readString(file.toPath());

		file = new File(args[2]);
		String content3 = Files.readString(file.toPath());

		//compress decompress
		LZW.main(args);
		DeLZW.main(compargs);

		//read the file after compression and expansion
		file = new File(args[0]);
		String contentEx1 = Files.readString(file.toPath()); 

		file = new File(args[1]);
		String contentEx2 = Files.readString(file.toPath());

		file = new File(args[2]);
		String contentEx3 = Files.readString(file.toPath());


		//this stuff should be the same before and after
		assertEquals(content1, contentEx1);
		assertEquals(content2, contentEx2);
		assertEquals(content3, contentEx3);
	
	}

	@Test
	public void GlobsLZWTest2() throws IOException
	{
		System.err.println("GlobsLZWTest2()");

		String[] args = {LZWpath + "glob.bin", LZWpath + "glob.tar", LZWpath + "glob.txt"};
		String[] compargs = {LZWpath + "glob.bin.ll", LZWpath + "glob.tar.ll", LZWpath + "glob.txt.ll"};

		//make the files
		File uncomp1 = new File(args[0]);
		File uncomp2 = new File(args[1]);
		File uncomp3 = new File(args[2]);
		uncomp1.createNewFile();
		uncomp2.createNewFile();
		uncomp3.createNewFile();

		//random strings
		String str1 = (rand.r("upper", "5") + rand.r("lower", "7")); 
		String str2 = (rand.r("upper", "3") + rand.r("lower", "10")); 
		String str3 = (rand.r("upper", "2") + rand.r("lower", "9")); 

		//write the strings		
		FileWriter writer = new FileWriter(args[0]);
		writer.write(str1);
		writer.close();
		
		writer = new FileWriter(args[1]);
		writer.write(str2);
		writer.close();

		writer = new FileWriter(args[2]);
		writer.write(str3);
		writer.close();

		//write the strings to our testoutputfile
		TestWriter("GlobsLZWTest2 : " + str1 + "\n" + str2 + "\n" + str3 + "\n");

		//read before compression and expansion
		File file = new File(args[0]);
		String content1 = Files.readString(file.toPath()); 

		file = new File(args[1]);
		String content2 = Files.readString(file.toPath());

		file = new File(args[2]);
		String content3 = Files.readString(file.toPath());

		//compressions and expansion
		LZW.main(args);
		DeLZW.main(compargs);

		//read the file after compression and expansion
		file = new File(args[0]);
		String contentEx1 = Files.readString(file.toPath()); 

		file = new File(args[1]);
		String contentEx2 = Files.readString(file.toPath());

		file = new File(args[2]);
		String contentEx3 = Files.readString(file.toPath());
		
		//equal before and after the compressions/expansion
		assertEquals(content1, contentEx1);
		assertEquals(content2, contentEx2);
		assertEquals(content3, contentEx3);
	
	}

		
	//-----------------------------------------------//
	//------All Tars are for the A level grade-------//
	//-------------------TARS TESTS------------------//
	//-----------------------------------------------//


	@Test
	public void TarsHuffmanGlobTest() throws IOException
	{
		System.err.println("TarsHuffmanGlobTest()");

		String[] args = {Tarspath + "globH1.txt", Tarspath + "globH2.txt", Tarspath + "globH3.txt"};
		String[] argsCompressed = {Tarspath + "globHArchive", Tarspath + "globH1.txt.hh", Tarspath + "globH2.txt.hh", Tarspath + "globH3.txt.hh"};
		String[] argsCompressed2 = {Tarspath + "globH1.txt.hh", Tarspath + "globH2.txt.hh", Tarspath + "globH3.txt.hh"};
		String[] argsArchive = {Tarspath + "globHArchive.zh"};

		//make the files
		File uncomp2 = new File(args[0]);
		File uncomp3 = new File(args[1]);
		File uncomp4 = new File(args[2]);
		uncomp2.createNewFile();
		uncomp3.createNewFile();
		uncomp4.createNewFile();


		//random strings
		String str1 = (rand.r("upper", "5") + rand.r("lower", "7")); 
		String str2 = (rand.r("upper", "3") + rand.r("lower", "10")); 
		String str3 = (rand.r("upper", "2") + rand.r("lower", "9")); 

		//write to the files		
		FileWriter writer = new FileWriter(args[0]);
		writer.write(str1);
		writer.close();
		
		writer = new FileWriter(args[1]);
		writer.write(str2);
		writer.close();

		writer = new FileWriter(args[2]);
		writer.write(str3);
		writer.close();

		//write the strings to our testoutputfile
		TestWriter("TarsHuffmanGlobTest : " + str1 + "\n" + str2 + "\n" + str3 + "\n");

		//read before compression and expansion
		String content1 = str1;
		String content2 = str2;
		String content3 = str3;

		//compress my files from the psuedo glob
		Huffman.main(args);

		//Tar the Glob given
		Tarsn.main(argsCompressed);	

		//untar it
		UnTars.main(argsArchive);	

		//unLZW it
		DeHuffman.main(argsCompressed2);
		
		//read the file after compression and Tars
		File file = new File(args[0]);
		String contentEx1 = Files.readString(file.toPath()); 

		file = new File(args[1]);
		String contentEx2 = Files.readString(file.toPath());

		file = new File(args[2]);
		String contentEx3 = Files.readString(file.toPath());

		//the content will be the same before and after
		assertEquals(content1, contentEx1);
		assertEquals(content2, contentEx2);
		assertEquals(content3, contentEx3);
	
	}

	@Test
	public void TarsLZWGlobTest() throws IOException
	{
		System.err.println("TarsLZWGlobTest()");

		String[] args = {Tarspath + "globL1.txt", Tarspath + "globL2.txt", Tarspath + "globL3.txt"};
		String[] argsCompressed = {Tarspath + "globLArchive", Tarspath + "globL1.txt.ll", Tarspath + "globL2.txt.ll", Tarspath + "globL3.txt.ll"};
		String[] argsCompressed2 = {Tarspath + "globL1.txt.ll", Tarspath + "globL2.txt.ll", Tarspath + "globL3.txt.ll"};
		String[] argsArchive = {Tarspath + "globLArchive.zl"};
		File uncomp2 = new File(args[0]);
		File uncomp3 = new File(args[1]);
		File uncomp4 = new File(args[2]);

		//make the files
		uncomp2.createNewFile();
		uncomp3.createNewFile();
		uncomp4.createNewFile();

		String str1 = (rand.r("upper", "5") + rand.r("lower", "7")); 
		String str2 = (rand.r("upper", "3") + rand.r("lower", "10")); 
		String str3 = (rand.r("upper", "2") + rand.r("lower", "9")); 

		//write random strings
		FileWriter writer = new FileWriter(args[0]);
		writer.write(str1);
		writer.close();
		
		writer = new FileWriter(args[1]);
		writer.write(str2);
		writer.close();

		writer = new FileWriter(args[2]);
		writer.write(str3);
		writer.close();

		//write the strings to our testoutputfile
		TestWriter("TarsLZWGlobTest : " + str1 + "\n" + str2 + "\n" + str3 + "\n");

		//read before compression and expansion
		String content1 = str1;
		String content2 = str2;
		String content3 = str3;

		//compress my files from the psuedo glob
		LZW.main(args);

		//Tar the Glob given
		Tarsn.main(argsCompressed);	
		//untar it
		UnTars.main(argsArchive);	

		//unLZW it
		DeLZW.main(argsCompressed2);

		//read the file after expansion and Tars
		File file = new File(args[0]);
		String contentEx1 = Files.readString(file.toPath()); 

		file = new File(args[1]);
		String contentEx2 = Files.readString(file.toPath());

		file = new File(args[2]);
		String contentEx3 = Files.readString(file.toPath());

		//the content will be the same before and after
		assertEquals(content1, contentEx1);
		assertEquals(content2, contentEx2);
		assertEquals(content3, contentEx3);
	
	}


	@Test
	public void UnTarsLZWGlobTest() throws IOException
	{
		System.err.println("UnTarsLZWGlobTest()");

		//make files to check if they exist or not
		String[] args = {UnTarspath + "globL1.txt.ll", UnTarspath + "globL2.txt.ll", UnTarspath + "globL3.txt.ll"};
		File uncomp1 = new File(args[0]);
		File uncomp2 = new File(args[1]);
		File uncomp3 = new File(args[2]);

		//these files do not exist before reading the tar
		assertFalse(uncomp1.exists());
		assertFalse(uncomp2.exists());
		assertFalse(uncomp3.exists());
	
		//untar it
		String[] Targs = {UnTarspath + "globLArchive.zl"};
		UnTars.main(Targs);

		//the files exist now because the Untars worked
		assertTrue(uncomp1.exists());
		assertTrue(uncomp2.exists());
		assertTrue(uncomp3.exists());

		//delete the files so this tests works again
		uncomp1.delete();
		uncomp2.delete();
		uncomp3.delete();		
	}

	@Test
	public void UnTarsHuffmanGlobTest() throws IOException
	{
		System.err.println("UnTarsHuffGlobTest()");
		
		//make files to check if they exist or not
		String[] args = {UnTarspath + "globH1.txt.hh", UnTarspath + "globH2.txt.hh", UnTarspath + "globH3.txt.hh"};
		File uncomp1 = new File(args[0]);
		File uncomp2 = new File(args[1]);
		File uncomp3 = new File(args[2]);

		//these files do not exist before reading the tar
		assertFalse(uncomp1.exists());
		assertFalse(uncomp2.exists());
		assertFalse(uncomp3.exists());
	
		//untar it
		String[] Targs = {UnTarspath + "globHArchive.zh"};
		UnTars.main(Targs);

		//the files exist now because the Untars worked
		assertTrue(uncomp1.exists());
		assertTrue(uncomp2.exists());
		assertTrue(uncomp3.exists());

		//delete the files so this tests works again
		uncomp1.delete();
		uncomp2.delete();
		uncomp3.delete();
			
	}
	
}

