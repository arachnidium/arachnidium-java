package org.arachnidium.model.interfaces;

import org.arachnidium.core.fluenthandle.IHowToGetHandle;
import org.arachnidium.model.support.HowToGetByFrames;

public interface IDecomposableByHandles<U extends IHowToGetHandle> extends IDecomposable {
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			int handleIndex);
	
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			int handleIndex, long timeOut);	

	public <T extends IDecomposable> T getPart(Class<T> partClass,
			int handleIndex, HowToGetByFrames howToGetByFrames);
	
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			int handleIndex, HowToGetByFrames howToGetByFrames, long timeOut);	
	
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			U howToGetHandle);
	
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			U howToGetHandle, long timeOut);
	
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			U howToGetHandle, HowToGetByFrames howToGetByFrames);
	
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			U howToGetHandle, HowToGetByFrames howToGetByFrames, long timeOut);	
}
