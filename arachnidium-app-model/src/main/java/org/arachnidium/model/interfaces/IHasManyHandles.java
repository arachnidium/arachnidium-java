package org.arachnidium.model.interfaces;

import org.arachnidium.model.support.PathStrategy;

/**
 * For objects that open many handles (windows, contexts etc.)
 */
public interface IHasManyHandles {

	public <T extends IDecomposable> T getFromHandle(Class<T> partClass,
			int index);

	public <T extends IDecomposable> T getFromHandle(Class<T> partClass, PathStrategy strategy,
			int index);

	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass);

	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			PathStrategy pathStrategy);

	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			long timeOutSec);

	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			PathStrategy pathStrategy, long timeOutSec);

	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			String stringIdentifier);

	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			PathStrategy pathStrategy, String stringIdentifier);

	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			String stringIdentifier, long timeOutSec);

	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			PathStrategy pathStrategy, String stringIdentifier, long timeOutSec);
}
