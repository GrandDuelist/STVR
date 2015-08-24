package cn.com.model.beans;

import java.util.ArrayList;
import java.util.List;

public class PreviousClusterBean{
	String productName;
	String previousVersionName;
	List<StepToPerformBean> testCases = new ArrayList<StepToPerformBean>();
	
	
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
	public List<StepToPerformBean> getTestCases() {
		return testCases;
	}
	public void setTestCases(List<StepToPerformBean> testCases) {
		this.testCases = testCases;
	}
	
	
	
}