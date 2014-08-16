package org.arachnidium.model.common;

import org.arachnidium.core.Handle;
import org.arachnidium.core.Manager;
import org.arachnidium.core.WebDriverEncapsulation;
import org.arachnidium.core.fluenthandle.IHowToGetHandle;
import org.arachnidium.model.abstractions.ModelObject;
import org.arachnidium.model.interfaces.IDecomposable;
import org.arachnidium.model.interfaces.IDecomposibleByHandles;
import org.arachnidium.model.support.HowToGetByFrames;

/**
 * Common representation of any application Using it you can model your testing
 * application as a complex aggregated object it should only generate new
 * objects in general
 */
public class Application<S extends Handle, U extends IHowToGetHandle> extends ModelObject<S> implements IDecomposibleByHandles<U> {

	protected final Manager<U> manager;
	@SuppressWarnings("unchecked")
	protected Application(S handle) {
		super(handle);
		manager = (Manager<U>) handle.getManager();
		driverEncapsulation.addDestroyable(this);
	}

	/**
	 * using any accessible (!!!) FunctionalPart constructor Application creates
	 * page objects.
	 */
	protected <T extends IDecomposable> T get(Class<T> partClass,
			Object[] values) {
		T part = DefaultApplicationFactory.get(partClass, values);
		// get(partClass, params, values);
		((FunctionalPart<?>) part).application = this;
		addChild((ModelObject<?>) part);
		return part;
	}

	/**
	 * Gets a functional part (page object) from the main application handle.
	 */
	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass) {
		Object[] values = new Object[] { handle };
		return get(partClass, values);
	}

	/**
	 * Gets a functional part (page object) from the main application handle
	 * using indexed frame
	 */
	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			HowToGetByFrames pathStrategy) {
		Object[] values = new Object[] { handle, pathStrategy };
		return get(partClass, values);
	}

	WebDriverEncapsulation getWebDriverEncapsulation() {
		return driverEncapsulation;
	}

	/**
	 * destroys an Application instance and makes WebDriver quit
	 */
	public void quit() {
		driverEncapsulation.destroy();
	}

	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			int handleIndex) {
		Handle newHandle = manager.getHandle(handleIndex);
		Object[] values = new Object[] { newHandle };
		return get(partClass, values);
	}

	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			int handleIndex, long timeOut) {
		Handle newHandle = manager.getHandle(handleIndex, timeOut);
		Object[] values = new Object[] { newHandle };
		return get(partClass, values);
	}

	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			int handleIndex, HowToGetByFrames howToGetByFrames) {
		Handle newHandle = manager.getHandle(handleIndex);
		Object[] values = new Object[] { newHandle, howToGetByFrames };
		return get(partClass , values);
	}

	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			int handleIndex, HowToGetByFrames howToGetByFrames, long timeOut) {
		Handle newHandle = manager.getHandle(handleIndex, timeOut);
		Object[] values = new Object[] { newHandle, howToGetByFrames };
		return get(partClass, values);
	}

	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			U howToGetHandle) {
		Handle newHandle = manager.getHandle(howToGetHandle);
		Object[] values = new Object[] {newHandle};
		return get(partClass, values);
	}

	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			U howToGetHandle, long timeOut) {
		Handle newHandle = manager.getHandle(timeOut, howToGetHandle);
		Object[] values = new Object[] {newHandle};
		return get(partClass, values);
	}

	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			U howToGetHandle, HowToGetByFrames howToGetByFrames) {
		Handle newHandle = manager.getHandle(howToGetHandle);
		Object[] values = new Object[] { newHandle, howToGetByFrames };
		return get(partClass, values);
	}

	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			U howToGetHandle, HowToGetByFrames howToGetByFrames, long timeOut) {
		Handle newHandle = manager.getHandle(timeOut, howToGetHandle);
		Object[] values = new Object[] { newHandle, howToGetByFrames };
		return get(partClass, values);
	}
}
