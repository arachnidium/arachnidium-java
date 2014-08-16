package org.arachnidium.core.interfaces;

public interface ICloneable extends Cloneable{
	public <T extends Object> T cloneThis();
}
