package com.github.arachnidium.model.support.annotations.rootelements;

import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ByIdOrName;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.pagefactory.ByAll;
import org.openqa.selenium.support.pagefactory.ByChained;

public class CommonRootElementReader implements IRootElementReader {

	private static By getBy(FindBy findBy) {
		if (!"".equals(findBy.className()))
			return By.className(findBy.className());

		if (!"".equals(findBy.css()))
			return By.cssSelector(findBy.css());

		if (!"".equals(findBy.id()))
			return By.id(findBy.id());

		if (!"".equals(findBy.linkText()))
			return By.linkText(findBy.linkText());

		if (!"".equals(findBy.name()))
			return By.name(findBy.name());

		if (!"".equals(findBy.partialLinkText()))
			return By.partialLinkText(findBy.partialLinkText());

		if (!"".equals(findBy.tagName()))
			return By.tagName(findBy.tagName());

		if (!"".equals(findBy.xpath()))
			return By.xpath(findBy.xpath());
		return null;
	}
	
	private static By getBy(How how, String using) {
		switch (how) {
	      case CLASS_NAME:
	        return By.className(using);

	      case CSS:
	        return By.cssSelector(using);

	      case ID:
	        return By.id(using);

	      case ID_OR_NAME:
	        return new ByIdOrName(using);

	      case LINK_TEXT:
	        return By.linkText(using);

	      case NAME:
	        return By.name(using);

	      case PARTIAL_LINK_TEXT:
	        return By.partialLinkText(using);

	      case TAG_NAME:
	        return By.tagName(using);

	      case XPATH:
	        return By.xpath(using);

	      default:
	        throw new IllegalArgumentException("Cannot determine how to locate element! Strategy is empty");
	    }	
	}
	
	private static ByChained getPossibleChain(RootElement rootElement){
		List<By> result = new ArrayList<>();		
		FindBy[] findBies = rootElement.chain();
		
		for (FindBy findBy: findBies) {
			By by = null;
			by = getBy(findBy);
			if (by != null){
				result.add(by);
				continue;
			}
			result.add(getBy(findBy.how(), findBy.using()));
		}
		return new ByChained(result.toArray(new By[]{}));
	}

	@Override
	public By readClassAndGetBy(AnnotatedElement annotatedTarget, Class<? extends WebDriver> driverClass) {
		List<By> result = new ArrayList<>();		
		RootElement[] rootElements = getAnnotations(RootElement.class, annotatedTarget);
		
		for (RootElement rootElement: rootElements) {
			result.add(getPossibleChain(rootElement));
		}	
		if (result.size() == 0)
			return null;
		return new ByAll(result.toArray(new By[]{}));
	}

}
