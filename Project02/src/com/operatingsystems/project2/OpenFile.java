package com.operatingsystems.project2;

import java.util.ArrayList;
import java.util.List;

public class OpenFile {
	
	private String name = "";
	private int size = 0;
	private int chunk;
	private List<FilePage> fpList = new ArrayList<FilePage>();
	
	public OpenFile(String name, int size){
		this.name = name;
		this.size = size;
		
		if(size%512 == 0){
			chunk = (int) (size / 512);
		}else
			chunk = (int) (size / 512)+1;
		
		
		createTable();
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
	
	public List<FilePage> getData(){
		
		//create file Page table
		FilePage fp = new FilePage(chunk);
		fpList.add(fp);
		
		
		
		
		
		
		
	
		
		return fpList;
	}

	
	
	public long getChunks(){
		return chunk;
	}
	
	
	public long getSize(){
		return size;
	}

	public int getPageRow(){
		return pageRow;
	}
	
	public int getPageCol(){
		return pageCol;
	}
	
	
	public void createTable(){
		
		
		
		
		
		
	}
}
