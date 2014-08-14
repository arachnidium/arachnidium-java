package org.arachnidium.model.interfaces;

import org.arachnidium.model.support.IPathStrategy;

@Deprecated //TODO Remove this
public interface IHasManyHandlesWithNamedContexts extends IHasManyHandles {
	public <T extends IDecomposable> T getFromHandle(Class<T> partClass,
			String contextName);

	public <T extends IDecomposable> T getFromHandle(Class<T> partClass,
			IPathStrategy pathStrategy, String contextName);
}
