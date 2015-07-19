package cn.com.sql.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.com.factory.DAOFactory;
import cn.com.model.beans.CaseStepInstructionBean;
import cn.com.model.beans.StepResultProductBean;
import cn.com.type.asistant.Page;

public class CaseStepInstructionDAO {
	Connection conn;

	/**
	 * 添加instruction 到一个测试案例中
	 * @param caseInstruction
	 * @return
	 */
	public CaseStepInstructionBean selectByCaseversionID(CaseStepInstructionBean caseInstruction) {
		try {
			int caseversionID = caseInstruction.testcase.getCaseversionID();
			String sql = "SELECT instruction from library_casestep WHERE library_casestep.caseversion_id = ?";
			PreparedStatement pst = this.conn.prepareStatement(sql);
			pst.setInt(1,caseversionID);
			ResultSet rst = pst.executeQuery();
		
			while(rst.next()){
				caseInstruction.stepToPerform.add(rst.getString("instruction"));
			}
			
			return caseInstruction;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return caseInstruction;
	}
	
	/**
	 * 批量添加
	 * @param caseInstructions
	 * @return
	 */
	public List<CaseStepInstructionBean> selectByList(List<CaseStepInstructionBean> caseInstructions){
		for(int i=0;i<caseInstructions.size();i++){
			CaseStepInstructionBean caseInstruction = caseInstructions.get(i);
			caseInstruction = this.selectByCaseversionID(caseInstruction);
		}
		return caseInstructions;
	}
	
	/**
	 * 封装test case和instructions
	 * @param stepResults
	 * @return
	 */
	public List<CaseStepInstructionBean> envelopStepResult(List<StepResultProductBean> stepResults){
		List<CaseStepInstructionBean> caseInstructions  = new ArrayList<CaseStepInstructionBean>();
		for(int i=0;i<stepResults.size();i++){
			CaseStepInstructionBean caseInstruction = new CaseStepInstructionBean();
			caseInstruction.testcase = stepResults.get(i);
		}
		return caseInstructions;
	}
	
	/**
	 * 通过页查询包含intructions的test case
	 * @param page
	 * @return
	 */
	public List<CaseStepInstructionBean> selectByPage(Page page){
		StepResultProductDAO stepResultDAO = DAOFactory.getStepResultProductDAO();
		List<StepResultProductBean> stepResults = stepResultDAO.selectByPage(page);
		return this.envelopStepResult(stepResults);
	}
	
}
