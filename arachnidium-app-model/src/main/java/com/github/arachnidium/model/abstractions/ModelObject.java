/*
 +Copyright 2014 Arachnidium contributors
 +Copyright 2014 Software Freedom Conservancy
 +
 +Licensed under the Apache License, Version 2.0 (the "License");
 +you may not use this file except in compliance with the License.
 +You may obtain a copy of the License at
 +
 +     http://www.apache.org/licenses/LICENSE-2.0
 +
 +Unless required by applicable law or agreed to in writing, software
 +distributed under the License is distributed on an "AS IS" BASIS,
 +WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 +See the License for the specific language governing permissions and
 +limitations under the License.
 + */

package com.github.arachnidium.model.abstractions;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;



import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.internal.WrapsDriver;



import com.github.arachnidium.core.BrowserWindow;
import com.github.arachnidium.core.Handle;
import com.github.arachnidium.core.MobileScreen;
import com.github.arachnidium.core.WebDriverEncapsulation;
import com.github.arachnidium.core.components.WebdriverComponent;
import com.github.arachnidium.core.components.common.Awaiting;
import com.github.arachnidium.core.components.common.DriverLogs;
import com.github.arachnidium.core.interfaces.IDestroyable;
import com.github.arachnidium.model.interfaces.IDecomposable;
import com.github.arachnidium.model.interfaces.IModelObjectExceptionHandler;
import com.github.arachnidium.model.support.HowToGetByFrames;
import com.github.arachnidium.model.support.annotations.IDefaultAnnotationReader;

/**
 * This is a basic abstraction 
 * which describes objects are used for modeling application.
 * 
 * @param <S> It means that objects can be stationed on browser window or mobile application screen.<br/>
 * 
 * If there is<br/> 
 * <code>&lt;S extends {@link Handle}&gt;</code> or <br/> * 
 * <code>&lt;{@link Handle}&gt;</code> it means that 
 * both {@link BrowserWindow} and {@link MobileScreen} are possible. It is good
 * for situations when interaction with UI of  browser client and the perfectly similar mobile client 
 * has to be automated.<br/>
 * <br/>
 * If it needs to be bounded by only one {@link Handle} subclass ({@link BrowserWindow} or {@link MobileScreen})
 * then <br/>
 * - <code>&lt;{@link BrowserWindow}&gt;</code> - only browser windows <br/>
 * <br/>
 * or<br/> 
 * <br/>
 * - <code>&lt;{@link MobileScreen}&gt;</code> - only mobile screens
 */
public abstract class ModelObject<S extends Handle> implements IDestroyable,
		IDecomposable, WrapsDriver {
	protected final S handle; //window or mobile context. This object is stationed on it.
	private final WebDriverEncapsulation driverEncapsulation;

	protected final Awaiting awaiting; //performs waiting
	protected final DriverLogs logs; //is for getting WebDriver logs
	
	//this is for interception and automatically handling exceptions
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

	@SuppressWarnings("rawtypes")
	final List<ModelObject> children = Collections
			.synchronizedList(new ArrayList<ModelObject>());

	/**
	 * This is the general constructor.
	 * 
	 * @param handle is the given browser window or 
	 * mobile context which currently present
	 * 
	 */
	protected ModelObject(S handle) {
		this.handle = handle;
		driverEncapsulation = handle.driverEncapsulation;
		awaiting = new Awaiting(getWrappedDriver());
		logs = driverEncapsulation.getComponent(DriverLogs.class);

		IDefaultAnnotationReader idar = new IDefaultAnnotationReader() {
		};
		UsedImplicitExceptionHandlers[] annotations = idar.getAnnotations(
				UsedImplicitExceptionHandlers.class, this.getClass());
		if (annotations.length != 0) {
			UsedImplicitExceptionHandlers ueh = annotations[0];
			List<Class<? extends ModelObjectExceptionHandler>> throwableHandlers = Arrays
					.asList(ueh.areUsed());
			throwableHandlers.forEach((handler) -> {
				try {
					checkInExceptionHandler(handler.newInstance());
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			});
		}
	}

	/**
	 * Adds the child object
	 * 
	 * @param child is the child {@link ModelObject}
	 */
	protected void addChild(ModelObject<?> child) {
		children.add(child);
	}

	/**
	 * This method add an object that 
	 * performs automatically handling of 
	 * some caught exception.  
	 * 
	 * @param exceptionHandler is the object which
	 * performs automatically handling of 
	 * some caught exception
	 * 
	 * @see ModelObjectExceptionHandler
	 * 
	 * @see IModelObjectExceptionHandler 
	 */
	public void checkInExceptionHandler(
			ModelObjectExceptionHandler exceptionHandler) {
		checkedInExceptionHandlers.add(exceptionHandler);
	}

	public void checkOutExceptionHandler(
			ModelObjectExceptionHandler exceptionHandler) {
		checkedInExceptionHandlers.remove(exceptionHandler);
	}

	/**
	 * This method destroys information
	 * about child objects 
	 */
	@Override
	public void destroy() {
		for (ModelObject<?> child : children)
			child.destroy();
		children.clear();
	}

	/**
	 * @see com.github.arachnidium.model.interfaces.IDecomposable#getPart(java.lang.Class)
	 */
	@Override
	public abstract <T extends IDecomposable> T getPart(Class<T> partClass);

	/**
	 * @see com.github.arachnidium.model.interfaces.IDecomposable#getPart(java.lang.Class, com.github.arachnidium.model.support.HowToGetByFrames)
	 * 
	 * @see HowToGetByFrames
	 */
	@Override
	public abstract <T extends IDecomposable> T getPart(Class<T> partClass,
			HowToGetByFrames pathStrategy);
	
	/**
	 * @see IDecomposable#getPart(Class, By)
	 */
	@Override
	public abstract <T extends IDecomposable> T getPart(Class<T> partClass, By by);

	/**
	 * @see IDecomposable#getPart(Class, HowToGetByFrames, By)
	 */
	@Override
	public  abstract <T extends IDecomposable> T getPart(Class<T> partClass,
			HowToGetByFrames pathStrategy, By by);	

	/**
	 * @see org.openqa.selenium.internal.WrapsDriver#getWrappedDriver()
	 */
	@Override
	public WebDriver getWrappedDriver() {
		return driverEncapsulation.getWrappedDriver();
	}

	/**
	 * @param required {@link WebdriverComponent} subclass
	 * @return The instance of required {@link WebdriverComponent} subclass
	 * 
	 * It is supposed to be for internal usage
	 */
	protected final <T extends WebdriverComponent> T getComponent(Class<T> required) {
		return driverEncapsulation.getComponent(required);
	}

	/**
	 * 
	 * @param required {@link WebdriverComponent} subclass
	 * 
	 * @param params is a Class[] which excludes {@link WebDriver}.class
	 * {@link WebDriver} + given Class[] should match to {@link WebdriverComponent} subclass
	 * constructor parameters
	 *   
	 * @param values is a Object[] which excludes {@link WebDriver} instance
	 * {@link WebDriver} instance + given Object[] should match to {@link WebdriverComponent} subclass
	 * constructor 
	 * 
	 * @return The instance of required {@link WebdriverComponent} subclass
	 * 
	 * It is supposed to be for internal usage
	 */
	protected final <T extends WebdriverComponent> T getComponent(Class<T> required,
			Class<?>[] params, Object[] values) {
		return driverEncapsulation.getComponent(required, params, values);
	}
	
	public final WebDriverEncapsulation getWebDriverEncapsulation(){
		return driverEncapsulation;
	}
	
	public Handle getHandle(){
		return handle;
	}
}
