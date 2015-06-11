package web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import com.github.arachnidium.util.configuration.Configuration;

import org.openqa.selenium.Platform;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.github.arachnidium.model.browser.WebFactory;
import com.github.arachnidium.model.common.Application;
import com.github.arachnidium.web.googledrive.Document;
import com.github.arachnidium.web.googledrive.DocumentList;
import com.github.arachnidium.web.googledrive.GoogleDriveService;
import com.github.arachnidium.web.googledrive.LogOut;
import com.github.arachnidium.web.googledrive.LoginToGoogleService;
import com.github.arachnidium.web.googledrive.ShareDocumentSettings;
import com.github.arachnidium.web.googledrive.SpreadSheet;

public class GoogleDriveTest {
	private final String GOOGLE_DRIVE = "https://drive.google.com";
	//please don't use this account. There is nothing interesting for you. 
	private final String USER = "ArachnidiumTester"; //I have created it only for this test.
	private final String PASSWORD = "ArachnidTester_789"; //It is temporary. Later
	//I will made a simple site which will be used by the similar test 

	// settings according to current OS
	private final HashMap<Platform, List<String>> settings = new HashMap<Platform, List<String>>();

	List<String> getConfigsByCurrentPlatform() {
		Set<Entry<Platform, List<String>>> entries = settings.entrySet();
		for (Entry<Platform, List<String>> entry : entries) {
			if (Platform.getCurrent().is(entry.getKey())) {
				return entry.getValue();
			}
		}

		return new ArrayList<String>();
	}

	@BeforeTest
	public void beforeTest() {
		// for Windows
		settings.put(Platform.WINDOWS, new ArrayList<String>() {
			private static final long serialVersionUID = -1718278594717074313L;
			{
				add("chrome_remote.json");
				add("chrome_withDesiredUrl.json");
				add("chrome.json");

				add("firefox_remote.json");
				add("firefox.json");
				add("firefox_withDesiredUrl.json");

				//add("internetexplorer_remote.json");
				//add("internetexplorer.json");
				// Internet Explorer is too slow for this

				//add("phantomjs_remote.json");
				//add("phantomjs.json");
				//Doesn't work against Google drive
			}

		});
		// for MAC
		settings.put(Platform.MAC, new ArrayList<String>() {
			private static final long serialVersionUID = -1718278594717074313L;
			{
				add("chrome_remote.json");
				add("chrome_withDesiredUrl.json");
				add("chrome.json");

				add("firefox_remote.json");
				add("firefox.json");
				add("firefox_withDesiredUrl.json");

				// add("safari_remote.json");
				// add("safari.json");
				// the Actions works bad against SafariDriver

				//add("phantomjs_remote.json");
				//add("phantomjs.json");
				//Doesn't work against Google drive

			}

		});
	}

	private void test1(Application<?, ?> googleDrive) throws Exception {
		try {
			LoginToGoogleService<?> loginToGoogleService = googleDrive
					.getPart(LoginToGoogleService.class);
			loginToGoogleService.setEmail(USER);
			loginToGoogleService.next();
			loginToGoogleService.setPassword(PASSWORD);
			loginToGoogleService.clickOnPersistentCookie();
			loginToGoogleService.singIn();

			Document<?> document = googleDrive.getPart(Document.class);
			document.clickShareButton();

			// is always inside frame
			ShareDocumentSettings<?> shareSettings = document
					.getPart(ShareDocumentSettings.class);
			shareSettings.clickOnManagePermissions();
			shareSettings.invite("arachnidiumtester@gmail.com");
			shareSettings.clickCancel();
			shareSettings.clickDone();

			LogOut<?> logOut = document.getPart(LogOut.class);
			logOut.clickOnProfile();
			logOut.quit();
		} finally {
			googleDrive.quit();
		}
	}

	@Test(description = "Classes are marked by annotatins whose values form strategies of getting page objects. Check this out!")
	@Parameters(value = { "path", "configList" })
	public void test1(
			@Optional("src/test/resources/configs/desctop/") String path,
			@Optional("chrome_withDesiredUrl.json,firefox_withDesiredUrl.json") String configList)
			throws Exception {

		List<String> configs = getConfigsByCurrentPlatform();
		String[] configNames = configList.split(",");

		for (String config : configNames) {
			if (!configs.contains(config)) {
				continue;
			}
			Configuration configuration = Configuration.get(path + config);
			test1(new WebFactory(configuration).launch(Application.class));
		}
	}

	private void test2(Application<?, ?> googleDrive) throws Exception {
		try {
			LoginToGoogleService<?> loginToGoogleService = googleDrive
					.getPart(LoginToGoogleService.class);

			loginToGoogleService.setEmail(USER);
			loginToGoogleService.next();
			loginToGoogleService.setPassword(PASSWORD);
			loginToGoogleService.clickOnPersistentCookie();
			loginToGoogleService.singIn();

			DocumentList<?> documentList = googleDrive
					.getPart(DocumentList.class);
			documentList.choseSection(0);
			documentList.clickOnDoc("TestDocument");

			Document<?> document = googleDrive.getPart(Document.class, 1);
			document.clickShareButton();

			// is always inside frame
			ShareDocumentSettings<?> shareSettings = document
					.getPart(ShareDocumentSettings.class);
			shareSettings.clickOnManagePermissions();
			shareSettings.invite("arachnidiumtester@gmail.com");
			shareSettings.clickCancel();
			shareSettings.clickDone();

			document.clickShareButton();
			shareSettings = googleDrive.getPart(ShareDocumentSettings.class, 1);
			shareSettings.clickOnManagePermissions();
			shareSettings.invite("arachnidiumtester@gmail.com");
			shareSettings.clickCancel();
			shareSettings.clickDone();

			documentList.clickOnDoc("TestSpreadSheet");
			SpreadSheet<?> spreadSheet = googleDrive.getPart(SpreadSheet.class,
					2);
			spreadSheet.clickShareButton();
			shareSettings = spreadSheet.getPart(ShareDocumentSettings.class);
			shareSettings.clickOnManagePermissions();
			shareSettings.invite("arachnidiumtester@gmail.com");
			shareSettings.clickCancel();
			shareSettings.clickDone();

			spreadSheet.clickShareButton();
			shareSettings = googleDrive.getPart(ShareDocumentSettings.class, 2);
			shareSettings.clickOnManagePermissions();
			shareSettings.invite("arachnidiumtester@gmail.com");
			shareSettings.clickCancel();
			shareSettings.clickDone();

			LogOut<?> logOut = documentList.getPart(LogOut.class);
			logOut.clickOnProfile();
			logOut.quit();
		} finally {
			googleDrive.quit();
		}
	}
	
	private void test3(GoogleDriveService<?, ?> service) {
		try {
			service.login.setEmail(USER);
			service.login.next();
			service.login.setPassword(PASSWORD);
			service.login.clickOnPersistentCookie();
			service.login.singIn();
			
			service.documentList.choseSection(0);
			service.documentList.clickOnDoc("TestDocument");

			service.document.clickShareButton();
			service.document.shareDocumentSettings.clickOnManagePermissions();
			service.document.shareDocumentSettings
					.invite("arachnidiumtester@gmail.com");
			service.document.shareDocumentSettings.clickCancel();
			service.document.shareDocumentSettings.clickDone();
			
			service.documentList.clickOnDoc("TestSpreadSheet");
			
			service.sheet.clickShareButton();
			service.sheet.shareDocumentSettings.clickOnManagePermissions();
			service.sheet.shareDocumentSettings
					.invite("arachnidiumtester@gmail.com");
			service.sheet.shareDocumentSettings.clickCancel();
			service.sheet.shareDocumentSettings.clickDone();

			service.documentList.logOut.clickOnProfile();
			service.documentList.logOut.quit();
		} finally {
			service.quit();
		}
	}

	@Test(description = "Classes are marked by annotatins whose values form strategies of getting page objects. But some parameters are defined explicitly. "
			+ "Check this out!")
	@Parameters(value = { "path", "configList" })
	public void test2(
			@Optional("src/test/resources/configs/desctop/") String path,
			@Optional("chrome.json,firefox.json") String configList)
			throws Exception {

		List<String> configs = getConfigsByCurrentPlatform();
		String[] configNames = configList.split(",");

		for (String config : configNames) {
			if (!configs.contains(config)) {
				continue;
			}
			Configuration configuration = Configuration.get(path + config);
			test2(new WebFactory(configuration).launch(Application.class, GOOGLE_DRIVE));
		}
	}
	
	@Test(description = "This test checks ability to create proxy instances and use them to fill FunctionalPart fields")
	@Parameters(value = { "path", "configList" })
	public void test3(
			@Optional("src/test/resources/configs/desctop/") String path,
			@Optional("chrome.json,firefox.json") String configList)
			throws Exception {

		List<String> configs = getConfigsByCurrentPlatform();
		String[] configNames = configList.split(",");

		for (String config : configNames) {
			if (!configs.contains(config)) {
				continue;
			}
			Configuration configuration = Configuration.get(path + config);
			test3(new WebFactory(configuration).launch(GoogleDriveService.class, GOOGLE_DRIVE));
		}
	}
}
