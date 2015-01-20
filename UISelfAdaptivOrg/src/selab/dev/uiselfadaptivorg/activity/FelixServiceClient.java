package selab.dev.uiselfadaptivorg.activity;

import selab.dev.uiselfadaptivorg.view.TabView;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.view.ViewGroup.LayoutParams;

public class FelixServiceClient {
	private BroadcastReceiver receiver;
	private boolean registered;
	private Effector effector = new Effector();
	private TabView	 tabView;
	private Activity mainActivity;
	private boolean messageRecive = false;
	private LayoutParams params;
	public FelixServiceClient(Activity activity, TabView tabView, LayoutParams params) {
		mainActivity = activity;
		this.tabView = tabView; 
		this.params = params;
		FelixServiceIntentRegistration(tabView, params);
		FelixServiceRun();
	}
	
	public void FelixServiceRun() {
		Intent intent = mainActivity.getIntent();
		intent.setComponent( 
				new ComponentName(
						"selab.dev.adaptization.felixservicelauncher",
						"selab.dev.adaptization.felixservicelauncher.FelixService"));
		mainActivity.startService(intent);
		
		 Handler handler = new Handler(); 
		    handler.postDelayed(new Runnable() { 
		         public void run() { 
		        	 if( !messageRecive )
						{
							mainActivity.addContentView(tabView.getRootView(), params);
						}
		         } 
		    }, 1000); 
	}
	
	public void FelixServiceIntentRegistration(final TabView tabView, final LayoutParams params)
	{
		if (receiver == null) {
			receiver = new BroadcastReceiver() {
		        @Override
		        public void onReceive(Context context, Intent intent)
		        {
		        	String outValue = intent.getStringExtra("serivce_execute_result");
		        	mainActivity.addContentView(tabView.getRootView(), params);
		        	effector.changeTab(tabView, outValue);
		        	messageRecive = true;
		        }
		    };
	    }
	}

	public void FelixServiceIntentDestory() {
		Intent i = new Intent(getClass().getName());
		i.putExtra("activity_result", effector.getCurrentTab(tabView));
		mainActivity.sendBroadcast(i);
	}
	
	public void FelixServiceResume() {
		if (!registered) {
			mainActivity.registerReceiver(receiver, new IntentFilter("selab.dev.adaptization.felixservicelauncher.FelixService"));
			registered = true;
		}
	}
	
	public void FelixServicePause() {
		if (registered) {
			mainActivity.unregisterReceiver(receiver);
			registered = false;
		}
	}
}
