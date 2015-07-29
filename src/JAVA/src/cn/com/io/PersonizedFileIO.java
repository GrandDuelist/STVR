package cn.com.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class PersonizedFileIO {
	File file;
	String filePath;
	PrintWriter out;
	
	
	public PersonizedFileIO(String filePath) {
		this.filePath = filePath;
		File file = new File(filePath);
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void appendFile(String content) {
		try {
			
			if (this.filePath != null) {
				if (file == null) {
					file = new File(this.filePath);
				}
				if (!file.exists()) {
					file.createNewFile();
				}
				if(out!=null){
					out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
				}
				out.print(content);
				this.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void close(){
		this.out.close();
	}
	
	
}
