package badsymptomchecker.rule;

import java.util.List;
import java.util.TreeMap;

public interface Rule {
	public boolean myRule(Object currentBM,Object designModel);	
	public int getRuleType();
}
