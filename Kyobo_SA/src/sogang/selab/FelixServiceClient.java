package sogang.selab;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class FelixServiceClient {
	private BroadcastReceiver receiver;
	private boolean registered;
	private Effector effector = new Effector();
	private Activity mainActivity;
	private boolean messageRecive = false;
	
	public FelixServiceClient(Activity activity, Object target, 
			LinearLayout targetView, Class clazz, LinearLayout rootView) {
		mainActivity = activity;
		FelixServiceIntentRegistration();
		FelixServiceRun(target, targetView, clazz, rootView);
	}

	public void FelixServiceRun(final Object target, final LinearLayout targetView, 
			final Class clazz, final LinearLayout rootView) {
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
					effector.relocateGUI(mainActivity, target, targetView, clazz, rootView);
				}
			} 
		}, 1000); 
	}

	public void FelixServiceIntentRegistration()
	{
		if (receiver == null) {
			receiver = new BroadcastReceiver() {
				@Override
				public void onReceive(Context context, Intent intent)
				{
					// String outValue = intent.getStringExtra("serivce_execute_result");
					//	mainActivity.addContentView(tabView.getRootView(), params);
					messageRecive = true;
				}
			};
		}
	}

	public void FelixServiceIntentDestory() {
		Intent i = new Intent(getClass().getName());
		//i.putExtra("activity_result", effector.getCurrentTab(tabView));
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
