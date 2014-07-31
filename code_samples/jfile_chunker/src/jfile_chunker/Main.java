/*
 * A Java implementation (hack) by Sam Birch to demonstrate how to 
 * write to and read from files in chunks (aka pages).  This
 * example is probably horrible Java...but it does work just as the
 * C example did.  
 * 
 * One huge issue with a Java version is that Java is UTF-16 by 
 * default, so if you use any standard calls to read/write characters
 * or strings, the default character is 2 bytes.  This means that a 
 * file that is supposed to be 1024 Bytes will actually be 2048 Bytes
 * if you fill it with 1024 characters.  So...this example treats all
 * characters as a Byte...which produces identical output to the C 
 * example.
*/
package jfile_chunker;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {
		String filename = null;
		int filekbytes;
		int filesize;
		char chunkchoice;
		int chunksize;
		int chunkcount;
		int modchunk;
		int i,j;
		ByteBuffer chunk;
		RandomAccessFile fp;
		byte temp;
		
		Scanner in = new Scanner(System.in);
		
		System.out.println("This program will use Java to read in specific chunks of a");
		System.out.println("file in and write specific chucks back to the file.");
		
		try {
			do {
				System.out.printf("->Type in the name of a new file to create: ");
				filename = in.nextLine();
				fp = new RandomAccessFile(filename,"r");
				fp.close();
				System.err.printf("That file existed...try again: ");
			} while (true);
		} catch (IOException e) {
			System.out.printf("Congrats...we'll create %s",filename);			
		} 
		
		// Create the new file
		try {
			fp = new RandomAccessFile(filename,"rw");
		} catch (IOException e) {
			System.err.println("Could not create file...exiting!");
			return;
		}
		
		// Let's ask how big to make the file:
		do { // How big?
			System.out.printf("How many KBytes should we initially fill the file with (1-10): ");
			filekbytes = in.nextInt();
		} while ((filekbytes < 1) || (filekbytes > 10));
		filesize = filekbytes*1024;
		
		// How large should our chunks be?
		do { // How big are the chunks?
			System.out.printf("Select a chunk size: \n ");
			System.out.printf("  a) 128 Bytes\n");
			System.out.printf("  b) 256 Bytes\n");
			System.out.printf("  c) 512 Bytes\n");
			System.out.printf("Choose (a,b,c): ");
			chunkchoice = in.next().charAt(0);
		} while ( (chunkchoice != 'a') && (chunkchoice != 'b') && (chunkchoice != 'c'));
		
		// Set Chunk Size
		switch (chunkchoice) {
		case 'a':
			chunksize = 128;
			break;
		case 'b':
			chunksize = 256;
			break;
		case 'c':
			chunksize = 512;
			break;
		default:
			System.err.printf("Switch error...shouldn't get here...closing.\n");
			fp.close();
			return;
		}
		chunkcount = filesize/chunksize;
	
		System.out.printf("> Creating %s and filling with %d \'d\' characters\n",filename,filesize);
		System.out.printf(">   - The file is accessible in %d Byte chunks\n",chunksize);
		System.out.printf(">   - It contains %d chunk(s) of data\n\n",chunkcount);
		
		// Allocate memory to our single chunk
		chunk = ByteBuffer.allocate(chunksize);
		
		// Fill our chunk with the initialization character
		for (i = 0; i < chunksize; i++){
			temp = 'd';
			chunk.put(temp);
		}
		
		// Use this initialized chunk to initialize the file
		fp.seek(0); // Not really necessary since this is still an empty file here...
		for (i = 0; i < chunkcount;i++) {
			chunk.rewind();
			for (j = 0; j < chunksize; j++) {
				fp.writeByte(chunk.get(j)); // writeUTF(chunk.subSequence(j,j).toString());
			}
		}
		
		System.out.printf("Successfully initialized file!\n");
		do { // What chunk do you want to modify?
			System.out.printf("What chunk do you want to modify (0-%d): ",chunkcount-1);
			modchunk = in.nextInt();
		} while ((modchunk < 0) || (modchunk >= chunkcount));
		System.out.printf("-> Now incrementing each character of chunk %d...\n",modchunk);
		
		// First get the chunk from the file...
		fp.seek(modchunk*chunksize); // set the starting position...
		chunk.rewind();
		for (j = 0; j < chunksize; j++) {
			chunk.put(fp.readByte());
		}
			
		// Increment each character...
		chunk.rewind();
		for (i = 0; i < chunksize; i++){
			temp = chunk.get(i);
			temp++;
			chunk.put(i,temp);
		}

		// Write that chunk back to the file...
		fp.seek(modchunk*chunksize); // set the starting position...
		chunk.rewind();
		for (j = 0; j < chunksize; j++) {
			//fp.writeChar(chunk.charAt(j));
			fp.writeByte(chunk.get(j));
		}
		fp.close();
		
		System.out.printf("Successfully edited chunk %d and wrote the data back to the file.\n",modchunk);
		System.out.printf("Open %s in a text editor to see the results...bye\n",filename);
		



		
	}
	
}
