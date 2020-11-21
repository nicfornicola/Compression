# Compression
This project implements LZW and Huffman encoding with Tarsing. Huffman and LZW compress files using different algorithms with different pros and cons shown below. Tarsing put files into an organized arhive.

# Pros and Cons:
Huffman:
-------
Pros: Lossless compression like LZW, can compress symbols and abstract things better then LZW, can be used in  JPG or MP3 unlike LZW  
Cons: Uncompressed small files might be smaller then their compressed version, two passes through the file, slow 

LZW:
-------
Pros: Lossless compression like Huffman, Fast unlike Huffman (doest have to read through the whole file) simpler to implement then Huffman
Cons: If the file doesnt have any repeating pars not much can be compressed, not good at compressing files that are not text files, we can't predict how big the strings will be, it can get really big really fast


# Downloading:
Download the whole Compression zip

# Testing:
Run "mvn test" in a command line in the dir with the pom.xml

# Compile and run instructions:
This is for running in src/main/java/  
If it says .txt, it could be any file extension

Note: in example commands, "->" means a returns b, do not include "->" in command line

Huffman and LZW: 
-------------------
(compress any text file extension including globs that contain all the same type of file or different types of files)

javac Huffman.java  
java Huffman \<filename\>.txt | java Huffman *.txt  
  
javac LZW.java  
java LZW \<filename\>.txt | java Huffman *.txt  
  
i.e. java Huffman a.txt -> a.txt.hh | java LZW a.txt -> a.txt.ll 

DeHuffman and DeLZW: 
-------------------
(uncompres text files that are compressed useing Huffman encodings)

javac DeHuffman.java  
java DeHuffman \<filename\>.hh | java DeHuffman *.hh  
  
javac DeLZW.java  
java DeLZW \<filename\>.ll | java DeLZW *.ll  
  
i.e. java DeHuffman a.txt.hh -> a.txt | java DeLZW a.txt.ll -> a.txt

Tarsn: 
-------------------
(puts any type of file or glob into an archive, the arhive is made if it does not exist already)

javac Tarsn.java  
java Tarsn archive \<filename\>.txt | java Tarsn archive *.txt  
  
i.e. java Tarsn archive a.txt -> arhive.tar | java Tarsn arc a.ll -> arc.zl | java Tarsn arc a.hh -> arc.zh | java Tarsn arc1 *.txt arc1.tar
  
*Note: if the file being Tarsd is a .ll|.hh|.txt the arhive will be .zl|.zh|.tar  
Note:  if the glob is of different types, the arhives extension will be named after the first file read*
  
UnTars: 
-------------------
(reads .tar, .zh, .zl and makes a new file or overwrites files from the files stored in the arhive)

javac UnTars.java  
java UnTars archive.tar | java UnTars archive.zl | java UnTars archive.zh

i.e. java UnTars archive.tar -> a.txt, b.txt | java UnTars archive.zl-> a.txt.ll, b.txt.ll | java UnTars archive.zh -> a.txt.hh, b.txt.hh








