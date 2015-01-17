package selab.dev.uiselfadaptive.util;

import java.util.StringTokenizer;



public class StringUtil {

	public static String chopStr(String s, String chopExpression) {
		int chopIdx = s.indexOf(chopExpression);
		String choppedExpression = s.substring(chopIdx + chopExpression.length());
		return choppedExpression;
	}

	public static String getTabClassName(String s) {

		String choppedStr = chopStr(s, "class");
		
		if(!choppedStr.contains("selab"))
			return "";
		/*
		StringTokenizer tokenizer = new StringTokenizer(choppedStr);
		for(int i = 0; i < 4; ++i) {
			tokenizer.nextToken(".");
		}

		String classInfo = tokenizer.nextToken();
		*/
		String classInfo = choppedStr;
		classInfo = classInfo.trim();
		classInfo = classInfo.substring(0,classInfo.indexOf("-"));
				
		return classInfo.trim();
	}
	

	public static String reverseString(String s) {
		return ( new StringBuffer(s) ).reverse().toString();
	}

}
