package web.mocks;

import java.util.concurrent.TimeUnit;

import org.arachnidium.core.eventlisteners.IExtendedWebDriverEventListener;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.WebElement;

public class MockWebDriverListener implements IExtendedWebDriverEventListener {
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
	public void beforeWebDriverSetTimeOut(WebDriver driver, Timeouts timeouts,
			long timeOut, TimeUnit timeUnit) {
		wasInvoked = true;
	}

	@Override
	public void afterWebDriverSetTimeOut(WebDriver driver, Timeouts timeouts,
			long timeOut, TimeUnit timeUnit) {
		wasInvoked = true;
	}

}
