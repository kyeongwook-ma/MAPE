package badsymptomchecker.rule;




public class WrongPOSGUIRule implements Rule{
	
	private int ruleType;

	public int getRuleType() {
		return ruleType;
	}

	@Override
	public boolean myRule(Object currentBM, Object designModel) {
		ruleType = 2;
		return currentBM.equals(designModel) ? true : false;
	}

}
