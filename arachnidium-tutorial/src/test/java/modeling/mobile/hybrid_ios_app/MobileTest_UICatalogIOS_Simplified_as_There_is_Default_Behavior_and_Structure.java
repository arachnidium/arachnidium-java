package modeling.mobile.hybrid_ios_app;


import io.appium.java_client.remote.MobileCapabilityType;

import java.io.File;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.github.arachnidium.core.settings.supported.ESupportedDrivers;
import com.github.arachnidium.model.common.Application;
import com.github.arachnidium.model.mobile.MobileFactory;
import com.github.arachnidium.tutorial.app_modeling.mobile.hybrid_app.
	annotated_pageobjects.AppleCom; /**<==!!!*/
//import com.github.arachnidium.model.support.annotations.MobileContextNamePatterns;
import com.github.arachnidium.tutorial.app_modeling.mobile.hybrid_app.
	annotated_pageobjects.UICatalog;/**<==!!!*/

public class MobileTest_UICatalogIOS_Simplified_as_There_is_Default_Behavior_and_Structure {

	private Application<?, ?> uiCatalogApp;
	
	@Before
	public void setUp() throws Exception {
		uiCatalogApp = new MobileFactory(
				ESupportedDrivers.IOS_APP,
				new DesiredCapabilities() {
					private static final long serialVersionUID = 1L;
					{
						setCapability(MobileCapabilityType.APP, new File(
								"src/test/resources/UICatalog.app.zip")
								.getAbsolutePath()); //here is the path to *.apk, *.zip, *.app files
						setCapability(MobileCapabilityType.PLATFORM_VERSION, "8.0");
						setCapability(MobileCapabilityType.DEVICE_NAME, "iPad Simulator");
					}
				}, new URL("http://127.0.0.1:4723/wd/hub")). //URL of the remote host where Appium NodeJS server is launched
				launch(Application.class 
						//MobileApplication.class
						//IOSApp.class  are available here
						); 
		//When app is launched then we chose the "WebView" section
		uiCatalogApp.getPart(UICatalog.class).selectItem("Web View");
	}

	@After
	public void tearDown() throws Exception {
		uiCatalogApp.quit();
	}

	@Test
	public void test() {
		/**We want to interact with apple.com which is loaded
		 * in web view*/
		AppleCom appleCom = uiCatalogApp.getPart(AppleCom.class);
		appleCom.selectLink("Store");
		appleCom.selectShop("Shop iPhone");
		//Later we read something about iPhone
		//...................................
	}

}
