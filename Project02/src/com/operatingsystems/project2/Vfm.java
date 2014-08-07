package com.operatingsystems.project2;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.BufferedWriter;
import java.nio.ByteBuffer;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class Vfm {
	
	private String[] pageTable;
	private boolean initInitial = false;
	private List<OpenFile> openFileList = new ArrayList<OpenFile>();
	private RandomAccessFile ra = null;
	private ByteBuffer chunk;
	private int chunkCount = 0;
	int frameCount=0, frameSize=0;
	
	//empty constructor
	public Vfm() {
	}


	// VFMINIT: Initializes the virtual file manager framework
	// by defining the number and size of page frames
	int vfminit(int frameCount, int frameSize)
	{
		this.frameCount = frameCount;
		this.frameSize = frameSize;
		initInitial = true;
		return 0;
	}
	
	
	// VFMCREATE: Creates AND opens a new file of given size
	int vfmcreate(String fileName,int filesize){
	
		Writer output = null;
		File f = new File(fileName); //initialize file variable

		try {
			
			//create file on disk 
			f.createNewFile();
			
			//initialize BufferedWriter to file
			output = new BufferedWriter(new FileWriter(f, true));
			
			System.out.printf("File %s is currently being populated with %d bytes of data...\n", fileName, filesize);
			
			//placeHolder used to store the entire String value of the (.) placeholder to be written to file
			String placeHolder = ".";
			int count =0;
			
			
			//generating the desired placeholder length
			while(count < filesize){
				placeHolder = placeHolder + ".";
				count++;
			}
			
			//if file is empty then write placeholder. otherwise nothing will be written to file.
			if(f.length() == 0){
				output.write(placeHolder); //writing to file
				output.flush();		
            }

		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		vfmopen(fileName);
				
		return 0;
	}

	
	// VFMOPEN: Opens an existing file
	int vfmopen(String fileName)
	{
			File file = new File(fileName);
			
			if(file.exists()){
				
				long fileSize = file.length()-1;
				//create new openFile object with specified name, size and adds to openFileList
				//used as the open file table
				OpenFile openfile =  new OpenFile(fileName, (int)fileSize); 
				openFileList.add(openfile);
				
			}			
			else{
				System.err.print("ERROR, File Not Found!");		
			}
		
			return -1;
	}

	
	
	// VFMCLOSE: Closes an open file
	int vfmclose(String fileName)
	{
		for(int i=0;i<openFileList.size();i++){
				
			OpenFile tmp = openFileList.get(i);
			if(tmp.getName().equals(fileName)){
				openFileList.remove(i);
				System.out.printf("Closing %s\n", fileName);
			}
		}
		return 0;
	}
	

	// VFMREAD: Reads a string of bytes from a file using the
	// virtual file manager
	String vfmread(String fileName, int address, int length, int err)
	{
		char[] readChar = new char[length];
		int count = 0;
		RandomAccessFile readFile = null;
		int position=0;
		int chunkCount=0;
		String s = null;
		
		List<Character> charList = new ArrayList<Character>();
		
		if(isFileOpen(fileName)){
			
			try {
				
				readFile = new RandomAccessFile(fileName, "r");
				
				//used to get position in openFile table
				for(int i=0;i<openFileList.size();i++){
					OpenFile tmp = openFileList.get(i);
					
					if(tmp.getName().equals(fileName)){
						position = i;
					}
				}
				
				//read entire file into file table
				while(readFile.getFilePointer() < readFile.length()){
					charList.add((char)readFile.readByte());
					
					if(count%512==0){

						s = new String(charList.toString());
						openFileList.get(position).setFpAddress(chunkCount,  s);
						openFileList.get(position).setValid(chunkCount, 1);
						chunkCount++;
					}
					
					count++;
				}
				
				readFile.seek(address);
				chunkCount = 0;
				count=0;
				
				
				//gets characters that need to be returned ()
				while(readFile.getFilePointer() < address+length-1){

					readChar[count] = (char) readFile.readByte();

					if(count==511){

						s = new String(readChar);
						openFileList.get(position).setFpAddress(chunkCount,  s);
						//openFileList.get(position).setValid(chunkCount, 1);
						chunkCount++;
						readChar = new char[length];
					}
					
					count++;
				}
				

				if(readChar.length == length){
					
					s = new String(readChar);
					openFileList.get(position).setFpAddress(chunkCount,  s);
					openFileList.get(position).setValid(chunkCount, 1);
				}
			
						
			}catch(EOFException ex){
				System.err.print("End of file reached. Cannot read from current position\n");
				return null;
			}catch (IOException e) {
				System.err.print("File Not Found\n");
				return null;
			}finally{
				
				try {
					readFile.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}else{
			
			System.out.print("File is currently Closed!\n");
		}
		
		pageTable = new String[frameCount];
		
		//add data to pageTable
		for(int i=0;i<pageTable.length;i++){
		
			if(openFileList.get(position).isValid(i) && i<pageTable.length)
				pageTable[i] = openFileList.get(position).getFpAddress(i);	
		}

	
		return charToString(readChar);
	}
	

	// VFMWRITE: Writes to the pages of a file using the virtual
	// file manager
	String vfmwrite(String fileName, int address, String value)
	{
		
		int tmp=0;
		int modulus = 0;
		int position = 0;
		
		if(isFileOpen(fileName)){
			
			for(int i=0;i<openFileList.size();i++){
				OpenFile tmpB = openFileList.get(i);
				if(tmpB.getName().equals(fileName)){
					position = i;
				}
			}
	
			if(address > 512){
				tmp = address/512;
				modulus = address%512;	
				openFileList.get(position).replaceText(tmp, modulus, value, fileName, address);;			
			}
			else{
				modulus = address%512;	
				openFileList.get(position).replaceText(tmp, modulus, value, fileName, address);;			
			}
			

		}
		
		
		
		return null;
	}

	// VFMSTATUS: a formatted report about the file names of all
	// open files in the VFM, and the pagetable for each.
	void vfmstatus()
	{
		System.out.printf(">---------[VFM STATUS REPORT]---------( address)---<\n");
		
		if(!initInitial)
			System.out.print("\t\tVFM is not initialized\n");
		else{
			System.out.print("Filename\tBytes \t Pagetable\n");
			
			for(int i=0;i<openFileList.size();i++){
				System.out.printf("%s \t \t %d \t %s \n", openFileList.get(i).getName(), openFileList.get(i).getSize(), printTableHeader());
				printTableBody(i);
			}
		}		
	}	
	
	private String charToString(char[] bytes) {
		 String tmp = new String(bytes);
		 return tmp;
	}
	
	private String printTableHeader(){
		
		String header = String.format("+----+--------+-+-+---+------+\n"
				+ "\t \t \t |Page| V Addr |V|D|Ref| RAddr|\n"
				+ "\t \t \t +----+--------+-+-+---+------+");

		return header;
	}
	
	private void printTableBody(int index){
		
		int vAddr = 0;
		int[] isValid = openFileList.get(index).getValid();
		int[] ditryBit = openFileList.get(index).getDirty();

		
		
			
			for(int x=0;x< openFileList.get(index).getChunks();x++){
				
							
				
				if(vAddr ==0){
					System.out.printf("\t \t \t |  %d |     %d  |%d|%d| 0 |   0  |\n", x, vAddr, isValid[x], ditryBit[x]);
				}
				else if(vAddr ==512){
					System.out.printf("\t \t \t |  %d |   %d  |%d|%d| 0 |   0  |\n", x, vAddr, isValid[x], ditryBit[x]);
				}
				else{
					System.out.printf("\t \t \t |  %d |  %d  |%d|%d| 0 |   0  |\n", x, vAddr, isValid[x], ditryBit[x]);
				}
				
				vAddr+=512;
			}
		
		System.out.print("\t \t \t +----+--------+-+-+---+------+\n");
	}
	
	
	public boolean isFileOpen(String fileName){
		
		
		//used to check and see if the fileName is in the openFile table/list
		for(int i=0;i<openFileList.size();i++){
			OpenFile tmp = openFileList.get(i);
			if(tmp.getName().equals(fileName)){
				return true;
			}
		}
		
		return false;
	}

}
