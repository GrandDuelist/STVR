package io.github.dataspy.service;

import java.io.File;

public class SheetFileService {

	public File sheetFile;
	File[] columnFiles;
	public int currentSheetNum; 
	
	public void writeSheet(){
		this.writeSheetName();
		this.writeSheetContent();
	}
	
	public void createSheet(){
		
	}
	
	public void writeSheetName(){
		
	}
	
	public void writeSheetContent(){
		
	}
	
	public void  readColumnHeadName(){
		this.columnFiles = sheetFile.listFiles();
		
		for(int i=0;i<this.columnFiles.length;i++){
			File clumnFile = this.columnFiles[i];
			
		}
		
	}
	
	
	
	
}
