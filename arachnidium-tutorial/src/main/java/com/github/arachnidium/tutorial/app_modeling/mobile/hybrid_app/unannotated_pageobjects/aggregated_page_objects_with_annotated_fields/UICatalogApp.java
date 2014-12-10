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
		unannotated_pageobjects.AppleCom; /**<==!!*/
import com.github.arachnidium.tutorial.app_modeling.mobile.hybrid_app.
		unannotated_pageobjects.UICatalog; /**<==!!*/

public class UICatalogApp extends IOSApp {

	@Static /**<== This annotation means that it is content which always/very frequently present
	and frequently used*/
	public UICatalog uiCatalog; /**It is not necessary to 
	make fields public. You can make them private and implement public getter 
	for each one. I made that just for visibility*/
	
	@Static
	@ExpectedURL(regExp = ".apple.com") /**<== Possible URLs that should be loaded can be declared by annotations*/ 
	/**Each one @ExpectedURL means one more possible web address*/
	@DefaultPageIndex(index = 0) /**<-Also it is possible to define the default expected window/tab index*/
	@ExpectedPageTitle(regExp = "Apple") /**and page title*/

	@ExpectedContext(regExp = MobileContextNamePatterns.WEBVIEW) /**Here is the name of the expected mobile application context - 
	NATIVE_APP, name of WebView*/
	/**
	 * If class of annotated field is the subclass of {@link WebViewContent} then there is no need to annotate field 
	 * by @ExpectedContext(regExp = MobileContextNamePatterns.WEBVIEW) because
	 * {@link WebViewContent} is already annotated that. :)  	 */	
	public AppleCom appleCom;
	
	public UICatalogApp(MobileScreen context) {
		super(context);
	}

}
