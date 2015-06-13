package com.github.arachnidium.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.arachnidium.core.interfaces.IHasHandle;

/**
 * @author s.tihomirov it registers handles that are instantiated as
 *         {@link IHasHandle}
 */
class HandleReceptionist {

	final private List<String> handleObjects = Collections
			.synchronizedList(new ArrayList<String>());
	final private Map<String, List<IHasHandle>> instantiatedHandles = 
			new HashMap<String, List<IHasHandle>>();

	/** adds a new handle that is instantiated as {@link IHasHandle} object **/
	void addKnown(IHasHandle handleObject) {
		if (!handleObjects.contains(handleObject.getHandle()))
			handleObjects.add(handleObject.getHandle());
		
		List<IHasHandle> value = instantiatedHandles.get(handleObject.getHandle());
		if (value == null){
			value = new ArrayList<>();
		}
		value.add(handleObject);
		instantiatedHandles.put(handleObject.getHandle(), value);
	}

	/** is handle known as instance of {@link IHasHandle} **/
	boolean isInstantiated(String handle) {
		return handleObjects.contains(handle);
	}
	
	public List<IHasHandle> getInstantiated(){
		final List<IHasHandle> result = new ArrayList<>();
		handleObjects.forEach(handle -> {
			result.addAll(instantiatedHandles.get(handle));
		});
		return result;
	}

	/** removes handle that is instantiated as {@link IHasHandle} object **/
	void remove(IHasHandle handle) {
		String stringHandle = handle.getHandle();
		List<IHasHandle> result = instantiatedHandles.get(stringHandle);
		if (result != null)
			result.remove(handle);
		
		if (result == null || result.size() == 0){
			handleObjects.remove(stringHandle);
			instantiatedHandles.remove(stringHandle);
		}
	}

}
