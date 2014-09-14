package org.arachnidium.model.support.annotations.classdeclaration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.openqa.selenium.By;
import org.openqa.selenium.support.How;

/**
 * This annotation is for
 * UI specification when described UI  
 * is always inside some frame (browser clients and
 * hybrid mobile apps). This frame has the same
 * path on each required page. 
 * 
 * So, the class-specification could be marked 
 * by the annotation. If there is only one frame
 * <p>
 * <code>
 * <p>@Frame(someValue)</p>
 * <p>public class ...</p>
 * </code>
 * <p>
 * is enough. If there more than one frame to get through
 * <p>
 * <code>
 * <p>@Frame(someValue1)</p>
 * <p>@Frame(someValue2)</p>
 * <p>@Frame(someValue3)</p>
 * <p>public class ...</p>
 * </code>
 * <p>
 * The set of annotation values is the path to desired frame.
 * 
 */
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Repeatable(Frames.class)
public @interface Frame {
   static String ILLEGAL_LOCATOR = "";
   static int ILLEGAL_FRAME_INDEX = -1;
   static String ILLEGAL_FRAME_STRING_PATH = "";

   /**
    * @return string frame path value
    */
   String stringPath() default ILLEGAL_FRAME_STRING_PATH;
   /**
    * @return the index of specified frame
    */
   int frameIndex() default ILLEGAL_FRAME_INDEX;
   /**
    * @return The {@link By} strategy of the desired
    * frame location. 
    * 
    * @see How
    */
   How howToGet() default How.ID_OR_NAME;
   /**
    * @return The locater value
    * 
    * @see By
    * @see How
    */
   String locator()  default ILLEGAL_LOCATOR;
}
