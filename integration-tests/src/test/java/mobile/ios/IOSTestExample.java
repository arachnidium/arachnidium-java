package mobile.ios;

import org.arachnidium.mobile.ios.iucatalog.ActionSheets;
import org.arachnidium.mobile.ios.iucatalog.AlertView;
import org.arachnidium.mobile.ios.iucatalog.UICatalog;
import org.arachnidium.model.mobile.MobileAppliction;
import org.arachnidium.model.mobile.MobileFactory;
import org.arachnidium.util.configuration.Configuration;
import org.testng.annotations.Test;

public class IOSTestExample {
	
  @Test
  	public void iOSUICatalogTest() {
  		Configuration config = Configuration
				.get("src/test/resources/configs/mobile/app/ios/ios_uiCatalog.json");
		MobileAppliction uiCatalog = MobileFactory.getApplication(
				MobileAppliction.class, config);
		UICatalog uicatalog = uiCatalog.getPart(UICatalog.class);
		uicatalog.shake();
		uicatalog.selectItem("Action Sheets, AAPLActionSheetViewController");
		
		ActionSheets<?> actionSheets = uiCatalog.getPart(ActionSheets.class);
		actionSheets.clickOnOk_Cancel();
		actionSheets.clickOnSplashButton("OK");
		actionSheets.clickOnOther();
		actionSheets.clickOnSplashButton("Safe Choice");
		
		uicatalog.backToMe();
		uicatalog.selectItem("Alert Views, AAPLAlertViewController");
		
		AlertView alertView = uiCatalog.getPart(AlertView.class);
		alertView.invokeSimpleAlert();		
		uiCatalog.getManager().getAlert().dismiss();	
		uicatalog.backToMe();
		
		uiCatalog.quit();
  	}
}
