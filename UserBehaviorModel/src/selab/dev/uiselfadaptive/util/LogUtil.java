package selab.dev.uiselfadaptive.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//import android.os.Environment;

public class LogUtil {

	public static List analyzeLog(String dirPath) throws IOException {

		ArrayList tabNames = new ArrayList();
		//File path = Environment.getDataDirectory();
		BufferedReader br = new BufferedReader(new FileReader(new File( "/data/mylog.txt")));

		String strLine = null;   

		while( (strLine = br.readLine()) != null) {

			String className = StringUtil.getTabClassName(strLine);				
			tabNames.add(className);
		} 

		br.close();
		
		return tabNames;
 		
	}

}
