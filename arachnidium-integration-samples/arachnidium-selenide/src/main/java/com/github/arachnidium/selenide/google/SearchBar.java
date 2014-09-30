package com.github.arachnidium.selenide.google;

import static com.codeborne.selenide.Selenide.$;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;
import com.github.arachnidium.model.support.annotations.classdeclaration.IfBrowserDefaultPageIndex;
import com.github.arachnidium.model.support.annotations.classdeclaration.IfBrowserURL;
import org.openqa.selenium.By;

@IfBrowserURL(regExp = "www.google.")
@IfBrowserDefaultPageIndex(index = 0)
public class SearchBar<T extends Handle> extends FunctionalPart<T>{
	
	protected SearchBar(T handle) {
		super(handle);
	}

	@InteractiveMethod
	public void performSearch(String searchString) {
		$(By.name("q")).setValue(searchString);
		$(By.name("btnG")).click();
	}
}
