package selab.dev.adaptization.felixservicelauncher;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.osgi.framework.Bundle;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class FelixService extends Service {

	public static AdaptationBundleActivator	runFelix = null;
	public static String clientIntentPackageName;
	private BroadcastReceiver receiver;
	private boolean registered;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onCreate() {
//////////////load file////////////////////	
		try {
//			String []mape = new String[3];
//			mape[0] = "mapebundle";
//			mape[1] = "mapebundle.ctxmonitor.ContextMonitor";
//			mape[2] = "Monitoring";
			 
				
//			FelixService.clientIntentPackageName = "selab.dev.uiselfadaptiveorg.activity.MainActivity";
			
			File file = new File("/data/felix/felix_config.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;
			
				dBuilder = dbFactory.newDocumentBuilder();
			
			Document doc = dBuilder.parse(file);
			
			XPath xpath = XPathFactory.newInstance().newXPath();
	        Node nodemape = (Node)xpath.evaluate("//config_list/mapbundle", doc, XPathConstants.NODE);
	        if(nodemape != null)
	        {
	        	String []mape = new String[3];
	        	NamedNodeMap attrMap = nodemape.getAttributes();
	        	mape[0] = attrMap.getNamedItem("bundle_name").getTextContent();
	        	mape[1] = attrMap.getNamedItem("service_name").getTextContent();
	        	mape[2] = attrMap.getNamedItem("method_name").getTextContent();
	        	AdaptationBundleActivator.mapebundle_start_name = mape;
	        }
	        
	        Node nodeclient = (Node)xpath.evaluate("//config_list/client_package", doc, XPathConstants.NODE);
	        if(nodeclient != null)
	        {
	        	NamedNodeMap attrMap = nodeclient.getAttributes();
	        	FelixService.clientIntentPackageName = attrMap.getNamedItem("name").getTextContent();
	        }
	        
		} catch (ParserConfigurationException e) {    
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	
//////////////load file////////////////////
		
		//dependence Client
		if (receiver == null) {
			receiver = new BroadcastReceiver() {
		        @Override
		        public void onReceive(Context context, Intent intent)
		        {
		        	String out = intent.getStringExtra("activity_result");
		        	System.out.println("exit:"+out);
		        	if(runFelix != null)
		        		runFelix.saveEffector(out);
		        }
		      };
	    }
		if (!registered) {
		    //registerReceiver(receiver, new IntentFilter("selab.dev.uiselfadaptiveorg.activity.MainActivity"));
			registerReceiver(receiver, new IntentFilter(clientIntentPackageName));
		    registered = true;
	    }
		super.onCreate();
	}
	public void sendMessage(String msg) {
		Intent i = new Intent(getClass().getName());
		i.putExtra("serivce_execute_result", msg);
	    sendBroadcast(i);
	}
	@Override
	public void onDestroy() {

		System.out.println("서비스 Finish");
		if (registered) {
	      unregisterReceiver(receiver);
	      registered = false;
	    }
		if(runFelix != null)
		{
			runFelix.onStop();
			runFelix.onDestroy();
			runFelix = null;
		}
		sendMessage("Service Stop");
		super.onDestroy();
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		System.out.println("서비스 Start");
		//System.out.println("Package:"+intent.getPackage());
		//System.out.println("Action:"+intent.getAction());
		//System.out.println("CompoentName:"+intent.getComponent());
		
		if(intent.getAction() == null)
		{
			if(FelixService.runFelix == null)
			{
				FelixService.runFelix = new AdaptationBundleActivator(getApplicationContext(),getFilesDir().getAbsolutePath(), getResources());
				FelixService.runFelix.onCreate();
				FelixService.runFelix.onStart();
				
				sendMessage("Service Start");
			}
			else
			{
				sendMessage("Service Running");
			}
		}
		else
		{
			//dependence Client
			if(FelixService.runFelix != null)
			{
				System.out.println("run");
				String value = FelixService.runFelix.run(); 
				sendMessage(value);
			}
		}
		return super.onStartCommand(intent, flags, startId);
	}
}
