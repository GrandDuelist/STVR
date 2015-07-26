package cn.com.service.main;

import cn.com.service.InstructionService;
import cn.com.sql.dao.SqlConnection;
import cn.com.type.asistant.DataMapping;

public class GenerateInstruction {
	public static void main(String[] args){
		SqlConnection myconnection = new SqlConnection();
		myconnection.connect();
		
		InstructionService service = new InstructionService();
		service.setConnection(myconnection.conn);
		service.saveAllTestCaseToFile(DataMapping.TOP_INSTRUCTION_DIR);
		
		myconnection.close();
	}
}
