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
import org.arachnidium.core.WebElementHighLighter;
import org.arachnidium.core.components.common.Ime;
import org.arachnidium.core.components.common.Interaction;
import org.arachnidium.core.components.common.TimeOut;
import org.arachnidium.core.interfaces.ISwitchesToItself;
import org.arachnidium.core.interfaces.ITakesPictureOfItSelf;
import org.arachnidium.core.interfaces.IWebElementHighlighter;
import org.arachnidium.model.abstractions.ModelObject;
import org.arachnidium.model.interfaces.IDecomposable;
import org.arachnidium.model.support.FramePathStrategy;
import org.arachnidium.model.support.PathStrategy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

/**
 * @author s.tihomirov It describes simple web page or mobile app context or
 *         their fragment
 */
public abstract class FunctionalPart extends ModelObject implements ITakesPictureOfItSelf, ISwitchesToItself {

	/**
	 * @author s.tihomirov
	 *
	 */
	@Target(value = ElementType.METHOD)
	@Retention(value = RetentionPolicy.RUNTIME)
	public static @interface InteractiveMethod {

	}

	protected FunctionalPart parent; // parent test object
	// page object is created by specified entity
	protected Application application;
	private IWebElementHighlighter highLighter;
	protected final Interaction interaction;
	protected final Ime ime;
	protected final TimeOut timeOuts;
	protected final PathStrategy pathStrategy;
	/**
	 * TODO this is workaround. Preparation to {@link https://github.com/arachnidium/arachnidium-java/issues/6}
	 */
	private final AppiumFieldDecorator appiumFieldDecorator;

	// constructs from another page object
	protected FunctionalPart(FunctionalPart parent) {
		this(parent.handle, new FramePathStrategy());
	}
	
	protected FunctionalPart(FunctionalPart parent, PathStrategy pathStrategy) {
		this(parent.handle, pathStrategy);
		parent.addChild(this);
	}

	protected FunctionalPart(Handle handle) {
		this(handle, new FramePathStrategy());
	}

	protected FunctionalPart(Handle handle, PathStrategy pathStrategy) {
		super(handle);
		this.pathStrategy = pathStrategy;
		timeOuts = driverEncapsulation.getTimeOut();
		/**
		 * TODO this is workaround. Preparation to {@link https://github.com/arachnidium/arachnidium-java/issues/6}
		 */
		appiumFieldDecorator = new AppiumFieldDecorator(
				driverEncapsulation.getWrappedDriver(),
				timeOuts.getImplicitlyWaitTimeOut(),
				timeOuts.getImplicitlyWaitTimeUnit());
		highLighter = new WebElementHighLighter();
		interaction = driverEncapsulation.getComponent(Interaction.class);
		ime = driverEncapsulation.getComponent(Ime.class);
	}

	@Override
	protected final void addChild(ModelObject child) {
		super.addChild(child);
		FunctionalPart childPart = (FunctionalPart) child;
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
		Class<?>[] params = new Class[] { FunctionalPart.class };
		Object[] values = new Object[] { this };
		return DefaultApplicationFactory.get(partClass, params, values);
	}

	// - with specified frame index
	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			PathStrategy pathStrategy) {
		Class<?>[] params = new Class[] { FunctionalPart.class, PathStrategy.class };
		Object[] values = new Object[] { this, pathStrategy };
		return DefaultApplicationFactory.get(partClass, params, values);
	}

	@InteractiveMethod
	public void highlightAsFine(WebElement element, Color highlight,
			String comment) {
		highLighter.highlightAsFine(driverEncapsulation.getWrappedDriver(),
				element, highlight, comment);
	}

	@InteractiveMethod
	public void highlightAsFine(WebElement element, String comment) {
		highLighter.highlightAsFine(driverEncapsulation.getWrappedDriver(),
				element, comment);
	}

	@InteractiveMethod
	public void highlightAsInfo(WebElement element, Color highlight,
			String comment) {
		highLighter.highlightAsInfo(driverEncapsulation.getWrappedDriver(),
				element, highlight, comment);
	}

	@InteractiveMethod
	public void highlightAsInfo(WebElement element, String comment) {
		highLighter.highlightAsInfo(driverEncapsulation.getWrappedDriver(),
				element, comment);
	}

	@InteractiveMethod
	public void highlightAsSevere(WebElement element, Color highlight,
			String comment) {
		highLighter.highlightAsSevere(driverEncapsulation.getWrappedDriver(),
				element, highlight, comment);
	}

	@InteractiveMethod
	public void highlightAsSevere(WebElement element, String comment) {
		highLighter.highlightAsSevere(driverEncapsulation.getWrappedDriver(),
				element, comment);
	}

	@InteractiveMethod
	public void highlightAsWarning(WebElement element, Color highlight,
			String comment) {
		highLighter.highlightAsWarning(driverEncapsulation.getWrappedDriver(),
				element, highlight, comment);
	}

	@InteractiveMethod
	public void highlightAsWarning(WebElement element, String comment) {
		highLighter.highlightAsWarning(driverEncapsulation.getWrappedDriver(),
				element, comment);
	}

	// The method below simply loads page factory
	protected void load() {
		PageFactory.initElements(appiumFieldDecorator, this);
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
		pathStrategy.switchTo(driverEncapsulation);
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
}
