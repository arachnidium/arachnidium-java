package org.arachnidium.model.interfaces;

import java.net.URL;
import java.util.List;

import org.arachnidium.model.support.IPathStrategy;

/**
 * For objects that open many handles (windows, contexts etc.) that have some
 * URL
 */
@Deprecated //TODO Remove this
public interface IHasManyHandlesWithURL extends IHasManyHandles {
	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			List<String> urls);

	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			List<String> urls, IPathStrategy pathStrategy);

	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			List<String> urls, long timeOutSec);

	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			IPathStrategy pathStrategy, List<String> urls, long timeOutSec);

	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			URL url);

	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			IPathStrategy pathStrategy, URL url);

	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			IPathStrategy pathStrategy, URL url, long timeOutSec);

	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			URL url, long timeOutSec);
}
