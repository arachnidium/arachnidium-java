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
import com.github.arachnidium.model.common.Application;
import com.github.arachnidium.model.mobile.MobileFactory;
import com.github.arachnidium.tutorial.app_modeling.mobile.native_app.
		annotated_pageobjects.HermitageMuseumQuickGuide; /**<==*/
import com.github.arachnidium.tutorial.app_modeling.mobile.native_app.
		annotated_pageobjects.HermitageMuseumMainScreen; /**<==*/
import com.github.arachnidium.tutorial.app_modeling.mobile.native_app.
		annotated_pageobjects.TheHistoryOfTheHermitage;  /**<==*/
import com.github.arachnidium.tutorial.app_modeling.mobile.native_app.
		annotated_pageobjects.InformationAboutTickets; /**<==*/




public class MobileTest_HermitageAndroid_Simplified_as_There_is_Default_Behavior_and_Structure {

	private Application<?, ?> hermitage;
	
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
				launch(Application.class 
						//MobileApplication.class
						//AndroidApp.class  are available here
						); 
			//When app is launched then we close the quick tab guide
		    hermitage.getPart(HermitageMuseumQuickGuide.class).close();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		//here we want to see the history of the museum
		HermitageMuseumMainScreen mainScreen = hermitage.getPart(HermitageMuseumMainScreen.class);
		mainScreen.clickHistory();
		
		TheHistoryOfTheHermitage theHistoryOfTheHermitage = 
				hermitage.getPart(TheHistoryOfTheHermitage.class);
		/**We should see that it is the history of the museum is shown*/
		Assert.assertEquals("History", theHistoryOfTheHermitage.getTitle());
		/**We look at pictures and read the text*/
		Assert.assertNotEquals(0, theHistoryOfTheHermitage.getPictiresCount());
		Assert.assertNotEquals(0, theHistoryOfTheHermitage.getParagraphCount());
		/**Ok! And then we get back to the general screen*/
		theHistoryOfTheHermitage.back();
		
		/**And now we want to see the price of entry ticket*/
		mainScreen.clickTickets();
		
		/**And now we want to see the price of entry ticket*/
		InformationAboutTickets informationAboutTickets = 
				hermitage.getPart(InformationAboutTickets.class); 
		/**We should see that the entry price is here*/
		Assert.assertEquals(true, informationAboutTickets.isEntryTicketLabelVisible());
		Assert.assertEquals(true, informationAboutTickets.
				isTicketForNonProfitPhotographyAndVideoFilmingVisible());
		
		/**Ok! And then we get back to the general screen*/
		theHistoryOfTheHermitage.back();		
	}

}
