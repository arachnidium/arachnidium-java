package junitdemo;

import com.github.arachnidium.model.browser.WebFactory;
import com.github.arachnidium.model.common.Application;
import com.github.arachnidium.util.configuration.Configuration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.arachnidium.htmlelements.googledrive.Document;
import com.github.arachnidium.htmlelements.googledrive.DocumentList;
import com.github.arachnidium.htmlelements.googledrive.LogOut;
import com.github.arachnidium.htmlelements.googledrive.LoginToGoogleService;
import com.github.arachnidium.htmlelements.googledrive.ShareDocumentSettings;
import com.github.arachnidium.htmlelements.googledrive.SpreadSheet;

/**
 * This is simplified copy of my integration test
 * on google drive
 *
 */
public class JUnitSimpleDemo {

	private Application<?, ?> googleDrive;
	private Configuration configuration;
	private static final String SETTIGS_OF_CHROME = "chrome.json";
	private static String RESOURCE_PATH = "src/test/resources/";
	private static String DOC_LINK = "https://docs.google.com/document/d/1PNtBQwSHQyedeIZqV5VYakPWJtW5incbuiKekB33HyQ/edit";
	private static String GOOGLE_DRIVE = "https://drive.google.com";
	//please don't use this account. There is nothing interesting for you. 
    private final String USER = "ArachnidiumTester"; //I have created it only for demonstration of 
    //framework capabilities
	private final String PASSWORD = "ArachnidTester_456"; 

	
	@Before
	public void setUp() throws Exception {
		configuration = Configuration.get(RESOURCE_PATH + SETTIGS_OF_CHROME);
	}

	@After
	public void tearDown() throws Exception {
		googleDrive.quit();
	}

	@Test
	public void test1() throws Exception {
		googleDrive = new WebFactory(configuration).launch(Application.class, DOC_LINK);
		
		LoginToGoogleService<?> loginToGoogleService = googleDrive
				.getPart(LoginToGoogleService.class);
		loginToGoogleService.clickOnPersistentCookie();
		loginToGoogleService.setEmail(USER);
		loginToGoogleService.setPassword(PASSWORD);
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
	}
	
	@Test
	public void test2 () throws Exception {
		googleDrive = new WebFactory(configuration).launch(Application.class,
				GOOGLE_DRIVE);
		LoginToGoogleService<?> loginToGoogleService = googleDrive
				.getPart(LoginToGoogleService.class);

		loginToGoogleService.clickOnPersistentCookie();
		loginToGoogleService.setEmail(USER);
		loginToGoogleService.setPassword(PASSWORD);
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
	}	

}
