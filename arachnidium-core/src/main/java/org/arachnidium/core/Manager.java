package org.arachnidium.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.arachnidium.core.components.common.AlertHandler;
import org.arachnidium.core.components.common.Awaiting;
import org.arachnidium.core.fluenthandle.IHowToGetHandle;
import org.arachnidium.core.fluenthandle.IFluentHandleWaiting;
import org.arachnidium.core.interfaces.IDestroyable;
import org.arachnidium.core.interfaces.IHasHandle;
import org.arachnidium.core.settings.AlertIsPresentTimeOut;
import org.arachnidium.core.settings.HandleWaitingTimeOut;
import org.arachnidium.util.logging.Photographer;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;

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

	abstract void changeActive(String handle);

	@Override
	public void destroy() {
		managerMap.remove(driverEncapsulation);
		isAlive = false;
		List<IHasHandle> toBeDestroyed = handleReceptionist.getInstantiated();
		toBeDestroyed.forEach((hasHandle) -> ((IDestroyable) hasHandle)
				.destroy());
	}

	public Alert getAlert() throws NoAlertPresentException{
		Long time = driverEncapsulation.getWrappedConfiguration()
				.getSection(AlertIsPresentTimeOut.class).getAlertIsPresentTimeOut();
		return driverEncapsulation.getComponent(AlertHandler.class,
				new Class[] { long.class },
				new Object[] {time});
		
	}

	public synchronized Alert getAlert(long timeOut)
			throws NoAlertPresentException {
		return driverEncapsulation.getComponent(AlertHandler.class,
				new Class[] { long.class },
				new Object[] { timeOut });
	}

	public abstract <T extends Handle> T getHandle(int index);
	
	public abstract <T extends Handle> T getHandle(int index, long timeOut);

	abstract String getStringHandle(int index, long timeOut);

	HandleReceptionist getHandleReceptionist() {
		return handleReceptionist;
	}

	abstract Set<String> getHandles();

	public abstract <T extends Handle> T getHandle(U howToGet);
	
	public abstract <T extends Handle> T getHandle(long timeOut, U howToGet);

	WebDriverEncapsulation getWebDriverEncapsulation() {
		return driverEncapsulation;
	}

	WebDriver getWrappedDriver() {
		return driverEncapsulation.getWrappedDriver();
	}

	boolean isAlive() {
		return isAlive;
	}

	synchronized void switchTo(String Handle) {
		changeActive(Handle);
	}

	abstract String getStringHandle(long timeOut, U howToGet);

	synchronized void takeAPictureOfAFine(String handle, String Comment) {
		changeActive(handle);
		Photographer.takeAPictureOfAFine(
				driverEncapsulation.getWrappedDriver(), Comment);
	}

	synchronized void takeAPictureOfAnInfo(String handle, String Comment) {
		changeActive(handle);
		Photographer.takeAPictureOfAnInfo(
				driverEncapsulation.getWrappedDriver(), Comment);
	}

	synchronized void takeAPictureOfASevere(String handle, String Comment) {
		changeActive(handle);
		Photographer.takeAPictureOfASevere(
				driverEncapsulation.getWrappedDriver(), Comment);
	}

	synchronized void takeAPictureOfAWarning(String handle, String Comment) {
		changeActive(handle);
		Photographer.takeAPictureOfAWarning(
				driverEncapsulation.getWrappedDriver(), Comment);
	}
	
	/**
	 * Gets a new created listenable and notifies listener
	 * that it is a new object
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
