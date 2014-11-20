package com.github.arachnidium.model.support.annotations.classdeclaration.rootelements;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.pagefactory.ByAll;
import org.openqa.selenium.support.pagefactory.ByChained;

public class ElementReaderForMobilePlatforms implements IRootElementReader {
	private static final String UI_AUTOMATOR = "uiAutomator";
	private static final String ACCESSIBILITY = "accessibility";
	private static final String CLASSNAME = "className";
	private static final String ID =  "id";
	private static final String TAG_NAME = "tagName";
	private static final String NAME = "name";
	private static final String XPATH = "xpath";
	
	private static final String CHAIN = "chain";
	
	private static Class<?>[] emptyParams = new Class[]{};
	private static Object[] emptyValues = new Object[]{};
	
	@SuppressWarnings("unchecked")
	private static <T> T getValueFromAnnotation(Annotation annotation, String methodName){
		try {
			Method m = annotation.getClass().getDeclaredMethod(methodName, emptyParams);
			return (T) m.invoke(annotation, emptyValues);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private static By getBy(Annotation annotation, WebDriver driver){
		String value = getValueFromAnnotation(annotation, ACCESSIBILITY);
		if (!"".equals(value)){
			return MobileBy.AccessibilityId(value);
		}
		
		value = getValueFromAnnotation(annotation, CLASSNAME);
		if (!"".equals(value)){
			return MobileBy.className(value);
		}
		
		value = getValueFromAnnotation(annotation, ID);
		if (!"".equals(value)){
			return MobileBy.id(value);
		}
		
		value = getValueFromAnnotation(annotation, TAG_NAME);
		if (!"".equals(value)){
			return MobileBy.tagName(value);
		}
		
		value = getValueFromAnnotation(annotation, NAME);
		if (!"".equals(value)){
			return MobileBy.name(value);
		}
		
		value = getValueFromAnnotation(annotation, XPATH);
		if (!"".equals(value)){
			return MobileBy.xpath(value);
		}	
		
		value = getValueFromAnnotation(annotation, UI_AUTOMATOR);
		if (!"".equals(value)){
			if (AndroidDriver.class.isAssignableFrom(driver.getClass())){
				return MobileBy.AndroidUIAutomator(value);
			}
			return MobileBy.IosUIAutomation(value);
		}	
		throw new IllegalArgumentException("No one known locator strategy was defined!");
	}
	
	private static ByChained getPossibleChain(Annotation annotation, WebDriver driver){
		List<By> result = new ArrayList<>();		
		Annotation[] bies = getValueFromAnnotation(annotation, CHAIN);
		
		for (Annotation chainElement: bies) {
			By by = getBy(chainElement, driver);
			result.add(by);
		}
		return new ByChained(result.toArray(new By[]{}));
	}	

	@Override
	public By readClassAndGetBy(Class<?> readableClass, WebDriver driver) {
		List<By> result = new ArrayList<>();
		Annotation[] possibleRoots = null;		
		if (AndroidDriver.class.isAssignableFrom(driver.getClass())){
			possibleRoots = getAnnotations(RootAndroidElement.class, readableClass);
		}
		if (IOSDriver.class.isAssignableFrom(driver.getClass())){
			possibleRoots = getAnnotations(RootIOSElement.class, readableClass);
		}
		if (possibleRoots == null){
			return null;
		}
		
		if (possibleRoots.length == 0){
			return null;
		}
		
		for (Annotation chain: possibleRoots) {
			result.add(getPossibleChain(chain, driver));
		}	
		
		//this is an attempt to get By strategy
		//by present @FindBy annotations
		if (result.size() == 0)
			return new CommonRootElementReader().readClassAndGetBy(readableClass, driver);
		return new ByAll(result.toArray(new By[]{}));
	}

}
