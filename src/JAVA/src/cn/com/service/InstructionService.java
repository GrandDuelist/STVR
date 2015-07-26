package cn.com.service;

import java.sql.Connection;
import java.util.List;

import sun.text.normalizer.Trie.DataManipulate;
import cn.com.factory.DAOFactory;
import cn.com.io.BasicFileController;
import cn.com.io.PersonizedFileIO;
import cn.com.model.beans.CaseStepInstructionBean;
import cn.com.sql.dao.CaseStepInstructionDAO;
import cn.com.type.asistant.DataMapping;
import cn.com.type.asistant.Page;

public class InstructionService {

	Connection conn;
	
	public void setConnection(Connection conn){
		this.conn = conn;
	}
	/**
	 * 将单个instruction 存到文件中
	 * @param topDirectory
	 * @param caseInstruction
	 */
	public void saveSingleTestCaseToFile(String topDirectory,
			CaseStepInstructionBean caseInstruction) {
		String productName = caseInstruction.testcase.getProduct();
		String version = caseInstruction.testcase.getVersion();

		String dir = topDirectory + DataMapping.PATH_SEPERATOR + productName
				+ DataMapping.PATH_SEPERATOR + version;
		String filePath = dir + DataMapping.PATH_SEPERATOR
				+DataMapping.FILE_PRELIX+ caseInstruction.testcase.getCaseversionID();
		BasicFileController.createDirectory(dir);

		PersonizedFileIO io = new PersonizedFileIO(filePath);
		List<String> contents = caseInstruction.stepToPerform;
		for (int i = 0; i < contents.size(); i++) {
			io.appendFile(contents.get(i)+"\n");
		}
	}

	/**
	 * 将list instruction存到文件中
	 * @param topDirectory
	 * @param caseInstructions
	 */
	public void saveListTestCaseToFile(String topDirectory,
			List<CaseStepInstructionBean> caseInstructions) {
		for (int i = 0; i < caseInstructions.size(); i++) {
			this.saveSingleTestCaseToFile(topDirectory, caseInstructions.get(i));
		}
	}
	
	
	public  void saveTestToFileByPage(String topDirectory,Page page){
		CaseStepInstructionDAO insDAO = DAOFactory.getCaseStepInstructionDAO();
		insDAO.setConnection(conn);
		List<CaseStepInstructionBean> caseInstructions = insDAO.selectByPage(page);
		this.saveListTestCaseToFile(topDirectory, caseInstructions);
	}
	
	public void saveAllTestCaseToFile(String topDirectory){
		Page page = new Page(1,DataMapping.NUM_OF_PER_PAGE);
		page.practicalCurrentPageNum = DataMapping.NUM_OF_PER_PAGE;
		while (page.practicalCurrentPageNum == page.numPerPage ){
			this.saveTestToFileByPage(topDirectory,page);
			page.currentPlusOne();
		}
	}
	
	
	
	
	
}
