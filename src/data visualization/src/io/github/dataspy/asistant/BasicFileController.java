package io.github.dataspy.asistant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BasicFileController {
	
	public static void copyFile(String oldPath, String newPath){
		
	/*	String cmd = "cp "+sourceFilePath+" "+targetFilePath;
		Runtime run = Runtime.getRuntime();
		try {
			Process p = run.exec(cmd);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		   try     {    
               int     bytesum     =     0;    
               int     byteread     =     0;    
               File     oldfile     =     new     File(oldPath);    
               if     (oldfile.exists())     {      
                       InputStream     inStream     =     new     FileInputStream(oldPath);     
                       FileOutputStream     fs     =     new     FileOutputStream(newPath);    
                       byte[]     buffer     =     new     byte[1444];    
                       int     length;    
                       while     (     (byteread     =     inStream.read(buffer))     !=     -1)     {    
                               bytesum     +=     byteread;        
                               System.out.println(bytesum);    
                               fs.write(buffer,     0,     byteread);    
                       }    
                       inStream.close();  
                       fs.close();
               }    
       }    
       catch     (Exception     e)     {    
               System.out.println( "error  ");    
               e.printStackTrace();    
       }    
	}
	
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
	
	public static String readFromFile(File file) throws IOException{
         if(!file.exists()||file.isDirectory())
             throw new FileNotFoundException();
         FileInputStream fis=new FileInputStream(file);
         byte[] buf = new byte[1024];
         StringBuffer sb=new StringBuffer();
         while((fis.read(buf))!=-1){
             sb.append(new String(buf));    
             buf=new byte[1024];//重新生成，避免和上次读取的数据重复
         }
         return sb.toString();
	}
	
}
