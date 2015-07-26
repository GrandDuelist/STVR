package cn.com.test;

import cn.com.service.MatrixService;
import cn.com.sql.dao.SqlConnection;

public class MatrixTest {
	public static void main(String[] args){
		
		SqlConnection myConnection = new SqlConnection();
		myConnection.connect();
		MatrixService service = new MatrixService();
		service.setConnection(myConnection.conn);
		service.generateFaultMatrixAllProduct();
		myConnection.close();
	}
}
