package usabilityimprover;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;

import usabilityimprover.service.UsabilityImprover;
import usabilityimprover.streatgy.StrategyFactory;


/**
 * Explanation : 
 * @version : 2014. 6. 10.
 * @author : se
 * UsabilityImprover / package usabilityimprover;
 */

public class UsabilityImproverImpl implements UsabilityImprover {

	public UsabilityImproverImpl(BundleContext context) {
		StrategyFactory.bundleContext = context;
	}

	private ArrayList genStreatgies(List diagnosis, Object curModel) {

		ArrayList strategies = new ArrayList();

		for(Object type : diagnosis) {
			strategies.add( StrategyFactory.getStreatgy((Integer) type, curModel) );			
		}
		
		System.out.println("Usability : streatgie start");
		return strategies;
	}

	@Override
	public List adapt(List diagnosis, Object curModel) {
		try {
			return genStreatgies(diagnosis, curModel);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return diagnosis;
	}

}

