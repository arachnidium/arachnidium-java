package com.github.arachnidium.tutorial.app_modeling.mobile.hybrid_app.annotated_pageobjects.aggregated_page_objects;

import com.github.arachnidium.core.MobileScreen;
import com.github.arachnidium.model.common.Static;
import com.github.arachnidium.model.mobile.ios.IOSApp;
import com.github.arachnidium.tutorial.app_modeling.mobile.hybrid_app.
		annotated_pageobjects.AppleCom; /**<==!!*/
import com.github.arachnidium.tutorial.app_modeling.mobile.hybrid_app.
		annotated_pageobjects.UICatalog; /**<==!!*/

public class UICatalogApp extends IOSApp {

	@Static
	public UICatalog uiCatalog;
	
	@Static
	public AppleCom appleCom;
	
	public UICatalogApp(MobileScreen context) {
		super(context);
	}

}
