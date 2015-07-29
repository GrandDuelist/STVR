package cn.com.model.beans;

import java.util.ArrayList;
import java.util.List;

public class StepToPerformBean {
	List<String> instructions = new ArrayList<String>();  //包含若干条指令
	String productName;
	String versionName;
	String fileName;
	public boolean hasBeenClustered = false;
	public String fromTestCaseFileName="";
	
	
	
	public String getFileName() {
		return fileName;
	}



	public void setFileName(String fileName) {
		this.fileName = fileName;
	}



	public boolean isHasBeenClustered() {
		return hasBeenClustered;
	}



	public void setHasBeenClustered(boolean hasBeenClustered) {
		this.hasBeenClustered = hasBeenClustered;
	}



	public String getProductName() {
		return productName;
	}



	public void setProductName(String productName) {
		this.productName = productName;
	}



	public String getVersionName() {
		return versionName;
	}



	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}



	public List<String> getInstructions() {
		return instructions;
	}



	public void setInstructions(List<String> instructions) {
		this.instructions = instructions;
	}



	public boolean equals(StepToPerformBean stepToPerform){
		if(stepToPerform.getInstructions().size()!=this.instructions.size()){
			return false;
		}
		int length = stepToPerform.getInstructions().size();
		boolean[] secondUsedMap = new boolean[length];
		
		for(int i=0;i<length;i++){
			String currentFirst = this.instructions.get(i);
			boolean pairYes = false;
			for(int j=0; j<length;j++){
				String currentSecond = stepToPerform.getInstructions().get(j);
				if(!secondUsedMap[j] && currentSecond.equals(currentFirst)){
					pairYes = true;
					secondUsedMap[j] =  true;
					break;
				}
			}
			if(!pairYes )return false;
		}
		
		return true;
	}
} 
