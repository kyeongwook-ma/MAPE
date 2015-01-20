package selab.dev.uiselfadaptivorg.view;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import selab.dev.uiselfadaptivorg.R;


public class TabView extends LinearLayout  implements OnClickListener {

	private ImageView iv;

	public TabView(Context context, final ImageView iv) {
		super(context);
		this.iv = iv;
		BtnGroups btn1 = new CheckIn(context);
		BtnGroups btn2 = new Info(context);
		BtnGroups btn3 = new News(context);
		addView(btn1);
		addView(btn2);
		addView(btn3);
		btn1.attachOnClickListener(this);
		btn2.attachOnClickListener(this);
		btn3.attachOnClickListener(this);

	}
	public void onClick(View v) {

		String viewClassName = v.getClass().getName();

		if(viewClassName.equals(CheckIn.class.getName()))
			iv.setImageResource(R.drawable.shop);

		else if(viewClassName.equals(Info.class.getName()))
			iv.setImageResource(R.drawable.info);

		else if(viewClassName.equals(News.class.getName()))
			iv.setImageResource(R.drawable.service);
	}
}


