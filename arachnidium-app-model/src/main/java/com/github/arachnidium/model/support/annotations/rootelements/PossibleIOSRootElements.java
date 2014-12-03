package com.github.arachnidium.model.support.annotations.rootelements;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * Each {@link RootIOSElement} declaration is a possible
 * variant where desired element could be located.
 */
@Target(value = {ElementType.TYPE, ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface PossibleIOSRootElements {
    RootIOSElement[] value();
}
