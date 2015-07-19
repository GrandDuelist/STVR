package cn.com.test;

import java.io.IOException;

import cn.com.io.BasicFileController;
import cn.com.io.PersonizedFileIO;

public class IOTest {
	public static void main(String[] args) throws IOException{
		//BasicFileController.createFile("/home/zhihan/Downloads/test");
		/*BasicFileController.writeFileByFileOutputStream("/home/zhihan/Downloads/test", "nihao");*/
		/*BasicFileController.writeFileByFileOutputStream("/home/zhihan/Downloads/test", "nihao");*/
		
		PersonizedFileIO io = new PersonizedFileIO("/home/zhihan/Downloads/test");
		
		io.appendFile("test\n");
		io.appendFile("2222");
		io.appendFile("1111");
		
		io.close();
	}
}
