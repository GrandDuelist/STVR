package io.github.dataspy.service;
import io.github.dataspy.asistant.DataMapping;

import java.io.File;
import java.io.IOException;

public class TableFileService {
	File[] secondLayerFiles;
	File topFile;   //最顶层，一共四层
	String outputFileName;
	SheetFileService currentSheet = new SheetFileService();
	File outputFile;
	
	public TableFileService(String outputFileName) throws IOException{
		this.outputFileName = outputFileName;
		this.createResultFile();
		this.readSecondLayerFile();
		this.writeAllSheet();
		
	}
	
	public void createResultFile() throws IOException{
		File topDir = new File(DataMapping.TOP_OUTPUT_DIR);
		if(!topDir.exists()){
			topDir.mkdirs();
		}
		
		String filePath = DataMapping.TOP_OUTPUT_DIR + DataMapping.PATH_SEPERATOR + this.outputFileName;
		outputFile = new File(filePath);
		if(!outputFile.exists()){
			outputFile.createNewFile();
		}
	}
	
	public void readSecondLayerFile(){
		this.topFile = new File(DataMapping.TOP_DIR);
		this.secondLayerFiles = topFile.listFiles();
	}
	
	/**
	 * 写入一个表单
	 */
	public void writeAllSheet(){
		for(int i=0;i<this.secondLayerFiles.length;i++){
			this.currentSheet.sheetFile = this.secondLayerFiles[i]; // 得到当前sheetFile
			this.currentSheet.currentSheetNum = i;
			this.currentSheet.writeSheet();
		}
	}
	

}
