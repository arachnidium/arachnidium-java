package com.github.arachnidium.core;

import java.util.List;

import com.github.arachnidium.core.fluenthandle.HowToGetHandle;
import com.github.arachnidium.core.interfaces.ICloneable;

/**
 * Strategy of a browser window receiving
 */
public class HowToGetPage extends HowToGetHandle implements ICloneable {
	
	/**
	 * @param expected window index
	 * 
	 * @see com.github.arachnidium.core.fluenthandle.HowToGetHandle#setExpected(int)
	 */
	@Override
	public void setExpected(int index) {
		super.setExpected(index);
	}

	/**
	 * @param expected window title.
	 * A title can be defined as a regular expression
	 * 
	 * @see com.github.arachnidium.core.fluenthandle.HowToGetHandle#setExpected(java.lang.String)
	 */
	@Override
	public void setExpected(String titleRegExp) {
		super.setExpected(titleRegExp);
	}

	/**
	 * @param Expected URLs.
	 * Each one URL can be defined as a regular expression
	 *  
	 * @see com.github.arachnidium.core.fluenthandle.HowToGetHandle#setExpected(java.util.List)
	 */
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
			result = result + " URLs are " + uniqueIdentifiers.toString();
		}
		
		return result;
	}
	
	/**
	 * @see com.github.arachnidium.core.interfaces.ICloneable#cloneThis()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public HowToGetPage cloneThis(){
		try {
			return (HowToGetPage) this.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}	
}
