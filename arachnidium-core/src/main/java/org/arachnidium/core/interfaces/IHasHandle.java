package org.arachnidium.core.interfaces;

/**
 * For all entities that can be identified by string handle
 *
 */
public interface IHasHandle {
	public String getHandle();
	default void whenIsCreated() {
		//does nothing, for listeners only
	}
}
