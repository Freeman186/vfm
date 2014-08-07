package com.operatingsystems.project2;

public class FilePage {
	
	int chunks;
	int address[];
	int validBit[]; 
	
	public FilePage(int chunks){
		this.chunks  = chunks;
		validBit = new int[chunks];
		address = new int[chunks];
	}
	
	public void setValidInvalid(int position, int value){
		validBit[position]	= value;
	}
	
	public void setChunks(int chunks){
		this.chunks = chunks;
	}
	
	public int getValidInvid(int position){
		return validBit[position];
	}
	
	public int getChunks(){
		return chunks;
	}
	
	
	
}
