package cn.com.model.beans;

public class PreviousStatisticBean {
	
	//版本信息
	public String fromVersionName;
	public int fromVersionIndex;
	public String fromVersionStatus;
	
	//统计信息
	public int failedInCluster =0;
	public int notFailedInCluster = 0;
	public int wholeInCluster = 0;
}
