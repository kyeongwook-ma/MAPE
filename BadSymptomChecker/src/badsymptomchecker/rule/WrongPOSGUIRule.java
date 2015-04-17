package badsymptomchecker.rule;

import java.util.List;

import sogang.selab.model.Transition;

public class WrongPOSGUIRule implements Rule{
	
	private int ruleType;

	public int getRuleType() {
		return ruleType;
	}

	@Override
	public boolean myRule(Object currentBM, Object designModel) {
		ruleType = 2;
		
		List currBM = (List) currentBM;
		List designedBM = (List) designModel;
			
		final double THRESHOLD = 65.0;
		
		return getRatio(currBM, designedBM) > THRESHOLD ? true : false;
	}
	
	private double getRatio(List<Transition> currBM, List<Transition> designedBM) {

		int ratio = 0;
		
		for(Transition t : currBM) {
			if(designedBM.contains(t)) {
				ratio++;
			}
		}
		
		return ratio / designedBM.size();
	}

}
