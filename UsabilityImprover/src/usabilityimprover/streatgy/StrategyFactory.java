package usabilityimprover.streatgy;

import org.osgi.framework.BundleContext;

import usabilityimprover.service.Strategy;


public class StrategyFactory {
	public static BundleContext bundleContext;
	
	public static Strategy getStreatgy(Integer streatgyNum, Object curModel) {
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
		} else if(streatgyNum.intValue() == 2){
			
			//Activity activity, LinearLayout targetView, LinearLayout rootView, Object target
			Class[] parameterTypes = {};
			Object[] parameters = {""};
			return new Strategy("relocateGUI",
					parameterTypes, parameters, streatgyNum.intValue());
		}
////////////////////////////////Change/////////////////////////////////////
		return null;
	}
}
