package web.mocks;

import org.arachnidium.core.eventlisteners.IUnhandledWindowEventListener;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;

public class MockUnhandledWindowEventListener implements
		IUnhandledWindowEventListener {

	public static MockUnhandledWindowEventListener listener;
	public static boolean wasInvoked = false;

	public MockUnhandledWindowEventListener() {
		listener = this;
		wasInvoked = false;
	}

	@Override
	public void whenUnhandledWindowIsFound(WebDriver weddriver) {
		wasInvoked = true;
	}

	@Override
	public void whenUnhandledAlertIsFound(Alert alert) {
		wasInvoked = true;
	}

	@Override
	public void whenUnhandledWindowIsNotClosed(WebDriver webdriver) {
		wasInvoked = true;
	}

	@Override
	public void whenUnhandledWindowIsAlreadyClosed(WebDriver weddriver) {
		wasInvoked = true;
	}

	@Override
	public void whenNoAlertThere(WebDriver weddriver) {
		wasInvoked = true;
	}

}
