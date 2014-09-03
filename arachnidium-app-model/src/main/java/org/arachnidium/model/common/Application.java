package org.arachnidium.model.common;

import org.arachnidium.core.Handle;
import org.arachnidium.core.Manager;
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
		getWebDriverEncapsulation().addDestroyable(this);
	}

	/**
	 * using any accessible FunctionalPart constructor Application creates
	 * page objects.
	 */
	private <T extends IDecomposable> T get(Class<T> partClass,
			Object[] values) {
		T part = DefaultApplicationFactory.get(partClass, values);
		// get(partClass, params, values);
		((FunctionalPart<?>) part).application = this;
		addChild((ModelObject<?>) part);
		return part;
	}

	private <T extends IDecomposable> T getPart(Class<T> partClass, Handle handle) {
		Object[] values = new Object[] { handle };
		return get(partClass, values);
	}
	
	private <T extends IDecomposable> T getPart(Class<T> partClass, Handle handle, HowToGetByFrames path) {
		Object[] values = new Object[] { handle, path};
		return get(partClass, values);
	}	
	
	/**
	 * Gets a functional part (page object) from the main application handle.
	 */
	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass) {
		return getPart(partClass, this.handle);
	}

	/**
	 * Gets a functional part (page object) from the main application handle
	 * using indexed frame
	 */
	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			HowToGetByFrames path) {
		return getPart(partClass, this.handle, path);
	}

	/**
	 * destroys an Application instance and makes WebDriver quit
	 */
	public void quit() {
		getWebDriverEncapsulation().destroy();
	}

	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			int handleIndex) {
		return getPart(partClass, manager.getHandle(handleIndex));
	}

	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			int handleIndex, long timeOut) {
		return getPart(partClass, manager.getHandle(handleIndex, timeOut));
	}

	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			int handleIndex, HowToGetByFrames path) {
		return getPart(partClass, manager.getHandle(handleIndex), path);
	}

	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			int handleIndex, HowToGetByFrames path, long timeOut) {
		return getPart(partClass, manager.getHandle(handleIndex, timeOut), path);
	}

	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			U howToGetHandle) {
		return getPart(partClass, manager.getHandle(howToGetHandle));
	}

	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			U howToGetHandle, long timeOut) {
		return getPart(partClass, manager.getHandle(timeOut, howToGetHandle));
	}

	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			U howToGetHandle, HowToGetByFrames path) {
		return getPart(partClass, manager.getHandle(howToGetHandle), path);
	}

	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			U howToGetHandle, HowToGetByFrames path, long timeOut) {
		return getPart(partClass, manager.getHandle(timeOut, howToGetHandle), path);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Manager<?>> T getManager(){
		return (T) manager;
	}
}
