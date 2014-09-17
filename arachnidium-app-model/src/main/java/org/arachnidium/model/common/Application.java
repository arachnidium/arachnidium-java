package org.arachnidium.model.common;

import org.arachnidium.core.BrowserWindow;
import org.arachnidium.core.Handle;
import org.arachnidium.core.HowToGetBrowserWindow;
import org.arachnidium.core.HowToGetMobileScreen;
import org.arachnidium.core.Manager;
import org.arachnidium.core.MobileScreen;
import org.arachnidium.core.fluenthandle.IHowToGetHandle;
import org.arachnidium.core.settings.HandleWaitingTimeOut;
import org.arachnidium.model.abstractions.ModelObject;
import org.arachnidium.model.interfaces.IDecomposable;
import org.arachnidium.model.interfaces.IDecomposableByHandles;
import org.arachnidium.model.support.HowToGetByFrames;
import org.springframework.context.annotation.Configuration;

/**
 *
 *The Common representation of any application.
 *Using it you can model automated interaction with application 
 *as with a complex aggregated object. 
 *<br/>
 *It is implemented as a factory by default.
 *
 *@param <S> It means that objects can be stationed on browser window or 
 *mobile application screen. As for application it means the first or
 *the single (main) {@link Handle} is a {@link BrowserWindow} or {@link MobileScreen}.
 *And all child (decomposition) objects are on {@link BrowserWindow} or(/and) {@link MobileScreen}.
 *<br/>
 *If there is <code>&lt;S extends {@link Handle}&gt;</code> or <code>&lt;{@link Handle}&gt;</code> it means that 
 *both {@link BrowserWindow} and {@link MobileScreen} are possible. It is good
 *for situations when interaction with UI of  browser client and the perfectly similar mobile client 
 *has to be automated. 
 *<br/>
 *If it needs to be bounded by only one {@link Handle} subclass ({@link BrowserWindow} or {@link MobileScreen})
 *then<br/> 
 *<br/> 
 *<code>&lt;{@link BrowserWindow}&gt;</code>  only browser windows
 *<br/> 
 *or 
 *<br/>
 *<code>&lt;{@link MobileScreen}&gt;</code>  only mobile screens
 *
 *@param <U> It means the way of getting decomposable parts of the application. 
 *It needs to be used when it is possible the interaction 
 *with more than one window or mobile screen/context at the same time. 
 *Possible strategies are {@link HowToGetBrowserWindow} and {@link HowToGetMobileScreen}
 *If there is <code>&lt;S extends {@link IHowToGetHandle}&gt;</code> or <code>&lt;{@link IHowToGetHandle}&gt;</code> it means that 
 *both strategies are possible. It is good for situations when interaction with UI of browser client and the perfectly similar
 * mobile client has to be automated. <br/>
 * <br/>
 * Also, it can be useful when it needs to bind some decomposition parts to concrete URLs/Android
 * activities, page titles/context names etc.  
 */
public class Application<S extends Handle, U extends IHowToGetHandle> extends ModelObject<S> implements IDecomposableByHandles<U> {

	/**
	 *@see Manager
	 *
	 *@see WindowManager 
	 *
	 *@see ScreenManager
	 */
	protected final Manager<U> manager;
	
	/**
     *{@link Handle} is the given browser window or 
	 *mobile context which currently present.
	 *<br/>
	 *The main form or general UI of the
	 *application is stationed on this window or 
	 *mobile context/screen 
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
	 *Gets a representation of UI or some logically final piece of UI. 
	 *It is general UI or its peace.
	 *
	 *@param partClass It is class where 
	 *UI representation is described
	 *
	 *@see org.arachnidium.model.abstractions.ModelObject#getPart(java.lang.Class)
	 *
	 *@see IDecomposable#getPart(Class)
     */
	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass) {
		return getPart(partClass, this.handle);
	}

    /**
	 *Gets a  representation of UI or some logically final piece of UI. 
	 *It is general UI or its peace which is inside frame (frame, iframe, etc).
	 *It is actual for browser and mobile hybrid UI.
	 *
	 *@param partClass It is class where 
	 *representation is described
	 *
	 *@param path is a path to frame specified by instance
	 *of {@link HowToGetByFrames}
	 *
	 *@see IDecomposable#getPart(Class, HowToGetByFrames)
	 *
	 *@see ModelObject#getPart(Class, HowToGetByFrames)
	 *
	 *@see HowToGetByFrames
     */
	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			HowToGetByFrames path) {
		return getPart(partClass, this.handle, path);
	}

	/**
	 *This method shuts down the application
	 *and starts the destroying of related
	 *information
	 */
	public void quit() {
		getWebDriverEncapsulation().destroy();
	}

	/**
	 *Gets a representation of UI or some logically final piece of UI.
	 *This method is used when where are more than one browser window or 
	 *mobile contexts exist (NATIVE_APP, WEBVIEW_0, WEBVIEW_1 and so on)
	 *at one time.
	 *<br/>
	 *
	 *If the window/context is not instantly appears the implicit
	 *time out can be specified in {@link Configuration}.
	 *How? See {@link HandleWaitingTimeOut}. Default time out is 5 seconds 
	 *
	 *@param partClass It is class where 
	 *representation is described
	 *
	 *@param handleIndex is an expected index of the browser window
	 *or mobile context.
	 *
	 *@see IDecomposableByHandles#getPart(Class, int) 
	 *
	 *@see HandleWaitingTimeOut
     */
	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			int handleIndex) {
		return getPart(partClass, manager.getHandle(handleIndex));
	}
	
	
	/**
	 *Gets a representation of UI or some logically final piece of UI.
	 *This method is used when where are more than one browser window or 
	 *mobile contexts exist (NATIVE_APP, WEBVIEW_0, WEBVIEW_1 and so on)
	 *at one time.
	 *
	 *@param partClass It is class where 
	 *representation is described
	 *
	 *@param handleIndex is an expected index of the browser window
	 *or mobile context
	 *
	 *@param timeOut is a time to wait this window/context defined
	 *explicitly. Time unit is seconds 
	 *
	 *@see IDecomposableByHandles#getPart(Class, int, HowToGetByFrames)
     */
	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			int handleIndex, long timeOut) {
		return getPart(partClass, manager.getHandle(handleIndex, timeOut));
	}

	/**
	 *
	 *Gets a representation of UI or some logically final piece of UI.
	 *This method is used when where are more than one browser window or 
	 *mobile contexts exist (NATIVE_APP, WEBVIEW_0, WEBVIEW_1 and so on)
	 *at one time.
	 *<br/>
	 *
	 *This UI or fragment of UI is inside frame (frame, iframe, etc).
	 *It is actual for browser and mobile hybrid UI.
	 *
	 *<br/>
	 *
	 *If the window/context is not instantly appears the implicit
	 *time out can be specified in {@link Configuration}.
	 *How? See {@link HandleWaitingTimeOut}. Default time out is 5 seconds 
	 *
	 *@param partClass It is class where 
	 *representation is described
	 *
	 *@param handleIndex is an expected index of the browser window
	 *or mobile context
	 *
	 *@param path is a path to desired frame specified by instance
	 *of {@link HowToGetByFrames}
	 *
	 *@see IDecomposableByHandles#getPart(Class, int, HowToGetByFrames)
	 *
	 *@see HowToGetByFrames
	 *
	 *@see HandleWaitingTimeOut
     */	
	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			int handleIndex, HowToGetByFrames path) {
		return getPart(partClass, manager.getHandle(handleIndex), path);
	}

	
	/**
	 *Gets a representation of UI or some logically final piece of UI.
	 *This method is used when where are more than one browser window or 
	 *mobile contexts exist (NATIVE_APP, WEBVIEW_0, WEBVIEW_1 and so on)
	 *at one time.
	 *<br/>
	 *
	 *This UI or fragment of UI is inside frame (frame, iframe, etc)
	 *It is actual for browser and mobile hybrid UI.
	 *
	 *
	 *@param partClass It is class where 
	 *representation is described
	 *
	 *@param handleIndex is an expected index of the browser window
	 *or mobile context
	 *
	 *@param path is a path to frame specified by instance
	 *of {@link HowToGetByFrames}
	 *
	 *@param timeOut is a time to wait this window/context defined
	 *explicitly. Time unit is seconds 
	 *
	 *@see IDecomposableByHandles#getPart(Class, IHowToGetHandle, HowToGetByFrames, long)
	 *
	 *@see HowToGetByFrames
     */		
	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			int handleIndex, HowToGetByFrames path, long timeOut) {
		return getPart(partClass, manager.getHandle(handleIndex, timeOut), path);
	}

	/**
	 *Gets a representation of UI or some logically final piece of UI.
	 *This method is used when where are more than one browser window or 
	 *mobile contexts exist (NATIVE_APP, WEBVIEW_0, WEBVIEW_1 and so on)
	 *at one time.
	 *<br/>
	 *
	 *If the window/context is not instantly appears the implicit
	 *time out can be specified in {@link Configuration}.
	 *How? See {@link HandleWaitingTimeOut}. Default time out is 5 seconds 
	 *
	 *@param partClass It is class where 
	 *representation is described
	 *
	 *@param howToGetHandle is a strategy of getting a new browser window/context
	 *defined by {@link IHowToGetHandle} implementor 
	 *
	 *@see IDecomposableByHandles#getPart(Class, IHowToGetHandle) 
	 *
	 *@see IHowToGetHandle
	 *
	 *@see HowToGetBrowserWindow
	 *
	 *@see HowToGetMobileScreen
	 *
	 *@see HandleWaitingTimeOut
     */	
	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			U howToGetHandle) {
		return getPart(partClass, manager.getHandle(howToGetHandle));
	}

	/**
	 *
	 *Gets a representation of UI or some logically final piece of UI.
	 *This method is used when where are more than one browser window or 
	 *mobile contexts exist (NATIVE_APP, WEBVIEW_0, WEBVIEW_1 and so on)
	 *at one time.
	 *<br/>
	 *
	 *@param partClass It is class where 
	 *representation is described
	 *
	 *@param howToGetHandle is a strategy of getting a new browser window/context
	 *defined by {@link IHowToGetHandle} implementor 
	 *
	 *@param timeOut is a time to wait this window/context defined
	 *explicitly. Time unit is seconds 
	 *
	 *@see IDecomposableByHandles#getPart(Class, IHowToGetHandle, long) 
	 *
	 *@see IHowToGetHandle
	 *
	 *@see HowToGetBrowserWindow
	 *
	 *@see HowToGetMobileScreen
     */		
	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			U howToGetHandle, long timeOut) {
		return getPart(partClass, manager.getHandle(timeOut, howToGetHandle));
	}

	/**
	 *Gets a representation of UI or some logically final piece of UI.
	 *This method is used when where are more than one browser window or 
	 *mobile contexts exist (NATIVE_APP, WEBVIEW_0, WEBVIEW_1 and so on)
	 *at one time.
	 *<br/>
	 *
	 *This UI or fragment of UI is inside frame (frame, iframe, etc).
	 *It is actual for browser and mobile hybrid UI.
	 *
	 *<br/>
	 *
	 *If the window/context is not instantly appears the implicit
	 *time out can be specified in {@link Configuration}.
	 *How? See {@link HandleWaitingTimeOut}. Default time out is 5 seconds 
	 *
	 *@param partClass It is class where 
	 *representation is described
	 *
	 *@param howToGetHandle is a strategy of getting a new browser window/context
	 *defined by {@link IHowToGetHandle} implementor 
	 *
     *@param path is a path to frame specified by instance
	 *of {@link HowToGetByFrames}
	 *
	 *@see IDecomposableByHandles#getPart(Class, IHowToGetHandle, HowToGetByFrames)
	 *
	 *@see IHowToGetHandle
	 *
	 *@see HowToGetBrowserWindow
	 *
	 *@see HowToGetMobileScreen
	 *
	 *@see HowToGetByFrames
	 *
	 *@see HandleWaitingTimeOut
     */		
	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			U howToGetHandle, HowToGetByFrames path) {
		return getPart(partClass, manager.getHandle(howToGetHandle), path);
	}

	/**
	 *
	 *Gets a representation of UI or some logically final piece of UI.
	 *This method is used when where are more than one browser window or 
	 *mobile contexts exist (NATIVE_APP, WEBVIEW_0, WEBVIEW_1 and so on)
	 *at one time.
	 *<br/>
	 *
	 *This UI or fragment of UI is inside frame (frame, iframe, etc).
	 *It is actual for browser and mobile hybrid UI.
	 *
	 *<br/>
	 *
	 *@param partClass It is class where 
	 *representation is described
	 *
	 *@param howToGetHandle is a strategy of getting a new browser window/context
	 *defined by {@link IHowToGetHandle} implementor 
	 *
     *@param path is a path to frame specified by instance
	 *of {@link HowToGetByFrames}
	 *
	 *@param timeOut is a time to wait this window/context defined
	 *explicitly. Time unit is seconds 
	 *
	 *@see IDecomposableByHandles#getPart(Class, IHowToGetHandle, HowToGetByFrames, long)
	 *
	 *@see IHowToGetHandle
	 *
	 *@see HowToGetBrowserWindow
	 *
	 *@see HowToGetMobileScreen
	 *
	 *@see HowToGetByFrames
     */		
	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			U howToGetHandle, HowToGetByFrames path, long timeOut) {
		return getPart(partClass, manager.getHandle(timeOut, howToGetHandle), path);
	}
	
	/**
	 *@return wrapped instance of {@link Manager}
	 *
	 *@see Manager
	 *
	 *@see WindowManager 
	 *
	 *@see ScreenManager
	 */
	@SuppressWarnings("unchecked")
	public <T extends Manager<?>> T getManager(){
		return (T) manager;
	}
}
