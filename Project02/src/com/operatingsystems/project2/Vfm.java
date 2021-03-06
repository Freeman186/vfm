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
import java.util.List;



public class Vfm {
	
	private int[][] pageTable;
	private boolean initInitial = false;
	private List<OpenFileTable> openFileList = new ArrayList<OpenFileTable>();
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
	
	
	 
	/**
	 *	VFMCREATE: Creates AND opens a new file of given size 
	 * 	@param fileName
	 * 	@param filesize
	 * 	@return int 0 on success
	 */
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
		
		
		//create new openFile object with specified name, size and adds to openFileList
		//used as the open file table
		OpenFileTable openFile =  new OpenFileTable(fileName, filesize); 
		openFileList.add(openFile);
		
		
		chunkCount = (filesize / frameSize) + 1;
		
		PageTable pt = new PageTable();
		
		//System.out.printf("Page count = %s\n",String.valueOf(chunkCount));

		return 0;
	}

	
	
	/**
	 *	VFMOPEN: Opens an existing file 
	 * 	@param fileName
	 * 	@return int -1 on success 
	 */
	int vfmopen(String fileName)
	{
		int i;
		for(i=0;i<openFileList.size();i++){
			
			OpenFileTable tmp = openFileList.get(i);
			if(tmp.getName().equals(fileName)){
				return i;
			}
		}
		return -1;
	}

	
	

	/**
	 *	VFMCLOSE: Closes an open file 
	 * 	@param fileName string file name
	 * 	@return int 0 on success
	 */
	int vfmclose(String fileName)
	{
		for(int i=0;i<openFileList.size();i++){
				
			OpenFileTable tmp = openFileList.get(i);
			if(tmp.getName().equals(fileName)){
				openFileList.remove(i);
				System.out.printf("Closing %s\n", fileName);
			}
		}
		return 0;
	}

	 
	 
	/**
	 *	VFMREAD: Reads a string of bytes from a file using the
	 * 	virtual file manager
	 * 	@param fileName file name
	 * 	@param address virtual address from page table
	 * 	@param length length of the file
	 * 	@param err
	 * 	@return string length value of the file read
	 */
	String vfmread(String fileName, int address, int length, int err)
	{
		char[] readChar = new char[length];
		int count = 0;
		RandomAccessFile readFile = null;
		
		try {
			readFile = new RandomAccessFile(fileName, "r");
			readFile.seek(address);
					
			while(readFile.getFilePointer() < address+(length)){
				readChar[count] = (char) readFile.readByte();
				count++;
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
		return charToString(readChar);
	}

	
	/** 
	 *	VFMWRITE: Writes to the pages of a file using the virtual
	 * 	file manager
	 *  @param string file name 
	 *  @param int virtual address in page table 
	 *  @param string value of the data being written
	 *  @return null
	 */
	 
	String vfmwrite(String fileName, int address, String value)
	{
		System.err.printf("vfmwrite() hasn't been implemented yet..."+value+"\n");
		return null;
	}

	
	/**
	 *	VFMSTATUS: a formatted report about the file names of all
	 *	open files in the VFM, and the pagetable for each. 
	 */
	void vfmstatus()
	{
		System.out.printf(">---------[VFM STATUS REPORT]---------( address)---<\n");
		
		if(!initInitial)
			System.out.print("\t\tVFM is not initialized\n");
		else{
			System.out.print("Filename\tBytes \t Pagetable\n");
		}		
	}	
	/** 
	 * String mutator for byte data read from file	
	 * @param bytes
	 * @return string returns a string from bytes
	 */
	private String charToString(char[] bytes) {
		 String tmp = new String(bytes);
		 return tmp;
	}

}
