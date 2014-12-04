package com.github.arachnidium.model.support.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Is used when it is necessary to switch from frame to frame 
 * Is useful to browser and hybrid applications.
 */
@Target(value = {ElementType.TYPE, ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Frames {
   Frame[] value();
}
