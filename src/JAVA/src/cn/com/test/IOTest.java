package cn.com.test;

import java.io.IOException;

import cn.com.io.BasicFileController;
import cn.com.io.PersonizedFileIO;
import cn.com.type.asistant.DataMapping;

public class IOTest {
	public static void main(String[] args) throws IOException{
		//BasicFileController.createFile("/home/zhihan/Downloads/test");
		/*BasicFileController.writeFileByFileOutputStream("/home/zhihan/Downloads/test", "nihao");*/
		/*BasicFileController.writeFileByFileOutputStream("/home/zhihan/Downloads/test", "nihao");*/
		
		/*PersonizedFileIO io = new PersonizedFileIO("/home/zhihan/Downloads/test");
		BasicFileController.copyFile("/home/zhihan/Downloads/letter.pdf", "/home/zhihan/Downloads/letter2.pdf");*/
		String[] result = "&1&2&3&4".split("&");
		int[] a= {1,1,2,2};
		a=DataMapping.arrayUnique(a);
		/*
		io.appendFile("test\n");
		io.appendFile("2222");
		io.appendFile("1111");
		
		io.close();*/
	}
}
