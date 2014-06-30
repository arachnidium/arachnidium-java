package org.arachnidium.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.arachnidium.core.interfaces.IHasHandle;

/**@author s.tihomirov
 * it registers handles that are instantiated as {@link IHasHandle}
 *
 */
class HandleReceptionist {

	final private Map<String,IHasHandle> handleObjects = Collections.synchronizedMap(new HashMap<String, IHasHandle>());

	/**adds a new handle that is instantiated as {@link IHasHandle} object**/
	void addKnown(IHasHandle handleObject){
		handleObjects.put(handleObject.getHandle(), handleObject);
	}

	/**is handle known as instance of {@link IHasHandle}**/
	IHasHandle isInstantiated(String handle)	{
		return handleObjects.get(handle);
	}

	/**removes handle that is instantiated as {@link IHasHandle} object**/
	void remove(IHasHandle handle){
		handleObjects.remove(handle.getHandle());
	}
	
	/**gets {@link IHasHandle} objects**/
	List<IHasHandle> getInstantiated(){
		return new ArrayList<>(handleObjects.values());
	}
	
	/**gets known handles**/
	List<String> getKnownHandles(){
		return new ArrayList<>(handleObjects.keySet());
	}
	
}
