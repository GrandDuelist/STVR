package cn.com.sql.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cn.com.model.beans.StepResultProductBean;
import cn.com.type.asistant.Page;


public class StepResultProductDAO implements DaoBaseInterface {

	Connection conn;

	public Connection getConnection() {
		return conn;
	}

	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public List selectAll() {
		// TODO Auto-generated method stub
		try {

			String sql = "select * from step_result_table LIMIT ?,?";
			PreparedStatement pst = this.conn.prepareStatement(sql);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
	public List selectByPage(Page page) {
		// TODO Auto-generated method stub
		try {
			List<StepResultProductBean> results = new ArrayList<StepResultProductBean>();
			String sql = "select * from step_result_product_view LIMIT ?,?";
			PreparedStatement pst = this.conn.prepareStatement(sql);
			pst.setInt(2,page.numPerPage);
			pst.setInt(1, page.startNum-1);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){
				StepResultProductBean current = new StepResultProductBean();
				current.setId(rs.getInt("id"));
				current.setCaseversionID(rs.getInt("caseversion_id"));
				current.setProduct(rs.getString("name"));
				current.setVersion(rs.getString("version"));
				current.setResultStatus(rs.getString("result_status"));
				current.setResultID(rs.getInt("result_id"));
				results.add(current);
				
			}
			page.practicalCurrentPageNum = results.size();
			return results;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Object selectById() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
