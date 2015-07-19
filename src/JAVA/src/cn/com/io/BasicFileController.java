package cn.com.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BasicFileController {
	public static boolean createFile(String filePath){
		boolean result  = false;
		File file = new File(filePath);
		if(!file.exists()){
			try{
				result = file.createNewFile();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static boolean createDirectory(String directory){
		boolean result = false;
		File file = new File(directory);
		if(!file.exists()){
			result =  file.mkdirs();
		}	
		return result;
	}
	
	public static boolean deleteFile(String filePath){
		boolean result = false;
		File file = new File(filePath);
		if(file.exists() && file.isFile()){
			result = file.delete();
		}
		return result;
	}
	
	public static void deleteDirectory(String directory){
		File file = new File(directory);
		if(!file.exists()){
			return;
		}
		
		if(file.isFile()){
			file.delete();
		}else if(file.isDirectory()){
			File[] files = file.listFiles();
			for(File myfile:files){
				deleteDirectory(directory+"/"+myfile.getName());
			}
			
			file.delete();
		}
	}
	
	
	public static void writeFileByFileOutputStream(String filePath,String content) throws IOException{
		File file = new File(filePath);
		synchronized(file){
			FileOutputStream fos = new FileOutputStream(filePath);
			fos.write(content.getBytes());
			fos.close();
		}
	}
	
}
