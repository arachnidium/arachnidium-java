package com.github.arachnidium.model.support.annotations.rootelements;

import io.appium.java_client.pagefactory.AndroidFindBy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation allows to define the root element
 * in the chain of further searches. Each {@link AndroidFindBy} declaration
 * is a declaration of element in the chain of the searchings
 * for the root element. It is used when mobile app is launched
 * against Android. 
 *
 */
@Target(value = {ElementType.TYPE, ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface RootAndroidElement {
	AndroidFindBy[] chain();
}