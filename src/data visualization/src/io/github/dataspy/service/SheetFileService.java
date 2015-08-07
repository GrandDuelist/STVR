package io.github.dataspy.service;

import io.github.dataspy.asistant.BasicFileController;
import io.github.dataspy.asistant.DataMapping;
import io.github.dataspy.io.BasicHSSFIO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.sun.rowset.internal.Row;

public class SheetFileService {

	public File sheetFile; // product name
	File[] columnFiles; // cluster string string random
	public int currentSheetNum;
	public HSSFSheet sheet;

	String[] headNames = null; // cluster
	String[] rowNames = null; // versions
	HashMap<String, Boolean> headNameExist = null;
	
	HashMap<String,HSSFRow> rows = null;

	public void refresh() {
		this.sheetFile = null;
		this.currentSheetNum = 0;
		this.sheet = null;
		this.headNames = null;
		this.headNameExist = null;
		this.rows = null;
	}

	public void writeSheet() throws IOException {
		this.writeSheetName();
		if (this.sheetFile.listFiles() != null
				&& this.sheetFile.listFiles().length != 0) {
			this.writeRowNames();
			this.writeSheetContent();
		}
	}

	public void writeRowNames() {

	}

	public void writeSheetName() {
		this.sheet = BasicHSSFIO.createSheet(TableFileService.wb,
				TableFileService.outputFilePath, sheetFile.getName());
	}

	public void writeSheetContent() throws IOException {
		this.headNameExist = new HashMap<String, Boolean>();
		this.rows = new HashMap<String,HSSFRow>();
		
		this.columnFiles = sheetFile.listFiles();
		HSSFRow row = this.sheet.createRow(0);
		// 写入列头名称
		row = this.sheet.createRow(0);
		row.createCell((short)0).setCellValue(DataMapping.SHEET_TITLE);
		for (int i = 0; i < this.columnFiles.length; i++) {
			// 得到目录
			File currentColumnDir = this.columnFiles[i];
			if (!this.headNameExist.containsKey(currentColumnDir.getName())
					|| !this.headNameExist.get(currentColumnDir.getName())) {
				this.headNameExist.put(currentColumnDir.getName(), true);

			}
			row.createCell((short) (i+1)).setCellValue(currentColumnDir.getName());
			this.filloutOneDir(currentColumnDir,i+1);
			// 写入文件
			FileOutputStream fileOut = new FileOutputStream(TableFileService.outputFilePath);
			TableFileService.wb.write(fileOut);
			fileOut.close();

		}
	}

	public void filloutOneDir(File currentDir, int index){
		File[] files = currentDir.listFiles();
		
		for(int i=0;i<files.length;i++){
			File currentFile = files[i];
			String fileName = currentFile.getName();
			HSSFRow row = null;
			if(!this.rows.containsKey(fileName)){
				row = this.sheet.createRow((i+1));
				row.createCell((short)0).setCellValue(fileName.substring(0,fileName.indexOf(".txt")));
				this.rows.put(fileName, row);
			}else{
				row = this.rows.get(fileName);
			}
			
			
			try {
				String content = BasicFileController.readFromFile(currentFile);
				String extractInfo = DataMapping.extractFromContent(content);
				row.createCell((short)index).setCellValue(extractInfo);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void readHeadName() {
		this.columnFiles = sheetFile.listFiles();

		for (int i = 0; i < this.columnFiles.length; i++) {
			File columnFile = this.columnFiles[i];

		}

	}

}
