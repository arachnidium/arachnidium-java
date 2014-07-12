package org.arachnidium.model.interfaces;

public interface IHasManyHandlesWithNamedContexts extends IHasManyHandles {
	public <T extends IDecomposable> T getFromHandle(Class<T> partClass,
			Integer frameIndex, String contextName);

	public <T extends IDecomposable> T getFromHandle(Class<T> partClass,
			String contextName);

	public <T extends IDecomposable> T getFromHandle(Class<T> partClass,
			String pathToFrame, String contextName);
}
