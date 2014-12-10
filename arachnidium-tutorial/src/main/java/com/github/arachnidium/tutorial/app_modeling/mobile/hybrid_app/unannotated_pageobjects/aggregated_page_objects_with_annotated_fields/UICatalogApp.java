package com.github.arachnidium.tutorial.app_modeling.mobile.hybrid_app.unannotated_pageobjects.aggregated_page_objects_with_annotated_fields;

import com.github.arachnidium.core.MobileScreen;
import com.github.arachnidium.model.common.Static;
import com.github.arachnidium.model.mobile.ios.IOSApp;
import com.github.arachnidium.model.support.annotations.DefaultPageIndex;
import com.github.arachnidium.model.support.annotations.ExpectedContext;
import com.github.arachnidium.model.support.annotations.ExpectedPageTitle;
import com.github.arachnidium.model.support.annotations.ExpectedURL;
import com.github.arachnidium.model.support.annotations.MobileContextNamePatterns;
import com.github.arachnidium.tutorial.app_modeling.mobile.hybrid_app.
		annotated_pageobjects.AppleCom; /**<==!!*/
import com.github.arachnidium.tutorial.app_modeling.mobile.hybrid_app.
		annotated_pageobjects.UICatalog; /**<==!!*/

public class UICatalogApp extends IOSApp {

	@Static
	public UICatalog uiCatalog;
	
	@Static
	@ExpectedURL(regExp = ".apple.com")
	@DefaultPageIndex(index = 0)
	@ExpectedPageTitle(regExp = "Apple")

	@ExpectedContext(regExp = MobileContextNamePatterns.WEBVIEW)
	public AppleCom appleCom;
	
	public UICatalogApp(MobileScreen context) {
		super(context);
	}

}
