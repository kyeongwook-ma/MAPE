package mapebundle.cfgctlr;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import usabilityimprover.service.Strategy;

/**
 * Explanation : 
 * @version : 2014. 5. 14.
 * @author : se
 * ConfigController / package configcontroller;
 */

public class ConfigController implements IConfigController {

	private static ConfigController instance;
	
	static {
		instance = new ConfigController();
	}
	private ConfigController() {
		
	}
	
	public static ConfigController getInstance() {
		if(instance == null) {
			return new ConfigController();
		} else {
			return instance;
		}
	}

	public void reconfigure(BundleContext bundleContext, ArrayList strategies,List currModel) 
	{
		System.out.println("ConfigController start:");
		execute(bundleContext, strategies);
	}
	private void execute(BundleContext bundleContext, ArrayList strategies)
	{
		for(int i=0;i<strategies.size();i++)
		{
			Strategy streatgy = (Strategy) strategies.get(i);
			
			ServiceReference ref = bundleContext.getServiceReference("selab.dev.adaptization.Effector.service.IEffector");
			if (ref != null)
			{
				System.out.println("Config Find!!");
				Object service = bundleContext.getService(ref);
				startBundleMethod(service, streatgy.getMethod(), 
						streatgy.getParmeterTypes(), streatgy.getParmeters());
				
				bundleContext.ungetService(ref);
			}
		}
	}
	public static Object startBundleMethod(Object service ,String methodgetName,Class[] parameterTypes,Object[] parameters) 
	{
		Object returnObject = null;
		//System.out.println(service.getClass().getName());
		if(service != null)
		{
			try {
				Method method = service.getClass().getDeclaredMethod(methodgetName, parameterTypes);
				System.out.println("method: "+method.getName());
				try {
					returnObject = method.invoke(service, parameters);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}

		return returnObject;
	}
}
