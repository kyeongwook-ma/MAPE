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

	@Override
	public List reason(List currentBM, String designModel) {
		initRuleSet();

		//LogData check 
		compareBM(currentBM, designModel);

		return diffList;
	}


	@Override
	public void compareBM(List currentBM, String designModel) {
		ArrayList ruleList = new ArrayList();
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
	}

}
