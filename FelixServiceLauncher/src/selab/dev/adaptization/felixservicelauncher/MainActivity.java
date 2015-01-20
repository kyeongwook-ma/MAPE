package selab.dev.adaptization.felixservicelauncher;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity{
	
	private BroadcastReceiver receiver;
	private boolean registered;
	  
	@Override
	  public void onResume() {
	    super.onResume();
	    if (!registered) {
	      registerReceiver(receiver, new IntentFilter(FelixService.class.getName()));
	      registered = true;
	    }
	  }
	  
	  @Override
	  public void onPause() {
	    super.onPause();
	    if (registered) {
	      unregisterReceiver(receiver);
	      registered = false;
	    }
	  }
	private void showToast(String msg) {
	    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if (receiver == null) {
			receiver = new BroadcastReceiver() {
		        @Override
		        public void onReceive(Context context, Intent intent)
		        {
		        	String out = intent.getStringExtra("serivce_execute_result");
		        	System.out.println(out);
		        	showToast(out);
		    		System.out.println("recevie broadcast");
		        }
		      };
	    }
		
		Button btnStart = (Button)findViewById(R.id.buttonStart);
        Button btnEnd = (Button)findViewById(R.id.buttonEnd);;
         
        btnStart.setOnClickListener(new OnClickListener() {
             
            @Override
            public void onClick(View v) {
                 
                Intent service= new Intent(getApplicationContext(), FelixService.class);
                startService(service);
            }
        });
         
        btnEnd.setOnClickListener(new OnClickListener() {
             
            @Override
            public void onClick(View v) {
	            Intent service = new Intent( getApplicationContext(), FelixService.class);
	            stopService(service);
            }
        });

	}
}
