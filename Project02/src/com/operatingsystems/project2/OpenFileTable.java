package com.operatingsystems.project2;

public class OpenFileTable {
	
	private String name = "";
	private int size = 0;
	private int pageRow = 0;
	private int pageCol = 0;
	
	public OpenFileTable(String name, int size){
		this.name = name;
		this.size = size;
	}
	
	
	public void setName(String name){
		this.name = name;
	}

	
	public void setSize(int size){
		this.size = size;
	}

	
	public void setPageRow(int pageRow){
		this.pageRow = pageRow;
	}
	
	public void setPageCol(int pageCol){
		this.pageRow = pageCol;
	}
	
	
	public String getName(){
		return name;
	}

	
	public int getSize(){
		return size;
	}

	public int getPageRow(){
		return pageRow;
	}
	
	public int getPageCol(){
		return pageCol;
	}
}
