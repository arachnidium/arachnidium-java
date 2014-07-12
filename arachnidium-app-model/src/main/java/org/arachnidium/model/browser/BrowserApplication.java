package org.arachnidium.model.browser;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.arachnidium.core.Handle;
import org.arachnidium.core.SingleWindow;
import org.arachnidium.core.WindowManager;
import org.arachnidium.core.components.overriden.Cookies;
import org.arachnidium.model.common.Application;
import org.arachnidium.model.interfaces.IDecomposable;
import org.arachnidium.model.interfaces.IHasManyHandlesWithURL;

/**
 * Representation of a browser application
 */
public abstract class BrowserApplication extends Application implements
		IHasManyHandlesWithURL {

	protected final Cookies cookies;

	protected BrowserApplication(SingleWindow window) {
		super(window);
		cookies = driverEncapsulation.getCookies();
	}

	/**
	 * Gets a functional part (page object) from the new opened handle using
	 * list of regular expressions that describe expected URLs
	 */
	@Override
	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			List<String> urls) {
		Handle newHandle = ((WindowManager) manager).getNewHandle(urls);
		Class<?>[] params = new Class[] { Handle.class };
		Object[] values = new Object[] { newHandle };
		return get(partClass,
				replaceHandleParamIfItNeedsToBe(params, partClass, newHandle),
				values);
	}

	/**
	 * Gets a functional part (page object) from the new opened handle using
	 * list of regular expressions that describe expected URLs and index of
	 * required frame
	 */
	@Override
	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			List<String> urls, Integer frameIndex) {
		Handle newHandle = ((WindowManager) manager).getNewHandle(urls);
		Class<?>[] params = new Class[] { Handle.class, Integer.class };
		Object[] values = new Object[] { newHandle, frameIndex };
		return get(partClass,
				replaceHandleParamIfItNeedsToBe(params, partClass, newHandle),
				values);
	}

	/**
	 * Gets a functional part (page object) from the new opened handle using
	 * list of regular expressions that describe expected URLs and timeout
	 */
	@Override
	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			List<String> urls, long timeOutSec) {
		Handle newHandle = ((WindowManager) manager).getNewHandle(timeOutSec,
				urls);
		Class<?>[] params = new Class[] { Handle.class };
		Object[] values = new Object[] { newHandle };
		return get(partClass,
				replaceHandleParamIfItNeedsToBe(params, partClass, newHandle),
				values);
	}

	/**
	 * Gets a functional part (page object) from the new opened handle using
	 * list of regular expressions that describe expected URLs, timeout and
	 * index of required frame
	 */
	@Override
	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			List<String> urls, long timeOutSec, Integer frameIndex) {
		Handle newHandle = ((WindowManager) manager).getNewHandle(timeOutSec,
				urls);
		Class<?>[] params = new Class[] { Handle.class, Integer.class };
		Object[] values = new Object[] { newHandle, frameIndex };
		return get(partClass,
				replaceHandleParamIfItNeedsToBe(params, partClass, newHandle),
				values);
	}

	@Override
	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			String title) {
		return super.getFromNewHandle(partClass, title);
	}

	@Override
	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			String title, Integer frameIndex) {
		return super.getFromNewHandle(partClass, title, frameIndex);
	}

	@Override
	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			String title, long timeOutSec) {
		return super.getFromNewHandle(partClass, title, timeOutSec);
	}

	@Override
	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			String title, long timeOutSec, Integer frameIndex) {
		return super.getFromNewHandle(partClass, title, timeOutSec, frameIndex);
	}

	/**
	 * Gets a functional part (page object) from the new opened handle using
	 * expected URL
	 */
	@Override
	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			final URL url) {
		Handle newHandle = ((WindowManager) manager)
				.getNewHandle(new ArrayList<String>() {
					private static final long serialVersionUID = -1L;
					{
						add(url.toString());
					}
				});
		Class<?>[] params = new Class[] { Handle.class };
		Object[] values = new Object[] { newHandle };
		return get(partClass,
				replaceHandleParamIfItNeedsToBe(params, partClass, newHandle),
				values);
	}

	/**
	 * Gets a functional part (page object) from the new opened handle using
	 * expected URL and index of required frame
	 */
	@Override
	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			final URL url, Integer frameIndex) {
		Handle newHandle = ((WindowManager) manager)
				.getNewHandle(new ArrayList<String>() {
					private static final long serialVersionUID = -1L;
					{
						add(url.toString());
					}
				});
		Class<?>[] params = new Class[] { Handle.class, Integer.class };
		Object[] values = new Object[] { newHandle, frameIndex };
		return get(partClass,
				replaceHandleParamIfItNeedsToBe(params, partClass, newHandle),
				values);
	}

	/**
	 * Gets a functional part (page object) from the new opened handle using
	 * expected URL, timeout and index of required frame
	 */
	@Override
	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			final URL url, Integer frameIndex, long timeOutSec) {
		Handle newHandle = ((WindowManager) manager).getNewHandle(timeOutSec,
				new ArrayList<String>() {
					private static final long serialVersionUID = -1L;
					{
						add(url.toString());
					}
				});
		Class<?>[] params = new Class[] { Handle.class, Integer.class };
		Object[] values = new Object[] { newHandle, frameIndex };
		return get(partClass,
				replaceHandleParamIfItNeedsToBe(params, partClass, newHandle),
				values);
	}

	/**
	 * Gets a functional part (page object) from the new opened handle using
	 * expected URL and timeout
	 */
	@Override
	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			final URL url, long timeOutSec) {
		Handle newHandle = ((WindowManager) manager).getNewHandle(timeOutSec,
				new ArrayList<String>() {
			private static final long serialVersionUID = -1L;
			{
				add(url.toString());
			}
		});
		Class<?>[] params = new Class[] { Handle.class };
		Object[] values = new Object[] { newHandle };
		return get(partClass,
				replaceHandleParamIfItNeedsToBe(params, partClass, newHandle),
				values);
	}

	/**
	 * Gets a functional part (page object) from the new opened handle using
	 * list of regular expressions that describe expected URLs and path to
	 * required frame
	 */
	@Override
	public <T extends IDecomposable> T getFromNewHandle(String pathToFrame,
			Class<T> partClass, List<String> urls) {
		Handle newHandle = ((WindowManager) manager).getNewHandle(urls);
		Class<?>[] params = new Class[] { Handle.class, String.class };
		Object[] values = new Object[] { newHandle, pathToFrame };
		return get(partClass,
				replaceHandleParamIfItNeedsToBe(params, partClass, newHandle),
				values);
	}

	/**
	 * Gets a functional part (page object) from the new opened handle using
	 * list of regular expressions that describe expected URLs, timeout and path
	 * to the required frame
	 */
	@Override
	public <T extends IDecomposable> T getFromNewHandle(String pathToFrame,
			Class<T> partClass, List<String> urls, long timeOutSec) {
		Handle newHandle = ((WindowManager) manager).getNewHandle(timeOutSec,
				urls);
		Class<?>[] params = new Class[] { Handle.class, String.class };
		Object[] values = new Object[] { newHandle, pathToFrame };
		return get(partClass,
				replaceHandleParamIfItNeedsToBe(params, partClass, newHandle),
				values);
	}

	@Override
	public <T extends IDecomposable> T getFromNewHandle(String pathToFrame,
			Class<T> partClass, String title) {
		return super.getFromNewHandle(pathToFrame, partClass, title);
	}

	@Override
	public <T extends IDecomposable> T getFromNewHandle(String pathToFrame,
			Class<T> partClass, String title, long timeOutSec) {
		return super
				.getFromNewHandle(pathToFrame, partClass, title, timeOutSec);
	}

	/**
	 * Gets a functional part (page object) from the new opened handle using
	 * expected URL and path to the required frame
	 */
	@Override
	public <T extends IDecomposable> T getFromNewHandle(String pathToFrame,
			Class<T> partClass, final URL url) {
		Handle newHandle = ((WindowManager) manager)
				.getNewHandle(new ArrayList<String>() {
					private static final long serialVersionUID = -1L;
					{
						add(url.toString());
					}
				});
		Class<?>[] params = new Class[] { Handle.class, String.class };
		Object[] values = new Object[] { newHandle, pathToFrame };
		return get(partClass,
				replaceHandleParamIfItNeedsToBe(params, partClass, newHandle),
				values);
	}

	/**
	 * Gets a functional part (page object) from the new opened handle using
	 * expected URL, timeout and path to the required frame
	 */
	@Override
	public <T extends IDecomposable> T getFromNewHandle(String pathToFrame,
			Class<T> partClass, final URL url, long timeOutSec) {
		Handle newHandle = ((WindowManager) manager).getNewHandle(timeOutSec,
				new ArrayList<String>() {
					private static final long serialVersionUID = -1L;
					{
						add(url.toString());
					}
				});
		Class<?>[] params = new Class[] { Handle.class, Integer.class };
		Object[] values = new Object[] { newHandle, pathToFrame };
		return get(partClass,
				replaceHandleParamIfItNeedsToBe(params, partClass, newHandle),
				values);
	}

	public WindowManager getWindowManager() {
		return (WindowManager) manager;
	}
}
