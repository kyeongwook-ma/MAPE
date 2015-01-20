package selab.dev.uiselfadaptivorg.activity;

import selab.dev.uiselfadaptivorg.R;
import selab.dev.uiselfadaptivorg.view.TabView;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;


import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
	private FelixServiceClient	fService;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.main);
		
		ImageView iv = (ImageView)findViewById(R.id.iv);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		params.weight = 1.0f;
		params.gravity = Gravity.TOP;
		TabView tabView = new TabView(this, iv);
		
		fService = new FelixServiceClient(this, tabView, params);
	}
	@Override
	protected void onDestroy() {
		fService.FelixServiceIntentDestory();
		super.onDestroy();
	}
	@Override
	public void onResume() {
	    super.onResume();
	    fService.FelixServiceResume();
	}
	  
	@Override
	public void onPause() {
	    super.onPause();
	    fService.FelixServicePause();
	}
}
