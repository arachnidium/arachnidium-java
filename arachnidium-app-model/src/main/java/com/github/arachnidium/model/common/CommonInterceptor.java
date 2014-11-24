package com.github.arachnidium.model.common;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

import org.apache.commons.lang3.ArrayUtils;
import org.openqa.selenium.By;

import com.github.arachnidium.core.WebDriverEncapsulation;
import com.github.arachnidium.model.abstractions.ModelObjectInterceptor;
import com.github.arachnidium.model.interfaces.IDecomposable;
import com.github.arachnidium.model.support.HowToGetByFrames;
import com.github.arachnidium.model.support.annotations.classdeclaration.Frame;
import com.github.arachnidium.model.support.annotations.classdeclaration.rootelements.IRootElementReader;
import com.github.arachnidium.model.support.annotations.classdeclaration.rootelements.RootAndroidElement;
import com.github.arachnidium.model.support.annotations.classdeclaration.rootelements.RootIOSElement;

class CommonInterceptor extends ModelObjectInterceptor {
	
	/**
	 * This method is used by {@link IDecomposable} method substitution mechanism
	 * 
	 * @param wde it is high level {@link WebDriver} wrapper
	 * @param method Originally invoked method
	 * @param args explicitly given arguments
	 * @param annotatedElement is a {@link Field}, {@link Method} or {@link Class} which is 
	 * supposed to be marked by {@link Frame} and {@link RootElement}/{@link RootAndroidElement}/
	 * {@link RootIOSElement}
	 * @return A new argument array
	 * @throws Throwable
	 */
	static Object[] getArgs(WebDriverEncapsulation wde, Method method, Object[] args, 
			AnnotatedElement annotatedElement) throws Throwable{		
		HowToGetByFrames howTo = MethodReadingUtil
				.getDefinedParameter(method, HowToGetByFrames.class,
						args);
		if (howTo == null)
			howTo = AnnotationReadingUtil
					.getHowToGetByFramesStrategy(annotatedElement);
		
		By rootBy = MethodReadingUtil.getDefinedParameter(method,
				By.class, args);
		if (rootBy == null) {
			IRootElementReader rootElementReader = AnnotationReadingUtil
					.getRootElementReader(wde);
			rootBy = rootElementReader.readClassAndGetBy(annotatedElement,
					wde.getWrappedDriver());
		}		
		
		Object[] newArgs = new Object[]{};
		if (howTo != null) {
			newArgs = ArrayUtils.add(newArgs, howTo);
		}
		
		if (rootBy != null){
			newArgs = ArrayUtils.add(newArgs, rootBy);
		}
		
		return newArgs;
	}	
	
	/**
	 * For {@link IDecomposable#getPart(*)} methods
	 * 
	 * @param m method that is expected to be one of {@link IDecomposable#getPart(*)} methods
	 * @param args explicitly given arguments
	 * @return The target class if the given methods is one of {@link IDecomposable#getPart(*)}'s
	 * <code>null</code> will be returned otherwise
	 */
	static Class<?> extractTargetFromGetPart(Method m, Object[] args){
		if (m.getName().equals(GET_PART)){
			return (Class<?>) args[0];
		}
		return null;
	}
}
