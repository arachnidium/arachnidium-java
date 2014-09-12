package org.arachnidium.core;

import java.util.List;

import org.arachnidium.core.fluenthandle.HowToGetHandle;
import org.arachnidium.core.interfaces.ICloneable;

/**
 * Strategy of a mobile context/screen receiving
 */
public class HowToGetMobileScreen extends HowToGetHandle implements ICloneable{
	
	/**
	 * @param expected context index
	 * 
	 * @see org.arachnidium.core.fluenthandle.HowToGetHandle#setExpected(int)
	 */
	@Override
	public void setExpected(int index) {
		super.setExpected(index);
	}

	/**
	 * @param expected context name.
	 * A name can be defined as a regular expression
	 * 
	 * @see org.arachnidium.core.fluenthandle.HowToGetHandle#setExpected(java.lang.String)
	 */
	@Override
	public void setExpected(String contextRegExp) {
		super.setExpected(contextRegExp);
	}

	/**
	 * @param expected activities.
	 * Activities can be defined as a regular expressions
	 * 
	 * This parameter is ignored by iOS
	 * 
	 * @see org.arachnidium.core.fluenthandle.HowToGetHandle#setExpected(java.util.List)
	 */
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
			result = result + " activities are " + uniqueIdentifiers.toString();
		}
		
		return result;
	}	
	
	/**
	 * @see org.arachnidium.core.interfaces.ICloneable#cloneThis()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public HowToGetMobileScreen cloneThis(){
		try {
			return (HowToGetMobileScreen) this.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}
}
