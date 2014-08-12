package org.arachnidium.model.interfaces;

import java.net.URL;
import java.util.List;

import org.arachnidium.model.support.PathStrategy;

/**
 * For objects that open many handles (windows, contexts etc.) that have some
 * URL
 */
public interface IHasManyHandlesWithURL extends IHasManyHandles {
	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			List<String> urls);

	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			List<String> urls, PathStrategy pathStrategy);

	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			List<String> urls, long timeOutSec);

	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			PathStrategy pathStrategy, List<String> urls, long timeOutSec);

	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			URL url);

	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			PathStrategy pathStrategy, URL url);

	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			PathStrategy pathStrategy, URL url, long timeOutSec);

	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			URL url, long timeOutSec);
}
