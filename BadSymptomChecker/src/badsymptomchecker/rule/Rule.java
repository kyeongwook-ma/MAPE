package badsymptomchecker.rule;

import java.util.List;
import java.util.TreeMap;

public interface Rule {
	public boolean myRule(List currentBM,String designModel);	
	public int getRuleType();
}
