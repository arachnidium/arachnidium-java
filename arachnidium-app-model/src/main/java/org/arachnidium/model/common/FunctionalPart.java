/**
 *
 */
package org.arachnidium.model.common;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import java.awt.Color;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.arachnidium.core.Handle;
import org.arachnidium.core.components.common.Ime;
import org.arachnidium.core.components.common.Interaction;
import org.arachnidium.core.components.common.TimeOut;
import org.arachnidium.core.fluenthandle.IHowToGetHandle;
import org.arachnidium.core.highlighting.IWebElementHighlighter;
import org.arachnidium.core.highlighting.WebElementHighLighter;
import org.arachnidium.core.interfaces.ISwitchesToItself;
import org.arachnidium.core.interfaces.ITakesPictureOfItSelf;
import org.arachnidium.model.abstractions.ModelObject;
import org.arachnidium.model.interfaces.IDecomposable;
import org.arachnidium.model.support.HowToGetByFrames;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

/**
 * @author s.tihomirov It describes simple web page or mobile app context or
 *         their fragment
 */
public abstract class FunctionalPart<S extends Handle> extends ModelObject<S> implements ITakesPictureOfItSelf, ISwitchesToItself {

	/**
	 * @author s.tihomirov
	 *
	 */
	@Target(value = ElementType.METHOD)
	@Retention(value = RetentionPolicy.RUNTIME)
	protected static @interface InteractiveMethod {

	}

	protected FunctionalPart<?> parent; // parent test object
	// page object is created by specified entity
	protected Application<?,?> application;
	protected final Interaction interaction;
	protected final Ime ime;
	protected final TimeOut timeOuts;
	protected final HowToGetByFrames pathStrategy;

	// constructs from another page object
	@SuppressWarnings("unchecked")
	protected FunctionalPart(FunctionalPart<?> parent) {
		this((S) parent.handle, new HowToGetByFrames());
	}
	
	@SuppressWarnings("unchecked")
	protected FunctionalPart(FunctionalPart<?> parent, HowToGetByFrames path) {
		this((S) parent.handle, path);
		parent.addChild(this);
	}

	protected FunctionalPart(S handle) {
		this(handle, new HowToGetByFrames());
	}

	protected FunctionalPart(S handle, HowToGetByFrames path) {
		super(handle);
		this.pathStrategy = path;
		timeOuts = getWebDriverEncapsulation().getTimeOut();
		interaction = getComponent(Interaction.class);
		ime =         getComponent(Ime.class);
	}

	@Override
	protected final void addChild(ModelObject<?> child) {
		super.addChild(child);
		FunctionalPart<?> childPart = (FunctionalPart<?>) child;
		childPart.parent = this;
		childPart.application = this.application;
	}

	/**
	 * if handle disappeared all objects that are placed on this will be
	 * destroyed. I think it should work this way
	 */
	@Override
	public void destroy() {
		if (!handle.exists())
			handle.destroy();
		super.destroy();
		return;
	}

	// - simple constructor
	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass) {
		Object[] values = new Object[] { this };
		return DefaultApplicationFactory.get(partClass, values);
	}

	// - with specified frame index
	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			HowToGetByFrames pathStrategy) {
		Object[] values = new Object[] { this, pathStrategy };
		return DefaultApplicationFactory.get(partClass, values);
	}

	private IWebElementHighlighter getHighlighter(){
		WebElementHighLighter highLighter = new WebElementHighLighter();
		highLighter.resetAccordingTo(getWebDriverEncapsulation().
				getWrappedConfiguration());
		return highLighter;
	}
	
	@InteractiveMethod
	public void highlightAsFine(WebElement element, Color highlight,
			String comment) {
		getHighlighter().highlightAsFine(getWrappedDriver(),
				element, highlight, comment);
	}

	@InteractiveMethod
	public void highlightAsFine(WebElement element, String comment) {
		getHighlighter().highlightAsFine(getWrappedDriver(),
				element, comment);
	}

	@InteractiveMethod
	public void highlightAsInfo(WebElement element, Color highlight,
			String comment) {
		getHighlighter().highlightAsInfo(getWrappedDriver(),
				element, highlight, comment);
	}

	@InteractiveMethod
	public void highlightAsInfo(WebElement element, String comment) {
		getHighlighter().highlightAsInfo(getWrappedDriver(),
				element, comment);
	}

	@InteractiveMethod
	public void highlightAsSevere(WebElement element, Color highlight,
			String comment) {
		getHighlighter().highlightAsSevere(getWrappedDriver(),
				element, highlight, comment);
	}

	@InteractiveMethod
	public void highlightAsSevere(WebElement element, String comment) {
		getHighlighter().highlightAsSevere(getWrappedDriver(),
				element, comment);
	}

	@InteractiveMethod
	public void highlightAsWarning(WebElement element, Color highlight,
			String comment) {
		getHighlighter().highlightAsWarning(getWrappedDriver(),
				element, highlight, comment);
	}

	@InteractiveMethod
	public void highlightAsWarning(WebElement element, String comment) {
		getHighlighter().highlightAsWarning(getWrappedDriver(),
				element, comment);
	}

	// The method below simply loads page factory
	protected void load() {
		PageFactory.initElements(new AppiumFieldDecorator(
				getWrappedDriver(),
				timeOuts.getImplicitlyWaitTimeOut(),
				timeOuts.getImplicitlyWaitTimeUnit()), this);
	}

	protected void load(FieldDecorator decorator) {
		PageFactory.initElements(decorator, this);
	}

	protected void load(ElementLocatorFactory factory) {
		PageFactory.initElements(factory, this);
	}

	/**
	 * switches to object this method can be overridden
	 */
	@Override
	public synchronized void switchToMe() {
		// firstly we should switch parent browser window on
		if (parent != null)
			parent.switchToMe();
		else
			handle.switchToMe();
		pathStrategy.switchTo(getWrappedDriver());
		return;
	}

	/**
	 * takes screenshots for log messages with FINE level
	 */
	@Override
	public void takeAPictureOfAFine(String comment) {
		handle.takeAPictureOfAFine(comment);
	}

	/**
	 * takes screenshots for log messages with INFO level
	 */
	@Override
	public void takeAPictureOfAnInfo(String comment) {
		handle.takeAPictureOfAnInfo(comment);
	}

	/**
	 * takes screenshots for log messages with SEVERE level
	 */
	@Override
	public void takeAPictureOfASevere(String comment) {
		handle.takeAPictureOfASevere(comment);
	}

	/**
	 * takes screenshots for log messages with WARNING level
	 */
	@Override
	public void takeAPictureOfAWarning(String comment) {
		handle.takeAPictureOfAWarning(comment);
	}
	
	public Application<? extends Handle, ? extends IHowToGetHandle> getApplication(){
		return application;
	}
}
