package com.github.arachnidium.model.support;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

/**
 * This class allows to receive the single {@link WebElement} by the locator and
 * the number of the element in the resulted list
 *
 */
public class ByNumbered extends By {

	private final By theGivenBy;
	private final int theDesiredNumber;

	public ByNumbered(By by, int number) {
		theGivenBy = by;
		theDesiredNumber = number;
	}

	public ByNumbered(By by) {
		this(by, 0);
	}

	@Override
	public List<WebElement> findElements(SearchContext context) {
		return theGivenBy.findElements(context);
	}

	/**
	 * Find a single element with the given locator
	 * and the number in the resulted list of found elements 
	 */
	@Override
	public WebElement findElement(SearchContext context)
			throws NoSuchElementException {
		List<WebElement> resulted = findElements(context);
		if (resulted == null || resulted.isEmpty())
			throw new NoSuchElementException("Cannot locate an element using "
					+ toString());
		if (resulted.size() < theDesiredNumber + 1)
			throw new NoSuchElementException("Cannot find the desired element in the resulted list. "
					+ "The resulted list size is " +
					String.valueOf(resulted.size()) + ". "
					+ toString());
		
		return resulted.get(theDesiredNumber);
	}

	public String toString() {
		return theGivenBy.toString() + ". The desired number in the list is "
				+ String.valueOf(theDesiredNumber);
	}

}
