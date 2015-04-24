package badsymptomchecker.rule;

import java.util.List;
import java.util.StringTokenizer;

public class WrongPOSGUIRule implements Rule{

	private int ruleType;

	public int getRuleType() {
		return ruleType;
	}

//	private double getRatio(List<Transition> currBM, List<Transition> designedBM) {
//
//		int ratio = 0;
//
//		for(Transition t : currBM) {
//			if(designedBM.contains(t)) {
//				ratio++;
//			}
//		}
//
//		return ratio / designedBM.size();
//	}

	@Override
	public boolean myRule(List currentBM, String designModel) {
		ruleType = 2;
		final double THRESHOLD = 65.0;

		
		return true;
		//return getRatio(currBM, designedBM) > THRESHOLD ? true : false;
	}

}
