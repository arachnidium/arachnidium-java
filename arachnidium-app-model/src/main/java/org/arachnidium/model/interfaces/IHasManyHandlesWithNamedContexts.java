package org.arachnidium.model.interfaces;

import org.arachnidium.model.support.PathStrategy;

public interface IHasManyHandlesWithNamedContexts extends IHasManyHandles {
	public <T extends IDecomposable> T getFromHandle(Class<T> partClass,
			String contextName);

	public <T extends IDecomposable> T getFromHandle(Class<T> partClass,
			PathStrategy pathStrategy, String contextName);
}
