package UBMGenerator;


//import hostactivator.RepositoryService;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import UBMGenerator.service.UBMGeneratorService;
import selab.dev.uiselfadaptive.data.CountInfoData;
import selab.dev.uiselfadaptive.util.LogUtil;

public class UBMGeneratorServiceImpl implements UBMGeneratorService {

	private BundleContext bundleContext = null;
	public UBMGeneratorServiceImpl(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}

	public List genCurBM(String dirPath) {
			
////////////////////////////////Change/////////////////////////////////////
		System.out.println("UBMGen: start");
		try {
			ArrayList tabNames = (ArrayList) LogUtil.analyzeLog(dirPath);
			System.out.println("UBMGen: tabnames"+tabNames.toString());
			
			if(tabNames!= null) {
				System.out.println("UBMGen: in if(TabNames not null");
				CountInfoData.getInstance().incrementCountAll(tabNames);

				return CountInfoData.getInstance().getOrderedTabNames();
			}
			else
				System.out.println("UserBehavior: LogUtill.analyzeLog Error : return null");

		} catch (IOException e) {
			System.out.println("error");
			e.printStackTrace();
		}
////////////////////////////////Change/////////////////////////////////////
		
		return null;
	}

	@Override
	public String getDesignedModel() {
		String designedModel = null;

////////////////////////////////Change/////////////////////////////////////				
		//for(String modelName : RepositoryService.GetEffectores())
		//{
			//ServiceReference ref = bundleContext.getServiceReference(modelName);
			ServiceReference ref = bundleContext.getServiceReference("selab.dev.adaptization.Effector.service.IEffector");
			//System.out.println("DesignedModel: "+modelName);
			if (ref != null)
			{
				System.out.println(" UBMGeneratorService Bundle Find OK!");
				
				Object service = bundleContext.getService(ref);
				Class[] parameterTypes = {String.class};
				Object[] parameters = {"designedTab"};
				designedModel = (String) startBundleMethod(service, "getSharedPreference", parameterTypes, parameters);
				
				bundleContext.ungetService(ref);
				//break;
			}
		//}
////////////////////////////////Change/////////////////////////////////////
		
		return designedModel;
	}
	public Object startBundleMethod(Object service ,String methodgetName,Class[] parameterTypes,Object[] parameters) 
	{
		Object returnObject = null;
		//System.out.println(service.getClass().getName());
		if(service != null)
		{
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

		return returnObject;
	}
}