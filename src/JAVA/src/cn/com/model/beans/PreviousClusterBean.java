package cn.com.model.beans;

import java.util.ArrayList;
import java.util.List;

public class PreviousClusterBean{
	String productName;
	String previousVersionName;
	List<String> testCaseNames = new ArrayList<String>();
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getPreviousVersionName() {
		return previousVersionName;
	}
	public void setPreviousVersionName(String previousVersionName) {
		this.previousVersionName = previousVersionName;
	}
	public List<String> getTestCaseNames() {
		return testCaseNames;
	}
	public void setTestCaseNames(List<String> testCaseNames) {
		this.testCaseNames = testCaseNames;
	}
	
	
}