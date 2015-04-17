package sogang.selab;


import sogang.selab.kyobo.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class Effector {
	
	public void relocateGUI(Activity baseActivity, Object target, 
			LinearLayout targetView, Class clazz, LinearLayout rootView) {		
		
		Button btnPurchase = (Button)target;
		
		View v = (View) clazz.cast(target);
		ViewGroup parent = (ViewGroup) v.getParent();
		parent.removeView(v);
		
		LayoutInflater li = (LayoutInflater) BadUI.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout baseView  = (LinearLayout) li.inflate(R.layout.base, null);
		baseView.addView(v);
	
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);		
		baseActivity.addContentView(baseView, lp);
		
		FrameLayout.LayoutParams lp2 = (android.widget.FrameLayout.LayoutParams) rootView.getLayoutParams();
		targetView.measure(MeasureSpec.UNSPECIFIED,MeasureSpec.UNSPECIFIED);
		lp.setMargins(0, btnPurchase.getMeasuredHeight(), 0, 0);
		rootView.setLayoutParams(lp);
	}
}
