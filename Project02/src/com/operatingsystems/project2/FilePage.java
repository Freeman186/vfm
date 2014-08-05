package com.operatingsystems.project2;

public class FilePage {
	
	int isValid = 0; //0=false
	int chunks;
	int address[];
	int validBit[]; 
	
	public FilePage(int chunks){
		this.chunks  = chunks;
		validBit = new int[chunks];
		address = new int[chunks];
	}
	
	public void setIsValid(int isValid){
		this.isValid = isValid;
	}
	
	public void setChunks(int chunks){
		this.chunks = chunks;
	}
	
	public int getIsValid(){
		return isValid;
	}
	
	public int getChunks(){
		return chunks;
	}
	
	
	
}
