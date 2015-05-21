package com.github.arachnidium.core;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
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
	private static final int MIN_ACCEPTABLE_NUMBER = 0;

	public ByNumbered(By by, int number) {
		theGivenBy = by;
		theDesiredNumber = number;
	}

	public ByNumbered(By by) {
		this(by, -1);
	}

	 /**
       *It finds many elements if the desired number is not defined. If the desired number is defined then it 
       *returns the list which contains one element if number of relevant elements equals or higher 
       *than the given index. An empty list is returned otherwise.
	   * 
	   * @param context A context to use to find the element
	   * @return A list of WebElements matching the selector
	 */
	@Override
	public List<WebElement> findElements(SearchContext context) {
		List<WebElement> result = theGivenBy.findElements(context);
		if (theDesiredNumber < MIN_ACCEPTABLE_NUMBER)
			return result;
		ArrayList<WebElement> toBeReturned = new ArrayList<>();
		if (result.size() >= theDesiredNumber + 1)
			toBeReturned.add(result.get(theDesiredNumber));
		return toBeReturned;
	}

	public String toString() {
		String result = theGivenBy.toString();
		if (theDesiredNumber >= MIN_ACCEPTABLE_NUMBER )
			result = result + ". The desired number in the list is "
					+ String.valueOf(theDesiredNumber);
		return result;
	}

}
