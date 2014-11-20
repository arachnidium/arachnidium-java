package com.github.arachnidium.model.interfaces;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.core.HowToGetBrowserWindow;
import com.github.arachnidium.core.HowToGetMobileScreen;
import com.github.arachnidium.core.fluenthandle.IHowToGetHandle;
import com.github.arachnidium.model.support.HowToGetByFrames;

/**
 * There is an assumption that any object (whole application, page/screen and so on)
 * could be decomposed to limited set of logically final and reusable parts.
 * 
 * This interface specifies decomposition model 
 * provided by Arachnidium Java framework.
 * 
 * It is possible to interact with more than one browser windows or 
 * mobile contexts (NATIVE_APP, WEBVIEW_0, WEBVIEW_1 etc) at the same
 * time. So the interface provides advanced decomposition model and
 * considers this fact.   
 */

public interface IDecomposableByHandles<U extends IHowToGetHandle> extends IDecomposable {
	
	/**
	 * This method should provide the model
	 * of the getting objects by given browser window
	 * or mobile screen/context. It is for situations when 
	 * the index of window/context is expected.  
	 * 
	 * @param partClass is required class that implements {@link IDecomposable}
	 * 
	 * @param handleIndex is an expected index of the {@link Handle}, e.g. index of the
	 * browser window or mobile context
	 * 
	 * @return The instance of required class specified by <code>partClass</code> 
	 * parameter
	 */
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			int handleIndex);
	
	/**
	 * This method should provide the model
	 * of the getting objects by given browser window
	 * or mobile screen/context. It is for situations when 
	 * the index of window/context is expected.  Also we know the 
	 * root element where this "part" is located
	 *  
	 * @param partClass is required class that implements {@link IDecomposable}
	 * @param handleIndex handleIndex is an expected index of the {@link Handle}, e.g. index of the
	 * @param by Is a locator strategy which is used to get the root {@link WebElement}
	 * 
	 * @return The instance of required class specified by <code>partClass</code> 
	 * parameter
	 */
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			int handleIndex, By by);
	
	
	/**
	 * This method should provide the model
	 * of the getting objects by given browser window
	 * or mobile screen/context. It is for situations when 
	 * the index of window/context is expected.  
	 * 
	 * @param partClass is required class that implements {@link IDecomposable}
	 * @param handleIndex handleIndex is an expected index of the {@link Handle}, e.g. index of the
	 * browser window or mobile context
	 * @param timeOut is time to wait for.
	 * 
	 * @return The instance of required class specified by <code>partClass</code> 
	 * parameter
	 */
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			int handleIndex, long timeOut);	
	
	
	/**
	 * This method should provide the model
	 * of the getting objects by given browser window
	 * or mobile screen/context. It is for situations when 
	 * the index of window/context is expected. Also we know the 
	 * root element where this "part" is located
	 * 
	 * @param partClass is required class that implements {@link IDecomposable}
	 * @param handleIndex handleIndex is an expected index of the {@link Handle}, e.g. index of the
	 * browser window or mobile context
	 * @param by Is a locator strategy which is used to get the root {@link WebElement} 
	 * @param timeOut is time to wait for.
	 * 
	 * @return The instance of required class specified by <code>partClass</code> 
	 * parameter
	 */
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			int handleIndex, By by, long timeOut);		

	/**
	 * This method should provide the model
	 * of the getting objects by given browser window
	 * or mobile screen/context. It is for situations when 
	 * the index of window/context is expected. 
	 * <br/><br/>
	 * As it is all about UI of browser and mobile applications, 
	 * the usage of frames is possible (browser applications, hybrid mobile clients).
	 * So this fact is considered by method.
	 * <br/><br/>
	 * @param partClass is required class that implements {@link IDecomposable}
	 * @param handleIndex handleIndex handleIndex is an expected index of the {@link Handle}, e.g. index of the
	 * browser window or mobile context
	 * @param howToGetByFrames is a path to frame which is specified by {@link HowToGetByFrames} 
	 * 
	 * @return The instance of required class specified by <code>partClass</code> 
	 * parameter
	 */
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			int handleIndex, HowToGetByFrames howToGetByFrames);
	
	
	/**
	 * This method should provide the model
	 * of the getting objects by given browser window
	 * or mobile screen/context. It is for situations when 
	 * the index of window/context is expected. 
	 * <br/><br/>
	 * As it is all about UI of browser and mobile applications, 
	 * the usage of frames is possible (browser applications, hybrid mobile clients).
	 * So this fact is considered by method. 
	 * <br/><br/>
	 * Also if elements are located in some required root element it is 
	 * possible to define it {@link By} locator strategy. 
	 * 
	 * @param partClass is required class that implements {@link IDecomposable}
	 * @param handleIndex handleIndex handleIndex handleIndex is an expected index of the {@link Handle}, e.g. index of the
	 * browser window or mobile context
	 * @param howToGetByFrames s a path to frame which is specified by {@link HowToGetByFrames} 
	 * the root element
	 * @param by Is a locator strategy which is used to get the root {@link WebElement}
	 * 
	 * @return he instance of required class specified by <code>partClass</code> 
	 * parameter
	 */
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			int handleIndex, HowToGetByFrames howToGetByFrames, By by);
	
	/**
	 * This method should provide the model
	 * of the getting objects by given browser window
	 * or mobile screen/context. It is for situations when 
	 * the index of window/context is expected. 
	 * <br/><br/>
	 * As it is all about UI of browser and mobile applications, 
	 * the usage of frames is possible (browser applications, hybrid mobile clients).
	 * So this fact is considered by method.
	 * 
	 * @param partClass is required class that implements {@link IDecomposable}
	 * @param handleIndex handleIndex handleIndex is an expected index of the {@link Handle}, e.g. index of the
	 * browser window or mobile context
	 * @param howToGetByFrames is a path to frame which is specified by {@link HowToGetByFrames} 
	 * @param timeOut is time to wait for.
	 * 
	 * @return The instance of required class specified by <code>partClass</code> 
	 * parameter
	 */
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			int handleIndex, HowToGetByFrames howToGetByFrames, long timeOut);	
	
	
	/**
	 * This method should provide the model
	 * of the getting objects by given browser window
	 * or mobile screen/context. It is for situations when 
	 * the index of window/context is expected. 
	 * <br/><br/>
	 * As it is all about UI of browser and mobile applications, 
	 * the usage of frames is possible (browser applications, hybrid mobile clients).
	 * So this fact is considered by method.
	 * 
     * <br/><br/>
	 * Also if elements are located in some required root element it is 
	 * possible to define it {@link By} locator strategy. 
	 * 
	 * @param partClass is required class that implements {@link IDecomposable}
	 * @param handleIndex handleIndex handleIndex is an expected index of the {@link Handle}, e.g. index of the
	 * @param howToGetByFrames s a path to frame which is specified by {@link HowToGetByFrames}
	 * @param by Is a locator strategy which is used to get the root {@link WebElement}
	 * the root element
	 * @param timeOut is time to wait for.
	 * 
	 * @return The instance of required class specified by <code>partClass</code> 
	 * parameter
	 */	
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			int handleIndex, HowToGetByFrames howToGetByFrames, By by, long timeOut);		
	/**
	 * This method should provide the model 
	 * of the getting objects by given browser 
	 * window or mobile screen/context. 
	 * It is for situations when some complex condition is known, 
	 * e.g. URLs, title of the page, 
	 * index of the window/context, 
	 * context name (or part of the name) activities (is for Android) and so on. 
	 * 
	 * @param partClass is required class that implements {@link IDecomposable}
	 * @param howToGetHandle is a strategy where conditions are combined. Conditions are
	 * expected URLs, page title or context name, index of the window/context, activities
	 * etc.
	 * @return The instance of required class specified by <code>partClass</code> 
	 * parameter
	 * 
	 * @see IHowToGetHandle
	 * @see HowToGetBrowserWindow
	 * @see HowToGetMobileScreen
	 */
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			U howToGetHandle);
	
	
	/**
	 * This method should provide the model 
	 * of the getting objects by given browser 
	 * window or mobile screen/context. 
	 * It is for situations when some complex condition is known, 
	 * e.g. URLs, title of the page, 
	 * index of the window/context, 
	 * context name (or part of the name) activities (is for Android) and so on. 
	 * <br/><br/>
	 * Also we know the 
	 * root element where this "part" is located
	 * 
	 * @param partClass is required class that implements {@link IDecomposable}
	 * @param howToGetHandle is a strategy where conditions are combined. Conditions are
	 * expected URLs, page title or context name, index of the window/context, activities
	 * etc.
	 * @param by Is a locator strategy which is used to get the root {@link WebElement}
	 * 
	 * @return The instance of required class specified by <code>partClass</code> 
	 * parameter
	 * 
	 * @see IHowToGetHandle
	 * @see HowToGetBrowserWindow
	 * @see HowToGetMobileScreen
	 * @see By
	 */	
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			U howToGetHandle, By by);
	
	/**
	 * This method should provide the model 
	 * of the getting objects by given browser 
	 * window or mobile screen/context. 
	 * It is for situations when some complex condition is known, 
	 * e.g. URLs, title of the page, 
	 * index of the window/context, 
	 * context name (or part of the name) activities (is for Android) and so on. 
	 * 
	 * @param partClass is required class that implements {@link IDecomposable}
	 * @param howToGetHandle howToGetHandle is a strategy where conditions are combined. Conditions are
	 * expected URLs, page title or context name, index of the window/context, activities
	 * etc.
	 * @param timeOut is time to wait for.
	 * 
	 * @returnThe instance of required class specified by <code>partClass</code> 
	 * parameter
	 * 
	 * @see IHowToGetHandle
	 * @see HowToGetBrowserWindow
	 * @see HowToGetMobileScreen
	 */
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			U howToGetHandle, long timeOut);
	
	/**
	 * This method should provide the model 
	 * of the getting objects by given browser 
	 * window or mobile screen/context. 
	 * It is for situations when some complex condition is known, 
	 * e.g. URLs, title of the page, 
	 * index of the window/context, 
	 * context name (or part of the name) activities (is for Android) and so on. 
	 * <br/><br/>
	 * Also if elements are located in some required root element it is 
	 * possible to define it {@link By} locator strategy.  
	 * 
	 * @param partClass is required class that implements {@link IDecomposable}
	 * @param howToGetHandle howToGetHandle is a strategy where conditions are combined. Conditions are
	 * expected URLs, page title or context name, index of the window/context, activities
	 * etc.
	 * @param by Is a locator strategy which is used to get the root {@link WebElement}
	 * @param timeOut is time to wait for.
	 * 
	 * @returnThe instance of required class specified by <code>partClass</code> 
	 * parameter
	 * 
	 * @see IHowToGetHandle
	 * @see HowToGetBrowserWindow
	 * @see HowToGetMobileScreen
	 * @see By
	 */	
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			U howToGetHandle,  By by, long timeOut);	
	
	/**
	 * This method should provide the model 
	 * of the getting objects by given browser 
	 * window or mobile screen/context. 
	 * It is for situations when some complex condition is known, 
	 * e.g. URLs, title of the page, 
	 * index of the window/context, 
	 * context name (or part of the name) activities (is for Android) and so on. 
	 * <br/><br/>
	 * As it is all about UI of browser and mobile applications, 
	 * the usage of frames is possible (browser applications, hybrid mobile clients).
	 * So this fact is considered by method.
	 * 
	 * @param partClass is required class that implements {@link IDecomposable}
	 * @param howToGetHandle howToGetHandle howToGetHandle is a strategy where conditions are combined. Conditions are
	 * expected URLs, page title or context name, index of the window/context, activities
	 * etc.
	 * @param howToGetByFrames is a path to frame which is specified by {@link HowToGetByFrames} 
	 * @return instance of required class specified by <code>partClass</code> 
	 * parameter
	 * 
	 * @see IHowToGetHandle
	 * @see HowToGetBrowserWindow
	 * @see HowToGetMobileScreen
	 */
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			U howToGetHandle, HowToGetByFrames howToGetByFrames);
	
	
	/**
	 * This method should provide the model 
	 * of the getting objects by given browser 
	 * window or mobile screen/context. 
	 * It is for situations when some complex condition is known, 
	 * e.g. URLs, title of the page, 
	 * index of the window/context, 
	 * context name (or part of the name) activities (is for Android) and so on. 
	 * <br/><br/>
	 * As it is all about UI of browser and mobile applications, 
	 * the usage of frames is possible (browser applications, hybrid mobile clients).
	 * So this fact is considered by method.
	 * <br/><br/>
	 * Also if elements are located in some required root element it is 
	 * possible to define it {@link By} locator strategy.  
	 * 
	 * @param partClass is required class that implements {@link IDecomposable}
	 * @param howToGetHandle howToGetHandle howToGetHandle is a strategy where conditions are combined. Conditions are
	 * expected URLs, page title or context name, index of the window/context, activities
	 * etc.
	 * @param howToGetByFrames s a path to frame which is specified by {@link HowToGetByFrames} 
	 * the root element
	 * @param by Is a locator strategy which is used to get the root {@link WebElement}
	 * 
	 * @return instance of required class specified by <code>partClass</code> 
	 * parameter
	 * 
	 * @see IHowToGetHandle
	 * @see HowToGetBrowserWindow
	 * @see HowToGetMobileScreen
	 */
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			U howToGetHandle, HowToGetByFrames howToGetByFrames, By by);
	
	/**
	 * This method should provide the model 
	 * of the getting objects by given browser 
	 * window or mobile screen/context. 
	 * It is for situations when some complex condition is known, 
	 * e.g. URLs, title of the page, 
	 * index of the window/context, 
	 * context name (or part of the name) activities (is for Android) and so on. 
	 * <br/><br/>
	 * As it is all about UI of browser and mobile applications, 
	 * the usage of frames is possible (browser applications, hybrid mobile clients).
	 * So this fact is considered by method.
	 * 
	 * @param partClass partClass is required class that implements {@link IDecomposable}
	 * @param howToGetHandle howToGetHandle howToGetHandle howToGetHandle is a strategy where conditions are combined. Conditions are
	 * expected URLs, page title or context name, index of the window/context, activities
	 * etc.
	 * @param howToGetByFrames is a path to frame which is specified by {@link HowToGetByFrames}
	 * @param timeOut is time to wait for.
	 * 
	 * @return instance of required class specified by <code>partClass</code> 
	 * parameter
	 * 
	 * @see IHowToGetHandle
	 * @see HowToGetBrowserWindow
	 * @see HowToGetMobileScreen
	 */
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			U howToGetHandle, HowToGetByFrames howToGetByFrames, long timeOut);	
	
	
	/**
	 * This method should provide the model 
	 * of the getting objects by given browser 
	 * window or mobile screen/context. 
	 * It is for situations when some complex condition is known, 
	 * e.g. URLs, title of the page, 
	 * index of the window/context, 
	 * context name (or part of the name) activities (is for Android) and so on. 
	 * <br/><br/>
	 * As it is all about UI of browser and mobile applications, 
	 * the usage of frames is possible (browser applications, hybrid mobile clients).
	 * So this fact is considered by method.
	 * <br/><br/>
	 * Also if elements are located in some required root element it is 
	 * possible to define it {@link By} locator strategy.  
	 * 
	 * @param partClass partClass is required class that implements {@link IDecomposable}
	 * @param howToGetHandle howToGetHandle howToGetHandle howToGetHandle is a strategy where conditions are combined. Conditions are
	 * expected URLs, page title or context name, index of the window/context, activities
	 * etc.
	 * @param howToGetByFrames is a path to frame which is specified by {@link HowToGetByFrames}
	 * @param by Is a locator strategy which is used to get the root {@link WebElement}
	 * @param timeOut is time to wait for.
	 * 
	 * @return instance of required class specified by <code>partClass</code> 
	 * parameter
	 * 
	 * @see IHowToGetHandle
	 * @see HowToGetBrowserWindow
	 * @see HowToGetMobileScreen
	 * @see By
	 */	
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			U howToGetHandle, HowToGetByFrames howToGetByFrames, By by, long timeOut);		
}
