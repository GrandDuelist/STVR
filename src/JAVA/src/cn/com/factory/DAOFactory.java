package cn.com.factory;

import cn.com.sql.dao.*;

public class DAOFactory {
	public static SqlConnection getConnection(){
		return new SqlConnection();
	}
	public static StepResultProductDAO getStepResultProductDAO(){
		return new StepResultProductDAO();
	}
	public static CaseStepInstructionDAO getCaseStepInstructionDAO(){
		return new CaseStepInstructionDAO();
	}
}
