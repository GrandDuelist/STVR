package cn.com.model.beans;

import java.util.HashMap;
import java.util.List;

public class Product {
	
	String productName;
	String productVersion;
	public List<String> bugs;   //all bugs
	public List<CaseStepBugsBean> testCases; //all test cases
	
	
	public void setProductName(String productName){
		this.productName = productName;
	}
	
	public void setProductVersion(String productVersion){
		this.productVersion = productVersion;
	}
	
	public String getProductName(){
		return this.productName;
	}
	
	public String getProductVersion(){
		return this.productVersion;
	}

}
