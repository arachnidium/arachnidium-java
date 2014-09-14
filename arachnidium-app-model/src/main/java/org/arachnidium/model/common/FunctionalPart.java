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
import java.util.logging.Level;

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
import org.arachnidium.util.logging.Log;
import org.arachnidium.util.logging.eLogColors;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

/**
 *<p>This class is for description of browser or mobile UI or the fragment 
 *<p>of this UI. It is assumed that this description can be reusable.
 *<p>
 *<p>Interaction and behavior should describe subclasses of this. 
 *<p>
 */
public abstract class FunctionalPart<S extends Handle> extends ModelObject<S> implements ITakesPictureOfItSelf, ISwitchesToItself {

	/**
	 *<p>This annotation is useful 
	 *<p>when there is need to interact with more
	 *<p>than one browser window/mobile context
	 *<p>at the same time. Also it is convenient
	 *<p>when described UI is inside frame
	 *<p>
	 *<p>The presence of this annotation means
	 *<p>that {@link WebDriver} will be switched
	 *<p>to window/context and to frame automatically 
	 */
	@Target(value = ElementType.METHOD)
	@Retention(value = RetentionPolicy.RUNTIME)
	protected static @interface InteractiveMethod {

	}

	protected FunctionalPart<?> parent; // parent test object
	// parent application
	protected Application<?,?> application;
	protected final Interaction interaction;
	protected final Ime ime;
	protected final HowToGetByFrames pathStrategy;

	/**
	 *<p>This constructor should present 
	 *<p>when it is possible that described UI or its part
	 *<p>we are going to got from another (more general).
	 *<p>
	 *<p>
	 *<p>Example:
	 *<p><code>someUIDescriptionInstance.getPart(someUIDescriptionInstance.class)</code>
	 *<p> 
	 *<p>This instantiation means that described UI or fragment is stationed
     *<p>on the same window/mobile context and inside the same frame (it is
     *<p>actual for browser and mobile hybrid apps) as the "parent"
	 *<p> 
	 *@param parent is like a more general UI or part of client UI
	 */
	@SuppressWarnings("unchecked")
	protected FunctionalPart(FunctionalPart<?> parent) {
		this((S) parent.handle, new HowToGetByFrames());
	}
	
	/**
	 *<p>This constructor should present 
	 *<p>when it is possible that described UI or its part
	 *<p>we are going to got from another (more general).
	 *<p>The described piece of UI is stationed inside frame.
	 *<p>Path to the frame is specified by {@link HowToGetByFrames}
	 *<p>instance
	 *<p>
	 *<p>
	 *<p>Example:
	 *<p><code>someUIDescriptionInstance.getPart(someUIDescriptionInstance.class,
	 *<p>howToGetByFramesObject)</code>
	 *<p> 
	 *<p>This instantiation means that described UI or fragment is stationed
     *<p>on the same window/mobile context and inside the same frame (it is
     *<p>actual for browser and mobile hybrid apps) as the "parent".
	 *<p> 
	 *@param parent is like a more general UI or part of client UI
	 *@param path is a path to frame which is specified by {@link HowToGetByFrames}
	 *<p>
	 *@see HowToGetByFrames
	 */	
	@SuppressWarnings("unchecked")
	protected FunctionalPart(FunctionalPart<?> parent, HowToGetByFrames path) {
		this((S) parent.handle, path);
		parent.addChild(this);
	}

	/**
	 *<p>This constructor should present 
	 *<p>when it is possible that described UI or its part
	 *<p>we are going to got from the given browser window
	 *<p>or mobile context. It is expected that this is
	 *<p>most frequently used case. 
	 *<p>
	 *@param handle is the given browser window
	 *<p>or mobile context
	 */
	protected FunctionalPart(S handle) {
		this(handle, new HowToGetByFrames());
	}

	/**
	 *<p>This constructor should present 
	 *<p>when it is possible that described UI or its part
	 *<p>we are going to got from the given browser window
	 *<p>or mobile context. Also, this piece of UI
	 *<p>is stationed inside frame (it is actual for browser and mobile hybrid apps). 
	 *<p>Path to the frame is specified 
	 *<p>by {@link HowToGetByFrames} instance
	 *<p>
	 *@param handle is the given browser window
	 *<p>or mobile context
	 *<p>
	 *@param path is a path to frame which is specified by {@link HowToGetByFrames}
	 *<p>
	 *@see HowToGetByFrames	
	*/
	protected FunctionalPart(S handle, HowToGetByFrames path) {
		super(handle);
		this.pathStrategy = path;
		interaction = getComponent(Interaction.class);
		ime =         getComponent(Ime.class);
	}

	/**
	 *@see org.arachnidium.model.abstractions.ModelObject#addChild(org.arachnidium.model.abstractions.ModelObject)
	 */
	@Override
	protected final void addChild(ModelObject<?> child) {
		super.addChild(child);
		FunctionalPart<?> childPart = (FunctionalPart<?>) child;
		childPart.parent = this;
		childPart.application = this.application;
	}

	/**
	 *<p>this method 
	 *<p>starts destruction of related
	 *<p>information
	 */
	@Override
	public void destroy() {
		if (!handle.exists())
			handle.destroy();
		super.destroy();
		return;
	}

    /**
     *<p>This method returns another UI description (child).
     *<p>It is assumed that current UI is more generalized and the
     *<p>child is more specific. 
     *<p>
     *<p>It means, that required UI or fragment of UI 
     *<p>is stationed on the same browser window/mobile screen as the
     *<p>current. Also "child" is inside the same frame as the
     *<p>current (it is actual for browser and mobile hybrid apps). 
     *<p>
     *@see org.arachnidium.model.abstractions.ModelObject#getPart(java.lang.Class)
     *<p>
     *@see IDecomposable#getPart(Class)
     */
	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass) {
		Object[] values = new Object[] { this };
		return DefaultApplicationFactory.get(partClass, values);
	}

	/**
     *<p>This method returns another UI description (child).
     *<p>It is assumed that current UI is more generalized and the
     *<p>child is more specific. 
     *<p>
     *<p>It means, that required UI or fragment of UI 
     *<p>is stationed on the same browser window/mobile screen as the
     *<p>current. Also "child" is inside the same frame as the
     *<p>current. The child is in enclosed frame. 
     *<p>Frames are actual for browser and mobile hybrid apps.
	 *<p>
	 *@see org.arachnidium.model.abstractions.ModelObject#getPart(Class, HowToGetByFrames)
	 *<p>
	 *@see IDecomposable#getPart(Class, HowToGetByFrames)
	 */
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
	
	/**
	 *<p>Highlights HTML element by given color, creates 
	 *<p>{@link Log} message with FINE {@link Level} and narrative
	 *<p>text, optionally takes a screen shot and attaches to {@link Log} 
	 *<p>
	 *@param element to highlighted
	 *@param highlight is a using color
	 *@param comment is a narrative message text
	 */
	@InteractiveMethod
	public void highlightAsFine(WebElement element, Color highlight,
			String comment) {
		getHighlighter().highlightAsFine(getWrappedDriver(),
				element, highlight, comment);
	}

	/**
	 *<p>Highlights HTML element by given color, creates 
	 *<p>{@link Log} message with FINE {@link Level} and narrative
	 *<p>text, optionally takes a screen shot and attaches to {@link Log} 
	 *<p>
	 *<p>{@link eLogColors#DEBUGCOLOR} is used.
	 *<p>
	 *@param element to highlighted
	 *@param comment is a narrative message text
	 */	
	@InteractiveMethod
	public void highlightAsFine(WebElement element, String comment) {
		getHighlighter().highlightAsFine(getWrappedDriver(),
				element, comment);
	}

	/**
	 *<p>Highlights HTML element by given color, creates 
	 *<p>{@link Log} message with INFO {@link Level} and narrative
	 *<p>text, optionally takes a screen shot and attaches to {@link Log} 
	 *<p>
	 *@param element to highlighted
	 *@param highlight is a using color
	 *@param comment is a narrative message text
	 */	
	@InteractiveMethod
	public void highlightAsInfo(WebElement element, Color highlight,
			String comment) {
		getHighlighter().highlightAsInfo(getWrappedDriver(),
				element, highlight, comment);
	}

	/**
	 *<p>Highlights HTML element by given color, creates 
	 *<p>{@link Log} message with INFO {@link Level} and narrative
	 *<p>text, optionally takes a screen shot and attaches to {@link Log} 
	 *<p>
	 *<p>{@link eLogColors#CORRECTSTATECOLOR} is used.
	 *<p>
	 *@param element to highlighted
	 *@param comment is a narrative message text
	 */		
	@InteractiveMethod
	public void highlightAsInfo(WebElement element, String comment) {
		getHighlighter().highlightAsInfo(getWrappedDriver(),
				element, comment);
	}

	/**
	 *<p>Highlights HTML element by given color, creates 
	 *<p>{@link Log} message with SEVERE {@link Level} and narrative
	 *<p>text, optionally takes a screen shot and attaches to {@link Log} 
	 *<p>
	 *@param element to highlighted
	 *@param highlight is a using color
	 *@param comment is a narrative message text
	 */		
	@InteractiveMethod
	public void highlightAsSevere(WebElement element, Color highlight,
			String comment) {
		getHighlighter().highlightAsSevere(getWrappedDriver(),
				element, highlight, comment);
	}

	/**
	 *<p>Highlights HTML element by given color, creates 
	 *<p>{@link Log} message with SEVERE {@link Level} and narrative
	 *<p>text, optionally takes a screen shot and attaches to {@link Log} 
	 *<p>
	 *<p>{@link eLogColors#SEVERESTATECOLOR} is used.
	 *<p>
	 *@param element to highlighted
	 *@param comment is a narrative message text
	 */		
	@InteractiveMethod
	public void highlightAsSevere(WebElement element, String comment) {
		getHighlighter().highlightAsSevere(getWrappedDriver(),
				element, comment);
	}

	/**
	 *<p>Highlights HTML element by given color, creates 
	 *<p>{@link Log} message with WARN {@link Level} and narrative
	 *<p>text, optionally takes a screen shot and attaches to {@link Log} 
	 *<p>
	 *@param element to highlighted
	 *@param highlight is a using color
	 *@param comment is a narrative message text
	 */		
	@InteractiveMethod
	public void highlightAsWarning(WebElement element, Color highlight,
			String comment) {
		getHighlighter().highlightAsWarning(getWrappedDriver(),
				element, highlight, comment);
	}

	/**
	 *<p>Highlights HTML element by given color, creates 
	 *<p>{@link Log} message with WARN {@link Level} and narrative
	 *<p>text, optionally takes a screen shot and attaches to {@link Log} 
	 *<p>
	 *<p>{@link eLogColors#WARNSTATECOLOR} is used.
	 *<p>
	 *@param element to highlighted
	 *@param comment is a narrative message text
	 */		
	@InteractiveMethod
	public void highlightAsWarning(WebElement element, String comment) {
		getHighlighter().highlightAsWarning(getWrappedDriver(),
				element, comment);
	}

	/**
	 *<p>Instantiates declared {@link WebElement} fields
	 *<p>using {@link PageFactory} and {@link AppiumFieldDecorator}
	 */
	protected void load() {
		TimeOut timeOut = getWebDriverEncapsulation().getTimeOut();
		PageFactory.initElements(new AppiumFieldDecorator(
				getWrappedDriver(),
				timeOut.getImplicitlyWaitTimeOut(),
				timeOut.getImplicitlyWaitTimeUnit()), this);
	}

	/**
	 *<p>Instantiates declared {@link WebElement} fields
	 *<p>using {@link PageFactory} and customized 
	 *<p>{@link FieldDecorator}
	 *<p>
	 *@param decorator is an instance of the customized
	 *<p>{@link FieldDecorator}
	 */
	protected void load(FieldDecorator decorator) {
		PageFactory.initElements(decorator, this);
	}

	/**
	 *<p> Instantiates declared {@link WebElement} fields
	 *<p>using {@link PageFactory} and customized 
	 *<p>{@link ElementLocatorFactory}
	 *<p>
	 *@param factoryis an instance of the customized
	 *<p>{@link ElementLocatorFactory}
	 */
	protected void load(ElementLocatorFactory factory) {
		PageFactory.initElements(factory, this);
	}

	/**
	 *<p>Performs focus on the described UI.
	 *<p>
	 *<p>First of all it performs switch to the window/mobile 
	 *<p>context where UI is existing now.
	 *<p>If described UI is inside frame this method
	 *<p>performs switching from one frame to another
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
	 *<p>takes screenshots and attache it to
	 *<p>log messages with FINE {@link Level}
	 *<p>
	 *@see org.arachnidium.core.interfaces.ITakesPictureOfItSelf#takeAPictureOfAFine(java.lang.String)
	 */
	@Override
	public void takeAPictureOfAFine(String comment) {
		handle.takeAPictureOfAFine(comment);
	}

	/**
	 *<p>takes screenshots and attache it to
	 *<p>log messages with INFO {@link Level}
	 *<p>
	 *@see org.arachnidium.core.interfaces.ITakesPictureOfItSelf#takeAPictureOfAnInfo(java.lang.String)
	 */
	@Override
	public void takeAPictureOfAnInfo(String comment) {
		handle.takeAPictureOfAnInfo(comment);
	}

	/**
	 *<p>takes screenshots and attache it to
	 *<p>log messages with SEVERE {@link Level}
	 *<p>
	 *@see org.arachnidium.core.interfaces.ITakesPictureOfItSelf#takeAPictureOfAnInfo(java.lang.String)
	 */
	@Override
	public void takeAPictureOfASevere(String comment) {
		handle.takeAPictureOfASevere(comment);
	}

	/**
	 *<p>takes screenshots and attache it to
	 *<p>log messages with WARN {@link Level}
	 *<p>
	 *@see org.arachnidium.core.interfaces.ITakesPictureOfItSelf#takeAPictureOfAnInfo(java.lang.String)
	 */
	@Override
	public void takeAPictureOfAWarning(String comment) {
		handle.takeAPictureOfAWarning(comment);
	}
	
	/**
	 *<p>@return An instance of the Application 
	 *<p>this this UI description has been received from 
	 */
	public Application<? extends Handle, ? extends IHowToGetHandle> getApplication(){
		return application;
	}
}
