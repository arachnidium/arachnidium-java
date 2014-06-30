package org.arachnidium.model.interfaces;


/**
 * @author s.tihomirov
 *	It is an interface for any functional part by itself or 
 *  its parts. So, it for any object that can be decomposed. All this should work with webdriver and Page Objects 
 */

public interface IDecomposable {

	public <T extends IDecomposable> T getPart(Class<T> partClass);
    //if object is placed in frame defined by integer value
	public <T extends IDecomposable> T getPart(Class<T> partClass, Integer frameIndex);
	//if object is placed in frame defined by string path
	public <T extends IDecomposable> T getPart(Class<T> partClass, String pathToFrame);
		
}
