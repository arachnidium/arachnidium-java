package com.github.arachnidium.model.mobile;

import java.net.URL;

import org.openqa.selenium.WebDriver.Navigation;

import com.github.arachnidium.core.MobileScreen;
import com.github.arachnidium.model.common.FunctionalPart;
import com.github.arachnidium.model.support.HowToGetByFrames;
import com.github.arachnidium.model.support.annotations.classdeclaration.IfMobileContext;

@IfMobileContext(regExp = "WEBVIEW")
public abstract class WebViewContent extends FunctionalPart<MobileScreen> implements Navigation{

	protected WebViewContent(WebViewContent parent) {
		super(parent);
	}
	
	protected WebViewContent(WebViewContent parent, HowToGetByFrames howToGetByFrames) {
		super(parent, howToGetByFrames);
	}	
	
	protected WebViewContent(MobileScreen context){
		super(context);
	}
	
	protected WebViewContent(MobileScreen context, HowToGetByFrames howToGetByFrames){
		super(context, howToGetByFrames);
	}

	@InteractiveMethod
	@Override
	public void back() {
		getWrappedDriver().navigate().back();		
	}

	@InteractiveMethod
	@Override
	public void forward() {
		getWrappedDriver().navigate().forward();		
	}

	@InteractiveMethod
	@Override
	public void to(String url) {
		getWrappedDriver().navigate().to(url);		
	}

	@InteractiveMethod
	@Override
	public void to(URL url) {
		getWrappedDriver().navigate().to(url);
		
	}

	@InteractiveMethod
	@Override
	public void refresh() {
		getWrappedDriver().navigate().refresh();		
	}
	
	@InteractiveMethod
	public String getCurrentUrl(){
		return getWrappedDriver().getCurrentUrl();
	}

}
