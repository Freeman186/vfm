package com.operatingsystems.project2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class OpenFile {
	
	private String name = "";
	private int size = 0;
	private int chunk;
	private String[] fpAddress;
	private int[] valid;
	private int[] dirty;
	
	
	
	//private List<FilePage> fpList = new ArrayList<FilePage>();
	
	public OpenFile(String name, int size){
		this.name = name;
		this.size = size;
		
		if(size%512 == 0){
			chunk = (int) (size / 512);
		}else
			chunk = (int) (size / 512)+1;
		
		fpAddress = new String[chunk];
		valid = new int [chunk];
		dirty = new int [chunk];
	}
	
	
	public void setName(String name){
		this.name = name;
	}

	
	public void setSize(int size){
		this.size = size;
	}


	public String getName(){
		return name;
	}
	
	
	public long getChunks(){
		return chunk;
	}
	
	
	public long getSize(){
		return size;
	}
	
	
	public String getFpAddress(int position){
		return fpAddress[position];
	}
	
	public int[] getValid(){
		return valid;
	}
	
	public void setFpAddress(int position, String value){
		fpAddress[position] = value;
	}
	
	public void replaceText(int chunk, int position, String value, String fileName, int address){
		
		try{
		
		String tmp = fpAddress[chunk].toString();
			
		
		//NOT WORKING CORRECTLY Writing to file directly, just to get program to run
		
		
		String beg = tmp.substring(0, position);
		String change = tmp.substring(position, position+value.length());
		
		String end = tmp.substring(position+value.length());
			
		change = change.replaceAll("[.]", value);
		String text = beg+change+end;		
		
		
		setFpAddress(chunk, text);
		setDirty(chunk, 1);

		}catch(NullPointerException e){
			//error still working on this
		}
		
		
		
		
		//used to write to file b/c cannot get previous code working using filepage and page table. 
		RandomAccessFile readFile = null;
		
		try {
			readFile = new RandomAccessFile(fileName, "rw");			
			readFile.seek(address);
			readFile.writeBytes(value);
			readFile.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	public void setValid(int position, int value){
		valid[position] = value;
	}
	
	public boolean isValid(int position){
		
		if(valid[position] == 0)
			return false;
		else	
			return true;
	}
	
	
	public void setDirty(int position, int value){
		dirty[position] = value;
	}


	public int[]  getDirty(){
		return dirty;
	}
	
	
	

}
