package org.arachnidium.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import org.arachnidium.core.components.common.AlertHandler;
import org.arachnidium.core.components.common.Awaiting;
import org.arachnidium.core.fluenthandle.IHowToGetHandle;
import org.arachnidium.core.fluenthandle.IFluentHandleWaiting;
import org.arachnidium.core.interfaces.IDestroyable;
import org.arachnidium.core.interfaces.IHasHandle;
import org.arachnidium.core.settings.AlertIsPresentTimeOut;
import org.arachnidium.core.settings.HandleWaitingTimeOut;
import org.arachnidium.util.logging.Log;
import org.arachnidium.util.logging.Photographer;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.springframework.context.annotation.Bean;

/**
 * This an abstraction that describes 
 * way how to get a new {@link Handle} and 
 * how to switch from one another
 * 
 * @param <U> it is a s strategy of the {@link Handle} receiving
 */
public abstract class Manager<U extends IHowToGetHandle> implements IDestroyable {

	static long getTimeOut(Long possibleTimeOut) {
		if (possibleTimeOut == null)
			return defaultTimeOut;
		else
			return possibleTimeOut;
	}

	final Awaiting awaiting;
	private final WebDriverEncapsulation driverEncapsulation;
	boolean isAlive = true;
	private final HandleReceptionist handleReceptionist = new HandleReceptionist();

	private final static Map<WebDriverEncapsulation, Manager<?>> managerMap = Collections
			.synchronizedMap(new HashMap<WebDriverEncapsulation, Manager<?>>());
	final static long defaultTimeOut = 5; // we will wait
	// appearance of а handle for 5 seconds by default
	protected IFluentHandleWaiting handleWaiting;
	
	/**
	 * @param driverEncapsulation
	 *            Instantiated {@link WebDriverEncapsulation}
	 * @return If there is an instantiated {@link Manager} binded with the given
	 *         {@link WebDriverEncapsulation} this method returns the instance.
	 *         In another case it returns <code>null</code>.
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Manager<?>> T getInstanstiatedManager(
			WebDriverEncapsulation driverEncapsulation) {
		return (T) managerMap.get(driverEncapsulation);
	}

	Manager(WebDriverEncapsulation initialDriverEncapsulation) {
		driverEncapsulation = initialDriverEncapsulation;
		awaiting = new Awaiting(driverEncapsulation.getWrappedDriver());
		managerMap.put(driverEncapsulation, this);
		driverEncapsulation.addDestroyable(this);
	}

	/**
	 * Focus on the given window or mobile context will be implemented by
	 * subclasses
	 */
	abstract void changeActive(String handle);

	/**
	 * This method destroys information
	 * about related windows or mobile context
	 */
	@Override
	public void destroy() {
		managerMap.remove(driverEncapsulation);
		isAlive = false;
		List<IHasHandle> toBeDestroyed = handleReceptionist.getInstantiated();
		toBeDestroyed.forEach((hasHandle) -> ((IDestroyable) hasHandle)
				.destroy());
	}

	/**
	 * @return {@link Alert} which is present
	 * @throws {@link NoAlertPresentException}
	 */
	public Alert getAlert() throws NoAlertPresentException{
		Long time = driverEncapsulation.getWrappedConfiguration()
				.getSection(AlertIsPresentTimeOut.class).getAlertIsPresentTimeOut();
		return driverEncapsulation.getComponent(AlertHandler.class,
				new Class[] { long.class },
				new Object[] {time});
		
	}

	/**
	 * @param timeOut It is an explicitly given time (seconds) 
	 *    to wait for Alert is present
	 * @return {@link Alert} which is present
	 * @throws {@link NoAlertPresentException}
	 */
	public synchronized Alert getAlert(long timeOut)
			throws NoAlertPresentException {
		return driverEncapsulation.getComponent(AlertHandler.class,
				new Class[] { long.class },
				new Object[] { timeOut });
	}

	/**
	 * @param An expected window/mobile context index
	 * @return Window or mobile context
	 */
	public abstract <T extends Handle> T getHandle(int index);
	
	/**
	 * @param An expected window/mobile context index
	 * @param It is an explicitly given time (seconds) to wait for
	 *            window/mobile context is present
	 * @return Window or mobile context
	 */
	public abstract <T extends Handle> T getHandle(int index, long timeOut);

	/** 
	 * @param An expected window/mobile context index
	 * @param It is an explicitly given time (seconds) to wait for
	 *            window/mobile context is present
	 * @return Window handle/context name
	 */
	abstract String getStringHandle(int index, long timeOut);

	HandleReceptionist getHandleReceptionist() {
		return handleReceptionist;
	}

	/**
	 * @return Set of string window handles/context names
	 */
	abstract Set<String> getHandles();

	/**
	 * Returns window on mobile context 
	 * by conditions. @see IHowToGetHandle
	 * 
	 * @param howToGet Given strategy. @see IHowToGetHandle
	 * @return Window or mobile context
	 */
	public abstract <T extends Handle> T getHandle(U howToGet);
	
	/**
	 * Returns window on mobile context 
	 * by conditions. @see IHowToGetHandle. 
	 * 
	 * @param timeOut It is an explicitly given time (seconds) to wait for
	 *            window/mobile context is present
	 *            
	 * @param howToGet Given strategy. @see IHowToGetHandle
	 * @return Window or mobile context
	 */
	public abstract <T extends Handle> T getHandle(long timeOut, U howToGet);

	WebDriverEncapsulation getWebDriverEncapsulation() {
		return driverEncapsulation;
	}

	WebDriver getWrappedDriver() {
		return driverEncapsulation.getWrappedDriver();
	}

	/**
	 * @return <code>false</code> if wrapped {@link WebDriver} was shut down
	 */
	boolean isAlive() {
		return isAlive;
	}

	/**
	 * Sets focus on window/mobile context by string 
	 * parameter
	 * 
	 * @param String window handle/context name
	 */
	synchronized void switchTo(String Handle) {
		changeActive(Handle);
	}

	/**
	 * @param timeOut It is an explicitly given time (seconds) to wait for
	 *            window/mobile context is present
	 *            
	 * @param howToGet Given strategy. @see IHowToGetHandle
	 * @return Window handle/context name
	 */
	abstract String getStringHandle(long timeOut, U howToGet);

	/**
	 * Takes a picture of the given window/mobile context.
	 * It creates FINE {@link Level} {@link Log} message with 
	 * attached picture 
	 * 
	 * @param handle String window handle/context name
	 * @param comment Narrative message text
	 */
	synchronized void takeAPictureOfAFine(String handle, String comment) {
		changeActive(handle);
		Photographer.takeAPictureOfAFine(
				driverEncapsulation.getWrappedDriver(), comment);
	}

	/**
	 * Takes a picture of the given window/mobile context.
	 * It creates INFO {@link Level} {@link Log} message with 
	 * attached picture 
	 * 
	 * @param handle String window handle/context name
	 * @param comment Narrative message text
	 */	
	synchronized void takeAPictureOfAnInfo(String handle, String comment) {
		changeActive(handle);
		Photographer.takeAPictureOfAnInfo(
				driverEncapsulation.getWrappedDriver(), comment);
	}
	
	/**
	 * Takes a picture of the given window/mobile context.
	 * It creates SEVERE {@link Level} {@link Log} message with 
	 * attached picture 
	 * 
	 * @param handle String window handle/context name
	 * @param comment Narrative message text
	 */		
	synchronized void takeAPictureOfASevere(String handle, String comment) {
		changeActive(handle);
		Photographer.takeAPictureOfASevere(
				driverEncapsulation.getWrappedDriver(), comment);
	}

	/**
	 * Takes a picture of the given window/mobile context.
	 * It creates WARN {@link Level} {@link Log} message with 
	 * attached picture 
	 * 
	 * @param handle String window handle/context name
	 * @param comment Narrative message text
	 */		
	synchronized void takeAPictureOfAWarning(String handle, String comment) {
		changeActive(handle);
		Photographer.takeAPictureOfAWarning(
				driverEncapsulation.getWrappedDriver(), comment);
	}
	
	/**
	 * Gets a new created listenable {@link Handle} and notifies listener
	 * that there it is a new object
	 * 
	 * @param handle instantiated {@link Handle}
	 * @param beanName Is a name of the {@link Bean}
	 * @return listenable {@link Handle} instance
	 */
	@SuppressWarnings("unchecked")
	<T extends Handle> T returnNewCreatedListenableHandle(Handle handle, String beanName){
		T result = (T) driverEncapsulation.context.getBean(beanName, handle);
		result.whenIsCreated();
		getHandleReceptionist().addKnown(result);
		return result;
	}
	
	HandleWaitingTimeOut getHandleWaitingTimeOut() {
		return driverEncapsulation.getWrappedConfiguration()
				.getSection(HandleWaitingTimeOut.class);
	}
}
