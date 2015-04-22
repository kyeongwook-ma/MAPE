package mapebundle.adaptreasoner;

import java.util.List;

import mapebundle.adaptmanager.AdaptationManager;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import badsymptomchecker.services.BadSymptomService;

/**
 * Explanation : 
 * @version : 2014. 5. 12.
 * @author : se
 * AdaptationReasoner / package adaptationreasoner;
 */

public class AdaptationReasoner implements IAdaptationReasoner {

	private static AdaptationReasoner instance;

	static {
		instance = new AdaptationReasoner();
	}


	private AdaptationReasoner() {
	}

	public static AdaptationReasoner getInstance() {
		if(instance == null) {
			return new AdaptationReasoner();
		} else {
			return instance;
		}
	}

	//dependence code
	public void reason(BundleContext bundleContext, Object currModel,  Object desginedModel)  {
		
		System.out.println("ContextMonitor: Monitoring");
		List diagnosis = null;
////////////////////////////////Change/////////////////////////////////////
		ServiceReference ref = bundleContext.getServiceReference(BadSymptomService.class.getName());
		if (ref != null)
		{
			System.out.println("BadSymptom Bundle Find OK!");
			
			BadSymptomService guiBadSymptomAnalyzeService = bundleContext.getService(ref);
			diagnosis = guiBadSymptomAnalyzeService.reason(currModel, desginedModel);
			bundleContext.ungetService(ref);
		}
////////////////////////////////Change/////////////////////////////////////
		
		if(diagnosis != null)
			AdaptationManager.getInstance().adapt(bundleContext, diagnosis, currModel);
	}

}
