package cn.com.test;

import java.util.List;

import cn.com.factory.DAOFactory;
import cn.com.model.beans.StepResultProductBean;
import cn.com.sql.dao.SqlConnection;
import cn.com.sql.dao.StepResultProductDAO;
import cn.com.type.asistant.Page;

public class SqlTest {
	public static void main(String[] args){
		Page page = new Page(2,10);
		SqlConnection myConnection = DAOFactory.getConnection();
		myConnection.connect();
		StepResultProductDAO stepResultDAO = DAOFactory.getStepResultProductDAO();
		stepResultDAO.setConnection(myConnection.conn);
		List<StepResultProductBean> results = stepResultDAO.selectByPage(page);
		
		
		System.out.println(results.size());
		myConnection.close();
	}
}
