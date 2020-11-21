# Compression
This project implements LZW and Huffman encoding with Tarsing.


//huffman lzw compare pros and cons


# Downloading:
Download the whole Compression zip


# Testing:
Run mvn clean test in a command line in dir with the pom.xml



# Compile and run instructions:
(This is for running in src/main/java/)
(if it says .txt, it could be any file extension)

Huffman and LZW: 
-------------------
(compress any text file extension including globs that contain all the same type of file or different types of files)

javac Huffman.java 

java Huffman <filename>.txt | java Huffman *.txt 

DeHuffman and DeLZW: 
-------------------
(uncompres text files that are compressed useing Huffman encodings)

javac DeHuffman.java \n

java DeHuffman <filename>.hh | java Huffman *.hh \n
  
javac DeLZW.java 

java DeLZW <filename>.ll | java Huffman *.ll 

Tarsn: 
-------------------
(puts any type of file or group of files into an archive, the arhive is made if it does not exist already)

javac Tarsn.java

java Tarsn archive <filename>.txt | java Tarsn archive *.txt
  
*Note: if the file being Tarsd is a .ll|.hh|.txt the arhive will be .zl|.zh|.tar  
      if the glob is of different types, the arhives extension will be named after the first file read*
  
UnTars: 
-------------------
(reads .tar, .zh, .zl and makes or overwrites files that have the same names as the files stored in the arhive)

javac UnTars.java

java UnTars arhive.tar | java UnTars arhive.zl | java UnTars arhive.zh 


//describe the thing





