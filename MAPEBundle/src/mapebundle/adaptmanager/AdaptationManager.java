package mapebundle.adaptmanager;

import java.util.ArrayList;
import java.util.List;

import mapebundle.cfgctlr.ConfigController;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import usabilityimprover.service.UsabilityImprover;


public class AdaptationManager implements IAdaptationManager {

	private static AdaptationManager instance;

	static {
		instance = new AdaptationManager();
	}


	public static AdaptationManager getInstance() {
		if(instance == null) {
			return new AdaptationManager();
		} else {
			return instance;
		}
	}


	//dependence code
	public void adapt(BundleContext bundleContext, List diagnosis, Object currModel){		
		System.out.println("diagnosis:"+diagnosis);
		System.out.println("currModel:"+currModel);
		
		List streatgies = null;
		
////////////////////////////////Change/////////////////////////////////////
	
		ServiceReference ref2 = bundleContext.getServiceReference(UsabilityImprover.class.getName());
		if (ref2 != null)
		{
			System.out.println("Improver Bundle Find OK!");
			UsabilityImprover usabilityService = (UsabilityImprover) bundleContext.getService(ref2);
			streatgies = usabilityService.adapt(diagnosis, currModel);
			bundleContext.ungetService(ref2);
		}
		
		
		
////////////////////////////////Change/////////////////////////////////////
		
		if(streatgies != null)
			ConfigController.getInstance().reconfigure(bundleContext, streatgies, currModel);
	}

}
