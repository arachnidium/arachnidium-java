package org.arachnidium.model.support.annotations.classdeclaration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is useful when 
 * default timeout of waiting for browser window 
 * with loaded page or mobile context 
 * by the given condition is not suitable 
 * for some UI (for example, it needs more time)
 * 
 * It this kind of situations customized time 
 * could be specified like this
 * 
 * <p>
 * <code>
 * <p>
 * <p>@TimeOut(timeOut = 15)
 * <p>public class ...
 * </code>
 * <p>
 * There is an assumption that time unit
 * is seconds. 
 */
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface TimeOut {
	long timeOut();
}
