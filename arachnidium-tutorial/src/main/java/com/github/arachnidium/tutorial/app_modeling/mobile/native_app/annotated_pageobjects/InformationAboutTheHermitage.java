package com.github.arachnidium.tutorial.app_modeling.mobile.native_app.annotated_pageobjects;

import io.appium.java_client.pagefactory.AndroidFindBy;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.FindBy;


import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;
import com.github.arachnidium.model.mobile.NativeContent;
import com.github.arachnidium.model.support.annotations.DefaultContextIndex;
import com.github.arachnidium.model.support.annotations.DefaultPageIndex;
import com.github.arachnidium.model.support.annotations.ExpectedAndroidActivity;
import com.github.arachnidium.model.support.annotations.ExpectedContext;
import com.github.arachnidium.model.support.annotations.ExpectedPageTitle;
import com.github.arachnidium.model.support.annotations.ExpectedURL;
import com.github.arachnidium.model.support.annotations.MobileContextNamePatterns;
import com.github.arachnidium.model.support.annotations.rootelements.RootAndroidElement;
import com.github.arachnidium.model.support.annotations.rootelements.RootElement;

/**
 * This class can be annotated by {@link ExpectedURL}, {@link ExpectedPageTitle}
 * or {@link DefaultPageIndex} if it would needs to check the interaction with
 * browser. So, lets imagine that these annotations are present here :)
 * 
 * But... all these are ignored when we need to interact with native Android/iOS
 * content. See below.   
 */
@ExpectedContext(regExp = MobileContextNamePatterns.NATIVE) /**Here is the name of the expected mobile application context - 
NATIVE_APP, name of WebView*/
/**Declaration is applied to subclasses till they are annotated by @ExpectedContext with 
another values. Also if the class is going to be instantiated by {@link Application#getPart(Class, com.github.arachnidium.core.fluenthandle.IHowToGetHandle)}
(where IHowToGetHandle is a {@link HowToGetMobileScreen} instance) then 
the values contained by given strategy will be used instead of declared by annotation*/
/**
 * If {@link NativeContent} is extended then there is no need to annotate class 
 * by @ExpectedContext(regExp = MobileContextNamePatterns.NATIVE) because
 * {@link NativeContent} is already annotated that. :)  
 */
@ExpectedAndroidActivity(regExp = "SomeActivity") /**<= Here are possible activities for this screen or its part.*/
@ExpectedAndroidActivity(regExp = "AcSingleFragment_")/**Each one @ExpectedAndroidActivity declaration is one more possible activity
of Android app. It is Android-only parameter which is ignored when here is iOS*/
/**Declaration is applied to subclasses till they are annotated by @ExpectedAndroidActivity with 
another values. Also if the class is going to be instantiated by {@link Application#getPart(Class, com.github.arachnidium.core.fluenthandle.IHowToGetHandle)}
(where IHowToGetHandle is a {@link HowToGetMobileScreen} instance) then 
the values contained by given strategy will be used instead of declared by annotations*/

@DefaultContextIndex(index = 0) /**<= Sometimes it is useful to define the index of the context. For example, 
it could be useful when here are few WEBVIEW's. This declaration is just example*/

@RootElement(chain = {@FindBy(id = "someRootId")})   /**<--It is the demonstration of the ability
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
/**Declaration is applied to subclasses till they are annotated by @RootElement/@RootAndroidElement/@RootIOSElement 
 * with another values. Also if the class is going to be instantiated by {@link FunctionalPart#getPart(Class, By)} or
 * {@link Application#getPart(Class, By)} then the given By-strategy will be used instead of declared by annotations*/
@SuppressWarnings("unused")
public class InformationAboutTheHermitage extends FunctionalPart<Handle> { /** <==
	 * Below is an available option if we want the interaction with
	 * only native mobile client
	 * 
	 *  ...
	 *  import com.github.arachnidium.model.mobile.NativeContent;
	 *  ....
	 * 
	 *  public class InformationAboutTheHermitage extends NativeContent{
	 *  ...
	 *  
	 */
	
	@FindBy(id = "some_title")/**<== Lets imagine that there is the similar 
	browser UI.*/
	@AndroidFindBy(id = "android:id/action_bar_title") /**<==  It will 
	be used by Android*/
	private RemoteWebElement title;
	
	@FindBy(id = "some_back_id")
	@AndroidFindBy(id = "android:id/up")
	private RemoteWebElement back;

	protected InformationAboutTheHermitage(Handle handle, By by) {
		super(handle, by);
	}
	
	/**
	 * Below is an available option if we want the interaction with
	 * only native mobile client
	 * 
	 *  ...
	 *  import com.github.arachnidium.model.mobile.NativeContent;
	 *  import com.github.arachnidium.core.MobileScreen;
	 *  ....
	 * 
	 *	protected InformationAboutTheHermitage(MobileScreen screen, By by) {
	 *		super(screen, by);
	 *	}
	 *  
	 */	
	
	@InteractiveMethod
	public String getTitle(){
		return title.getText();
	}
	
	@InteractiveMethod /**<-- This annotations is useful for methods which simulate
	some interaction. By default the presence of it means that Webdriver should be focused
	on the window/context and switched to the required frame if currently this content is 
	placed inside iframe. All this will have been done before the annotated method
	will be invoked*/
	public void back(){
		back.click();
	}		
	/**Some more interesting things could be implemented below*/
	/**.................................*/
}
