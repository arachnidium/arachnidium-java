package web.mocks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

public class MockWebDriverEventListener2 implements WebDriverEventListener {

	public static MockWebDriverEventListener2 listener;
	public static boolean wasInvoked = false;

	public MockWebDriverEventListener2() {
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

}
