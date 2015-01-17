package mapebundle.adaptreasoner;

import java.util.List;

import org.osgi.framework.BundleContext;

/**
 * Explanation : 
 * @version : 2014. 5. 12.
 * @author : se
 * AdaptationReasoner / package adaptationreasoner;
 */

public interface IAdaptationReasoner {
	void reason(BundleContext bundleContext, String metaData,List currentBM);
}
