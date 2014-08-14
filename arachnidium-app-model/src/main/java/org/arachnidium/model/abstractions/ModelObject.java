package org.arachnidium.model.abstractions;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.arachnidium.core.Handle;
import org.arachnidium.core.WebDriverEncapsulation;
import org.arachnidium.core.components.common.Awaiting;
import org.arachnidium.core.components.common.DriverLogs;
import org.arachnidium.core.components.common.ScriptExecutor;
import org.arachnidium.core.interfaces.IDestroyable;
import org.arachnidium.model.interfaces.IDecomposable;
import org.arachnidium.model.interfaces.IModelObjectExceptionHandler;
import org.arachnidium.model.support.FramePathStrategy;

public abstract class ModelObject implements IDestroyable, IDecomposable {
	protected final Handle handle; // handle that object is placed on
	protected final WebDriverEncapsulation driverEncapsulation; // wrapped web
																// driver
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
					(proxy, method, args) -> {
						// it needs to know exception
						Throwable t = (Throwable) args[4];
						for (ModelObjectExceptionHandler handler : checkedInExceptionHandlers)
							// it looks for the suitable handler
							if (handler.isThrowableInList(t.getClass()))
								try {
									return method.invoke(handler, args);
								} catch (Exception e) {
									continue; // it wasn't the suitable
												// handler
								}
						// if there are no suitable handlers
						throw t;
					});

	final List<ModelObject> children = Collections
			.synchronizedList(new ArrayList<ModelObject>());
	protected ModelObject(Handle handle) {
		this.handle = handle;
		driverEncapsulation = handle.getDriverEncapsulation();
		awaiting = driverEncapsulation.getComponent(Awaiting.class);
		scriptExecutor = driverEncapsulation.getComponent(ScriptExecutor.class);
		logs = driverEncapsulation.getComponent(DriverLogs.class);
	}

	protected void addChild(ModelObject child) {
		children.add(child);
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
	public void destroy() {
		for (ModelObject child : children)
			child.destroy();
		children.clear();
	}

	@Override
	public abstract <T extends IDecomposable> T getPart(Class<T> partClass);

	@Override
	public abstract <T extends IDecomposable> T getPart(Class<T> partClass,
			FramePathStrategy pathStrategy);
}
