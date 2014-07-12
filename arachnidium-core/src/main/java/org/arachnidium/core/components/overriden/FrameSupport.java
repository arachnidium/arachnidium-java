package org.arachnidium.core.components.overriden;

import org.arachnidium.core.components.WebdriverComponent;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * @author s.tihomirov This is a class for comfort working with frames
 */
public final class FrameSupport extends WebdriverComponent {

	private Awaiting awaiting;

	public FrameSupport(WebDriver driver) {
		super(driver);
		this.awaiting = new Awaiting(driver);
	}

	public void switchTo(int frame) {
		driver.switchTo().frame(frame);
	}

	public void switchTo(String frame) {
		driver.switchTo().frame(frame);
	}

	public void switchTo(String frame, long secTimeOut) throws TimeoutException {
		awaiting.awaitCondition(secTimeOut,
				ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame));
	}

	public void switchTo(WebElement frame) {
		driver.switchTo().frame(frame);
	}

	public void switchToParentFrame() {
		driver.switchTo().parentFrame();
	}

}
