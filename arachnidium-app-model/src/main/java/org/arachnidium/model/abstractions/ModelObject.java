package org.arachnidium.model.abstractions;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.arachnidium.model.interfaces.*;
import org.arachnidium.core.Handle;
import org.arachnidium.core.WebDriverEncapsulation;
import org.arachnidium.core.components.bydefault.ComponentFactory;
import org.arachnidium.core.components.bydefault.DriverLogs;
import org.arachnidium.core.components.bydefault.ScriptExecutor;
import org.arachnidium.core.components.bydefault.WebdriverInterfaceImplementor;
import org.arachnidium.core.components.overriden.Awaiting;
import org.arachnidium.core.interfaces.IDestroyable;

public abstract class ModelObject implements IDestroyable, IDecomposable {
	protected final Handle handle; // handle that object is placed on
	protected final WebDriverEncapsulation driverEncapsulation; // wrapped web driver
															// for situations
															// when it needs to
															// be used

	protected final Awaiting awaiting;
	protected final ScriptExecutor scriptExecutor;
	protected final DriverLogs logs;
	protected final HashSet<ModelObjectExceptionHandler> checkedInExceptionHandlers = new HashSet<ModelObjectExceptionHandler>();

	// this will be invoked when some exception is caught out
	IModelObjectExceptionHandler exceptionHandler = (IModelObjectExceptionHandler) Proxy
			.newProxyInstance(
					IModelObjectExceptionHandler.class.getClassLoader(),
					new Class[] { IModelObjectExceptionHandler.class },
					new InvocationHandler() {
						public Object invoke(Object proxy, Method method,
								Object[] args) throws Throwable {
							// it needs to know exception
							Throwable t = (Throwable) args[4];
							for (ModelObjectExceptionHandler handler : checkedInExceptionHandlers) {
								// it looks for the suitable handler
								if (handler.isThrowableInList(t.getClass())) {
									try {
										return method.invoke(handler, args);
									} catch (Exception e) {
										continue; // it wasn't the suitable
													// handler
									}
								}
							}
							// if there are no suitable handlers
							throw t;
						}
					});

	final List<ModelObject> children = Collections
			.synchronizedList(new ArrayList<ModelObject>());

	protected <T extends WebdriverInterfaceImplementor> T getComponent(Class<T> required){
		return ComponentFactory.getComponent(required, 
				driverEncapsulation.getWrappedDriver());
	}
	
	protected ModelObject(Handle handle){
			this.handle = handle;
			driverEncapsulation = handle.getDriverEncapsulation();
			awaiting = driverEncapsulation.getAwaiting();
			scriptExecutor = driverEncapsulation.getScriptExecutor();
			logs = driverEncapsulation.getLogs();
	}

	public void destroy() {
		for (ModelObject child : children) {
			child.destroy();
		}
		children.clear();
	}

	public void checkInExceptionHandler(
			ModelObjectExceptionHandler exceptionHandler) {
		checkedInExceptionHandlers.add(exceptionHandler);
	}

	public void checkOutExceptionHandler(
			ModelObjectExceptionHandler exceptionHandler) {
		checkedInExceptionHandlers.remove(exceptionHandler);
	}

	@Override
	public abstract <T extends IDecomposable> T getPart(Class<T> partClass);

	@Override
	public abstract <T extends IDecomposable> T getPart(Class<T> partClass,
			Integer frameIndex);

	@Override
	public abstract <T extends IDecomposable> T getPart(Class<T> partClass,
			String pathToFrame);

	protected void addChild(ModelObject child) {
		children.add(child);
	}
}
