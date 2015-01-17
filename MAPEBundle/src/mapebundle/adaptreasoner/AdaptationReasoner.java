package mapebundle.adaptreasoner;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import badsymptomchecker.services.BadSymptomService;
import mapebundle.adaptmanager.AdaptationManager;

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
	public void reason(BundleContext bundleContext, String desginedModel,List currModel)  {
		
		System.out.println("ContextMonitor: Monitoring");
		ArrayList diagnosis = null;
		
////////////////////////////////Change/////////////////////////////////////
		ServiceReference ref = bundleContext.getServiceReference(BadSymptomService.class.getName());
		if (ref != null)
		{
			System.out.println("BadSymptom Bundle Find OK!");
			BadSymptomService badSymptomService = (BadSymptomService) bundleContext.getService(ref);
			diagnosis = badSymptomService.reason(currModel,desginedModel);
			bundleContext.ungetService(ref);
		}
////////////////////////////////Change/////////////////////////////////////
		
		if(diagnosis != null)
			AdaptationManager.getInstance().adapt(bundleContext,diagnosis,currModel);
	}

}
