package com.github.arachnidium.model.mobile;

import java.net.URL;

import org.openqa.selenium.Rotatable;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.interactions.TouchScreen;
import org.openqa.selenium.interactions.internal.Coordinates;

import com.github.arachnidium.core.MobileScreen;
import com.github.arachnidium.model.common.FunctionalPart;
import com.github.arachnidium.model.support.HowToGetByFrames;
import com.github.arachnidium.model.support.annotations.classdeclaration.IfMobileContext;

/**
 *Can be used to describe a single mobile app web view or its fragment
 */
@IfMobileContext(regExp = "WEBVIEW")
public class WebViewContent extends FunctionalPart<MobileScreen> implements Navigation, TouchScreen, Rotatable{

	/**
	 * @see {@link FunctionalPart#FunctionalPart(FunctionalPart)}
	 */
	protected WebViewContent(WebViewContent parent) {
		super(parent);
	}
	
	/**
	 * @see {@link FunctionalPart#FunctionalPart(FunctionalPart, HowToGetByFrames)}
	 */
	protected WebViewContent(WebViewContent parent, HowToGetByFrames howToGetByFrames) {
		super(parent, howToGetByFrames);
	}	
	
	/**
	 * @see {@link FunctionalPart#FunctionalPart(com.github.arachnidium.core.Handle)
	 */	
	protected WebViewContent(MobileScreen context){
		super(context);
	}
	
	/**
	 * @see {@link FunctionalPart#FunctionalPart(com.github.arachnidium.core.Handle, HowToGetByFrames))
	 */
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

	@Override
	@InteractiveMethod
	@Deprecated
	/**
	 * This method is deprecated because there is unimplemented command.
	 * This invocation throws<br/> 
	 * <code>org.openqa.selenium.WebDriverException: unimplemented command: session/31ec041f19315109f3c3b5773c4a5ec7/touch/doubleclick</code> 
	 */
	public void doubleTap(Coordinates where) {
		touch.doubleTap(where);		
	}

	@Override
	@InteractiveMethod
	public void down(int x, int y) {
		touch.down(x, y);		
	}

	@Override
	@InteractiveMethod
	public void flick(Coordinates where, int xOffset, int yOffset, int speed) {
		touch.flick(where, xOffset, yOffset, speed);		
	}

	@Override
	@InteractiveMethod
	public void flick(int xSpeed, int ySpeed) {
		touch.flick(xSpeed, ySpeed);		
	}

	@Override
	@InteractiveMethod
	public void longPress(Coordinates where) {
		touch.longPress(where);		
	}

	@Override
	@InteractiveMethod
	public void move(int x, int y) {
		touch.move(x, y);		
	}

	/**
	 * This method is deprecated because there is unimplemented command.
	 * This invocation throws<br/> 
	 * <code> org.openqa.selenium.WebDriverException: unimplemented command: session/f64cc4e818e1dd00a88ae9c93c5d78e7/touch/scroll</code> 
	 */
	@Override
	@InteractiveMethod
	@Deprecated
	public void scroll(Coordinates where, int xOffset, int yOffset) {
		touch.scroll(xOffset, yOffset);		
	}

	/**
	 * This method is deprecated because there is unimplemented command.
	 * This invocation throws<br/> 
	 * <code> org.openqa.selenium.WebDriverException: unimplemented command: session/f64cc4e818e1dd00a88ae9c93c5d78e7/touch/scroll</code> 
	 */
	@Override
	@InteractiveMethod
	@Deprecated
	public void scroll(int xOffset, int yOffset) {
		touch.scroll(xOffset, yOffset);
		
	}

	@Override
	@InteractiveMethod
	public void singleTap(Coordinates where) {
		touch.singleTap(where);		
	}

	@Override
	@InteractiveMethod
	public void up(int x, int y) {
		touch.up(x, y);		
	}

	@Override
	public ScreenOrientation getOrientation() {
		return rotator.getOrientation();
	}

	@Override
	public void rotate(ScreenOrientation orientation) {
		rotator.rotate(orientation);		
	}

}
