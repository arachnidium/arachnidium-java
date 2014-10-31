package simple;

import io.appium.java_client.remote.MobileCapabilityType;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.github.arachnidium.core.settings.supported.ESupportedDrivers;
import com.github.arachnidium.model.browser.WebFactory;
import com.github.arachnidium.model.common.Application;
import com.github.arachnidium.model.common.DefaultApplicationFactory;
import com.github.arachnidium.model.mobile.MobileFactory;
import com.github.arachnidium.model.mobile.android.AndroidApp;
import com.github.arachnidium.tutorial.simple.mobile_and_web.User;
import com.github.arachnidium.tutorial.simple.mobile_and_web.VKLogin;
import com.github.arachnidium.tutorial.simple.mobile_and_web.Videos;

@RunWith(Parameterized.class)
public class MobileAndWebTest {

	@Parameters
	public static Collection<Object[]> data() throws Exception {
		
        return Arrays.asList(new Object[][] {
                 { WebFactory.class, 
                	 new Object[]{new Class<?>[]{Class.class, ESupportedDrivers.class, String.class}, 
                		 new Object[]{Application.class, ESupportedDrivers.FIREFOX, "http://vk.com/login.php"}} },
                 
                 {MobileFactory.class, 
                   	 new Object[]{
                	 new Class<?>[]{Class.class, ESupportedDrivers.class, Capabilities.class, URL.class}, 
                   		 new Object[]{AndroidApp.class, ESupportedDrivers.ANDROID_APP, 
                			 
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
        				  }, new URL("http://127.0.0.1:4723/wd/hub")}} }
           });
    }

	private static final String GET_APPLICATION_METHOD = "getApplication";
	@Parameter(0)
	public Class<? extends DefaultApplicationFactory> desiredFactory;
	@Parameter(1)
	public Object[] parameterAndValues;

	private Application<?, ?> vk;
	
	
	private void loginPrerequisite(){
		VKLogin<?> vkLogin = vk.getPart(VKLogin.class);
		vkLogin.setLogin("ArachnidiumTester@gmail.com");
		vkLogin.setPassword("ArachnidTester123");
		vkLogin.enter();
	}
	
	/**
	 * We launch VK and run test against desktop Firefox
	 * Also we attempt to check Android native client
	 */	
	@Before
	public void setUp() throws Exception {
		Method getApplication = desiredFactory.getMethod(GET_APPLICATION_METHOD, (Class<?>[]) parameterAndValues[0]);
		vk = (Application<?, ?>) getApplication.invoke(desiredFactory, (Object[]) parameterAndValues[1]);
		loginPrerequisite();
		/**
		 * When here is Android we have to launch activity 'MainActivity' forcefully
		 * is it a bug of VK? 
		 */
		if (AndroidApp.class.isAssignableFrom(vk.getClass())){
			Thread.sleep(3000);
			((AndroidApp) vk).startActivity("com.vkontakte.android", "MainActivity");
		}
	}

	@After
	public void tearDown() throws Exception {
		vk.quit();
	}

	@Test
	public void test() {
		User<?> user = vk.getPart(User.class);
		user.goHome();
		user.selectVideos();
		
		Videos<?> videos = user.getPart(Videos.class);
		videos.enterSearchString("Selenium");
		Assert.assertNotEquals(0, videos.getVideosCount());
		videos.playVideo(0);
	}

}
