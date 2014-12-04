package mobile.ios;

import com.github.arachnidium.util.configuration.Configuration;

import org.testng.annotations.Test;

import com.github.arachnidium.mobile.ios.iucatalog.ActionSheets;
import com.github.arachnidium.mobile.ios.iucatalog.AlertView;
import com.github.arachnidium.mobile.ios.iucatalog.AppleCom;
import com.github.arachnidium.mobile.ios.iucatalog.UICatalog;
import com.github.arachnidium.model.mobile.MobileApplication;
import com.github.arachnidium.model.mobile.MobileFactory;
import com.github.arachnidium.model.mobile.WebViewContent;

public class IOSTestExample {

	@Test
	public void iOSUICatalogTest() {
		Configuration config = Configuration
				.get("src/test/resources/configs/mobile/app/ios/ios_uiCatalog.json");
		MobileApplication uiCatalog = new MobileFactory(config).launch(
				MobileApplication.class);
		try {
			UICatalog uicatalog = uiCatalog.getPart(UICatalog.class);
			uicatalog.backToMe();
			uicatalog.shake();
			uicatalog
					.selectItem("Action Sheets");

			ActionSheets<?> actionSheets = uiCatalog
					.getPart(ActionSheets.class);
			actionSheets.clickOnOk_Cancel();
			actionSheets.clickOnSplashButton("OK");
			actionSheets.clickOnOther();
			actionSheets.clickOnSplashButton("Safe Choice");

			uicatalog.backToMe();
			uicatalog.selectItem("Alert Views");

			AlertView alertView = uiCatalog.getPart(AlertView.class);
			alertView.invokeSimpleAlert();
			uiCatalog.getManager().getAlert().dismiss();
			uicatalog.backToMe();
			
			uicatalog.selectItem("Web View");
			
			WebViewContent webView = uiCatalog.getPart(WebViewContent.class);
			webView.getCurrentUrl();
			webView.refresh();
			
			AppleCom appleCom = uiCatalog.getPart(AppleCom.class);
			appleCom.selectLink("Store");
			appleCom.selectShop("Shop iPhone");
			uicatalog.backToMe();
			uicatalog
			.selectItem("Action Sheets");
		} finally {
			uiCatalog.quit();
		}
	}
}
