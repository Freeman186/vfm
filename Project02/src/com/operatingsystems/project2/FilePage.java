package com.operatingsystems.project2;

public class FilePage {
	
	boolean isValid = false;
	int chunks;
	int address[];
	int validBit[]; 
	
	public FilePage(int chunkSize){
		validBit = new int[chunks];
		address = new int[chunks];
	}
	
	public void setIsValid(boolean isValid){
		this.isValid = isValid;
	}
	
	public void setChunks(int chunks){
		this.chunks = chunks;
	}
	
	
	public boolean getIsValid(){
		return isValid;
	}
	
	
	public int getChunks(){
		return chunks;
	}
	
	
}
