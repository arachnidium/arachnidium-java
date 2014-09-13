package org.arachnidium.model.common;

import org.arachnidium.core.BrowserWindow;
import org.arachnidium.core.Handle;
import org.arachnidium.core.HowToGetBrowserWindow;
import org.arachnidium.core.HowToGetMobileScreen;
import org.arachnidium.core.Manager;
import org.arachnidium.core.MobileScreen;
import org.arachnidium.core.fluenthandle.IHowToGetHandle;
import org.arachnidium.model.abstractions.ModelObject;
import org.arachnidium.model.interfaces.IDecomposable;
import org.arachnidium.model.interfaces.IDecomposableByHandles;
import org.arachnidium.model.support.HowToGetByFrames;
import org.springframework.context.annotation.Configuration;

/**
 *<p>
 *<p>Common representation of any application.
 *<p>Using it you can model automated interaction with application 
 *<p>as with a complex aggregated object. 
 *<p>
 *<p>It is implemented as a factory by default.
 *<p>
 *@param <S> It means that objects can be stationed on browser window or 
 *<p>mobile application screen. As for application it means, that the first or
 *<p>the single {@link Handle} is a {@link BrowserWindow} or {@link MobileScreen}  
 *<p>
 *<p>If there is <b>S extends {@link Handle}</b> or <b>{@link Handle}</b> it means that 
 *<p>both {@link BrowserWindow} and {@link MobileScreen} are possible. It is good
 *<p>for situations when interaction with UI of  browser client and the same mobile client 
 *<p>has to be automated. 
 *
 *<p>If it needs to be bounded by only one {@link Handle} subclass ({@link BrowserWindow} or {@link MobileScreen})
 *<p>then 
 *<p>
 *<p><b>{@link BrowserWindow}</b>  only browser windows
 *<p>
 *<p>or 
 *<p>
 *<p><b>{@link MobileScreen}</b>  only mobile screens
 *<p> 
 *@param <U> It means the way of getting decomposable parts of the application. 
 *<p>It needs to be used when it is possible the interaction 
 *<p>with more than one window or mobile screen/context at the same time. 
 *<p> Possible strategies are {@link HowToGetBrowserWindow} and {@link HowToGetMobileScreen}
 *<p>If there is <b>S extends {@link IHowToGetHandle}</b> or <b>{@link IHowToGetHandle}</b> it means that 
 *<p>both strategies are possible. It is good
 *<p>for situations when interaction with UI of  browser client and the same mobile client 
 *<p>has to be automated. 
 */
public class Application<S extends Handle, U extends IHowToGetHandle> extends ModelObject<S> implements IDecomposableByHandles<U> {

	/**
	 *@see Manager
	 *<p>
	 *@see WindowManager 
	 *<p>
	 *@see ScreenManager
	 */
	protected final Manager<U> manager;
	
	/**
     *<p>handle is the given browser window or 
	 *<p>mobile context which currently present.
	 *<p>
	 *<p>The main form or general UI of the
	 *<p>application is stationed on this window or 
	 *<p>mobile context/screen 
	 */
	@SuppressWarnings("unchecked")
	protected Application(S handle) {
		super(handle);
		manager = (Manager<U>) handle.getManager();
		getWebDriverEncapsulation().addDestroyable(this);
	}

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
	 *<p>Creates a representation of UI or some logically final
	 *<p>and assumed to be repeatable part of UI that is stationed 
	 *<p>on the main form of the application.
	 *<p>
	 *<p>@param partClass It is class where 
	 *<p>representation is described
	 *<p>
	 *@see org.arachnidium.model.abstractions.ModelObject#getPart(java.lang.Class)
	 *<p>
	 *@see IDecomposable#getPart(Class)
     */
	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass) {
		return getPart(partClass, this.handle);
	}

    /**<p>
	 *<p>Creates a representation of UI or some logically final
	 *<p>and assumed to be repeatable part of UI that is stationed 
	 *<p>on the main form of the application inside frame (frame, iframe, etc)
	 *<p>It is actual for browser and mobile hybrid UI.
	 *<p>
	 *<p>@param partClass It is class where 
	 *<p>representation is described
	 *<p>
	 *<p>@param path is a path to frame specified by instance
	 *<p>of {@link HowToGetByFrames}
	 *<p>
	 *@see IDecomposable#getPart(Class, HowToGetByFrames)
	 *<p>
	 *@see ModelObject#getPart(Class, HowToGetByFrames)
     */
	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			HowToGetByFrames path) {
		return getPart(partClass, this.handle, path);
	}

	/**
	 *<p>This method shuts down the application
	 *<p>and starts the destroying of related
	 *<p>information
	 */
	public void quit() {
		getWebDriverEncapsulation().destroy();
	}

	/**<p>
	 *<p>Creates a representation of UI or some logically final
	 *<p>and assumed to be repeatable part of UI. This method 
	 *<p>used when where is mere than one browser window or 
	 *<p>mobile contexts (NATIVE_APP, WEBVIEW_0, WEBVIEW_1 and so on).
	 *<p>
	 *<p>@param partClass It is class where 
	 *<p>representation is described
	 *<p>
	 *<p>@param handleIndex is an expected index of the browser window
	 *<p>or mobile context.
	 *<p>
	 *<p>If the window/context is not instantly appeared the implicit
	 *<p>time out can be specified in {@link Configuration} like this
	 *<p>
	 *<p><p>"handleWaitingTimeOut":</p>
	 *<p><p>{</p>
	 *<p><p>&nbsp;&nbsp;"handleWaitingTimeOut":{</p>
	 *<p><p>&nbsp;&nbsp;&nbsp;&nbsp;"type":"LONG",</p>
	 *<p><p>&nbsp;&nbsp;&nbsp;&nbsp;"value":"time you want in seconds"<p>     
	 *<p><p>&nbsp;&nbsp;},</p>
	 *<p><p>}</p>
	 *<p>
	 *<p>Default time out is 5 seconds 
	 *<p>
	 *@see IDecomposableByHandles#getPart(Class, int) 
     */
	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			int handleIndex) {
		return getPart(partClass, manager.getHandle(handleIndex));
	}
	
	
	/**
	 *<p>
	 *<p>Creates a representation of UI or some logically final
	 *<p>and assumed to be repeatable part of UI. This method 
	 *<p>used when where is mere than one browser window or 
	 *<p>mobile contexts (NATIVE_APP, WEBVIEW_0, WEBVIEW_1 and so on).
	 *<p>
	 *<p>@param partClass It is class where 
	 *<p>representation is described
	 *<p>
	 *<p>@param handleIndex is an expected index of the browser window
	 *<p>or mobile context
	 *<p>
	 *<p>@param timeOut is a time to wait this window/context defined
	 *<p>explicitly. Time unit is seconds 
	 *<p>
	 *@see IDecomposableByHandles#getPart(Class, int, HowToGetByFrames)
     */
	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			int handleIndex, long timeOut) {
		return getPart(partClass, manager.getHandle(handleIndex, timeOut));
	}

	/**
	 *<p>
	 *<p>Creates a representation of UI or some logically final
	 *<p>and assumed to be repeatable part of UI. This method 
	 *<p>used when where is mere than one browser window or 
	 *<p>mobile contexts (NATIVE_APP, WEBVIEW_0, WEBVIEW_1 and so on).
	 *<p>This UI or fragment of UI is stationed inside frame (frame, iframe, etc)
	 *<p>It is actual for browser and mobile hybrid UI.
	 *<p>
	 *<p>@param partClass It is class where 
	 *<p>representation is described
	 *<p>
	 *<p>@param handleIndex is an expected index of the browser window
	 *<p>or mobile context
	 *<p>
	 *<p>
	 *<p>If the window/context is not instantly appeared the implicit
	 *<p>time out can be specified in {@link Configuration} like this
	 *<p>
	 *<p><p>"handleWaitingTimeOut":</p>
	 *<p><p>{</p>
	 *<p><p>&nbsp;&nbsp;"handleWaitingTimeOut":{</p>
	 *<p><p>&nbsp;&nbsp;&nbsp;&nbsp;"type":"LONG",</p>
	 *<p><p>&nbsp;&nbsp;&nbsp;&nbsp;"value":"time you want in seconds"<p>     
	 *<p><p>&nbsp;&nbsp;},</p>
	 *<p><p>}</p>
	 *<p>
	 *<p>Default time out is 5 seconds 
	 *<p>
	 *<p>@param path is a path to frame specified by instance
	 *<p>of {@link HowToGetByFrames}
	 *<p>
	 *@see IDecomposableByHandles#getPart(Class, int, HowToGetByFrames)
     */	
	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			int handleIndex, HowToGetByFrames path) {
		return getPart(partClass, manager.getHandle(handleIndex), path);
	}

	
	/**
	 *<p>
	 *<p>Creates a representation of UI or some logically final
	 *<p>and assumed to be repeatable part of UI. This method 
	 *<p>used when where is mere than one browser window or 
	 *<p>mobile contexts (NATIVE_APP, WEBVIEW_0, WEBVIEW_1 and so on).
	 *<p>This UI or fragment of UI is stationed inside frame (frame, iframe, etc)
	 *<p>It is actual for browser and mobile hybrid UI.
	 *<p>
	 *<p>@param partClass It is class where 
	 *<p>representation is described
	 *<p>
	 *<p>@param handleIndex is an expected index of the browser window
	 *<p>or mobile context
	 *<p>
	 *<p>@param path is a path to frame specified by instance
	 *<p>of {@link HowToGetByFrames}
	 *<p>
	 *<p>@param timeOut is a time to wait this window/context defined
	 *<p>explicitly. Time unit is seconds 
	 *<p>
	 *@see IDecomposableByHandles#getPart(Class, IHowToGetHandle, HowToGetByFrames, long)
     */		
	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			int handleIndex, HowToGetByFrames path, long timeOut) {
		return getPart(partClass, manager.getHandle(handleIndex, timeOut), path);
	}

	/**<p>
	 *<p>Creates a representation of UI or some logically final
	 *<p>and assumed to be repeatable part of UI. This method 
	 *<p>used when where is mere than one browser window or 
	 *<p>mobile contexts (NATIVE_APP, WEBVIEW_0, WEBVIEW_1 and so on).
	 *<p>
	 *<p>@param partClass It is class where 
	 *<p>representation is described
	 *<p>
	 *<p>@param howToGetHandle is a strategy of receiving a new browser window/context
	 *<p>defined by {@link IHowToGetHandle} implementor 
	 *<p>
	 *<p>If the window/context is not instantly appeared the implicit
	 *<p>time out can be specified in {@link Configuration} like this
	 *<p>
	 *<p>"handleWaitingTimeOut":</p>
	 *<p>{</p>
	 *<p>&nbsp;&nbsp;"handleWaitingTimeOut":{</p>
	 *<p>&nbsp;&nbsp;&nbsp;&nbsp;"type":"LONG",</p>
	 *<p>&nbsp;&nbsp;&nbsp;&nbsp;"value":"time you want in seconds"<p>     
	 *<p>&nbsp;&nbsp;},</p>
	 *<p>}</p>
	 *<p>
	 *<p>Default time out is 5 seconds 
	 *<p>
	 *@see IDecomposableByHandles#getPart(Class, IHowToGetHandle) 
     */	
	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			U howToGetHandle) {
		return getPart(partClass, manager.getHandle(howToGetHandle));
	}

	/**<p>
	 *<p>Creates a representation of UI or some logically final
	 *<p>and assumed to be repeatable part of UI. This method 
	 *<p>used when where is mere than one browser window or 
	 *<p>mobile contexts (NATIVE_APP, WEBVIEW_0, WEBVIEW_1 and so on).
	 *<p>
	 *<p>@param partClass It is class where 
	 *<p>representation is described
	 *<p>
	 *<p>@param howToGetHandle is a strategy of receiving a new browser window/context
	 *<p>defined by {@link IHowToGetHandle} implementor 
	 *<p>
	 *<p>@param timeOut is a time to wait this window/context defined
	 *<p>explicitly. Time unit is seconds 
	 *<p>
	 *<p>Default time out is 5 seconds 
	 *<p>
	 *@see IDecomposableByHandles#getPart(Class, IHowToGetHandle) 
     */		
	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			U howToGetHandle, long timeOut) {
		return getPart(partClass, manager.getHandle(timeOut, howToGetHandle));
	}

	/**<p>
	 *<p>Creates a representation of UI or some logically final
	 *<p>and assumed to be repeatable part of UI. This method 
	 *<p>used when where is mere than one browser window or 
	 *<p>mobile contexts (NATIVE_APP, WEBVIEW_0, WEBVIEW_1 and so on).
     *<p>This UI or fragment of UI is stationed inside frame (frame, iframe, etc)
	 *<p>It is actual for browser and mobile hybrid UI.
	 *<p>
	 *<p>@param partClass It is class where 
	 *<p>representation is described
	 *<p>
	 *<p>@param howToGetHandle is a strategy of receiving a new browser window/context
	 *<p>defined by {@link IHowToGetHandle} implementor 
	 *<p>
	 *<p>If the window/context is not instantly appeared the implicit
	 *<p>time out can be specified in {@link Configuration} like this
	 *<p>
	 *<p><p>"handleWaitingTimeOut":</p>
	 *<p><p>{</p>
	 *<p><p>&nbsp;&nbsp;"handleWaitingTimeOut":{</p>
	 *<p><p>&nbsp;&nbsp;&nbsp;&nbsp;"type":"LONG",</p>
	 *<p><p>&nbsp;&nbsp;&nbsp;&nbsp;"value":"time you want in seconds"<p>     
	 *<p><p>&nbsp;&nbsp;},</p>
	 *<p><p>}</p>
	 *<p>
	 *<p>Default time out is 5 seconds 
	 *<p>
     *<p>@param path is a path to frame specified by instance
	 *<p>of {@link HowToGetByFrames}
	 *<p>
	 *@see IDecomposableByHandles#getPart(Class, IHowToGetHandle, HowToGetByFrames)
     */		
	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			U howToGetHandle, HowToGetByFrames path) {
		return getPart(partClass, manager.getHandle(howToGetHandle), path);
	}

	/**<p>
	 *<p>Creates a representation of UI or some logically final
	 *<p>and assumed to be repeatable part of UI. This method 
	 *<p>used when where is mere than one browser window or 
	 *<p>mobile contexts (NATIVE_APP, WEBVIEW_0, WEBVIEW_1 and so on).
     *<p>This UI or fragment of UI is stationed inside frame (frame, iframe, etc)
	 *<p>It is actual for browser and mobile hybrid UI.
	 *<p>
	 *<p>@param partClass It is class where 
	 *<p>representation is described
	 *<p>
	 *<p>@param howToGetHandle is a strategy of receiving a new browser window/context
	 *<p>defined by {@link IHowToGetHandle} implementor 
	 *<p>
     *<p>@param path is a path to frame specified by instance
	 *<p>of {@link HowToGetByFrames}
	 *<p>
	 *<p>@param timeOut is a time to wait this window/context defined
	 *<p>explicitly. Time unit is seconds 
	 *<p>
	 *@see IDecomposableByHandles#getPart(Class, IHowToGetHandle, HowToGetByFrames, long)
     */		
	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			U howToGetHandle, HowToGetByFrames path, long timeOut) {
		return getPart(partClass, manager.getHandle(timeOut, howToGetHandle), path);
	}
	
	/**
	 *<p>@return wrapped instance of {@link Manager}
	 *<p>
	 *@see Manager
	 *<p>
	 *@see WindowManager 
	 *<p>
	 *@see ScreenManager
	 */
	@SuppressWarnings("unchecked")
	public <T extends Manager<?>> T getManager(){
		return (T) manager;
	}
}
