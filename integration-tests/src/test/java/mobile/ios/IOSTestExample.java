package mobile.ios;

import com.github.arachnidium.util.configuration.Configuration;
import org.testng.annotations.Test;

import com.github.arachnidium.mobile.ios.iucatalog.ActionSheets;
import com.github.arachnidium.mobile.ios.iucatalog.AlertView;
import com.github.arachnidium.mobile.ios.iucatalog.UICatalog;
import com.github.arachnidium.model.mobile.MobileApplication;
import com.github.arachnidium.model.mobile.MobileFactory;

public class IOSTestExample {
	
  @Test
  	public void iOSUICatalogTest() {
  		Configuration config = Configuration
				.get("src/test/resources/configs/mobile/app/ios/ios_uiCatalog.json");
		MobileApplication uiCatalog = MobileFactory.getApplication(
				MobileApplication.class, config);
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
