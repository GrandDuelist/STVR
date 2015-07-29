package cn.com.service.main;

import cn.com.service.LayerizeService;
import cn.com.type.asistant.DataMapping;

public class GenerateLayerizedStepToPerform {
	public static void main(String[] args){
		String productName = "Mobile Firefox";
		String sourceDir = DataMapping.TOP_INSTRUCTION_DIR+DataMapping.PATH_SEPERATOR+productName;
		String targetDir = DataMapping.TOP_LAYERIZED_DIR+DataMapping.PATH_SEPERATOR+productName;
		LayerizeService service = new LayerizeService(productName,sourceDir,targetDir);	
		}
}
