package cn.com.sql.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.com.factory.DAOFactory;
import cn.com.model.beans.CaseStepBugsBean;
import cn.com.model.beans.StepResultProductBean;
import cn.com.type.asistant.Page;

public class CaseStepBugsDAO {
	Connection conn;

	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	public CaseStepBugsBean selectByCaseVersionID(CaseStepBugsBean caseBug) {

		try {

			int resultID = caseBug.testcase.getResultID();
			if (resultID != 0) {
				String sql = "SELECT bug_url FROM execution_stepresult WHERE result_id = ?";
				PreparedStatement pst = this.conn.prepareStatement(sql);
				pst.setInt(1, resultID);
				ResultSet rst = pst.executeQuery();

				while (rst.next()) {
					String bugUrl = rst.getString("bug_url");
					if (bugUrl != null && !bugUrl.equals("")) {
						caseBug.bugurls.add(bugUrl);
					}
				}

				return caseBug;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return caseBug;
	}

	public List<CaseStepBugsBean> selectByList(List<CaseStepBugsBean> caseBugs) {
		for (int i = 0; i < caseBugs.size(); i++) {
			this.selectByCaseVersionID(caseBugs.get(i));
		}

		return caseBugs;
	}

	public List<CaseStepBugsBean> envelopStepResult(
			List<StepResultProductBean> stepResults) {
		List<CaseStepBugsBean> results = new ArrayList<CaseStepBugsBean>();
		for (int i = 0; i < stepResults.size(); i++) {
			CaseStepBugsBean current = new CaseStepBugsBean();
			current.testcase = stepResults.get(i);
			results.add(current);
		}
		return results;

	}

	public List<CaseStepBugsBean> selectByPage(Page page) {
		StepResultProductDAO stepDAO = DAOFactory.getStepResultProductDAO();
		stepDAO.conn = this.conn;
		List<StepResultProductBean> stepResults = stepDAO.selectByPage(page);
		List<CaseStepBugsBean> results = this.envelopStepResult(stepResults);
		this.selectByList(results);
		return results;
	}

}
