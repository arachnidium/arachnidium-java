package com.github.arachnidium.tutorial.app_modeling.mobile.native_app.annotated_pageobjects.aggregated_page_objects;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.core.fluenthandle.IHowToGetHandle;
import com.github.arachnidium.model.common.Application;
import com.github.arachnidium.model.common.Static;
import com.github.arachnidium.tutorial.app_modeling.mobile.native_app.
		annotated_pageobjects.HermitageMuseumMainScreen; /**<==!!!*/
import com.github.arachnidium.tutorial.app_modeling.mobile.native_app.
		annotated_pageobjects.HermitageMuseumQuickGuide; /**<==!!!*/
import com.github.arachnidium.tutorial.app_modeling.mobile.native_app.
		annotated_pageobjects.InformationAboutTickets; /**<==!!!*/
import com.github.arachnidium.tutorial.app_modeling.mobile.native_app.
       annotated_pageobjects.TheHistoryOfTheHermitage; /**<==!!!*/

public class Hermitage extends Application<Handle, IHowToGetHandle> {
	/**If there is only interaction with mobile app then possible options are below: */
	//public class Hermitage extends MobileApplication{ 
	//public class Hermitage extends AndroidApp { 
	//public class Hermitage extends IOSApp { 
	
	@Static /**<== This annotation means that it is content which always/very frequently present
	and frequently used*/
	public HermitageMuseumQuickGuide hermitageMuseumQuickGuide; /**It is not necessary to 
	make fields public. You can make them private and implement public getter 
	for each one. I made that just for visibility*/
	
	@Static
	public HermitageMuseumMainScreen hermitageMuseumMainScreen;
	
	@Static
	public TheHistoryOfTheHermitage theHistoryOfTheHermitage;
	
	@Static
	public InformationAboutTickets informationAboutTickets;
	
	protected Hermitage(Handle handle) {
		super(handle);
	}

}
