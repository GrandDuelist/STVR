package io.github.dataspy.io;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.model.Sheet;
import org.apache.poi.hssf.model.Workbook;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class BasicHSSFIO {
	
	/**
	 * 创建excel 文件
	 * @param fileName
	 * @return
	 */
	public static HSSFWorkbook createWorkBook(String fileName) {
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			FileOutputStream fileOut = new FileOutputStream(fileName);
			wb.write(fileOut);
			fileOut.close();
			return wb;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 创建表单
	 * @param fileName
	 * @param sheetName
	 * @return
	 */
	public static HSSFSheet createSheet(HSSFWorkbook wb,String fileName, String sheetName) {
		try {
			HSSFSheet sheet = wb.createSheet(sheetName);
			FileOutputStream fileOut = new FileOutputStream(fileName);
			wb.write(fileOut);
			return sheet;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
}
