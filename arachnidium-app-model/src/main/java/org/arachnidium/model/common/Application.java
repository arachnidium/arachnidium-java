package org.arachnidium.model.common;

import java.util.ArrayList;
import java.util.Arrays;

import org.arachnidium.core.Handle;
import org.arachnidium.core.Manager;
import org.arachnidium.core.WebDriverEncapsulation;
import org.arachnidium.model.abstractions.ModelObject;
import org.arachnidium.model.interfaces.IDecomposable;
import org.arachnidium.model.interfaces.IHasManyHandles;
import org.arachnidium.model.support.HowToGetByFrames;
import org.arachnidium.model.support.IPathStrategy;

/**
 * Common representation of any application Using it you can model your testing
 * application as a complex aggregated object it should only generate new
 * objects in general
 */
public abstract class Application extends ModelObject implements
		IHasManyHandles {

	protected final Manager manager;
	protected Application(Handle handle) {
		super(handle);
		manager = handle.getManager();
		driverEncapsulation.addDestroyable(this);
	}

	/**
	 * using any accessible (!!!) FunctionalPart constructor Application creates
	 * page objects.
	 */
	protected <T extends IDecomposable> T get(Class<T> partClass,
			Class<?>[] params, Object[] values) {
		T part = DefaultApplicationFactory.get(partClass, params, values);
		// get(partClass, params, values);
		((FunctionalPart) part).application = this;
		addChild((ModelObject) part);
		return part;
	}

	/**
	 * Gets a functional part (page object) from the existing handle by index
	 */
	@Override
	public <T extends IDecomposable> T getFromHandle(Class<T> partClass,
			int index) {
		Handle newHandle = manager.getHandle(index);
		Class<?>[] params = new Class[] { Handle.class };
		Object[] values = new Object[] { newHandle };
		return get(partClass,
				replaceHandleParamIfItNeedsToBe(params, partClass, newHandle),
				values);
	}

	/**
	 * Gets a functional part (page object) from the existing handle by index
	 * and frame index
	 */
	@Override
	public <T extends IDecomposable> T getFromHandle(Class<T> partClass, IPathStrategy pathStrategy,
			int index) {
		Handle newHandle = manager.getHandle(index);
		Class<?>[] params = new Class[] { Handle.class, IPathStrategy.class };
		Object[] values = new Object[] { newHandle, pathStrategy };
		return get(partClass,
				replaceHandleParamIfItNeedsToBe(params, partClass, newHandle),
				values);
	}


	/**
	 * Gets a functional part (page object) from the new opened handle
	 */
	@Override
	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass) {
		Handle newHandle = manager.getHandle();
		Class<?>[] params = new Class[] { Handle.class };
		Object[] values = new Object[] { newHandle };
		return get(partClass,
				replaceHandleParamIfItNeedsToBe(params, partClass, newHandle),
				values);
	}

	/**
	 * Gets a functional part (page object) from the new opened handle using
	 * frame index
	 */
	@Override
	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			IPathStrategy pathStrategy) {
		Handle newHandle = manager.getHandle();
		Class<?>[] params = new Class[] { Handle.class, IPathStrategy.class };
		Object[] values = new Object[] { newHandle, pathStrategy};
		return get(partClass,
				replaceHandleParamIfItNeedsToBe(params, partClass, newHandle),
				values);
	}

	/**
	 * Gets a functional part (page object) from the new opened handle using
	 * specified timeout
	 */
	@Override
	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			long timeOutSec) {
		Handle newHandle = manager.getNewHandle(timeOutSec);
		Class<?>[] params = new Class[] { Handle.class };
		Object[] values = new Object[] { newHandle };
		return get(partClass,
				replaceHandleParamIfItNeedsToBe(params, partClass, newHandle),
				values);
	}

	/**
	 * Gets a functional part (page object) from the new opened handle using
	 * specified timeout and frame index
	 */
	@Override
	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			IPathStrategy pathStrategy, long timeOutSec) {
		Handle newHandle = manager.getNewHandle(timeOutSec);
		Class<?>[] params = new Class[] { Handle.class, IPathStrategy.class };
		Object[] values = new Object[] { newHandle, pathStrategy };
		return get(partClass,
				replaceHandleParamIfItNeedsToBe(params, partClass, newHandle),
				values);
	}

	/**
	 * Gets a functional part (page object) from the new opened handle using
	 * specified string identification
	 */
	@Override
	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			String stringIdentifier) {
		Handle newHandle = manager.getNewHandle(stringIdentifier);
		Class<?>[] params = new Class[] { Handle.class };
		Object[] values = new Object[] { newHandle };
		return get(partClass,
				replaceHandleParamIfItNeedsToBe(params, partClass, newHandle),
				values);
	}

	/**
	 * Gets a functional part (page object) from the new opened handle using
	 * specified string identification and specified frame index
	 */
	@Override
	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			IPathStrategy pathStrategy, String stringIdentifier) {
		Handle newHandle = manager.getNewHandle(stringIdentifier);
		Class<?>[] params = new Class[] { Handle.class, IPathStrategy.class };
		Object[] values = new Object[] { newHandle, pathStrategy};
		return get(partClass,
				replaceHandleParamIfItNeedsToBe(params, partClass, newHandle),
				values);
	}

	/**
	 * Gets a functional part (page object) from the new opened handle using
	 * specified string identification and timeout
	 */
	@Override
	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			String stringIdentifier, long timeOutSec) {
		Handle newHandle = manager.getNewHandle(timeOutSec, stringIdentifier);
		Class<?>[] params = new Class[] { Handle.class };
		Object[] values = new Object[] { newHandle };
		return get(partClass,
				replaceHandleParamIfItNeedsToBe(params, partClass, newHandle),
				values);
	}

	/**
	 * Gets a functional part (page object) from the new opened handle using
	 * specified string identification, timeout and index of required frame
	 */
	@Override
	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			IPathStrategy pathStrategy, String stringIdentifier, long timeOutSec) {
		Handle newHandle = manager.getNewHandle(timeOutSec, stringIdentifier);
		Class<?>[] params = new Class[] { Handle.class, IPathStrategy.class };
		Object[] values = new Object[] { newHandle, pathStrategy};
		return get(partClass,
				replaceHandleParamIfItNeedsToBe(params, partClass, newHandle),
				values);
	}

	/**
	 * Gets a functional part (page object) from the main application handle.
	 */
	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass) {
		Class<?>[] params = new Class[] { Handle.class };
		Object[] values = new Object[] { handle };
		return get(partClass,
				replaceHandleParamIfItNeedsToBe(params, partClass, handle),
				values);
	}

	/**
	 * Gets a functional part (page object) from the main application handle
	 * using indexed frame
	 */
	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			HowToGetByFrames pathStrategy) {
		Class<?>[] params = new Class[] { Handle.class, HowToGetByFrames.class };
		Object[] values = new Object[] { handle, pathStrategy };
		return get(partClass,
				replaceHandleParamIfItNeedsToBe(params, partClass, handle),
				values);
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

	protected Class<?>[] replaceHandleParamIfItNeedsToBe(
			Class<?>[] originalParams, Class<?> required, Handle actualHandle) {
		Class<?>[] result = null;
		try {
			required.getDeclaredConstructor(originalParams);
			return originalParams;
		} catch (Exception ignored) {
		}

		ArrayList<Class<?>> params = new ArrayList<>(
				Arrays.asList(originalParams));
		int i = params.indexOf(Handle.class);
		params.remove(Handle.class);
		//TODO This is workaround. It has to be refactored
		params.add(i, actualHandle.getClass().getSuperclass());
		result = params.toArray(new Class<?>[] {});
		try {
			required.getDeclaredConstructor(result);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}
}
