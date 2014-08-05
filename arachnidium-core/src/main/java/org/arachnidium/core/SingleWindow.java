package org.arachnidium.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.arachnidium.core.components.common.NavigationTool;
import org.arachnidium.core.components.common.WindowTool;
import org.arachnidium.core.eventlisteners.IWindowListener;
import org.arachnidium.core.interfaces.IExtendedWindow;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.Point;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.remote.UnreachableBrowserException;

/**
 * @author s.tihomirov It is performs actions on a single window
 */
public final class SingleWindow extends Handle implements Navigation,
		IExtendedWindow {
	private final WindowTool windowTool;
	private final NavigationTool navigationTool;
	private final List<IWindowListener> windowEventListeners = new ArrayList<IWindowListener>();
	private final InvocationHandler windowListenerInvocationHandler = (proxy,
			method, args) -> {
		for (IWindowListener eventListener : windowEventListeners)
			method.invoke(eventListener, args);
		return null;
	};
	/**
	 * It listens to window events and invokes listener methods
	 */
	private final IWindowListener windowListenerProxy = (IWindowListener) Proxy
			.newProxyInstance(IWindowListener.class.getClassLoader(),
					new Class[] { IWindowListener.class },
					windowListenerInvocationHandler);

	SingleWindow(String handle, WindowManager windowManager) {
		super(handle, windowManager);
		this.windowTool = driverEncapsulation.getComponent(WindowTool.class);
		this.navigationTool = driverEncapsulation.getComponent(NavigationTool.class);
		;
				windowEventListeners.addAll(InnerSPIServises.getBy(driverEncapsulation)
						.getServices(IWindowListener.class));
				windowListenerProxy.whenNewHandleIsAppeared(this);
	}

	public void addListener(IWindowListener listener) {
		windowEventListeners.add(listener);
	}

	@Override
	public synchronized void back() {
		requestToMe();
		navigationTool.back();
	}

	public synchronized void close() throws UnclosedWindowException,
	NoSuchWindowException, UnhandledAlertException,
	UnreachableBrowserException {
		try {
			windowListenerProxy.beforeWindowIsClosed(this);
			((WindowManager) nativeManager).close(handle);
			windowListenerProxy.whenWindowIsClosed(this);
			destroy();
		} catch (UnhandledAlertException | UnclosedWindowException e) {
			throw e;
		} catch (NoSuchWindowException e) {
			destroy();
			throw e;
		}
	}

	@Override
	public void destroy() {
		super.destroy();
		removeAllListeners();
	}

	@Override
	public synchronized void forward() {
		requestToMe();
		navigationTool.forward();
	}

	@Override
	public synchronized String getCurrentUrl() throws NoSuchWindowException {
		return ((WindowManager) nativeManager).getWindowURLbyHandle(handle);
	}

	@Override
	public synchronized Point getPosition() {
		requestToMe();
		return windowTool.getPosition();
	}

	@Override
	public synchronized Dimension getSize() {
		requestToMe();
		return windowTool.getSize();
	}

	@Override
	public synchronized String getTitle() {
		return ((WindowManager) nativeManager).getTitleByHandle(handle);
	}

	@Override
	public synchronized void maximize() {
		requestToMe();
		windowListenerProxy.beforeWindowIsMaximized(this);
		windowTool.maximize();
		windowListenerProxy.whenWindowIsMaximized(this);
	}

	@Override
	public synchronized void refresh() {
		requestToMe();
		windowListenerProxy.beforeWindowIsRefreshed(this);
		navigationTool.refresh();
		windowListenerProxy.whenWindowIsRefreshed(this);
	}

	public void removeAllListeners() {
		windowEventListeners.clear();
	}

	public void removeListener(IWindowListener listener) {
		windowEventListeners.remove(listener);
	}

	@Override
	void requestToMe() {
		windowListenerProxy.beforeIsSwitchedOn(this);
		super.requestToMe();
		windowListenerProxy.whenIsSwitchedOn(this);
	}

	@Override
	public synchronized void setPosition(Point position) {
		requestToMe();
		windowListenerProxy.beforeWindowIsMoved(this, position);
		windowTool.setPosition(position);
		windowListenerProxy.whenWindowIsMoved(this, position);
	}

	@Override
	public synchronized void setSize(Dimension size) {
		requestToMe();
		windowListenerProxy.beforeWindowIsResized(this, size);
		windowTool.setSize(size);
		windowListenerProxy.whenWindowIsResized(this, size);
	}

	@Override
	public synchronized void to(String link) {
		requestToMe();
		navigationTool.to(link);
	}

	@Override
	public synchronized void to(URL url) {
		requestToMe();
		navigationTool.to(url);

	}
}