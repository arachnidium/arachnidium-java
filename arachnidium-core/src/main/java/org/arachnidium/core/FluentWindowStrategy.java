package org.arachnidium.core;

import java.util.List;

import org.arachnidium.core.fluenthandle.AbstractFluentHandleStrategy;
import org.arachnidium.core.interfaces.ICloneable;

/**
 * Is for browser windows only
 */
public class FluentWindowStrategy extends AbstractFluentHandleStrategy implements ICloneable {
	@Override
	public void setExpected(int index) {
		super.setExpected(index);
	}

	@Override
	public void setExpected(String titleRegExp) {
		super.setExpected(titleRegExp);
	}

	@Override
	public void setExpected(List<String> urlsRegExps) {
		super.setExpected(urlsRegExps);
	}
	
	@Override
	public String toString(){
		String result = "";
		if (index != null){
			result = result + " index is " + index.toString();
		}
		
		if (stringIdentifier != null){
			result = result + " title is " + stringIdentifier.toString();
		}
		
		if (uniqueIdentifiers != null){
			result = result + " URLs are " + uniqueIdentifiers.toArray().toString();
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public FluentWindowStrategy cloneThis(){
		try {
			return (FluentWindowStrategy) this.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}	
}
