package sogang.selab.kyobo_sa;

import sogang.selab.FelixServiceClient;
import sogang.selab.kyobo.R;
import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class MainActivity_SA extends Activity {

	private Button btnPurchase, btnMarket;
	private ScrollView sView;
	private MyView targetView;
	private LinearLayout rootView;
	private FelixServiceClient fService;
	private boolean isRegistered;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		sView = (ScrollView)findViewById(R.id.scrollView1);
		btnPurchase = (Button)findViewById(R.id.btnPurchase);
		targetView = (MyView)findViewById(R.id.targetview);
		rootView  = (LinearLayout) findViewById(R.id.rootview);
		
		sView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				final int i[] = new int[2];
				final Rect scrollBounds = new Rect();
				
				/* 대상 GUI의 화면 범위내 크기 설정 */
				rootView.getDrawingRect(scrollBounds);

				/* 화면 범위내에서 대상 GUI가 안보인다면 */
				if (!targetView.getGlobalVisibleRect(scrollBounds)) {
					if(!isRegistered) {
						adaptGUI();
					}
				}

				return false;
			}
		});
	}
	
	
	private void adaptGUI() {
		isRegistered = true;
		fService = new FelixServiceClient(MainActivity_SA.this, 
				btnPurchase, targetView, targetView.getClass(), rootView);
	}

	public interface VisibilityChangeListener {
		public void onVisibilityChanged(int visibility);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
