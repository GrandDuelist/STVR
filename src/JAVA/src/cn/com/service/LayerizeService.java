package cn.com.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import cn.com.io.BasicFileController;
import cn.com.model.beans.LayerizedStepPerformBean;
import cn.com.model.beans.PreviousClusterBean;
import cn.com.model.beans.PreviousClusterBean;
import cn.com.model.beans.StepToPerformBean;
import cn.com.type.asistant.DataMapping;

/**
 * 对应一个产品的所有版本
 * 
 * @author zhihan
 * 
 */
public class LayerizeService {

	public String[] versionNames;
	public String productName;
	public String productDir;
	public String layerizedProductDir;
	public LayerizedStepPerformBean[] layrizedVersionTestCases;

	public LayerizeService(String productName, String productDir,
			String layerizedProductDir) {
		this.productName = productName;
		this.productDir = productDir;
		this.layerizedProductDir = layerizedProductDir;
		this.readVersionNames();
		this.LayerizeStepToPerform();
		this.outputLayerizedResult();

	}

	
	public void outputLayerizedResult() {
		for (int i = 0; i < this.layrizedVersionTestCases.length; i++) {
			LayerizedStepPerformBean current = this.layrizedVersionTestCases[i];
			String currentVersion = this.layrizedVersionTestCases[i]
					.getVersionNames();
			String sourceFilePath1 = this.productDir
					+ DataMapping.PATH_SEPERATOR + currentVersion;
			String targetFilePath1 = this.layerizedProductDir
					+ DataMapping.PATH_SEPERATOR + currentVersion;
			List<PreviousClusterBean> currentClusters = current
					.getOrderedCluster();
			for (int j = 0; j < currentClusters.size(); j++) {
				PreviousClusterBean currentCluster = currentClusters.get(j);
				if (currentCluster.getTestCases().size() > 0) {
					List<StepToPerformBean> stepToPerforms = currentCluster
							.getTestCases();
					String clusterVersionName = currentCluster
							.getPreviousVersionName();
					String targetFilePath2 = targetFilePath1
							+ DataMapping.PATH_SEPERATOR + (j + 1);// +"-"+clusterVersionName;

					File targetPreviousVersionDir = new File(targetFilePath2);
					if (!targetPreviousVersionDir.exists()) {
						targetPreviousVersionDir.mkdirs();
					}

					for (int k = 0; k < stepToPerforms.size(); k++) {
						String sourceFilePath2 = sourceFilePath1
								+ DataMapping.PATH_SEPERATOR
								+ stepToPerforms.get(k).getFileName();
						String targetFilePath3 = targetFilePath2
								+ DataMapping.PATH_SEPERATOR
								+ stepToPerforms.get(k).getFileName();/*
																	 * +"from"+
																	 * stepToPerforms
																	 * .get(k).
																	 * fromTestCaseFileName
																	 * ;
																	 */
						BasicFileController.copyFile(sourceFilePath2,
								targetFilePath3);
					}
				}
			}
		}
	}

	/**
	 * 将一个文件的instruction 读取到一个StepToPerform类中
	 * 
	 * @param filePath
	 * @param version
	 * @param product
	 * @return
	 */
	public StepToPerformBean readFromFile(String versionDir, String fileName,
			String version, String product) {
		StepToPerformBean stepToPerform = null;
		FileReader fileReader;
		try {
			String line = null;
			stepToPerform = new StepToPerformBean();
			stepToPerform.setProductName(product);
			stepToPerform.setVersionName(version);
			stepToPerform.setFileName(fileName);
			String filePath = versionDir + DataMapping.PATH_SEPERATOR
					+ fileName;
			String resultStatusString = fileName.substring(
					fileName.indexOf(DataMapping.STRING_SEPERATOR)
							+ DataMapping.STRING_SEPERATOR.length(),
					fileName.length());

			if (resultStatusString.equals("failed")) {
				stepToPerform.setIsFailed(true);
			} else {
				stepToPerform.setIsFailed(false);
			}

			fileReader = new FileReader(new File(filePath));
			BufferedReader br = new BufferedReader(fileReader);
			while ((line = br.readLine()) != null) {
				stepToPerform.getInstructions().add(line);
			}
			fileReader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stepToPerform;
	}

	/**
	 * 读取一个版本的所有文件到List<StepToPerformBean>
	 * 
	 * @param versionDir
	 *            版本库的目录地址
	 * @param version
	 * @param product
	 * @return
	 */
	public List<StepToPerformBean> readVersionFromFile(String versionDir,
			String version, String product) {
		File versionDirFiles = new File(versionDir);
		File[] files = versionDirFiles.listFiles();
		List<StepToPerformBean> stepToPerforms = new ArrayList<StepToPerformBean>();
		for (int i = 0; i < files.length; i++) {

			StepToPerformBean stepToPerform = this.readFromFile(versionDir,
					files[i].getName(), version, product);
			stepToPerforms.add(stepToPerform);
		}
		return stepToPerforms;
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

	public void LayerizeStepToPerform() {
		// 排序
		Arrays.sort(this.versionNames);
		for (int i = 0; i < this.versionNames.length; i++) {
			System.out.println(this.versionNames[i]);
		}
		this.layrizedVersionTestCases = new LayerizedStepPerformBean[this.versionNames.length - 1];
		// 用之前版本进行归类
		for (int i = 1; i < this.versionNames.length; i++) {
			this.layrizedVersionTestCases[i - 1] = this.findPreviousTestCase(i);
		}
	}

	public LayerizedStepPerformBean findPreviousTestCase(int versionNameIndex) {
		LayerizedStepPerformBean result = new LayerizedStepPerformBean();
		result.setVersionNames(this.versionNames[versionNameIndex]);
		String targetVersionDir = this.productDir + DataMapping.PATH_SEPERATOR
				+ this.versionNames[versionNameIndex];
		List<StepToPerformBean> currentVersion = this.readVersionFromFile(
				targetVersionDir, this.versionNames[versionNameIndex],
				this.productName);

		for (int i = versionNameIndex - 1; i >= 0; i--) {
			result.getOrderedCluster().add(
					this.findTestCaseInVersion(currentVersion, i, true)); // 前面的failed的测试用例
		}
		for (int i = versionNameIndex - 1; i >= 0; i--) {
			result.getOrderedCluster().add(
					this.findTestCaseInVersion(currentVersion, i, false)); // 前面的passed的测试用例
		}
		result.getOrderedCluster().add(
				this.findTestCaseInVersion(currentVersion, versionNameIndex)); // 不分状态
		return result;
	}

	public PreviousClusterBean findTestCaseInVersion(
			List<StepToPerformBean> currentVersion, int previousVersionIndex,
			boolean isFailed) {

		PreviousClusterBean result = new PreviousClusterBean();
		// TODO Auto-generated method stub
		result.setPreviousVersionName(this.versionNames[previousVersionIndex]);
		result.setProductName(this.productName);
		String previousVersionDir = this.productDir
				+ DataMapping.PATH_SEPERATOR
				+ this.versionNames[previousVersionIndex];
		List<StepToPerformBean> previousVersionTestCases = this
				.readVersionFromFile(previousVersionDir,
						this.versionNames[previousVersionIndex],
						this.productName);
		for (int i = 0; i < currentVersion.size(); i++) {
			if (this.isInVersion(previousVersionTestCases,
					currentVersion.get(i), isFailed)) {
				result.getTestCases().add(currentVersion.get(i));
			}
		}

		return result;

	}

	public PreviousClusterBean findTestCaseInVersion(
			List<StepToPerformBean> currentVersion, int previousVersionIndex) {

		PreviousClusterBean result = new PreviousClusterBean();
		// TODO Auto-generated method stub
		result.setPreviousVersionName(this.versionNames[previousVersionIndex]);
		result.setProductName(this.productName);
		String previousVersionDir = this.productDir
				+ DataMapping.PATH_SEPERATOR
				+ this.versionNames[previousVersionIndex];
		List<StepToPerformBean> previousVersionTestCases = this
				.readVersionFromFile(previousVersionDir,
						this.versionNames[previousVersionIndex],
						this.productName);
		for (int i = 0; i < currentVersion.size(); i++) {
			if (this.isInVersion(previousVersionTestCases,
					currentVersion.get(i))) {
				result.getTestCases().add(currentVersion.get(i));
			}
		}

		return result;

	}

	/**
	 * 判断一个用例是否继承前一个版本
	 * 
	 * @param previousVersion
	 * @param targetBean
	 * @return
	 */
	public boolean isInVersion(List<StepToPerformBean> previousVersion,
			StepToPerformBean targetBean, boolean isFailed) {
		for (int i = 0; i < previousVersion.size(); i++) {
			StepToPerformBean currentPreviousTestCase = previousVersion.get(i);
			if (!targetBean.hasBeenClustered
					&& targetBean.equals(currentPreviousTestCase)
					&& currentPreviousTestCase.isFailed() == isFailed) {
				targetBean.fromTestCaseFileName = currentPreviousTestCase
						.getFileName();
				targetBean.hasBeenClustered = true;
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断一个用例是否继承前一个版本
	 * 
	 * @param previousVersion
	 * @param targetBean
	 * @return
	 */
	public boolean isInVersion(List<StepToPerformBean> previousVersion,
			StepToPerformBean targetBean) {
		for (int i = 0; i < previousVersion.size(); i++) {
			StepToPerformBean currentPreviousTestCase = previousVersion.get(i);
			if (!targetBean.hasBeenClustered
					&& targetBean.equals(currentPreviousTestCase)) {
				targetBean.fromTestCaseFileName = currentPreviousTestCase
						.getFileName();
				targetBean.hasBeenClustered = true;
				return true;
			}
		}
		return false;
	}

}
