package com.github.arachnidium.util.configuration.interfaces;

/**
 * It is for all configurable/configuration entities 
 * which can return a path to some file
 */
public interface IHasPathToFile extends IHasPathToFolder {
	public String getFile();
}
