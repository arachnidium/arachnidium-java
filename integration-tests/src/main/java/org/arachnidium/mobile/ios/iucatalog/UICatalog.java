package org.arachnidium.mobile.ios.iucatalog;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;

import org.openqa.selenium.support.FindBy;
import org.arachnidium.model.mobile.ios.IOSContext;
import org.arachnidium.core.SingleContext;

public class UICatalog extends IOSContext {
	@FindBy(name = "UICatalog")
	private MobileElement backToMe;
	
	public UICatalog(SingleContext context) {
		super(context);
		load();
	}

	public void shake() {
		shaker.shake();		
	}
	
	@InteractiveMethod
	public void selectItem(String item){
		TouchAction touchAction = touchActions.getTouchAction();
		touchAction.tap(namedTextFieldGetter.getNamedTextField(item));
		touchActionsPerformer.performTouchAction(touchAction);
	}
	
	@InteractiveMethod
	public void backToMe(){
		backToMe.click();
	}
	
}
