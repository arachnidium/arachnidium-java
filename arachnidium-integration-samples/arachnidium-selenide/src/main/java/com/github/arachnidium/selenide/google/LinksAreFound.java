package com.github.arachnidium.selenide.google;

import org.openqa.selenium.By;

import com.github.arachnidium.model.common.FunctionalPart;
import com.github.arachnidium.core.HowToGetByFrames;
import com.github.arachnidium.core.Handle;
import com.codeborne.selenide.ElementsCollection;

import static com.codeborne.selenide.Selenide.*;

public class LinksAreFound<T extends Handle> extends FunctionalPart<T>{
		
	protected LinksAreFound(T handle, HowToGetByFrames path, By by) {
		super(handle, path, by);
	}

	@InteractiveMethod
	public void openLinkByIndex(int index) {
		ElementsCollection links = $(By.id("ires")).$$(By.xpath(".//*[@class='r']/a"));
		links.shouldHaveSize(10);
		links.get(index - 1).click();
	}
}
