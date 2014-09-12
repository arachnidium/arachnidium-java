package org.arachnidium.core;

import java.util.Set;
import java.util.logging.Level;

import org.arachnidium.core.interfaces.IDestroyable;
import org.arachnidium.core.interfaces.IHasHandle;
import org.arachnidium.core.interfaces.ISwitchesToItself;
import org.arachnidium.core.interfaces.ITakesPictureOfItSelf;
import org.arachnidium.util.logging.Log;
import org.openqa.selenium.WebDriverException;

/**
 * Represents objects that have handles
 * They are browser window and mobile context/screen
 */
public abstract class Handle implements IHasHandle, ISwitchesToItself,
ITakesPictureOfItSelf, IDestroyable {

	static IHasHandle isInitiated(String handle, Manager<?> manager) {
		return manager.getHandleReceptionist().isInstantiated(handle);
	}

	final String handle;
	private final WebDriverEncapsulation driverEncapsulation;
	private final Manager<?> nativeManager;

	private final HandleReceptionist receptionist;

	Handle(String handle, Manager<?> manager) {
		this.nativeManager = manager;
		this.driverEncapsulation = manager.getWebDriverEncapsulation();
		this.handle = handle;
		this.receptionist = nativeManager.getHandleReceptionist();
	}

	@Override
	public void destroy() {
		receptionist.remove(this);
	}

	/**
	 * @return flag of the handle existing
	 */
	public synchronized boolean exists() {
		if (!nativeManager.isAlive())
			return false;
		try {
			Set<String> handles = nativeManager.getHandles();
			return handles.contains(handle);
		} catch (WebDriverException e) { // if there is no handle
			return false;
		}
	}

	public WebDriverEncapsulation getDriverEncapsulation() {
		return driverEncapsulation;
	}

	/**
	 * @return Window string handle/mobile context name
	 * 
	 * @see org.arachnidium.core.interfaces.IHasHandle#getHandle()
	 */
	@Override
	public String getHandle() {
		return handle;
	}

	/**
	 * @return {@link Manager}
	 */
	@SuppressWarnings("unchecked")
	public <T extends Manager<?>> T getManager() {
		return (T) nativeManager;
	}

	/**
	 * Sets focus to itself
	 */
	@Override
	public synchronized void switchToMe() {
		nativeManager.switchTo(handle);
	}

	/**
	 * Takes a picture of itself.
	 * It creates FINE {@link Level} {@link Log} message with 
	 * attached picture 
	 */
	@Override
	public synchronized void takeAPictureOfAFine(String comment) {
		nativeManager.takeAPictureOfAFine(handle, comment);
	}

	/**
	 * Takes a picture of itself.
	 * It creates INFO {@link Level} {@link Log} message with 
	 * attached picture 
	 */	
	@Override
	public synchronized void takeAPictureOfAnInfo(String comment) {
		nativeManager.takeAPictureOfAnInfo(handle, comment);
	}

	/**
	 * Takes a picture of itself.
	 * It creates SEVERE {@link Level} {@link Log} message with 
	 * attached picture 
	 */		
	@Override
	public synchronized void takeAPictureOfASevere(String comment) {
		nativeManager.takeAPictureOfASevere(handle, comment);
	}

	/**
	 * Takes a picture of itself.
	 * It creates WARN {@link Level} {@link Log} message with 
	 * attached picture 
	 */		
	@Override
	public synchronized void takeAPictureOfAWarning(String comment) {
		nativeManager.takeAPictureOfAWarning(handle, comment);
	}

}
