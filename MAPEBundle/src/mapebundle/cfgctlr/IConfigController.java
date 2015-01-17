package mapebundle.cfgctlr;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;


/**
 * Explanation : 
 * @version : 2014. 5. 14.
 * @author : se
 * ConfigController / package configcontroller;
 */

public interface IConfigController {
	public void reconfigure(BundleContext bundleContext, ArrayList strategies,List curUBM);
}
