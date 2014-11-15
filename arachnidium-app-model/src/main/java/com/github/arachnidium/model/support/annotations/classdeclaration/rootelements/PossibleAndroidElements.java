package com.github.arachnidium.model.support.annotations.classdeclaration.rootelements;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * Each {@link RootAndroidElement} declaration is a possible
 * variant where desired element could be located.
 */
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface PossibleAndroidElements {
    RootAndroidElement[] value();
}
