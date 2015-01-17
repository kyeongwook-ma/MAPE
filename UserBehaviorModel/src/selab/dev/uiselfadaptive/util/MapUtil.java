package selab.dev.uiselfadaptive.util;

import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;


public class MapUtil {


	public static Map sortByValue(Map unsortedMap){
				
		Map sortedMap = new TreeMap(new ValueComparator(unsortedMap));
	
		sortedMap.putAll(unsortedMap);

		Set unSortedKeySet = unsortedMap.keySet();
		Set sortedKeySet = sortedMap.keySet();
	
		/* case when sort is failed */
		if (!unSortedKeySet.equals(sortedKeySet)) {
			return unsortedMap;
		} 		
	
		return sortedMap; 
	}

	public static Map sortByKey(Map unsortedMap){
		Map sortedMap = new TreeMap();
		sortedMap.putAll(unsortedMap);
		return sortedMap;
	}

	public static NavigableMap getDescendedMap(TreeMap countMap) {
		TreeMap sortedMap = (TreeMap) sortByValue(countMap);
		return ((NavigableMap) sortedMap).descendingMap();
	}


}
