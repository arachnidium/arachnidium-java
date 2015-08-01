package com.github.arachnidium.core.fluenthandle;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

import com.github.arachnidium.core.interfaces.functions.IFunction;

public interface IFunctionalHandleCondition extends IFunction<WebDriver, String>, ExpectedCondition<String>{

}
