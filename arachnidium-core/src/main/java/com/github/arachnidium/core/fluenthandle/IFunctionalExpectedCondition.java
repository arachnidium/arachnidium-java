package com.github.arachnidium.core.fluenthandle;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

import com.github.arachnidium.core.interfaces.IFunctionalCondition;

public interface IFunctionalExpectedCondition extends IFunctionalCondition<WebDriver, String>, ExpectedCondition<String>{

}
