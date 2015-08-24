package cn.com.model.beans;

import java.util.List;

import cn.com.type.asistant.DataMapping;

public class StepResultProductBean {
	int id;
	String resultStatus;
	int caseversionID;
	String version;
	String product;
	String resultString;
	int[] resultIDs;
	
	
	
	public String getResultString() {
		return resultString;
	}
	public void setResultString(String resultString) {
		this.resultString = resultString;
		
		//set this.resultIDs;
		if(this.resultString!=null && !this.resultString.equals("")){
			String[] resultIDString = resultString.split("&");
			this.resultIDs = new int[resultIDString.length-1];
			
			for(int i=1;i<resultIDString.length;i++){
				this.resultIDs[i-1] = Integer.parseInt(resultIDString[i]);
			}
			this.resultIDs = DataMapping.arrayUnique(this.resultIDs);
		}
		
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getResultStatus() {
		return resultStatus;
	}
	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}
	public int getCaseversionID() {
		return caseversionID;
	}
	public void setCaseversionID(int caseversionID) {
		this.caseversionID = caseversionID;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public int[] getResultIDs(){
		return this.resultIDs;
	}
	public void setResultIDs(int[] resultIDs){
		this.resultIDs = resultIDs;
	}
	
	
}
