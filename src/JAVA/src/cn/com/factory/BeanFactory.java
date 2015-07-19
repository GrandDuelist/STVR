package cn.com.factory;

import cn.com.model.beans.*;

public class BeanFactory {
	
	public static StepResultProductBean getStepResultProductBean(){
		return new StepResultProductBean();
	}
	public static CaseStepInstructionBean getCaseStepInstructionBean(){
		return new CaseStepInstructionBean();
	}
}
