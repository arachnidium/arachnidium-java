package org.arachnidium.model.interfaces;

import java.net.URL;
import java.util.List;

/**
 * For objects that open many handles (windows, contexts etc.) that have some
 * URL
 */
public interface IHasManyHandlesWithURL extends IHasManyHandles {
	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			List<String> urls);

	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			List<String> urls, Integer frameIndex);

	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			List<String> urls, long timeOutSec);

	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			List<String> urls, long timeOutSec, Integer frameIndex);

	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			URL url);

	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			URL url, Integer frameIndex);

	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			URL url, Integer frameIndex, long timeOutSec);

	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			URL url, long timeOutSec);

	public <T extends IDecomposable> T getFromNewHandle(String pathToFrame,
			Class<T> partClass, List<String> urls);

	public <T extends IDecomposable> T getFromNewHandle(String pathToFrame,
			Class<T> partClass, List<String> urls, long timeOutSec);

	public <T extends IDecomposable> T getFromNewHandle(String pathToFrame,
			Class<T> partClass, URL url);

	public <T extends IDecomposable> T getFromNewHandle(String pathToFrame,
			Class<T> partClass, URL url, long timeOutSec);

}
