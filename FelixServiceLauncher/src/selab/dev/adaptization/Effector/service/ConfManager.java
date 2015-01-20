package selab.dev.adaptization.Effector.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class ConfManager implements IConfManager {
	
	private Context mainContext = null;
	public ConfManager(Context context) {
		mainContext = context;
	}
	//dependance UsabilityImprover getStreatgy run keyword
	@Override
	public boolean run(String tabOrder) {
		//String tabOrder = "selab.dev.uiselfadaptivorg.view.News;selab.dev.uiselfadaptivorg.view.CheckIn;selab.dev.uiselfadaptivorg.view.Info;";
		if(mainContext != null)
			putSharedPreference("tab", tabOrder);
		return true;
	}
	@Override
	public void putSharedPreference(String key, String value) {
		SharedPreferences prefs = 
				PreferenceManager.getDefaultSharedPreferences(mainContext);

		SharedPreferences.Editor editor = prefs.edit();

		editor.putString(key, value);
		editor.commit();
		
	}
	@Override
	public String getSharedPreference(String key) {
		SharedPreferences prefs = 
				PreferenceManager.getDefaultSharedPreferences(mainContext);

		return prefs.getString(key, null);
	}

}
