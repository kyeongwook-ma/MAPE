package selab.dev.adaptization.felixservicelauncher;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.felix.framework.Felix;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import selab.dev.adaptization.Effector.service.Effector;
import selab.dev.adaptization.Effector.service.IEffector;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

public class AdaptationBundleActivator {
	public static final String FELIX_BUNDLES_DIR = "/felix/bundle";
	public static String[] mapebundle_start_name = null;
	private static final String ANDROID_FRAMEWORK_PACKAGES = ("org.osgi.framework; version=1.4.0," +
	            "org.osgi.service.packageadmin; version=1.2.0," +
	            "org.osgi.service.startlevel; version=1.0.0," +
	            "org.osgi.service.url; version=1.0.0," +
	            "org.osgi.util.tracker," +
	            "android; " + 
	            "android.app;" + 
	            "android.content;" + 
	            "android.database;" + 
	            "android.database.sqlite;" + 
	            "android.graphics; " + 
	            "android.graphics.drawable; " + 
	            "android.graphics.glutils; " + 
	            "android.hardware; " + 
	            "android.location; " + 
	            "android.media; " + 
	            "android.net; " + 
	            "android.opengl; " + 
	            "android.os; " + 
	            "android.provider; " + 
	            "android.sax; " + 
	            "android.speech.recognition; " + 
	            "android.telephony; " + 
	            "android.telephony.gsm; " + 
	            "android.text; " + 
	            "android.text.method; " + 
	            "android.text.style; " + 
	            "android.text.util; " + 
	            "android.util; " + 
	            "android.view; " + 
	            "android.view.animation; " + 
	            "android.webkit; " + 
	            "android.widget; " + 
	            "com.google.android.maps; " + 
	            "com.google.android.xmppService; " + 
	            "javax.crypto; " + 
	            "javax.crypto.interfaces; " + 
	            "javax.crypto.spec; " + 
	            "javax.microedition.khronos.opengles; " + 
	            "javax.net; " + 
	            "javax.net.ssl; " + 
	            "javax.security.auth; " + 
	            "javax.security.auth.callback; " + 
	            "javax.security.auth.login; " + 
	            "javax.security.auth.x500; " + 
	            "javax.security.cert; " + 
	            "javax.sound.midi; " + 
	            "javax.sound.midi.spi; " + 
	            "javax.sound.sampled; " + 
	            "javax.sound.sampled.spi; " + 
	            "javax.sql; " + 
	            "javax.xml.parsers; " + 
	            "junit.extensions; " + 
	            "junit.framework; " + 
	            "org.apache.commons.codec; " + 
	            "org.apache.commons.codec.binary; " + 
	            "org.apache.commons.codec.language; " + 
	            "org.apache.commons.codec.net; " + 
	            "org.apache.commons.httpclient; " + 
	            "org.apache.commons.httpclient.auth; " + 
	            "org.apache.commons.httpclient.cookie; " + 
	            "org.apache.commons.httpclient.methods; " + 
	            "org.apache.commons.httpclient.methods.multipart; " + 
	            "org.apache.commons.httpclient.params; " + 
	            "org.apache.commons.httpclient.protocol; " + 
	            "org.apache.commons.httpclient.util; " + 
	            "org.bluez; " + 
	            "org.json; " + 
	            "org.w3c.dom; " + 
	            "org.xml.sax; " + 
	            "org.xml.sax.ext; " + 
	            "org.xml.sax.helpers; " + 
	            "version=1.0.0.m5-r15").intern();
	
	private File m_bundles = null;
	private File m_cache = null;
	private Felix m_felix = null;
	private Properties m_configMap;
	private String	m_absolutePath;
	private Context	m_context = null;
	private Resources m_res = null;
	
	//dependence Effector
	private Effector m_effector = null;
	
	public AdaptationBundleActivator(Context context, String absolutePath, Resources res) 
	{
		m_context = context;
		m_absolutePath = absolutePath;
		m_res = res;
	}
    /** Called when the activity is first created. 
     * @throws IOException */
    public synchronized void onCreate() {
    	//analyzeClassPath();
        PrintStream out = new PrintStream(new OutputStream(){
        	ByteArrayOutputStream output = new ByteArrayOutputStream();
			@Override
			public void write(int oneByte) throws IOException {
				output.write(oneByte);
				if (oneByte == '\n') {
					Log.v("out", new String(output.toByteArray()));
					output = new ByteArrayOutputStream();
				}
			}});
        System.setErr(out);
        System.setOut(out);
        m_configMap = new Properties();
        m_configMap.put("felix.log.level", "4");
        m_configMap.put("felix.fileinstall.debug", "1");
        // Configure the Felix instance to be embedded.
        m_configMap.put("felix.embedded.execution", "true");
        m_bundles = new File(m_absolutePath + FELIX_BUNDLES_DIR);
        delete(m_bundles);
        if (!m_bundles.exists()) {
        	if (!m_bundles.mkdirs()) {
        		throw new IllegalStateException("Unable to create bundles dir");
        	}
        }
        System.out.println(m_bundles);
        m_configMap.put("felix.fileinstall.dir", m_bundles.getAbsolutePath());
        // Add core OSGi packages to be exported from the class path
        // via the system bundle.
        m_configMap.put("org.osgi.framework.system.packages", ANDROID_FRAMEWORK_PACKAGES);
        m_cache = new File(m_absolutePath + "/felix/cache");
        delete(m_cache);
        //m_cache.delete();
		if (!m_cache.exists()) {
			if (!m_cache.mkdirs()) {
				throw new IllegalStateException("Unable to create felixcache dir");
			}
		}
        System.out.println(m_cache.getAbsolutePath());
        // felix.cache.rootdir=${user.dir}
        m_configMap.put("felix.cache.rootdir",m_absolutePath+"/felix");
        m_configMap.put("org.osgi.framework.storage", m_cache.getAbsolutePath());
    }
    //dependence Mapebundle, Effector
    public synchronized	String run() {
    	String retData = "";
    	if(m_felix != null)
    	{
//////////////////////////////////Start Point : mapebundel//////////////////////////////////////////////////
    		if(mapebundle_start_name == null)
    			throw new IllegalStateException("must is the start name");
    		if(mapebundle_start_name.length != 3)
    			throw new IllegalStateException("must have three names");
    		
    		startServiceMethod(mapebundle_start_name[0],mapebundle_start_name[1], mapebundle_start_name[2], null, null);
    		//startServiceMethod("mapebundle","mapebundle.ctxmonitor.ContextMonitor", "Monitoring", null, null);
    		System.out.println("run mapebundle");
    		if(m_effector != null)
    			retData = m_effector.getSharedPreference("tab");
    	}
    	return retData;
    }
   //dependence Effector
    private void RegistedEffector() 
    {
    	m_effector = new Effector(m_context);
    	//Test data
    	//m_effector.putSharedPreference("designedTab", "selab.dev.uiselfadaptivorg.view.News;selab.dev.uiselfadaptivorg.view.CheckIn;selab.dev.uiselfadaptivorg.view.Info;");
    	//m_effector.putSharedPreference("tab", "selab.dev.uiselfadaptivorg.view.News;selab.dev.uiselfadaptivorg.view.CheckIn;selab.dev.uiselfadaptivorg.view.Info;");
    	m_felix.getBundleContext().registerService(IEffector.class.getName(), m_effector, null);
    	System.out.println(IEffector.class.getName());
    }
  //dependence Effector
    public void saveEffector(String value)
    {
    	if(m_effector != null)
    		m_effector.putSharedPreference("designedTab", value);
    }
    //dependence File
    private void installBundles()  {
    	if(m_felix == null)
    		return;
		try {
			RegistedEffector();
			/*
			installAndStartBundle(R.raw.hostactivator,"hostactivator");
			
			Class[] classes = {String.class};

			Object[] objects4 = { IEffector.class.getName() };
			startServiceMethod("HostActivator","hostactivator.RepositoryService",
					"AddEffector",classes, objects4);
			*/
			
			File stocks = new File("/data/felix/bundle/install_bundle_list.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(stocks);
			
			XPath xpath = XPathFactory.newInstance().newXPath();
	        NodeList nodeList = (NodeList)xpath.evaluate("//bundle-list/bundle", doc, XPathConstants.NODESET);
	       
	        for( int idx=0 ; idx < nodeList.getLength() ; idx++ ){
	        	NamedNodeMap attrMap = nodeList.item(idx).getAttributes();
	        	String bundleName = attrMap.getNamedItem("name").getTextContent();
	        	File file = new File("/data/felix/bundle/"+bundleName+ ".jar");
	        	Log.i("bundleName", bundleName);
	        	Bundle bundle = m_felix.getBundleContext()
	    				.installBundle(m_absolutePath+"/felix/bundle/" + bundleName + ".jar", new FileInputStream(file));
	    		//if(idx != 1 && idx != 3 && idx != 5)
	    			bundle.start();
	    		Log.i(bundleName,Integer.toString(bundle.getState()));	            
	        }
	        
//			installAndStartBundle(R.raw.badsymptomcheckerservices, "badsymptomcheckerservices");
//			installAndStartBundle(R.raw.ubmgeneratorservice, "ubmgeneratorservice");
//			installAndStartBundle(R.raw.usabilityimproverservice, "usabilityimproverservice");
//			installAndStartBundle(R.raw.badsymptomchecker, "badsymptomchecker");
//			installAndStartBundle(R.raw.usabilityimprover, "usabilityimprover");
//			installAndStartBundle(R.raw.ubmgenerator, "ubmgenerator");
//			installAndStartBundle(R.raw.mapebundle, "mapebundle");
	        
//			installAndStartBundle(R.raw.selabdevubmgeneratorservice, "selabdevubmgeneratorservice");
//			installAndStartBundle(R.raw.selabdevbadsymptomcheckerservice, "selabdevbadsymptomcheckerservice");
//			installAndStartBundle(R.raw.selabdevusabilityimproverservice, "selabdevusabilityimproverservice");
//			
//			installAndStartBundle(R.raw.selabdevubmgenerator, "selabdevubmgenerator");
//			installAndStartBundle(R.raw.selabdevbadsymptomchecker, "selabdevbadsymptomchecker");
//			installAndStartBundle(R.raw.selabdevusabilityimprover, "selabdevusabilityimprover");
//			
//			installAndStartBundle(R.raw.mapebundle, "mapebundle");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    public Object startServiceMethod(String bundleSName,String serviceImplName,String methodgetName,Class[] parameterTypes,Object[] parameters) 
	{
		Object returnObject = null;
		for(Bundle b : m_felix.getBundleContext().getBundles())
		{
			System.out.println("installed bundle: "+b.getSymbolicName());
			if(b.getSymbolicName().equals(bundleSName))
			{
				System.out.println("OK");
				for(ServiceReference<?> ref : b.getRegisteredServices())
				{
					Object service = b.getBundleContext().getService(ref);
					System.out.println("service name : "+service.getClass().getName());
					if(service.getClass().getName().equals(serviceImplName))
					{
						//System.out.println("OK");
						try {
							Method method = service.getClass().getDeclaredMethod(methodgetName, parameterTypes);
							System.out.println("method: "+method.getName());
							try {
							returnObject = method.invoke(service, parameters);
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							}
						} catch (NoSuchMethodException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return returnObject;
	}
    public void installAndStartBundle(int bundleId, String bundleName )throws Exception {
		InputStream is = m_res.openRawResource(bundleId);
		Bundle bundle = m_felix.getBundleContext()
				.installBundle(m_absolutePath+"/felix/bundle/" + bundleName + ".jar", is);
		bundle.start();
		Log.i(bundleName,Integer.toString(bundle.getState()));
	}
    public synchronized void onStart() {
    	try {
    		m_felix = new Felix(m_configMap);
    		m_felix.start();
		} catch (BundleException ex) {
			throw new IllegalStateException(ex);
		}
    	installBundles();
    	BundleContext	context = m_felix.getBundleContext();
		for (Bundle b : context.getBundles())
		{
			Log.i("Felix","Bundle: "+ b.getSymbolicName());
		}
    }
    
    public synchronized void onStop() {
        System.out.println("============= ON STOP ==========");

    	try {
			m_felix.stop();
			m_felix.waitForStop(0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BundleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	m_felix = null;
    }
    
    public synchronized void onDestroy() {
        System.out.println("============= ON DESTROY ==========");

    	delete(m_cache);
    	System.out.println("Delete File:"+m_cache);
    	delete(m_bundles);
    	System.out.println("Delete File:"+m_bundles);
    	m_configMap = null;
    	m_cache = null;
    }
    
    private void delete(File target) {
    	if (target.isDirectory()) {
    		for (File file : target.listFiles()) {
    			delete(file);
    		}
    	}
    	target.delete();
    }
}