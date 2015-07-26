package cn.com.model.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CaseStepBugsBean {
	public StepResultProductBean testcase;
	public List<String> bugurls = new ArrayList<String>();
	public HashMap<String,Boolean> faultMatrixMap;
}
