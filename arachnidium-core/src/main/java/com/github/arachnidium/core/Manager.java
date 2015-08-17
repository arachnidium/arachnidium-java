package com.github.arachnidium.core;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.arachnidium.util.proxy.EnhancedProxyFactory;

import org.apache.commons.lang3.ArrayUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.AbstractApplicationContext;

import com.github.arachnidium.core.components.common.AlertHandler;
import com.github.arachnidium.core.components.common.Awaiting;
import com.github.arachnidium.core.fluenthandle.IHowToGetHandle;
import com.github.arachnidium.core.interfaces.IDestroyable;
import com.github.arachnidium.core.interfaces.IHasHandle;
import com.github.arachnidium.core.settings.AlertIsPresentTimeOut;
import com.github.arachnidium.core.settings.HandleWaitingTimeOut;

/**
 * This an abstraction that describes the
 * way how to get a new {@link Handle} and 
 * how to switch from one another
 * 
 * @param <U> it is a s strategy of the {@link Handle} receiving
 * @param <V> it is the expected {@link Handle} class e.g {@link BrowserWindow} or {@link MobileScreen}
 */
public abstract class Manager<U extends IHowToGetHandle, V extends Handle> implements IDestroyable {

	static long getTimeOut(Long possibleTimeOut) {
		if (possibleTimeOut == null)
			return defaultTimeOut;
		else
			return possibleTimeOut;
	}

	final Awaiting awaiting;
	private final WebDriverEncapsulation driverEncapsulation;
	protected final AbstractApplicationContext context;
	boolean isAlive = true;
	private final HandleReceptionist handleReceptionist = new HandleReceptionist();

	private final static Map<WebDriverEncapsulation, Manager<?,?>> managerMap = Collections
			.synchronizedMap(new HashMap<WebDriverEncapsulation, Manager<?,?>>());
	final static long defaultTimeOut = 5; // we will wait
	private String STUB_HANDLE = "STUB";
	private String currentHandle;

	Manager(WebDriverEncapsulation initialDriverEncapsulation, AbstractApplicationContext context) {
		driverEncapsulation = initialDriverEncapsulation;
		awaiting = new Awaiting(driverEncapsulation.getWrappedDriver());
		managerMap.put(driverEncapsulation, this);
		driverEncapsulation.addDestroyable(this);
		this.context = context;
	}

	/**
	 * Focus on the given window or mobile context will be implemented by
	 * subclasses
	 */
	abstract void changeActive(String handle);

	/**
	 * This method destroys information
	 * about related windows or mobile contexts
	 */
	@Override
	public void destroy() {
		managerMap.remove(driverEncapsulation);
		isAlive = false;
		List<IHasHandle> toBeDestroyed = handleReceptionist.getInstantiated();
		toBeDestroyed.forEach((hasHandle) -> ((IDestroyable) hasHandle)
				.destroy());
	}

	/**
	 * @return {@link Alert} which is present
	 * @throws {@link NoAlertPresentException}
	 */
	public Alert getAlert() throws NoAlertPresentException{
		Long time = driverEncapsulation.getWrappedConfiguration()
				.getSection(AlertIsPresentTimeOut.class).getAlertIsPresentTimeOut();
		return driverEncapsulation.getComponent(AlertHandler.class,
				new Class[] { long.class },
				new Object[] {time});
		
	}

	/**
	 * @param timeOut It is an explicitly given time (seconds) 
	 *    to wait for Alert is present
	 * @return {@link Alert} which is present
	 * @throws {@link NoAlertPresentException}
	 */
	public synchronized Alert getAlert(long timeOut)
			throws NoAlertPresentException {
		return driverEncapsulation.getComponent(AlertHandler.class,
				new Class[] { long.class },
				new Object[] { timeOut });
	}
	
	@SuppressWarnings("unchecked")
	private U returnRelevantHowToGetStrategy(){
		ParameterizedType generic = (ParameterizedType) this.getClass().getGenericSuperclass();
		Class<U> howToGetClass = null;
		try {
			howToGetClass = (Class<U>) Class
					.forName(generic.getActualTypeArguments()[0].getTypeName());
		} catch (Exception e) {
			throw new RuntimeException(e); 
		}
		
		try {
			return howToGetClass.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param An expected window/mobile context index
	 * @return Window or mobile context. Actually it returns CGLIB proxy
	 * which instantiate the real object by the invocation 
	 */
	public V getHandle(int index){
		return getHandle(index, getTimeOut(getHandleWaitingTimeOut()
				.getHandleWaitingTimeOut())); 
	}
	
	/**
	 * @param An expected window/mobile context index
	 * @param It is an explicitly given time (seconds) to wait for
	 *            window/mobile context is present
	 * @return Window or mobile context. Actually it returns CGLIB proxy
	 * which instantiate the real object by the invocation 
	 */
	public V getHandle(int index, long timeOut){
		U howToGet = returnRelevantHowToGetStrategy();
		howToGet.setExpected(index);
		return getHandle(timeOut, howToGet);
	}
	
	/**
	 * 
	 * @param index
	 * @param by
	 * @param timeOut
	 * @return
	 */
	public V getHandle(int index, By by, long timeOut){
		U howToGet = returnRelevantHowToGetStrategy();
		howToGet.setExpected(index);
		return getHandle(timeOut, howToGet, by, null);
	}
	
	/**
	 * 
	 * @param index
	 * @param by
	 * @return
	 */
	public V getHandle(int index, By by){
		U howToGet = returnRelevantHowToGetStrategy();
		howToGet.setExpected(index);
		return getHandle(getHandleWaitingTimeOut()
				.getHandleWaitingTimeOut(), howToGet, by, null);
	}
	
	/**
	 * 
	 * @param index
	 * @param howToGetByFramesStrategy
	 * @param timeOut
	 * @return
	 */
	public V getHandle(int index, HowToGetByFrames howToGetByFramesStrategy, long timeOut){
		U howToGet = returnRelevantHowToGetStrategy();
		howToGet.setExpected(index);
		return getHandle(timeOut, howToGet, null, howToGetByFramesStrategy);
	}
	
	/**
	 * 
	 * @param index
	 * @param howToGetByFramesStrategy
	 * @return
	 */
	public V getHandle(int index, HowToGetByFrames howToGetByFramesStrategy){
		U howToGet = returnRelevantHowToGetStrategy();
		howToGet.setExpected(index);
		return getHandle(getHandleWaitingTimeOut()
				.getHandleWaitingTimeOut(), howToGet, null, howToGetByFramesStrategy);
	}
	
	/**
	 * 
	 * @param index
	 * @param by
	 * @param howToGetByFramesStrategy
	 * @param timeOut
	 * @return
	 */
	public V getHandle(int index, By by, HowToGetByFrames howToGetByFramesStrategy, long timeOut){
		U howToGet = returnRelevantHowToGetStrategy();
		howToGet.setExpected(index);
		return getHandle(timeOut, howToGet, by, howToGetByFramesStrategy);
	}
	
	/**
	 * 
	 * @param index
	 * @param by
	 * @param howToGetByFramesStrategy
	 * @return
	 */
	public V getHandle(int index, By by, HowToGetByFrames howToGetByFramesStrategy){
		U howToGet = returnRelevantHowToGetStrategy();
		howToGet.setExpected(index);
		return getHandle(getHandleWaitingTimeOut()
				.getHandleWaitingTimeOut(), howToGet, by, howToGetByFramesStrategy);
	}
	
	HandleReceptionist getHandleReceptionist() {
		return handleReceptionist;
	}

	/**
	 * @return Set of string window handles/context names
	 */
	abstract Set<String> getHandles();
	
	@SuppressWarnings("unchecked")
	private V createProxy(long timeOut, U howToGet, By by, 
			HowToGetByFrames howToGetByFramesStrategy){
		HandleInterceptor<U> hi = new HandleInterceptor<U>(
				this, howToGet, timeOut, by, howToGetByFramesStrategy);
		Class<?>[] params = new Class<?>[] {String.class, this.getClass(),
				By.class, HowToGetByFrames.class};
		Object[] values = new Object[] {STUB_HANDLE, this, by, howToGetByFramesStrategy};
		ParameterizedType generic = (ParameterizedType) this.getClass().getGenericSuperclass();
		Class<V> required = null;
		try {
			required = (Class<V>) Class
					.forName(generic.getActualTypeArguments()[1].getTypeName());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		V proxy = EnhancedProxyFactory.getProxy(required, params, values, hi);
		proxy.timeOut = timeOut;
		proxy.howToGetHandleStrategy = howToGet;
		return proxy;		
	}
	
	/**
	 * 
	 * @param parent
	 * @param by
	 * @param howToGetByFramesStrategy
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public V getHandle(V parent, By by, 
			HowToGetByFrames howToGetByFramesStrategy){
		HowToGetByFrames path = null;
		if (parent.howToGetByFramesStrategy != null){
			path = new HowToGetByFrames();
			List<Object> previousFrames = parent.howToGetByFramesStrategy.getFramePath();
			for (Object o: previousFrames){
				path.addNextFrame(o);
			}
		}
		
		if (howToGetByFramesStrategy != null){
			if (path == null)
				path = new HowToGetByFrames();
			List<Object> definedFramePath = howToGetByFramesStrategy.getFramePath();
			for (Object o: definedFramePath){
				path.addNextFrame(o);
			}
		}
		
		By usedBy = null;
		if (howToGetByFramesStrategy != null)
			usedBy = by;
		else{
			if (parent.by == null){
				usedBy = by;
			}
			else{
				By[] chain = new By[]{parent.by};
				if (by != null)
					chain = ArrayUtils.add(chain, by);
				usedBy = new ByChained(chain);
			}
		}
		
		return createProxy(parent.timeOut, (U) parent.howToGetHandleStrategy, 
				usedBy, path);
	}
	
	/**
	 * 
	 * @param parent
	 * @param by
	 * @return
	 */
	public V getHandle(V parent, By by){
		return getHandle(parent, by, null);
	}
	
	public V getHandle(V parent, 
			HowToGetByFrames howToGetByFramesStrategy){
		return getHandle(parent, null, howToGetByFramesStrategy);
	}
	
	
	public V getHandle(long timeOut, U howToGet, By by, 
			HowToGetByFrames howToGetByFramesStrategy){
		return createProxy(timeOut, howToGet, by, howToGetByFramesStrategy);
	}
	
	/**
	 * 
	 * @param howToGet
	 * @param by
	 * @param howToGetByFramesStrategy
	 * @return
	 */
	public V getHandle(U howToGet, By by, 
			HowToGetByFrames howToGetByFramesStrategy){
		return getHandle(getHandleWaitingTimeOut()
				.getHandleWaitingTimeOut(), howToGet, by, howToGetByFramesStrategy);
	}
	
	/**
	 * 
	 * @param timeOut
	 * @param howToGet
	 * @param by
	 * @return
	 */
	public V getHandle(long timeOut, U howToGet, By by){
		return getHandle(timeOut, howToGet, by, null);
	}
	
	/**
	 * 
	 * @param howToGet
	 * @param by
	 * @return
	 */
	public V getHandle(U howToGet, By by){
		return getHandle(getHandleWaitingTimeOut()
				.getHandleWaitingTimeOut(), howToGet, by, null);
	}
	
	/**
	 * 
	 * @param timeOut
	 * @param howToGet
	 * @param howToGetByFramesStrategy
	 * @return
	 */
	public V getHandle(long timeOut, U howToGet, HowToGetByFrames howToGetByFramesStrategy){
		return getHandle(timeOut, howToGet, null, howToGetByFramesStrategy);
	}
	
	/**
	 * 
	 * @param howToGet
	 * @param howToGetByFramesStrategy
	 * @return
	 */
	public V getHandle(U howToGet, HowToGetByFrames howToGetByFramesStrategy){
		return getHandle(getHandleWaitingTimeOut()
				.getHandleWaitingTimeOut(), howToGet, null, 
				howToGetByFramesStrategy);
	}	
	
	/**
	 * Returns window on mobile context 
	 * by conditions. 
	 * 
	 * @param timeOut It is an explicitly given time (seconds) to wait for
	 *            window/mobile context is present
	 *            
	 * @param howToGet Given strategy.
	 * @return Window or mobile context. Actually it returns CGLIB proxy
	 * which instantiate the real object by the invocation 
	 * 
	 * @see IHowToGetHandle. 
	 */
	public V getHandle(long timeOut, U howToGet){
		return getHandle(timeOut, howToGet, null, null);
	}
	
	/**
	 * Returns window on mobile context 
	 * by conditions. 
	 * 
	 * @param howToGet Given strategy. 
	 * @return Window or mobile context. Actually it returns CGLIB proxy
	 * which instantiate the real object by the invocation 
	 * 
	 * @see IHowToGetHandle
	 */
	public V getHandle(U howToGet){
		return getHandle(getTimeOut(getHandleWaitingTimeOut()
				.getHandleWaitingTimeOut()), howToGet); 
	}	
	
	
	/**
	 * Returns window on mobile context 
	 * by conditions. 
	 * 
	 * @param timeOut It is an explicitly given time (seconds) to wait for
	 *            window/mobile context is present
	 *            
	 * @param howToGet Given strategy.
	 * @return Window or mobile context.
	 *  
	 * @see IHowToGetHandle. 
	 */
	abstract V getRealHandle(long timeOut, U howToGet, By by, 
			HowToGetByFrames howToGetByFramesStrategy);	

	WebDriverEncapsulation getWebDriverEncapsulation() {
		return driverEncapsulation;
	}

	WebDriver getWrappedDriver() {
		return driverEncapsulation.getWrappedDriver();
	}

	/**
	 * @return <code>false</code> if wrapped {@link WebDriver} was shut down
	 */
	boolean isAlive() {
		return isAlive;
	}

	/**
	 * Sets focus on window/mobile context by string 
	 * parameter
	 * 
	 * @param String window handle/context name
	 */
	synchronized void switchTo(String handle) {
		if (!handle.equals(currentHandle))
			changeActive(handle);
	}

	/**
	 * @param timeOut It is an explicitly given time (seconds) to wait for
	 *            window/mobile context is present
	 *            
	 * @param howToGet Given strategy. 
	 * @return Window handle/context name
	 * 
	 * @see IHowToGetHandle
	 */
	abstract String getStringHandle(long timeOut, U howToGet);
	
	/**
	 * Gets a new created listenable {@link Handle} and notifies listener
	 * that there it is a new object
	 * 
	 * @param handle instantiated {@link Handle}
	 * @param beanName Is a name of the {@link Bean}
	 * @return listenable {@link Handle} instance
	 */
	@SuppressWarnings("unchecked")
	<T extends Handle> T returnNewCreatedListenableHandle(Handle handle, String beanName){
		T result = (T) context.getBean(beanName, handle, driverEncapsulation);
		if (!getHandleReceptionist().isInstantiated(handle.getHandle()))
			result.whenIsCreated();
		getHandleReceptionist().addKnown(handle);
		return result;
	}
	
	HandleWaitingTimeOut getHandleWaitingTimeOut() {
		return driverEncapsulation.getWrappedConfiguration()
				.getSection(HandleWaitingTimeOut.class);
	}
}
