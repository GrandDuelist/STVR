package io.github.dataspy.service;
import io.github.dataspy.asistant.DataMapping;
import io.github.dataspy.io.BasicHSSFIO;

import java.io.File;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class TableFileService {
	File[] secondLayerFiles;
	File topFile;   //最顶层，一共四层
	
	String outputFileName;
	public static String outputFilePath;
	
	SheetFileService currentSheet = new SheetFileService();
	File outputFile;
	
	public static HSSFWorkbook wb;
	
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
		
		this.outputFilePath = DataMapping.TOP_OUTPUT_DIR + DataMapping.PATH_SEPERATOR + this.outputFileName;
		outputFile = new File(this.outputFilePath);
		if(!outputFile.exists()){
			outputFile.createNewFile();
		}
		this.wb = BasicHSSFIO.createWorkBook(this.outputFilePath);
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
			try {
				this.currentSheet.writeSheet();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

}
