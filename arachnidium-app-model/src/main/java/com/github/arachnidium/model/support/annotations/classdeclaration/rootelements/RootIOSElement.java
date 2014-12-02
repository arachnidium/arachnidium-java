package com.github.arachnidium.model.support.annotations.classdeclaration.rootelements;


import io.appium.java_client.pagefactory.iOSFindBy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation allows to define the root element
 * in the chain of further searches. Each {@link iOSFindBy} declaration
 * is a declaration of element in the chain of the searchings
 * for the root element. It is used when mobile app is launched
 * against iOS. 
 *
 */
@Target(value = {ElementType.TYPE, ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface RootIOSElement {
	iOSFindBy[] chain();
}
