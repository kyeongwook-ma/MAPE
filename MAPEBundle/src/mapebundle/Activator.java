package mapebundle;
import mapebundle.ctxmonitor.ContextMonitor;
import mapebundle.ctxmonitor.IContextMonitor;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {
		
	@Override
	public void start(BundleContext bundleContext) {
		// TODO Auto-generated method stub
		System.out.println("MAPE Bundle Start!");
		bundleContext.registerService(
				IContextMonitor.class.getName(),new ContextMonitor(bundleContext), null);
	}
	@Override
	public void stop(BundleContext bundleContext) {
		// TODO Auto-generated method stub
		
	}
	


}
