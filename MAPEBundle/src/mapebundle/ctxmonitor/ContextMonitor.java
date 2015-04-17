package mapebundle.ctxmonitor;

import mapebundle.adaptreasoner.AdaptationReasoner;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import UBMGenerator.service.LogMonitorService;


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

		////////////////////////////////Change/////////////////////////////////////
		ServiceReference ref = bundleContext.getServiceReference(LogMonitorService.class.getName());
		if (ref != null)
		{
			System.out.println("MAPE Bundle Find OK!");
		
			LogMonitorService logMonitor = (LogMonitorService) bundleContext.getService(ref);
			Object designedModel = logMonitor.getAllTransition();
			Object currentModel = logMonitor.getUserTransition(1);
			
			if(currentModel != null) {
				// metaData -> designModel : SharedPrefs 
				AdaptationReasoner.getInstance().reason(bundleContext, designedModel, currentModel);
			}
		}
		////////////////////////////////Change/////////////////////////////////////

		
	}
	public void activate() {
		isRunning = true;
	}

	public void deActivate() {
		isRunning = false;
	}

}
