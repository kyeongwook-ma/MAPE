package sogang.selab.kyobo_sa;

import android.app.Activity;
import android.util.DisplayMetrics;

public class DisplayMetric {
	
	private DisplayMetrics metrics;
	private static DisplayMetric instance;
	
	public static DisplayMetric getInstance(Activity activity) {
		if(instance != null) {
			return instance;
		} else {
			return new DisplayMetric(activity);
		}
	}
	
	private DisplayMetric(Activity activity) {
		metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
	}
	
	public float getScreenX() {
		return metrics.widthPixels;
	}
	
	public float getScreenY() {
		return metrics.heightPixels;
	}

}
