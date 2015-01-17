package usabilityimprover.streatgy;

import java.util.List;

import org.osgi.framework.BundleContext;

import usabilityimprover.service.Strategy;


public class StrategyFactory {
	public static BundleContext bundleContext;
	
	public static Strategy getStreatgy(Integer streatgyNum, List curModel) {
////////////////////////////////Change/////////////////////////////////////
		
		if (streatgyNum.intValue() == 1) {
			Class[] parTypes = {String.class};
			String model = curModel.toString();
			model = model.replace(" ", "");
			model = model.replace(",", ";");
			model = model.replace("[", "");
			model = model.replace("]", "");
			Object[] pars = {model};
			return new Strategy("run", parTypes, pars, streatgyNum.intValue());
		}
		
////////////////////////////////Change/////////////////////////////////////
		return null;
	}
}
