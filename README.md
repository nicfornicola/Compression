# Compression
This project implements LZW and Huffman encoding with Tarsing.


//huffman lzw compare pros and cons


# Downloading:
Download the whole Compression zip

# Testing:
Run "mvn test" in a command line in the dir with the pom.xml

# Compile and run instructions:
(This is for running in src/main/java/)
(if it says .txt, it could be any file extension)

Huffman and LZW: 
-------------------
(compress any text file extension including globs that contain all the same type of file or different types of files)

javac Huffman.java  
java Huffman <filename>.txt | java Huffman *.txt  
  
javac LZW.java  
java LZW <filename>.txt | java Huffman *.txt  
  
i.e. java Huffman a.txt -> a.txt.hh | java LZW a.txt -> a.txt.ll 

DeHuffman and DeLZW: 
-------------------
(uncompres text files that are compressed useing Huffman encodings)

javac DeHuffman.java  
java DeHuffman <filename>.hh | java Huffman *.hh  
  
javac DeLZW.java  
java DeLZW <filename>.ll | java Huffman *.ll  
  
i.e. java DeHuffman a.txt.hh -> a.txt | java DeLZW a.txt.ll -> a.txt

Tarsn: 
-------------------
(puts any type of file or group of files into an archive, the arhive is made if it does not exist already)

javac Tarsn.java  
java Tarsn archive <filename>.txt | java Tarsn archive *.txt  
  
i.e. java Tarsn archive a.txt -> arhive.tar | java Tarsn arc a.ll -> arc.zl | java Tarsn arc a.hh -> arc.zh | java Tarsn arc1 *.txt arc1.tar
  
*Note: if the file being Tarsd is a .ll|.hh|.txt the arhive will be .zl|.zh|.tar  
Note:  if the glob is of different types, the arhives extension will be named after the first file read*
  
UnTars: 
-------------------
(reads .tar, .zh, .zl and makes or overwrites files that have the same names as the files stored in the arhive)

javac UnTars.java  
java UnTars arhive.tar | java UnTars arhive.zl | java UnTars arhive.zh  


//describe the thing





