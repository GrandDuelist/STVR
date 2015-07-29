package cn.com.service.main;

import cn.com.service.MatrixService;
import cn.com.sql.dao.SqlConnection;

/**
 * 生成fault matrix 的函数
 * @author zhihan
 *
 */
public class GenerateMatrix {
	
public static void main(String[] args){
		
		SqlConnection myConnection = new SqlConnection();
		myConnection.connect();
		MatrixService service = new MatrixService();
		service.setConnection(myConnection.conn);
		service.generateFaultMatrixAllProduct();
		myConnection.close();
	}
}
