package selab.dev.adaptization.Effector.service;

public interface IConfManager {
	
	public boolean run(String tabOrder);
	public void putSharedPreference(String key, String value);
	public String getSharedPreference(String key);
}
