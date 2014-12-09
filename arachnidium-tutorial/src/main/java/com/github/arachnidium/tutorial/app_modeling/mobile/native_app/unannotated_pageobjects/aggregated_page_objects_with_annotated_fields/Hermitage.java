package com.github.arachnidium.tutorial.app_modeling.mobile.native_app.unannotated_pageobjects.aggregated_page_objects_with_annotated_fields;

import io.appium.java_client.pagefactory.AndroidFindBy;

import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.core.fluenthandle.IHowToGetHandle;
import com.github.arachnidium.model.common.Application;
import com.github.arachnidium.model.common.Static;
import com.github.arachnidium.model.support.annotations.DefaultContextIndex;
//import com.github.arachnidium.model.support.annotations.DefaultPageIndex;
import com.github.arachnidium.model.support.annotations.ExpectedAndroidActivity;
import com.github.arachnidium.model.support.annotations.ExpectedContext;
//import com.github.arachnidium.model.support.annotations.ExpectedPageTitle;
//import com.github.arachnidium.model.support.annotations.ExpectedURL;
import com.github.arachnidium.model.support.annotations.MobileContextNamePatterns;
import com.github.arachnidium.model.support.annotations.rootelements.RootAndroidElement;
import com.github.arachnidium.model.support.annotations.rootelements.RootElement;
import com.github.arachnidium.tutorial.app_modeling.mobile.native_app.
		unannotated_pageobjects.HermitageMuseumMainScreen; /**<==!!!*/
import com.github.arachnidium.tutorial.app_modeling.mobile.native_app.
		unannotated_pageobjects.HermitageMuseumQuickGuide; /**<==!!!*/
import com.github.arachnidium.tutorial.app_modeling.mobile.native_app.
		unannotated_pageobjects.InformationAboutTickets; /**<==!!!*/
import com.github.arachnidium.tutorial.app_modeling.mobile.native_app.
       unannotated_pageobjects.TheHistoryOfTheHermitage; /**<==!!!*/

public class Hermitage extends Application<Handle, IHowToGetHandle> {
	/**If there is only interaction with mobile app then possible options are below: */
	//public class Hermitage extends MobileApplication{ 
	//public class Hermitage extends AndroidApp { 
	//public class Hermitage extends IOSApp { 
	@Static  /**<== This annotation means that it is content which always/very frequently present
	and frequently used*/
	public HermitageMuseumQuickGuide hermitageMuseumQuickGuide; /**It is not necessary to 
	make fields public. You can make them private and implement public getter 
	for each one. I made that just for visibility*/
	
	@Static
	public HermitageMuseumMainScreen hermitageMuseumMainScreen;
	
	@Static /**<== Presence of @Static annotation activates handlers of annotations below: */
	/**------------------------------------------------------------------------------------*/
	/**
	 * This field can be annotated by {@link ExpectedURL}, {@link ExpectedPageTitle}
	 * or {@link DefaultPageIndex} if it would needs to check the interaction with
	 * browser. So, lets imagine that these annotations are present here :)
	 * 
	 * But... all these are ignored when we need to interact with native Android/iOS
	 * content. See below.   
	 */
	/**------------------------------------------------------------------------------------*/
	@ExpectedContext(regExp = MobileContextNamePatterns.NATIVE) /**Here is the name of the expected mobile application context - 
	NATIVE_APP, name of WebView*/
	/**
	 * If class of annotated field is the subclass of {@link NativeContent} then there is no need to annotate field 
	 * by @ExpectedContext(regExp = MobileContextNamePatterns.NATIVE) because
	 * {@link NativeContent} is already annotated that. :)  
	 */	
	@ExpectedAndroidActivity(regExp = "SomeActivity")  /**<= Here are possible activities for this screen or its part.*/
	@ExpectedAndroidActivity(regExp = "AcSingleFragment_") /**Each one @ExpectedAndroidActivity declaration is one more possible activity
	of Android app. It is Android-only parameter which is ignored when here is iOS*/
	/**------------------------------------------------------------------------------------*/
	@DefaultContextIndex(index = 0) /**<= Sometimes it is useful to define the index of the context. For example, 
	it could be useful when here are few WEBVIEW's. This declaration is just example*/
	/**------------------------------------------------------------------------------------*/
	@RootElement(chain = {@FindBy(id = "someRootId")}) /**<--It is the demonstration of the ability
	to define the default root element for the whole page object. All declared elements will be found from this element 
	instead of WebDriver. We can define it as a chain of searches*/
	/**We can define it as a set of possible element
	chains. */
	/**Lets imagine that there is the similar browser UI.*/ 
	@RootAndroidElement(chain = { /**It is possible to define the root element especially for Android and iOS*/
			@AndroidFindBy(className = "android.widget.FrameLayout"),
			@AndroidFindBy(id = "android:id/action_bar_overlay_layout")
			}/**<==  It will be used by Android and will be active right now!!!*/
	)
	public TheHistoryOfTheHermitage theHistoryOfTheHermitage;
	
	@Static
	@ExpectedContext(regExp = MobileContextNamePatterns.NATIVE) 
	@ExpectedAndroidActivity(regExp = "SomeActivity")
	@ExpectedAndroidActivity(regExp = "AcSingleFragment_")
	@DefaultContextIndex(index = 0)
	@RootElement(chain = {@FindBy(id = "someRootId")})
	@RootAndroidElement(chain = {
			@AndroidFindBy(className = "android.widget.FrameLayout"),
			@AndroidFindBy(id = "android:id/action_bar_overlay_layout")
			}
	)
	public InformationAboutTickets informationAboutTickets;
	
	protected Hermitage(Handle handle) {
		super(handle);
	}

}
