package mapebundle.ctxmonitor;

import java.io.IOException;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import sogang.selab.model.AFSM;
import sogang.selab.model.Transition;
import UBMGenerator.service.LogMonitorService;
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

		////////////////////////////////Change/////////////////////////////////////
		ServiceReference ref = bundleContext.getServiceReference(UBMGeneratorService.class.getName());
		if (ref != null)
		{
			System.out.println("MAPE Bundle Find OK!");
		
			LogMonitorService logMonitor = (LogMonitorService) bundleContext.getService(ref);
			List allTransition = logMonitor.getAllTransition();
			Transition userTransition = logMonitor.getUserTransition(1);
			
			if(userTransition != null) {
				// metaData -> designModel : SharedPrefs 
				AdaptationReasoner.getInstance().reason(bundleContext, allTransition, userTransition);
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
