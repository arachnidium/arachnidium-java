package app_modeling.web.raw_pageobjects;

import org.openqa.selenium.By;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;
import com.github.arachnidium.model.support.HowToGetByFrames;

public abstract class ModalDialog extends FunctionalPart<Handle> {

	//TODO to be implemented
	protected ModalDialog(FunctionalPart<?> parent, HowToGetByFrames path, By by) {
		super(parent, path, by);
	}

}
