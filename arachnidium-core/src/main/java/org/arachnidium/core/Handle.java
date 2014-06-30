package org.arachnidium.core;

import java.util.Set;

import org.arachnidium.core.interfaces.IDestroyable;
import org.arachnidium.core.interfaces.IHasHandle;
import org.arachnidium.core.interfaces.ISwitchesToItself;
import org.arachnidium.core.interfaces.ITakesPictureOfItSelf;
import org.openqa.selenium.WebDriverException;

/**
 * Represents objects that have handles
 */
public abstract class Handle implements IHasHandle, ISwitchesToItself,
		ITakesPictureOfItSelf, IDestroyable {

	final String handle;
	final WebDriverEncapsulation driverEncapsulation;
	final Manager nativeManager;
	private final HandleReceptionist receptionist;

	Handle(String handle, Manager manager) {
		this.nativeManager = manager;
		this.driverEncapsulation = manager.getWebDriverEncapsulation();
		this.handle = handle;
		this.receptionist = nativeManager.getHandleReceptionist();
		receptionist.addKnown(this);
	}

	@Override
	public String getHandle() {
		return handle;
	}
	
	public synchronized boolean exists() {
		if (!nativeManager.isAlive()) {
			return false;
		}
		try {
			Set<String> handles = nativeManager.getHandles();
			return handles.contains(handle);
		} catch (WebDriverException e) { // if there is no handle
			return false;
		}
	}
	
	public Manager getManager() {
		return nativeManager;
	}

	@Override
	public synchronized void switchToMe(){
		requestToMe();
	}

	@Override
	public synchronized void takeAPictureOfAnInfo(String Comment) {
		nativeManager.takeAPictureOfAnInfo(handle, Comment);
	}

	@Override
	public synchronized void takeAPictureOfAFine(String Comment) {
		nativeManager.takeAPictureOfAFine(handle, Comment);
	}

	@Override
	public synchronized void takeAPictureOfAWarning(String Comment) {
		nativeManager.takeAPictureOfAWarning(handle, Comment);
	}

	@Override
	public synchronized void takeAPictureOfASevere(String Comment) {
		nativeManager.takeAPictureOfASevere(handle, Comment);
	}
	
	@Override
	public void destroy() {
		receptionist.remove(this);
	}

	public WebDriverEncapsulation getDriverEncapsulation() {
		return (driverEncapsulation);
	}

	static IHasHandle isInitiated(String handle, Manager manager) {
		return manager.getHandleReceptionist().isInstantiated(handle);
	}
	
	void requestToMe() {
		nativeManager.switchTo(handle);
	}

}
