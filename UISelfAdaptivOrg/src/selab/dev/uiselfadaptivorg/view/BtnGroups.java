package selab.dev.uiselfadaptivorg.view;

import android.content.Context;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class BtnGroups extends Button {
	
	protected LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
			LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

	public BtnGroups(Context context) {
		super(context);
		params.weight = 1.0f;
		params.gravity = Gravity.TOP;
		setLayoutParams(params);
		
	}
		
	public void attachOnClickListener(OnClickListener listener) {
		this.setOnClickListener(listener);
	}

}
