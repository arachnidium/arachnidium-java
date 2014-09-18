package samles;

import net.thucydides.core.annotations.Steps;
import net.thucydides.junit.runners.ThucydidesRunner;

import org.arachnidium.util.configuration.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import arachnidium.thucydides.steps.GoogleDriveSteps;

@RunWith(ThucydidesRunner.class)
public class GoogleDriveTestCase {
	private static String PASSWORD = "ArachnidTester123";
	private static String RESOURCE_PATH = "src/test/resources/";
	private static final String SETTIGS_OF_CHROME = "chrome.json";
	private static String USER = "ArachnidiumTester";
	
	@Steps
	GoogleDriveSteps steps;
	
	@Before
	public void setUp() throws Exception {
		steps.insatantiateApp(Configuration.get(RESOURCE_PATH + SETTIGS_OF_CHROME));
	}
	
	@Test
	public void sampleTestOnTheGoogleDrive(){
		steps.login(USER, PASSWORD);
		steps.startShare();
		steps.clickOnManagePermissions();
		steps.setInviteEmail("arachnidiumtester@gmail.com");
		steps.clickCancel();
		steps.clickFinishShare();
		steps.logOut();
	}

	@After
	public void tearDown() throws Exception {
		steps.quit();
	}

}
