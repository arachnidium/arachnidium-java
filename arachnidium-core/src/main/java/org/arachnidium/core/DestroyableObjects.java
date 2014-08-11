package org.arachnidium.core;

import java.util.ArrayList;
import org.arachnidium.core.interfaces.IDestroyable;

class DestroyableObjects implements IDestroyable {

	private final ArrayList<IDestroyable> destroyableList = new ArrayList<IDestroyable>();
	
	void add(IDestroyable destroyable){
		destroyableList.add(destroyable);
	}
	
	@Override
	public void destroy() {
		//removes references or does something that 
		for (IDestroyable d: destroyableList){ //kills objects refereed to WebDriver
			d.destroy(); //Uses order of adding
		}
		destroyableList.clear();
	}

}
