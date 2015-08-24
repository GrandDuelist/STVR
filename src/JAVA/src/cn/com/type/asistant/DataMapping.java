package cn.com.type.asistant;

import java.util.LinkedList;
import java.util.List;

public class DataMapping {
	public static String TOP_INSTRUCTION_DIR = "/home/zhihan/Workspace/STVR/data/LDA_INPUT_DATA"; // 存放所有文件的最顶层目录
	public static String TOP_LAYERIZED_DIR = "/home/zhihan/Workspace/STVR/data/LDA_LAYERIZED_DATA";
	public static String TOP_FAULT_MATRIX_DIR = "/home/zhihan/Workspace/STVR/data/FAULT_MATRIX";
	public static String TOP_TERRACE_DIR = "/home/zhihan/Workspace/STVR/data/TERRACE_STATISTICS";
	public static String STATISTICS = "statistics";
	public static String PATH_SEPERATOR = "/";
	public static String FILE_PRELIX = "caseversionid = ";
	public static int NUM_OF_PER_PAGE = 100;
	public static int MAX_NUM_TEST_CASE = 50000; // 高于所有测试用例个数上限，以此来全查所有数据
	public static String FAULT_MATRIX = "fault_matrix.txt";
	public static String STRING_SEPERATOR = "--";

	public static int[] arrayUnique(int[] a) {
		List<Integer> list = new LinkedList<Integer>();
		for (int i = 0; i < a.length; i++) {
			if (!list.contains(a[i])) {
				list.add(a[i]);
			}
		}
		int[] result = new int[list.size()];
		for(int i=0;i<result.length;i++){
			result[i]=list.get(i);
		}
		return result;
		
	}

}
