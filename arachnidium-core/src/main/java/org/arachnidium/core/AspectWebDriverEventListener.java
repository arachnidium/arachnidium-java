package org.arachnidium.core;

import java.util.List;

import org.arachnidium.core.aspect.AbstractAspect;
import org.arachnidium.core.eventlisteners.IExtendedWebDriverEventListener;
import org.aspectj.lang.annotation.Aspect;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@Aspect
final class AspectWebDriverEventListener extends AbstractAspect implements IExtendedWebDriverEventListener{

	public AspectWebDriverEventListener(List<Object> objectsForFupport) {
		super(objectsForFupport);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void beforeNavigateTo(String url, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterNavigateTo(String url, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeNavigateBack(@SupportParam WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterNavigateBack(@SupportParam WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeNavigateForward(@SupportParam WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterNavigateForward(@SupportParam WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeFindBy(By by, WebElement element, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterFindBy(By by, WebElement element, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeClickOn(@TargetParam WebElement element, @SupportParam WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterClickOn(@TargetParam WebElement element, @SupportParam WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeChangeValueOf(@TargetParam WebElement element, @SupportParam WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterChangeValueOf(@TargetParam WebElement element, @SupportParam WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeScript(String script, @TargetParam WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterScript(String script, @SupportParam WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onException(Throwable throwable, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterAlertAccept(@SupportParam WebDriver driver, @TargetParam Alert alert) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterAlertDismiss(@SupportParam WebDriver driver, @TargetParam Alert alert) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterAlertSendKeys(@SupportParam WebDriver driver, @TargetParam Alert alert, String keys) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterSubmit(@SupportParam WebDriver driver, @TargetParam WebElement element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeAlertAccept(@SupportParam WebDriver driver, @TargetParam Alert alert) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeAlertDismiss(@SupportParam WebDriver driver, @TargetParam Alert alert) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeAlertSendKeys(@SupportParam WebDriver driver, @TargetParam Alert alert, String keys) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeSubmit(@SupportParam WebDriver driver, @TargetParam WebElement element) {
		// TODO Auto-generated method stub
		
	}

}
