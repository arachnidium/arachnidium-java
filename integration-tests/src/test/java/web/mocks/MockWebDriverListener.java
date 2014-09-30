package web.mocks;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.arachnidium.core.eventlisteners.IWebDriverEventListener;

public class MockWebDriverListener implements IWebDriverEventListener {
    public static MockWebDriverListener listener;
	
    public static boolean wasInvoked = false;
    
	public MockWebDriverListener() {
		super();
		listener = this;
	}

	@Override
	public void afterChangeValueOf(WebElement arg0, WebDriver arg1) {
		wasInvoked = true;
	}

	@Override
	public void afterClickOn(WebElement arg0, WebDriver arg1) {
		wasInvoked = true;
	}

	@Override
	public void afterFindBy(By arg0, WebElement arg1, WebDriver arg2) {
		wasInvoked = true;
	}

	@Override
	public void afterNavigateBack(WebDriver arg0) {
		wasInvoked = true;
	}

	@Override
	public void afterNavigateForward(WebDriver arg0) {
		wasInvoked = true;
	}

	@Override
	public void afterNavigateTo(String arg0, WebDriver arg1) {
		wasInvoked = true;
	}

	@Override
	public void afterScript(String arg0, WebDriver arg1) {
		wasInvoked = true;
	}

	@Override
	public void beforeChangeValueOf(WebElement arg0, WebDriver arg1) {
		wasInvoked = true;
	}

	@Override
	public void beforeClickOn(WebElement arg0, WebDriver arg1) {
		wasInvoked = true;
	}

	@Override
	public void beforeFindBy(By arg0, WebElement arg1, WebDriver arg2) {
		wasInvoked = true;
	}

	@Override
	public void beforeNavigateBack(WebDriver arg0) {
		wasInvoked = true;
	}

	@Override
	public void beforeNavigateForward(WebDriver arg0) {
		wasInvoked = true;

	}

	@Override
	public void beforeNavigateTo(String arg0, WebDriver arg1) {
		wasInvoked = true;
	}

	@Override
	public void beforeScript(String arg0, WebDriver arg1) {
		wasInvoked = true;
	}

	@Override
	public void onException(Throwable arg0, WebDriver arg1) {
		wasInvoked = true;
	}

	@Override
	public void beforeSubmit(WebDriver driver, WebElement element) {
		wasInvoked = true;
	}

	@Override
	public void afterSubmit(WebDriver driver, WebElement element) {
		wasInvoked = true;
	}

	@Override
	public void beforeAlertDismiss(WebDriver driver, Alert alert) {
		wasInvoked = true;
	}

	@Override
	public void afterAlertDismiss(WebDriver driver, Alert alert) {
		wasInvoked = true;
	}

	@Override
	public void beforeAlertAccept(WebDriver driver, Alert alert) {
		wasInvoked = true;
	}

	@Override
	public void afterAlertAccept(WebDriver driver, Alert alert) {
		wasInvoked = true;
	}

	@Override
	public void beforeAlertSendKeys(WebDriver driver, Alert alert, String keys) {
		wasInvoked = true;
	}

	@Override
	public void afterAlertSendKeys(WebDriver driver, Alert alert, String keys) {
		wasInvoked = true;
	}

	@Override
	public void beforeFindBy(String byString, WebElement element,
			WebDriver driver) {
		wasInvoked = true;
		
	}

	@Override
	public void afterFindBy(String byString, WebElement element,
			WebDriver driver) {
		wasInvoked = true;
		
	}

	@Override
	public void beforeQuit(WebDriver driver) {
		wasInvoked = true;
		
	}

}
