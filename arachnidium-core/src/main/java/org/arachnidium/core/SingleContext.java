package org.arachnidium.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import org.arachnidium.core.components.bydefault.ComponentFactory;
import org.arachnidium.core.components.bydefault.Rotator;
import org.arachnidium.core.eventlisteners.IContextListener;
import org.arachnidium.core.interfaces.IHasActivity;
import org.openqa.selenium.Rotatable;
import org.openqa.selenium.ScreenOrientation;

public final class SingleContext extends Handle implements IHasActivity, Rotatable{

	private final List<IContextListener> contextEventListeners = new ArrayList<IContextListener>();
	private final Rotator rotator;
	
	private final InvocationHandler contextListenerInvocationHandler = new InvocationHandler() {
		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			for (IContextListener eventListener : contextEventListeners) {
				method.invoke(eventListener, args);
			}
			return null;
		}
	};

	/**
	* It listens to context events and invokes listener methods
	*/
	private final IContextListener contextListenerProxy = (IContextListener) Proxy
			.newProxyInstance(IContextListener.class.getClassLoader(),
					new Class[] { IContextListener.class },
					 contextListenerInvocationHandler);
	
	SingleContext(String context, ContextManager manager){
		super(context, manager);
		rotator = ComponentFactory.getComponent(Rotator.class, 
				driverEncapsulation.getWrappedDriver());
		contextEventListeners.addAll(InnerSPIServises.getBy(driverEncapsulation)
				.getServices(IContextListener.class));
		contextListenerProxy.whenNewHandleIsAppeared(this);
	}
	
	public void addListener(IContextListener listener) {
		contextEventListeners.add(listener);
	}

	public void removeAllListeners() {
		contextEventListeners.clear();
	}

	public void removeListener(IContextListener listener) {
		contextEventListeners.remove(listener);
	}

	@Override
	void requestToMe() {
		contextListenerProxy.beforeIsSwitchedOn(this);
		super.requestToMe();
		contextListenerProxy.whenIsSwitchedOn(this);
	}

	@Override
	public String currentActivity() {
		return ((ContextManager) nativeManager).getActivityByHandle(handle);
	}

	@Override
	public synchronized void rotate(ScreenOrientation orientation) {
		requestToMe();
		rotator.rotate(orientation);
		
	}

	@Override
	public synchronized ScreenOrientation getOrientation() {
		requestToMe();
		return rotator.getOrientation();
	}

	@Override
	public void destroy() {
		super.destroy();
		removeAllListeners();
	}

}
