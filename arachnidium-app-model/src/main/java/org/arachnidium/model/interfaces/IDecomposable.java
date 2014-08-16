package org.arachnidium.model.interfaces;

import org.arachnidium.model.support.HowToGetByFrames;

/**
 * @author s.tihomirov It is an interface for any functional part by itself or
 *         its parts. So, it for any object that can be decomposed. All this
 *         should work with webdriver and Page Objects
 */

public interface IDecomposable {

	public <T extends IDecomposable> T getPart(Class<T> partClass);

	/**
	 *  if object is placed in frame 
	 */
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			HowToGetByFrames pathStrategy);
}
