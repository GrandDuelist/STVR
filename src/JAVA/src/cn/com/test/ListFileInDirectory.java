package cn.com.test;

import java.io.File;
import java.util.Arrays;

public class ListFileInDirectory {
	public static void main(String[] agrs){
		File file = new File("/home/zhihan/Workspace/STVR/data/FAULT_MATRIX/Mobile Firefox");
		File[] inFiles = file.listFiles();
		String[] fileNames = new String[inFiles.length];
		for(int i=0;i<fileNames.length;i++){
			fileNames[i]=inFiles[i].getName();
		}
		
		Arrays.sort(fileNames);
		String result="";
		
		for(int i=0;i<inFiles.length;i++){
			result+="\""+fileNames[i]+"\",";
		}
		
		 System.out.println(result);
	}
}
