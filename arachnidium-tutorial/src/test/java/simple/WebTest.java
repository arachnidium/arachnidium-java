package simple;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.arachnidium.core.settings.supported.ESupportedDrivers;
//import com.github.arachnidium.model.browser.BrowserApplication;
import com.github.arachnidium.model.browser.WebFactory;
//import com.github.arachnidium.core.Handle;
//import com.github.arachnidium.core.fluenthandle.IHowToGetHandle;
//import com.github.arachnidium.model.browser.BrowserApplication;
import com.github.arachnidium.model.common.Application;
import com.github.arachnidium.tutorial.simple.web.Videos;
import com.github.arachnidium.tutorial.simple.web.VKLogin;
import com.github.arachnidium.tutorial.simple.web.UserPage;

public class WebTest {
	
	private Application<?, ?> vk;
	//private Application<Handle, IHowToGetHandle> vk;
	//private BrowserApplication vk;

	/**
	 * We launch VK and run test against desktop Firefox
	 */
	@Before
	public void setUp() throws Exception {
		vk = WebFactory.getApplication(Application.class,
				ESupportedDrivers.FIREFOX, "http://vk.com/login.php");
		//vk = WebFactory.getApplication(BrowserApplication.class,
		//		ESupportedDrivers.FIREFOX, "http://vk.com/login.php");
		VKLogin<?> vkLogin = vk.getPart(VKLogin.class);
		vkLogin.setLogin("ArachnidiumTester@gmail.com");
		vkLogin.setPassword("ArachnidTester123");
		vkLogin.enter();
	}

	@Test
	public void test() {
		
		UserPage<?> userPage = vk.getPart(UserPage.class);
		userPage.goHome();
		userPage.selectVideos();
		
		Videos<?> videos = userPage.getPart(Videos.class);
		videos.enterSearchString("Selenium");
		Assert.assertNotEquals(0, videos.getVideosCount());
		videos.playVideo(0);
	}
	
	@After
	public void tearDown(){
		vk.quit();
	}
}
