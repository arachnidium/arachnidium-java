package org.arachnidium.model.support;

import org.arachnidium.core.WebDriverEncapsulation;import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


/**
 * Manages switching between frames on a page or context of the hybrid 
 * mobile application
 */
public class FramePathStrategy implements PathStrategy {

	@SuppressWarnings("unchecked")
	@Override
	public WebDriverEncapsulation switchTo(WebDriverEncapsulation webDriverEncapsulation) {
		framePath.forEach((frameIdentifier) -> {
			
			WebDriver wrapped = webDriverEncapsulation.getWrappedDriver();
			if (frameIdentifier instanceof String) {
				wrapped.switchTo().frame(String.valueOf(frameIdentifier));
				return;
			}
			
			if (frameIdentifier instanceof Integer) {
				wrapped.switchTo().frame((int) frameIdentifier);
				return;
			}
			
			if (frameIdentifier instanceof By) {
				wrapped.switchTo().frame(wrapped.findElement((By) frameIdentifier));
				return;
			}
			
			
			
		});
		return webDriverEncapsulation;
	}
}
