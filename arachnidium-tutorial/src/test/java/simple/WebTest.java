package simple;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.arachnidium.core.settings.supported.ESupportedDrivers;
import com.github.arachnidium.model.browser.WebFactory;
import com.github.arachnidium.model.common.Application;
import com.github.arachnidium.tutorial.simple.web.Videos;
import com.github.arachnidium.tutorial.simple.web.VKLogin;
import com.github.arachnidium.tutorial.simple.web.UserPage;

//This is the test of a browser application example.
//Here we use VK (http://vk.com/) as an application under test.
//This test performs:
//- login to the social network
//- selection of the Video section
//- the searching for video which name contains "Selenium"  
//- the selection of the first video which is found
public class WebTest {
	
	private Application<?, ?> vk;
	//There are another possible declaration variant:
	//
	//private Application<Handle, IHowToGetHandle> vk;
	//
	//private BrowserApplication vk; - if we are going to perform tests
	//only against desktop and mobile browser

	/**
	 * We launch VK and run test against desktop Firefox
	 */
	@Before
	public void setUp() throws Exception {
		//com.github.arachnidium.model.browser.WebFactory is a factory
		//which instantiates:
		
		//- com.github.arachnidium.model.common.Application - it the default representation  
		//of the launched client (browser as well as mobile).
		
		//- com.github.arachnidium.model.browser.BrowserApplication - it the default representation  
		//of the launched browser client. Actually it is com.github.arachnidium.model.common.Application
		//subclass which is already adapted to browsers. 
		//
		//package com.github.arachnidium.model.browser
		//...
		//public abstract class BrowserApplication extends Application<BrowserWindow, HowToGetBrowserWindow> {
		
		//- customized subclasses of com.github.arachnidium.model.common.Application and 
		//com.github.arachnidium.model.browser.BrowserApplication
		//(!!!)
		//except com.github.arachnidium.model.mobile.MobileApplication and its subclasses
		
		
		vk = WebFactory.getApplication(Application.class,
				ESupportedDrivers.FIREFOX, "http://vk.com/login.php");
		//vk = WebFactory.getApplication(BrowserApplication.class,
		//		ESupportedDrivers.FIREFOX, "http://vk.com/login.php");
		
		//Here we perform log in
		VKLogin<?> vkLogin = vk.getPart(VKLogin.class);
		//By default we consider that all elements are located
		//at the first opened browser window (at the top of the page). More advanced example
		//when there are more than one opened window and content inside frames 
		//will be described in another chapters		
		vkLogin.setLogin("ArachnidiumTester@gmail.com"); //set eMail or phone number
		vkLogin.setPassword("ArachnidTester123");//set passoword
		vkLogin.enter();//enter
	}

	@Test
	public void test() {		
		UserPage<?> userPage = vk.getPart(UserPage.class);
		userPage.goHome();
		userPage.selectVideos(); //select "Video" section
		
		Videos<?> videos = userPage.getPart(Videos.class);//Here we take that Videos is the part of the
		//loaded user page
		
		videos.enterSearchString("Selenium");//search for a video
		Assert.assertNotEquals(0, videos.getVideosCount());//check that video is found
		videos.playVideo(0);//select the first video
	}
	
	@After
	public void tearDown(){
		vk.quit();
	}
}
