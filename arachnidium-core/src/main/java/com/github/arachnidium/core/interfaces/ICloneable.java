package com.github.arachnidium.core.interfaces;

/**
 * This interface is for objects whose
 * information can be duplicated
 */
public interface ICloneable extends Cloneable{
	/**
	 * @return Duplicate of the original object
	 */
	public <T extends Object> T cloneThis();
}
