package com.github.arachnidium.tutorial.app_modeling.mobile.hybrid_app.annotated_pageobjects.aggregated_page_objects;

import com.github.arachnidium.core.MobileScreen;
import com.github.arachnidium.model.common.Static;
import com.github.arachnidium.model.mobile.ios.IOSApp;
import com.github.arachnidium.tutorial.app_modeling.mobile.hybrid_app.
		annotated_pageobjects.AppleCom; /**<==!!*/
import com.github.arachnidium.tutorial.app_modeling.mobile.hybrid_app.
		annotated_pageobjects.UICatalog; /**<==!!*/

public class UICatalogApp extends IOSApp {

	@Static /**<== This annotation means that it is content which always/very frequently present
	and frequently used*/
	public UICatalog uiCatalog; /**It is not necessary to 
	make fields public. You can make them private and implement public getter 
	for each one. I made that just for visibility*/
	
	@Static
	public AppleCom appleCom;
	
	public UICatalogApp(MobileScreen context) {
		super(context);
	}

}
