package modeling.mobile.native_android_app;

import io.appium.java_client.remote.MobileCapabilityType;

import java.io.File;
import java.net.URL;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.github.arachnidium.core.settings.supported.ESupportedDrivers;
import com.github.arachnidium.model.mobile.MobileFactory;
import com.github.arachnidium.tutorial.app_modeling.mobile.native_app. /**<==!!!*/
		unannotated_pageobjects.aggregated_page_objects_with_annotated_fields.Hermitage;

public class MobileTest_HermitageAndroid_Very_Simple_Example1 {

	private Hermitage hermitage;
	
	@Before
	public void setUp() throws Exception {
		hermitage = new MobileFactory(
				ESupportedDrivers.ANDROID_APP,
				new DesiredCapabilities() {
					private static final long serialVersionUID = 1L;
					{
						setCapability(MobileCapabilityType.APP, new File(
								"src/test/resources/org.hermitagemuseum.apk")
								.getAbsolutePath()); //here is the path to *.apk, *.zip, *.app files
						setCapability(MobileCapabilityType.APP_WAIT_PACKAGE,
								"org.hermitagemuseum"); //Android app package we wait for 
						setCapability(MobileCapabilityType.APP_WAIT_ACTIVITY,
								"ui.activities.AcHermitageSplash_"); //Android app activity we wait for 
						setCapability(MobileCapabilityType.DEVICE_NAME,
								"Android Emulator");
					}
				}, new URL("http://127.0.0.1:4723/wd/hub")). //URL of the remote host where Appium NodeJS server is launched
				launch(Hermitage.class 
						//MobileApplication.class
						//AndroidApp.class  are available here
						); 
			//When app is launched then we close the quick tab guide
		    hermitage.hermitageMuseumQuickGuide.close();
	}

	@After
	public void tearDown() throws Exception {
		hermitage.quit();
	}

	@Test
	public void test() {
		//here we want to see the history of the museum
		hermitage.hermitageMuseumMainScreen.clickHistory();
				
		/**We should see that it is the history of the museum is shown*/
		Assert.assertEquals("History", hermitage.theHistoryOfTheHermitage.getTitle());
	    /**We look at pictures and read the text*/
		Assert.assertNotEquals(0, hermitage.theHistoryOfTheHermitage.getPictiresCount());
		Assert.assertNotEquals(0, hermitage.theHistoryOfTheHermitage.getParagraphCount());
		/**Ok! And then we get back to the general screen*/
		hermitage.theHistoryOfTheHermitage.back();
				
		/**And now we want to see the price of entry ticket*/
		hermitage.hermitageMuseumMainScreen.clickTickets();
		
		/**And now we want to see the price of entry ticket*/
		/**We should see that the entry price is here*/
		Assert.assertEquals(true, hermitage.informationAboutTickets.isEntryTicketLabelVisible());
		Assert.assertEquals(true, hermitage.informationAboutTickets.
						isTicketForNonProfitPhotographyAndVideoFilmingVisible());
				
		/**Ok! And then we get back to the general screen*/
		hermitage.theHistoryOfTheHermitage.back();		
	}

}
