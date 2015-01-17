package selab.dev.uiselfadaptive.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

import selab.dev.uiselfadaptive.util.MapUtil;

public class CountInfoData {

	//private final String CHECKIN_TAB_NAME = "CheckIn";
	//private final String INFO_TAB_NAME = "Info";
	//private final String NEWS_TAB_NAME = "News";
	
	private TreeMap countMap;
	private static CountInfoData instance;

	static {
		instance = new CountInfoData();
	}

	public static CountInfoData getInstance() {
		if(instance == null) {
			return new CountInfoData() ;
		} return instance;
	}

	public void destroy() {
		init();
	}

	private CountInfoData() {
		init();
	}
	
	private void init() {
		countMap = new TreeMap();
		//countMap.put((String)CHECKIN_TAB_NAME, new Integer(0));
		//countMap.put((String)INFO_TAB_NAME, new Integer(0));
		//countMap.put((String)NEWS_TAB_NAME, new Integer(0));
	}
	
	public void incrementCountAll(List tabNames) {
		init();
		for(int i = 0; i < tabNames.size(); ++i) {
			incrementCount((String) tabNames.get(i));
		}
	}
		
	public void incrementCount(String tabName) {
		Integer count = (Integer) countMap.get(tabName);
		if (count == null) 
			count = new Integer(0);
		
		System.out.println(tabName +":"+ count);
		if(!"".equals(tabName)) {
			int tmp = count.intValue();
			countMap.put(tabName, new Integer(tmp + 1));
		}
	}

	public List getOrderedTabNames() {

		ArrayList orderedTabList = new ArrayList();
	
		/*for(Object data : countMap.values())
		{
			System.out.println("Map"+data);
		}*/
		
		NavigableMap descendedMap = MapUtil.getDescendedMap(countMap);
	
		Iterator set = descendedMap.keySet().iterator();
		
		while(set.hasNext()) {
			orderedTabList.add(set.next());
		}
	
		return orderedTabList;
	}
	/*
	public String getAnalzeResult(List currentTabOrder) {
		
		NavigableMap descendedMap = MapUtil.getDescendedMap(countMap);
		StringBuilder sb = new StringBuilder();

		int i = 0;
		
		Iterator set = descendedMap.keySet().iterator();
		
		while(set.hasNext()) {
			String key = set.next().toString();
			String value = null ;
			try {
				value = countMap.get(set.next()).toString(); 
			} catch(NullPointerException e) {
				e.printStackTrace();
			}
			
			sb.append("\n"+ String.valueOf(++i) + ". " + key + " : " + value + " ȸ" + "\n");  
		}
		
		
		
		sb.append( "\n\n<< Usability ���ܰ��?>>");
		sb.append("\n\n Current TAP order  : ");
		
		for(int j = 0; j < currentTabOrder.size(); ++j) {
			sb.append(" " + currentTabOrder.get(i).toString() + " ");
		}
		
				
		sb.append("\n\n Personalized TAP order : ");

		Iterator iterator = descendedMap.keySet().iterator();

		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			sb.append(" " + key + " ");
		}
		
		return sb.toString();
	}
	*/
	public TreeMap getCountMap() {
		return countMap;
	}

	

}
