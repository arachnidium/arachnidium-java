package org.arachnidium.core;

import java.util.List;

import org.arachnidium.core.fluenthandle.HowToGetHandle;
import org.arachnidium.core.interfaces.ICloneable;

/**
 * Strategy of a browser window receiving
 */
public class HowToGetBrowserWindow extends HowToGetHandle implements ICloneable {
	
	/**
	 * @param expected window index
	 * 
	 * @see org.arachnidium.core.fluenthandle.HowToGetHandle#setExpected(int)
	 */
	@Override
	public void setExpected(int index) {
		super.setExpected(index);
	}

	/**
	 * @param expected window title.
	 * A title can be defined as a regular expression
	 * 
	 * @see org.arachnidium.core.fluenthandle.HowToGetHandle#setExpected(java.lang.String)
	 */
	@Override
	public void setExpected(String titleRegExp) {
		super.setExpected(titleRegExp);
	}

	/**
	 * @param Expected URLs.
	 * URLs can be defined as a regular expressions
	 *  
	 * @see org.arachnidium.core.fluenthandle.HowToGetHandle#setExpected(java.util.List)
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
	 * @see org.arachnidium.core.interfaces.ICloneable#cloneThis()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public HowToGetBrowserWindow cloneThis(){
		try {
			return (HowToGetBrowserWindow) this.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}	
}
