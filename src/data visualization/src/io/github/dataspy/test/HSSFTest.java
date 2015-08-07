package io.github.dataspy.test;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import io.github.dataspy.asistant.DataMapping;
import io.github.dataspy.io.BasicHSSFIO;

public class HSSFTest {
	public static void main(String[] args){
		String fileName = "result.xls";
		String filePath = DataMapping.TOP_OUTPUT_DIR+DataMapping.PATH_SEPERATOR+fileName;
		HSSFWorkbook wb = BasicHSSFIO.createWorkBook(filePath);
		BasicHSSFIO.createSheet(wb,filePath, "Mobile");
		BasicHSSFIO.createSheet(wb,filePath, "Mobile2");
	}
}
