package io.github.dataspy.asistant;

public class DataMapping {
	public static String TOP_OUTPUT_DIR = "/home/zhihan/Workspace/STVR/data/RESULT_STATISTICS";
	public static String PREFIX_SEPERATOR = "medium:\"\n[1]";
	public static String POSTFIX_SEPERATOR = null;
	public static String TOP_DIR = "/home/zhihan/Workspace/STVR/data/RESULT";
	public static String PATH_SEPERATOR = "/";
	public static String SHEET_TITLE = "version";

	public static String extractFromContent(String content) {
		if (POSTFIX_SEPERATOR == null) {
			System.out.println(content.indexOf(PREFIX_SEPERATOR));
			return content.substring(content.indexOf(PREFIX_SEPERATOR)
					+ PREFIX_SEPERATOR.length(),
					content.length());
		} else {
			return content.substring(content.indexOf(PREFIX_SEPERATOR)
					+ PREFIX_SEPERATOR.length(),
					content.indexOf(POSTFIX_SEPERATOR));
		}
	}
}
