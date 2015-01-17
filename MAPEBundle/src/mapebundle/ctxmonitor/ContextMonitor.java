package mapebundle.ctxmonitor;

import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import UBMGenerator.service.UBMGeneratorService;
import mapebundle.adaptreasoner.AdaptationReasoner;


/**
 * Explanation : 
 * @version : 2014. 5. 12.
 * @author : se
 * ConfigController / package configcontroller;
 */

public class ContextMonitor implements IContextMonitor {

	private boolean isRunning = false;
	private BundleContext bundleContext;

	public ContextMonitor(BundleContext bundleContext) {

		activate();
		this.bundleContext = bundleContext;
		System.out.println("ContextMonitor: start");	
	}
	public void Monitoring() {
		
		System.out.println("ContextMonitor: Monitoring");
				
		List currModel = null;
		String designedModel = "";
		
////////////////////////////////Change/////////////////////////////////////
		ServiceReference ref = bundleContext.getServiceReference(UBMGeneratorService.class.getName());
		if (ref != null)
		{
			System.out.println("MAPE Bundle Find OK!");
			UBMGeneratorService ubmGenerator = (UBMGeneratorService) bundleContext.getService(ref);
			designedModel = ubmGenerator.getDesignedModel();
			currModel = ubmGenerator.genCurBM("/data/");
			bundleContext.ungetService(ref);
		}
////////////////////////////////Change/////////////////////////////////////
		
		if(currModel != null) {
			// metaData -> designModel : SharedPrefs 
			AdaptationReasoner.getInstance().reason(bundleContext, designedModel, currModel);
		}
	}
	public void activate() {
		isRunning = true;
	}

	public void deActivate() {
		isRunning = false;
	}

}
