package org.arachnidium.model.interfaces;

import org.arachnidium.model.support.IPathStrategy;

/**
 * For objects that open many handles (windows, contexts etc.)
 */
@Deprecated //TODO Remove this
public interface IHasManyHandles {

	public <T extends IDecomposable> T getFromHandle(Class<T> partClass,
			int index);

	public <T extends IDecomposable> T getFromHandle(Class<T> partClass, IPathStrategy strategy,
			int index);

	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass);

	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			IPathStrategy pathStrategy);

	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			long timeOutSec);

	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			IPathStrategy pathStrategy, long timeOutSec);

	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			String stringIdentifier);

	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			IPathStrategy pathStrategy, String stringIdentifier);

	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			String stringIdentifier, long timeOutSec);

	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			IPathStrategy pathStrategy, String stringIdentifier, long timeOutSec);
}
