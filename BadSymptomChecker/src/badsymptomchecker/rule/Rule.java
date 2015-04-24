package badsymptomchecker.rule;

import java.util.List;

public interface Rule {
	public boolean myRule(List currentBM, String designModel);	
	public int getRuleType();
}
