package selab.dev.uiselfadaptivorg.activity;

import java.util.HashMap;

import selab.dev.uiselfadaptivorg.view.TabView;

import android.view.View;

public class Effector {
	public String getCurrentTab(TabView tabView)
	{
		String tabOrder = "";
		for(int i=0; i < tabView.getChildCount(); i++)
		{
			tabOrder += tabView.getChildAt(i).getClass().getName() +";";
		}
		return tabOrder;
	}
	
	public boolean changeTab(TabView tabView, String preTabStr) {
		//System.out.println(preTabStr);
		if(preTabStr == null || preTabStr == "") {
			return false;
		} 
		else {
			HashMap<String,View> viewMap = new HashMap<String,View>();
			for(int i=0; i < tabView.getChildCount(); i++)
			{
				viewMap.put(tabView.getChildAt(i).getClass().getName(), tabView.getChildAt(i));
				//System.out.println( getChildAt(i).getClass().getName() );
			}
			tabView.removeAllViews();
			
			String[] preTabStrList = preTabStr.split(";");
			for(String viewClassName : preTabStrList)
			{
				//System.out.println("find class:"+ viewClassName);
				View findItemView = viewMap.get(viewClassName);
				//System.out.println("find:"+ findItemView);
				if(findItemView != null)
				{
					tabView.addView(findItemView);
				}
			}
			tabView.invalidate();
		}
		return true;
	}
}
