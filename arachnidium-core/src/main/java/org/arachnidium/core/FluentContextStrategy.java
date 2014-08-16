package org.arachnidium.core;

import java.util.List;

import org.arachnidium.core.fluenthandle.AbstractFluentHandleStrategy;
import org.arachnidium.core.interfaces.ICloneable;

/**
 * Is for mobile contexts only
 */
public class FluentContextStrategy extends AbstractFluentHandleStrategy implements ICloneable{
	@Override
	public void setExpected(int index) {
		super.setExpected(index);
	}

	@Override
	public void setExpected(String contextRegExp) {
		super.setExpected(contextRegExp);
	}

	@Override
	public void setExpected(List<String> activitiesRegExps) {
		super.setExpected(activitiesRegExps);
	}
	
	@Override
	public String toString(){
		String result = "";
		if (index != null){
			result = result + " index is " + index.toString();
		}
		
		if (stringIdentifier != null){
			result = result + " context is " + stringIdentifier.toString();
		}
		
		if (uniqueIdentifiers != null){
			result = result + " activities are " + uniqueIdentifiers.toArray().toString();
		}
		
		return result;
	}	
	
	@Override
	@SuppressWarnings("unchecked")
	public FluentContextStrategy cloneThis(){
		try {
			return (FluentContextStrategy) this.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}
}
