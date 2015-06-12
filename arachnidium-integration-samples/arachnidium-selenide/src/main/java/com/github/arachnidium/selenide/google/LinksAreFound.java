package com.github.arachnidium.selenide.google;

import static com.codeborne.selenide.Selenide.$;

import org.openqa.selenium.By;

import com.codeborne.selenide.ElementsCollection;
import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;

public class LinksAreFound<T extends Handle> extends FunctionalPart<T>{
		
	protected LinksAreFound(T handle) {
		super(handle);
	}

	@InteractiveMethod
	public void openLinkByIndex(int index) {
		ElementsCollection links = $(By.id("ires")).$$(By.xpath(".//*[@class='r']/a"));
		links.shouldHaveSize(10);
		links.get(index - 1).click();
	}
}
