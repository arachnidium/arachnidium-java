package org.arachnidium.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.arachnidium.core.components.ComponentFactory;
import org.arachnidium.core.components.common.AlertHandler;
import org.arachnidium.core.components.common.Awaiting;
import org.arachnidium.core.interfaces.IDestroyable;
import org.arachnidium.core.interfaces.IHasHandle;
import org.arachnidium.util.logging.Photographer;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;

public abstract class Manager implements IDestroyable {

	static long getTimeOut(Long possibleTimeOut, long defaultValue) {
		if (possibleTimeOut == null)
			return defaultValue;
		else
			return possibleTimeOut;
	}

	final Awaiting awaiting;
	private final WebDriverEncapsulation driverEncapsulation;
	boolean isAlive = true;
	private final HandleReceptionist handleReceptionist = new HandleReceptionist();

	private final static Map<WebDriverEncapsulation, Manager> managerMap = Collections
			.synchronizedMap(new HashMap<WebDriverEncapsulation, Manager>());
	final static long defaultTime = 1; // default time we waiting for anything

	final static long defaultTimeForNew = 30; // we will wait
	// appearance of a new handle for 30 seconds by default
	
	@SuppressWarnings("unchecked")
	public static <T extends Manager> T getInstanstiatedManager(
			WebDriverEncapsulation driverEncapsulation) {
		return (T) managerMap.get(driverEncapsulation);
	}

	Manager(WebDriverEncapsulation initialDriverEncapsulation) {
		driverEncapsulation = initialDriverEncapsulation;
		awaiting = driverEncapsulation.getComponent(Awaiting.class);
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

	public abstract Alert getAlert() throws NoAlertPresentException;

	public synchronized Alert getAlert(long timeOut)
			throws NoAlertPresentException {
		return ComponentFactory.getComponent(AlertHandler.class,
				getWrappedDriver(), new Class[] { long.class },
				new Object[] { timeOut });
	}

	public abstract Handle getByIndex(int index);

	abstract String getHandleByIndex(int index);

	HandleReceptionist getHandleReceptionist() {
		return handleReceptionist;
	}

	abstract Set<String> getHandles();

	public abstract Handle getNewHandle();

	public abstract Handle getNewHandle(long timeOutInSeconds);

	public abstract Handle getNewHandle(long timeOutInSeconds,
			String stringIdentifier);

	public abstract Handle getNewHandle(String stringIdentifier);

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

	abstract String switchToNew();

	abstract String switchToNew(long timeOutInSeconds);

	abstract String switchToNew(long timeOutInSeconds, String stringIdentifier);

	abstract String switchToNew(String stringIdentifier);

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
}
