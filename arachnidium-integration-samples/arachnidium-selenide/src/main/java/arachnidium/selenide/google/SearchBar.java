package arachnidium.selenide.google;

import static com.codeborne.selenide.Selenide.$;

import org.arachnidium.core.Handle;
import org.arachnidium.model.common.FunctionalPart;
import org.arachnidium.model.support.annotations.classdeclaration.IfBrowserDefaultPageIndex;
import org.arachnidium.model.support.annotations.classdeclaration.IfBrowserURL;
import org.openqa.selenium.By;

@IfBrowserURL(regExp = "www.google.")
@IfBrowserDefaultPageIndex(index = 0)
public class SearchBar<T extends Handle> extends FunctionalPart<T>{
	
	protected SearchBar(T handle) {
		super(handle);
		load();
	}

	@InteractiveMethod
	public void performSearch(String searchString) {
		$(By.name("q")).setValue(searchString);
		$(By.name("btnG")).click();
	}
}
