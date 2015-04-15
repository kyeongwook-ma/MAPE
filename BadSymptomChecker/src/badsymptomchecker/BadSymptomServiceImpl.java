package badsymptomchecker;
import java.util.ArrayList;
import java.util.List;

import badsymptomchecker.rule.Rule;
import badsymptomchecker.rule.WrongPOSGUIRule;
import badsymptomchecker.services.BadSymptomService;

public class BadSymptomServiceImpl implements BadSymptomService {

	public BadSymptomServiceImpl() {
	}

	private ArrayList diffList;

	private void initRuleSet() {
		diffList = new ArrayList<String>();
	}


	public ArrayList reason(List currentBM, String designModel) {
		initRuleSet();
		
		//LogData check 
		compareBM(currentBM,designModel);
		
		return diffList;
	}


	@Override
	public List reason(Object currentBM, Object designModel) {
		List ruleList = new ArrayList();
		ruleList.add(new WrongPOSGUIRule());
		
		System.out.println("Current BM: "+ currentBM.toString());
		System.out.println("Design Model: "+designModel);
		
		for(Object rule : ruleList) {
			Rule castedRule = (Rule) rule;
			System.out.println(castedRule.getRuleType());
			if(castedRule.myRule(currentBM, designModel))
				diffList.add(new Integer(castedRule.getRuleType()));
		}

		System.out.println("BadSystom : diff check done");
		return null;
	}


	@Override
	public void compareBM(Object currentBM, Object designModel) {
		// TODO Auto-generated method stub
		
	}

}
