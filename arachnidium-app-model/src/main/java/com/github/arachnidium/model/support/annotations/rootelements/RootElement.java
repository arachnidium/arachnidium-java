package com.github.arachnidium.model.support.annotations.rootelements;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.openqa.selenium.support.FindBy;


/**
 * This annotation allows to define the root element
 * in the chain of further searches. Each {@link FindBy} declaration
 * is a declaration of element in the chain of the searchings
 * for the root element. It is used by default when {@link RootAndroidElement}
 * and {@link RootIOSElement} are not present 
 *
 */
@Target(value = {ElementType.TYPE, ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
@Repeatable(PossibleRootElements.class)
public @interface RootElement {
	/**
	 * It is the chain of the searchings for the target element
	 * @return the chain of the searchings for the target element as the {@link FindBy} array
	 */
	FindBy[] chain();
	/**
	 * It is the index of the desired element if there few elements are found by the 
	 * given locator chain. This chain is defined by {@link RootElement#chain()}
	 * @return  the index of the desired element if there few elements are found by the 
	 * given locator chain. This chain is defined by {@link RootElement#chain()}
	 */
	int index() default 0;
}
