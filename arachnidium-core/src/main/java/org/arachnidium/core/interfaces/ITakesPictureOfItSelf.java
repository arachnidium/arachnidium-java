package org.arachnidium.core.interfaces;

import java.util.logging.Level;

import org.arachnidium.util.logging.Log;

/**
 * Implementors should take pictures of themselves
 */
public interface ITakesPictureOfItSelf {
	/**
	 * There is an assumption that implementor
	 * will crate a FINE {@link Level} {@link Log} message 
	 * with attached screenshot
	 */
	public void takeAPictureOfAFine(String Comment);

	/**
	 * There is an assumption that implementor
	 * will crate an INFO {@link Level} {@link Log} message 
	 * with attached screenshot
	 */
	public void takeAPictureOfAnInfo(String Comment);

	/**
	 * There is an assumption that implementor
	 * will crate a SEVERE {@link Level} {@link Log} message 
	 * with attached screenshot
	 */
	public void takeAPictureOfASevere(String Comment);

	/**
	 * There is an assumption that implementor
	 * will crate a WARNING {@link Level} {@link Log} message 
	 * with attached screenshot
	 */	
	public void takeAPictureOfAWarning(String Comment);
}
