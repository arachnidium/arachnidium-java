/**
 *
 */
package com.github.arachnidium.model.common;

import java.awt.Color;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import com.github.arachnidium.util.logging.Log;
import com.github.arachnidium.util.logging.eLogColors;

import io.appium.java_client.pagefactory.TimeOutDuration;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

import com.github.arachnidium.core.BrowserWindow;
import com.github.arachnidium.core.Handle;
import com.github.arachnidium.core.HowToGetPage;
import com.github.arachnidium.core.HowToGetMobileScreen;
import com.github.arachnidium.core.MobileScreen;
import com.github.arachnidium.core.components.common.Ime;
import com.github.arachnidium.core.components.common.ScriptExecutor;
import com.github.arachnidium.core.components.common.TimeOut;
import com.github.arachnidium.core.fluenthandle.IHowToGetHandle;
import com.github.arachnidium.core.highlighting.IWebElementHighlighter;
import com.github.arachnidium.core.highlighting.WebElementHighLighter;
import com.github.arachnidium.core.interfaces.ISwitchesToItself;
import com.github.arachnidium.core.interfaces.ITakesPictureOfItSelf;
import com.github.arachnidium.model.abstractions.ModelObject;
import com.github.arachnidium.model.interfaces.IDecomposable;
import com.github.arachnidium.core.HowToGetByFrames;

/**
 * This class is for description of browser or mobile UI or the fragment of this
 * UI. It is assumed that this description can be reusable.
 *
 * Interaction and behavior should describe subclasses of this.
 *
 */
public abstract class FunctionalPart<S extends Handle> extends ModelObject<S>
		implements ITakesPictureOfItSelf, ISwitchesToItself, SearchContext {

	/**
	 * This is used when general time out is not suitable for method that
	 * performs interaction. Defined time out will be applied before method is
	 * invoked.<br\>
	 * <br\>
	 * This annotation is ignored when {@link InteractiveMethod} is not present.
	 */
	@Target(value = ElementType.METHOD)
	@Retention(value = RetentionPolicy.RUNTIME)
	protected static @interface WithImplicitlyWait {
		/**
		 * @return customized value of timeout
		 */
		long timeOut();

		/**
		 * @return customized time unit
		 */
		TimeUnit timeUnit() default TimeUnit.SECONDS;
	}

	/**
	 * This annotation is useful when there is need to interact with more than
	 * one browser window/mobile context at the same time. Also it is convenient
	 * when described UI is inside frame
	 *
	 * The presence of this annotation means that {@link WebDriver} will be
	 * switched to window/context and to frame automatically
	 */
	@Target(value = ElementType.METHOD)
	@Retention(value = RetentionPolicy.RUNTIME)
	protected static @interface InteractiveMethod {

	}
	
	// parent application
	protected Application<?, ?> application;
	protected final Ime ime;
	private final TimeOut timeOut;
	final TimeOutDuration timeOutDuration;
	protected final ScriptExecutor scriptExecutor; // executes given javaScript
	

	/**
	 * This constructor should present when an instance of the class is going to
	 * be got from the given browser window or mobile context.<br/>
	 * <br/>
	 * The described piece of UI is inside frame (it is actual for browser and
	 * mobile hybrid apps). Path to desired frame is specified by
	 * {@link HowToGetByFrames} instance. <br/>
	 * <br/>
	 * There is known root {@link WebElement} defined {@link By} locator
	 * strategy
	 *
	 * @param handle
	 *            is the given browser window or mobile context
	 *
	 * @param path
	 *            is a path to frame which is specified by
	 *            {@link HowToGetByFrames}
	 * @param by
	 *            It is {@link By} strategy which is used to get the root
	 *            element
	 *
	 * @see Application
	 * @see IHowToGetHandle
	 * @see HowToGetPage
	 * @see HowToGetMobileScreen
	 * @see Handle
	 * @see BrowserWindow
	 * @see MobileScreen
	 * @see HowToGetByFrames
	 * @see By
	 */
	protected FunctionalPart(S handle) {
		super(handle);
		timeOut = handle.driverEncapsulation.getTimeOut();
		long primaryTimeOut = timeOut.getImplicitlyWaitTimeOut();
		TimeUnit primaryTimeUnit = timeOut.getImplicitlyWaitTimeUnit();

		scriptExecutor = getComponent(ScriptExecutor.class);
	    ime = getComponent(Ime.class);
	    timeOutDuration = new TimeOutDuration(primaryTimeOut, primaryTimeUnit);
	    load();
	}

	/**
	 * @see com.github.arachnidium.model.abstractions.ModelObject#addChild(com.github.arachnidium.model.abstractions.ModelObject)
	 */
	@Override
	protected final void addChild(ModelObject<?> child) {
		super.addChild(child);
		FunctionalPart<?> childPart = (FunctionalPart<?>) child;
		childPart.application = this.application;
	}

	private <T extends IDecomposable> T get(Class<T> partClass,
			Handle h) {
		T result = DecompositionUtil.get(partClass,
				new Object[]{h});
		addChild((ModelObject<?>) result);
		DecompositionUtil.populateFieldsWhichAreDecomposable((ModelObject<?>) result);
		return result;
	}

	/**
	 * This method returns another UI description (child). It is assumed that
	 * current UI is more generalized and the child is more specific. <br/>
	 * <br/>
	 * It means, that required UI or fragment of UI is on the same browser
	 * window/mobile screen as the current. Also "child" is inside the same
	 * frame as the current (it is actual for browser and mobile hybrid apps).
	 *
	 * @see com.github.arachnidium.model.abstractions.ModelObject#getPart(java.lang.Class)
	 *
	 * @see IDecomposable#getPart(Class)
	 */
	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass) {
		return get(partClass, handle);
	}

	/**
	 * This method returns another UI description (child). It is assumed that
	 * current UI is more generalized and the child is more specific. <br/>
	 * <br/>
	 * It means, that required UI or fragment of UI is on the same browser
	 * window/mobile screen as the current. Also "child" is inside the same
	 * frame as the current (it is actual for browser and mobile hybrid apps). <br/>
	 * <br/>
	 * <br/>
	 * The required piece of UI is inside frame (it is actual for browser and
	 * mobile hybrid apps). Path to desired frame is specified by
	 * {@link HowToGetByFrames} instance. <br/>
	 *
	 *
	 * @see com.github.arachnidium.model.abstractions.ModelObject#getPart(Class,
	 *      HowToGetByFrames)
	 *
	 * @see IDecomposable#getPart(Class, HowToGetByFrames)
	 *
	 * @see HowToGetByFrames
	 */
	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			HowToGetByFrames path) {
		return get(partClass, application.manager.getHandle(handle, path));
	}

	private IWebElementHighlighter getHighlighter() {
		WebElementHighLighter highLighter = new WebElementHighLighter();
		highLighter.resetAccordingTo(getWebDriverEncapsulation()
				.getWrappedConfiguration());
		return highLighter;
	}

	/**
	 * Highlights HTML element by given color, creates {@link Log} message with
	 * {@link Level#FINE} and narrative text, optionally takes a screen shot and
	 * attaches to {@link Log}
	 *
	 * @param element
	 *            to be highlighted
	 * @param highlight
	 *            is an used color
	 * @param comment
	 *            is a narrative message text
	 */
	@InteractiveMethod
	public void highlightAsFine(WebElement element, Color highlight,
			String comment) {
		getHighlighter().highlightAsFine(getWrappedDriver(), element,
				highlight, comment);
	}

	/**
	 * Highlights HTML element by given color, creates {@link Log} message with
	 * {@link Level#FINE} and narrative text, optionally takes a screen shot and
	 * attaches to {@link Log}
	 *
	 * {@link eLogColors#DEBUGCOLOR} is used.
	 *
	 * @param element
	 *            to be highlighted
	 * @param comment
	 *            is a narrative message text
	 */
	@InteractiveMethod
	public void highlightAsFine(WebElement element, String comment) {
		getHighlighter().highlightAsFine(getWrappedDriver(), element, comment);
	}

	/**
	 * Highlights HTML element by given color, creates {@link Log} message with
	 * {@link Level#INFO} and narrative text, optionally takes a screen shot and
	 * attaches to {@link Log}
	 *
	 * @param element
	 *            to be highlighted
	 * @param highlight
	 *            is an used color
	 * @param comment
	 *            is a narrative message text
	 */
	@InteractiveMethod
	public void highlightAsInfo(WebElement element, Color highlight,
			String comment) {
		getHighlighter().highlightAsInfo(getWrappedDriver(), element,
				highlight, comment);
	}

	/**
	 * Highlights HTML element by given color, creates {@link Log} message with
	 * {@link Level#INFO} and narrative text, optionally takes a screen shot and
	 * attaches to {@link Log}
	 *
	 * {@link eLogColors#CORRECTSTATECOLOR} is used.
	 *
	 * @param element
	 *            to be highlighted
	 * @param comment
	 *            is a narrative message text
	 */
	@InteractiveMethod
	public void highlightAsInfo(WebElement element, String comment) {
		getHighlighter().highlightAsInfo(getWrappedDriver(), element, comment);
	}

	/**
	 * Highlights HTML element by given color, creates {@link Log} message with
	 * {@link Level#SEVERE} and narrative text, optionally takes a screen shot
	 * and attaches to {@link Log}
	 *
	 * @param element
	 *            to be highlighted
	 * @param highlight
	 *            is an used color
	 * @param comment
	 *            is a narrative message text
	 */
	@InteractiveMethod
	public void highlightAsSevere(WebElement element, Color highlight,
			String comment) {
		getHighlighter().highlightAsSevere(getWrappedDriver(), element,
				highlight, comment);
	}

	/**
	 * Highlights HTML element by given color, creates {@link Log} message with
	 * {@link Level#SEVERE} and narrative text, optionally takes a screen shot
	 * and attaches to {@link Log}
	 *
	 * {@link eLogColors#SEVERESTATECOLOR} is used.
	 *
	 * @param element
	 *            to be highlighted
	 * @param comment
	 *            is a narrative message text
	 */
	@InteractiveMethod
	public void highlightAsSevere(WebElement element, String comment) {
		getHighlighter()
				.highlightAsSevere(getWrappedDriver(), element, comment);
	}

	/**
	 * Highlights HTML element by given color, creates {@link Log} message with
	 * {@link Level#WARNING} and narrative text, optionally takes a screen shot
	 * and attaches to {@link Log}
	 *
	 * @param element
	 *            to be highlighted
	 * @param highlight
	 *            is an used color
	 * @param comment
	 *            is a narrative message text
	 */
	@InteractiveMethod
	public void highlightAsWarning(WebElement element, Color highlight,
			String comment) {
		getHighlighter().highlightAsWarning(getWrappedDriver(), element,
				highlight, comment);
	}

	/**
	 * Highlights HTML element by given color, creates {@link Log} message with
	 * {@link Level#WARNING} and narrative text, optionally takes a screen shot
	 * and attaches to {@link Log}
	 *
	 * {@link eLogColors#WARNSTATECOLOR} is used.
	 *
	 * @param element
	 *            to be highlighted
	 * @param comment
	 *            is a narrative message text
	 */
	@InteractiveMethod
	public void highlightAsWarning(WebElement element, String comment) {
		getHighlighter().highlightAsWarning(getWrappedDriver(), element,
				comment);
	}

	/**
	 * Instantiates declared {@link WebElement} fields using {@link PageFactory}
	 * and {@link DefaultDecorator}
	 * This method can be overridden if it is needed
	 */
	protected void load() {
		PageFactory.initElements(new DefaultDecorator(handle, 
				this, timeOutDuration), this);
	}

	/**
	 * Instantiates declared {@link WebElement} fields using {@link PageFactory}
	 * and customized {@link FieldDecorator}
	 *
	 * @param decorator
	 *            is an instance of the customized {@link FieldDecorator}
	 */
	protected void load(FieldDecorator decorator) {
		PageFactory.initElements(decorator, this);
		load();
	}

	/**
	 * Instantiates declared {@link WebElement} fields using {@link PageFactory}
	 * and customized {@link ElementLocatorFactory}
	 *
	 * @param factoryis
	 *            an instance of the customized {@link ElementLocatorFactory}
	 */
	protected void load(ElementLocatorFactory factory) {
		PageFactory.initElements(factory, this);
		load();
	}

	/**
	 * Performs focus on the described UI.
	 *
	 * First of all it performs the switching to the window/mobile context where
	 * UI is existing now. If described UI is inside frame this method performs
	 * switching from one frame to another
	 */
	@Override
	public synchronized void switchToMe() {
		handle.switchToMe();
	}

	/**
	 * takes screenshots and attaches it to log messages. {@link Level#FINE}
	 *
	 * @see com.github.arachnidium.core.interfaces.ITakesPictureOfItSelf#takeAPictureOfAFine(java.lang.String)
	 */
	@Override
	public void takeAPictureOfAFine(String comment) {
		handle.takeAPictureOfAFine(comment);
	}

	/**
	 * takes screenshots and attache it to log messages. {@link Level#INFO}
	 *
	 * @see com.github.arachnidium.core.interfaces.ITakesPictureOfItSelf#takeAPictureOfAnInfo(java.lang.String)
	 */
	@Override
	public void takeAPictureOfAnInfo(String comment) {
		handle.takeAPictureOfAnInfo(comment);
	}

	/**
	 * takes screenshots and attache it to log messages. {@link Level#SEVERE}
	 *
	 * @see com.github.arachnidium.core.interfaces.ITakesPictureOfItSelf#takeAPictureOfAnInfo(java.lang.String)
	 */
	@Override
	public void takeAPictureOfASevere(String comment) {
		handle.takeAPictureOfASevere(comment);
	}

	/**
	 * takes screenshots and attache it to log messages. {@link Level#WARNING}
	 *
	 * @see com.github.arachnidium.core.interfaces.ITakesPictureOfItSelf#takeAPictureOfAnInfo(java.lang.String)
	 */
	@Override
	public void takeAPictureOfAWarning(String comment) {
		handle.takeAPictureOfAWarning(comment);
	}

	/**
	 * @return An instance of the current {@link Application}
	 */
	public Application<? extends Handle, ? extends IHowToGetHandle> getApplication() {
		return application;
	}

	TimeOut getTimeOut() {
		if (timeOut == null) {
			return getWebDriverEncapsulation().getTimeOut();
		}
		return timeOut;
	}

	/**
	 * This method returns another UI description (child). It is assumed that
	 * current UI is more generalized and the child is more specific. <br/>
	 * <br/>
	 * It means, that required UI or fragment of UI is on the same browser
	 * window/mobile screen as the current. Also "child" is inside the same
	 * frame as the current (it is actual for browser and mobile hybrid apps). <br/>
	 * <br/>
	 * <br/>
	 * The required piece of UI is inside frame (it is actual for browser and
	 * mobile hybrid apps). Path to desired frame is specified by
	 * {@link HowToGetByFrames} instance. <br/>
	 * <br/>
	 * The root {@link WebElement} where UI is placed is known. The given
	 * {@link By} defines the locator strategy of the searching for the root
	 * element.
	 *
	 * @see com.github.arachnidium.model.abstractions.ModelObject#getPart(Class,
	 *      HowToGetByFrames)
	 *
	 * @see IDecomposable#getPart(Class, HowToGetByFrames)
	 *
	 * @see HowToGetByFrames
	 */
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			HowToGetByFrames path, By by) {
		return get(partClass, application.manager.getHandle(handle, by, path));
	}

	/**
	 * This method returns another UI description (child). It is assumed that
	 * current UI is more generalized and the child is more specific. <br/>
	 * <br/>
	 * It means, that required UI or fragment of UI is on the same browser
	 * window/mobile screen as the current. Also "child" is inside the same
	 * frame as the current (it is actual for browser and mobile hybrid apps). <br/>
	 * <br/>
	 * The root {@link WebElement} where UI is placed is known. The given
	 * {@link By} defines the locator strategy of the searching for the root
	 * element.
	 */
	public <T extends IDecomposable> T getPart(Class<T> partClass, By by) {
		return get(partClass, application.manager.getHandle(handle, by));
	}
	
	@Override
	public WebElement findElement(By by){
		return handle.findElement(by);
	}
	
	@Override
	public List<WebElement> findElements(By by){
		return handle.findElements(by);
	}
}
