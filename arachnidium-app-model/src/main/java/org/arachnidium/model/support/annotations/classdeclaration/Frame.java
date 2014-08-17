package org.arachnidium.model.support.annotations.classdeclaration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.openqa.selenium.support.How;

/**
 * Is used when it is necessary to switch from frame to frame 
 * Is useful to browser and hybrid applications.
 */
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Repeatable(Frames.class)
public @interface Frame {
   public static String ILLEGAL_LOCATOR = "";
   public static int ILLEGAL_FRAME_INDEX = -1;
   public static String ILLEGAL_FRAME_STRING_PATH = "";
	
   String stringPath() default ILLEGAL_FRAME_STRING_PATH;
   int frameIndex() default ILLEGAL_FRAME_INDEX;
   How howToGet() default How.ID_OR_NAME;
   String locator()  default ILLEGAL_LOCATOR;
}
