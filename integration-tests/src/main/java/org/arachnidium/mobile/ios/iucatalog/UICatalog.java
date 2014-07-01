package org.arachnidium.mobile.ios.iucatalog;

import io.appium.java_client.TouchAction;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.arachnidium.model.mobile.ios.IOSContext;
import org.arachnidium.core.SingleContext;
import org.arachnidium.core.interfaces.IShakes;

public class UICatalog extends IOSContext implements IShakes {
	@FindBy(name = "UICatalog")
	private WebElement backToMe;
	
	public UICatalog(SingleContext context) {
		super(context);
		load();
	}

	@Override
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
