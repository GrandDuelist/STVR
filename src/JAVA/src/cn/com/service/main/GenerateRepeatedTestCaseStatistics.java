package cn.com.service.main;

import java.io.File;

import cn.com.service.LayerizeService;
import cn.com.service.RepeatedTestCaseStatisticsService;
import cn.com.type.asistant.DataMapping;

public class GenerateRepeatedTestCaseStatistics {
	public static void main(String[] args){
		generateLayerizedServiceForAllProducts();
	}
	

	public static void generateLayerizedServiceForAllProducts(){
		File topDir = new File(DataMapping.TOP_INSTRUCTION_DIR);
		File[] productDirs = topDir.listFiles();
		
		for(int i=0;i<productDirs.length;i++){
			File productDir = productDirs[i];
			String productName = productDir.getName();
			String sourceDir = DataMapping.TOP_INSTRUCTION_DIR+DataMapping.PATH_SEPERATOR+productName;
			String clusteredProductDir=DataMapping.TOP_LAYERIZED_DIR+DataMapping.PATH_SEPERATOR+productName;
			RepeatedTestCaseStatisticsService service = new RepeatedTestCaseStatisticsService(productName,clusteredProductDir,sourceDir);
		}
	}
}
