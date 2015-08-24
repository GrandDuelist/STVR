package cn.com.model.beans;

import java.util.ArrayList;
import java.util.List;

public class VersionStatisticBean {
	
	public ProductStatisticsBean product;
	public String versionName;
	public List<PreviousStatisticBean> previousVersionClusters = new ArrayList<PreviousStatisticBean>();
	
	public int failedTestCaseNumber = 0;
	public int wholeTestCaseNumber = 0;
	public int passedTestCaseNumber =0;
	
}
