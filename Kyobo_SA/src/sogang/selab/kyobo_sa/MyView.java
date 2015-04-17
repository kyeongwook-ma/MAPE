package sogang.selab.kyobo_sa;

import java.lang.ref.WeakReference;

import sogang.selab.kyobo_sa.MainActivity_SA.VisibilityChangeListener;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class MyView extends LinearLayout{
	WeakReference<VisibilityChangeListener> mListener;

	public MyView(Context context) {
		super(context);
	}

	public MyView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setVisibilityChangeListener(VisibilityChangeListener listener) {
		mListener = new WeakReference<VisibilityChangeListener>(listener);
	}

	@Override
	protected void onVisibilityChanged(View changedView, int visibility) {
		super.onVisibilityChanged(changedView, visibility);

		if (mListener != null && changedView == this) {
			VisibilityChangeListener listener = mListener.get();
			if (listener != null) {
				listener.onVisibilityChanged(visibility);
			}
		}

	}
}
