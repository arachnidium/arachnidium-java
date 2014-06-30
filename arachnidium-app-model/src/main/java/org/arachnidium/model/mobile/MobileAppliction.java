package org.arachnidium.model.mobile;

import org.arachnidium.model.common.Application;
import org.arachnidium.model.interfaces.IDecomposable;
import org.arachnidium.model.interfaces.IHasManyHandlesWithNamedContexts;
import org.arachnidium.core.ContextManager;
import org.arachnidium.core.Handle;
import org.arachnidium.core.SingleContext;

/**
 * Representation of a mobile application
 */
public abstract class MobileAppliction extends Application implements IHasManyHandlesWithNamedContexts {

	protected MobileAppliction(SingleContext context) {
		super(context);
	}
	
	public ContextManager getContextManager(){
		return (ContextManager) manager;
	}

	/**
	 * Gets a functional part (page object) from the existing handle by context name
	 */
	@Override
	public <T extends IDecomposable> T getFromHandle(Class<T> partClass,
			String contextName) {
		Handle newHandle = ((ContextManager) manager).getByContextName(contextName);
		Class<?>[] params = new Class[] { Handle.class };
		Object[] values = new Object[] { newHandle };
		return get(partClass,
				replaceHandleParamIfItNeedsToBe(params, partClass, newHandle),
				values);
	}

	/**
	 * Gets a functional part (page object) from the existing handle by context name
	 * and frame index
	 */
	@Override
	public <T extends IDecomposable> T getFromHandle(Class<T> partClass,
			Integer frameIndex, String contextName) {
		Handle newHandle = ((ContextManager) manager).getByContextName(contextName);
		Class<?>[] params = new Class[] {Handle.class, Integer.class};
		Object[] values = new Object[] {newHandle, frameIndex};
		return get(partClass,
				replaceHandleParamIfItNeedsToBe(params, partClass, newHandle),
				values);
	}

	/**
	 * Gets a functional part (page object) from the existing handle by context name
	 * and path to frame 
	 */
	@Override
	public <T extends IDecomposable> T getFromHandle(Class<T> partClass,
			String pathToFrame, String contextName) {
		Handle newHandle = ((ContextManager) manager).getByContextName(contextName);
		Class<?>[] params = new Class[] {Handle.class, String.class};
		Object[] values = new Object[] {newHandle, pathToFrame};
		return get(partClass,
				replaceHandleParamIfItNeedsToBe(params, partClass, newHandle),
				values);
	}

}
