package cn.com.test;

import cn.com.model.beans.StepToPerformBean;

public class LayerizeTest {
	public static void main(String[] args){
		boolean[] test =new boolean[50];
		
		StepToPerformBean first = new StepToPerformBean();
		StepToPerformBean second =new StepToPerformBean();
		
		first.getInstructions().add("yes");
		first.getInstructions().add("yes");
		first.getInstructions().add("no");
		first.getInstructions().add("no");
		second.getInstructions().add("no");
		second.getInstructions().add("yes");
		second.getInstructions().add("yes");
		second.getInstructions().add("no");

		System.out.println(first.equals(second));
	
	}

}
