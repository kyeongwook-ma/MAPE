package mapebundle.adaptmanager;


import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;


public interface IAdaptationManager {
	void adapt(BundleContext bundleContext, List diagnosis,Object curUBM);
}
