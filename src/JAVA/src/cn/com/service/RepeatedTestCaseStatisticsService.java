package cn.com.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.com.io.PersonizedFileIO;
import cn.com.model.beans.PreviousClusterBean;
import cn.com.model.beans.PreviousStatisticBean;
import cn.com.model.beans.ProductStatisticsBean;
import cn.com.model.beans.VersionStatisticBean;
import cn.com.type.asistant.DataMapping;

public class RepeatedTestCaseStatisticsService {

	public String[] versionNames;
	public String productDir;
	public String clusteredProductDir;
	public ProductStatisticsBean productStatistics;
	public String productName;
	
	public RepeatedTestCaseStatisticsService(String productName,String clusterProductDir,String productDir) {
		this.productName = productName;
		this.clusteredProductDir = clusterProductDir;
		this.productDir = productDir;
		
		this.readVersionNames();
		this.sortVersionName();
		this.setProductStatistics();
		this.outputProductStatisticsToFile();
		
	}

	
	public void outputProductStatisticsToFile(){
		this.clusteredProductDir = DataMapping.TOP_TERRACE_DIR+DataMapping.PATH_SEPERATOR+this.productStatistics.productName;
		File file = new File(this.clusteredProductDir);
		if(!file.exists()){
			file.mkdirs();
		}
		
		List<VersionStatisticBean> versionStatisticses = this.productStatistics.versions;
		for(int i=0;i<versionStatisticses.size();i++){
			VersionStatisticBean current = versionStatisticses.get(i);
			this.outputVersionStatisticsToFile(current, clusteredProductDir);
		}
	}
	
	/**
	 * 版本输出
	 * @param versionStatistics
	 * @param productDir
	 */
	public void outputVersionStatisticsToFile(VersionStatisticBean versionStatistics,String productDir){
		String versionDir = productDir+DataMapping.PATH_SEPERATOR+versionStatistics.versionName;
		File versionFile = new File(versionDir);
		if(!versionFile.exists()){
			versionFile.mkdirs();
		}
		//File statisticsFile = new File(versionDir+DataMapping.STRING_SEPERATOR+DataMapping.STATISTICS);
		
		PersonizedFileIO io = new PersonizedFileIO(versionDir+DataMapping.PATH_SEPERATOR+DataMapping.STATISTICS);
		io.appendFileLn("failedTestCaseNumber: "+versionStatistics.failedTestCaseNumber);
		io.appendFileLn("passedTestCaseNumber: "+versionStatistics.passedTestCaseNumber);
		io.appendFileLn("wholeTestCaseNumber: "+versionStatistics.wholeTestCaseNumber);
		io.close();
		List<PreviousStatisticBean> previousClusters = versionStatistics.previousVersionClusters;
		for(int i=0;i<previousClusters.size();i++){
			PreviousStatisticBean previousCluster = previousClusters.get(i);
			this.outputPreviousStatisticsToFile(previousCluster, versionDir);
		}
		
		
	}
	
	/**
	 * 每个聚类输出
	 * @param previousStatistics
	 * @param versionDir
	 */
	public void outputPreviousStatisticsToFile(PreviousStatisticBean previousStatistics,String versionDir)
	{
		String previousDir = versionDir+DataMapping.PATH_SEPERATOR+previousStatistics.fromVersionName;
		File previousDirFile = new File(previousDir);
		if(!previousDirFile.exists()){
			previousDirFile.mkdirs();
		}
		
		PersonizedFileIO io = new PersonizedFileIO(previousDir+DataMapping.PATH_SEPERATOR+DataMapping.STATISTICS);
		if(previousStatistics.fromVersionStatus.equals("passed")){
			io.appendFileLn("failedFromFailedTestCases: "+previousStatistics.failedInCluster);
			io.appendFileLn("failedTestCases: "+previousStatistics.wholeInCluster);
			io.appendFileLn("passedFromFailedTestCases: "+previousStatistics.notFailedInCluster);
		}else if(previousStatistics.fromVersionStatus.equals("failed")){
			io.appendFileLn("failedFromPassedTestCases: "+previousStatistics.failedInCluster);
			io.appendFileLn("PassedTestCases: "+previousStatistics.wholeInCluster);
			io.appendFileLn("passedFromPassedTestCases: "+previousStatistics.notFailedInCluster);
		}
		
		io.close();
		
	}
	/**
	 * 将版本名称存入到versionNames
	 */
	public void readVersionNames() {
		String dirPath = this.productDir;
		File dir = new File(dirPath);
		if (!dir.exists()) {
			new FileNotFoundException();
		}

		if (dir.isDirectory()) {
			File[] versions = dir.listFiles();
			int length = versions.length;
			versionNames = new String[length];
			for (int i = 0; i < length; i++) {
				versionNames[i] = versions[i].getName();
			}
		}
	}

	public void sortVersionName() {
		// 排序
		Arrays.sort(this.versionNames);
		for (int i = 0; i < this.versionNames.length; i++) {
			System.out.println(this.versionNames[i]);
		}
	}

	
	public void setProductStatistics(){
		this.productStatistics = new ProductStatisticsBean();
		this.productStatistics.productName = this.productName;
		this.productStatistics.versions = new ArrayList<VersionStatisticBean>();
		
		System.out.println(this.clusteredProductDir);
		File productFile = new File(this.clusteredProductDir);
		File[] versionFiles = productFile.listFiles();
		if(versionFiles!=null){
		for(int i=0;i<versionFiles.length;i++){
			File file = versionFiles[i];
			//得到每个版本的统计信息
			this.productStatistics.versions.add(this.setVersionStatistics(file));
		}
		}
		
	}
	
	
	//返回一个版本的统计信息
	public VersionStatisticBean setVersionStatistics(File versionFile){
		VersionStatisticBean versionStatistic =  new VersionStatisticBean();
		versionStatistic.product = this.productStatistics;
		versionStatistic.versionName = versionFile.getName();
	
		File[] previousClusterFiles = versionFile.listFiles();
		
		for(int i=0;i<previousClusterFiles.length;i++){
			File currentClusterFile = previousClusterFiles[i];
			PreviousStatisticBean current = this.setClusterStatistics(currentClusterFile,versionFile);
			versionStatistic.previousVersionClusters.add(current);
			versionStatistic.failedTestCaseNumber+=current.failedInCluster;
			versionStatistic.passedTestCaseNumber+= current.notFailedInCluster;
			versionStatistic.wholeTestCaseNumber+=current.wholeInCluster;
		}
		
		
		return versionStatistic;
	}
	
	
	
	/**
	 * 返回一个版本中一个聚类的统计信息
	 * @param clusterFile
	 * @param versionFile
	 * @return
	 */
	public PreviousStatisticBean setClusterStatistics(File clusterFile,File versionFile){	
		PreviousStatisticBean previousStatistics  = new PreviousStatisticBean();
		//设置previous cluster的index和名称
		previousStatistics.fromVersionIndex = Integer.parseInt(clusterFile.getName());
		ClusterStatus clusterStatus = this.getClusterVersionNameByClusterIndex(previousStatistics.fromVersionIndex,versionFile.getName());
		previousStatistics.fromVersionName = clusterStatus.versionName;
		previousStatistics.fromVersionStatus = clusterStatus.status;
		
		//得到分类中所有的测试用例
		File[] testCaseFiles = clusterFile.listFiles();
		//分别处理每个测试用例
		for(int i=0;i<testCaseFiles.length;i++){
			File currentTestCase = testCaseFiles[i];
			
			if(currentTestCase.getName().contains("failed")){
				previousStatistics.failedInCluster++;
			}else if(currentTestCase.getName().contains("passed")){
				previousStatistics.notFailedInCluster++;
			}
			previousStatistics.wholeInCluster++;
			}
		
		return previousStatistics;
	}
	
	
	class ClusterStatus{
		String versionName;
		String status;
	}
	/**
	 * 通过一个cluster的索引来返回它的版本名称 
	 * @param clusterIndex 聚类的版本索引，及分类的文件名称
	 * @param versionName  当前的版本名称
	 * @return cluster的名称的状态
	 */
	public ClusterStatus getClusterVersionNameByClusterIndex(int clusterIndex,String versionName){
		ClusterStatus status = new ClusterStatus();
		int versionIndex = this.getVersionIndexInVersionNames(versionName);
		int dValue = versionIndex - clusterIndex;
		if(dValue < 0 ){
			status.status = "passed";
			int ddValue = versionIndex+dValue;
			if(ddValue < 0){
				status.versionName = versionName;
			}else{
				status.versionName = this.versionNames[versionIndex+dValue];
			}
		}else{
			status.status = "failed";
			status.versionName = this.versionNames[dValue];}
		return status;
	}
	
	//返回一个版本名称在所有名称中的排序位置
	public int getVersionIndexInVersionNames(String versionName){
		for(int i=0;i<this.versionNames.length;i++){
			if(versionName.equals(versionNames[i])){
				return i;
			}}
		
		return -1;
	}


}
