package simple;

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
import com.github.arachnidium.model.mobile.android.AndroidApp;
import com.github.arachnidium.tutorial.simple.mobile.UserScreen;
import com.github.arachnidium.tutorial.simple.mobile.VKLogin;
//import com.github.arachnidium.model.mobile.MobileApplication;
import com.github.arachnidium.tutorial.simple.mobile.Videos;
//import com.github.arachnidium.model.browser.BrowserApplication;
//import com.github.arachnidium.core.Handle;
//import com.github.arachnidium.core.fluenthandle.IHowToGetHandle;

public class MobileTest {
	
	private Application<?, ?> vk;
	//private Application<Handle, IHowToGetHandle> vk;
	//private MobileApplication vk;

	/**
	 * We launch VK and run test against Android 4.4.4
	 */
	@Before
	public void setUp() throws Exception {
		vk = MobileFactory.getApplication(
				//Application.class, 
				//MobileApplication.class
				AndroidApp.class, ESupportedDrivers.ANDROID_APP,
				new DesiredCapabilities() {
					private static final long serialVersionUID = 1L;
					{
						setCapability(MobileCapabilityType.APP, new File(
								"src/test/resources/com.vkontakte.android.apk")
								.getAbsolutePath());
						setCapability(MobileCapabilityType.APP_PACKAGE,
								"com.vkontakte.android");
						setCapability(MobileCapabilityType.APP_ACTIVITY,
								"LoginActivity");
						setCapability(MobileCapabilityType.DEVICE_NAME,
								"Android Emulator");
					}
				}, new URL("http://127.0.0.1:4723/wd/hub"));
		VKLogin<?> vkLogin = vk.getPart(VKLogin.class);
		vkLogin.setLogin("ArachnidiumTester@gmail.com");
		vkLogin.setPassword("ArachnidTester123");
		vkLogin.enter();
		Thread.sleep(3000);
		((AndroidApp) vk).startActivity("com.vkontakte.android", "MainActivity");
	}

	@Test
	public void test() throws Exception{		
		UserScreen<?> userPage = vk.getPart(UserScreen.class);
		userPage.goHome();
		userPage.selectVideos();
		Videos<?> videos = userPage.getPart(Videos.class);
		videos.clickSearchButton();
		videos.enterSearchString("Selenium");
		Assert.assertNotEquals(0, videos.getVideosCount());
		videos.playVideo(0);
	}
	
	@After
	public void tearDown(){
		vk.quit();
	}
}
