package io.github.dataspy.main;

import java.io.IOException;

import io.github.dataspy.service.TableFileService;

public class GenerateTableFile {
	public static void main(String[] args){
		try {
			TableFileService service = new TableFileService("result.xls");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
