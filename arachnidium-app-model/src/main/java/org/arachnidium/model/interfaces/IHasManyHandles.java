package org.arachnidium.model.interfaces;

/**
 * For objects that open many handles (windows, contexts etc.)
 */
public interface IHasManyHandles {

	public <T extends IDecomposable> T getFromHandle(Class<T> partClass,
			int index);

	public <T extends IDecomposable> T getFromHandle(Class<T> partClass,
			Integer frameIndex, int index);

	public <T extends IDecomposable> T getFromHandle(Class<T> partClass,
			String pathToFrame, int index);

	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass);

	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			Integer frameIndex);

	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			long timeOutSec);

	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			long timeOutSec, Integer frameIndex);

	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			String stringIdentifier);

	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			String stringIdentifier, Integer frameIndex);

	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			String stringIdentifier, long timeOutSec);

	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			String stringIdentifier, long timeOutSec, Integer frameIndex);

	public <T extends IDecomposable> T getFromNewHandle(String pathToFrame,
			Class<T> partClass);

	public <T extends IDecomposable> T getFromNewHandle(String pathToFrame,
			Class<T> partClass, long timeOutSec);

	public <T extends IDecomposable> T getFromNewHandle(String pathToFrame,
			Class<T> partClass, String stringIdentifier);

	public <T extends IDecomposable> T getFromNewHandle(String pathToFrame,
			Class<T> partClass, String stringIdentifier, long timeOutSec);
}
