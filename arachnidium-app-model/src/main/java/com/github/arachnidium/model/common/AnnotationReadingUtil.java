package com.github.arachnidium.model.common;

import java.lang.annotation.Annotation;
import java.util.List;

import com.github.arachnidium.core.HowToGetBrowserWindow;
import com.github.arachnidium.core.HowToGetMobileScreen;
import com.github.arachnidium.core.WebDriverEncapsulation;
import com.github.arachnidium.core.fluenthandle.IHowToGetHandle;
import com.github.arachnidium.model.support.HowToGetByFrames;
import com.github.arachnidium.model.support.annotations.classdeclaration.ClassDeclarationReader;
import com.github.arachnidium.model.support.annotations.classdeclaration.Frame;
import com.github.arachnidium.model.support.annotations.classdeclaration.IfBrowserDefaultPageIndex;
import com.github.arachnidium.model.support.annotations.classdeclaration.IfBrowserPageTitle;
import com.github.arachnidium.model.support.annotations.classdeclaration.IfBrowserURL;
import com.github.arachnidium.model.support.annotations.classdeclaration.IfMobileAndroidActivity;
import com.github.arachnidium.model.support.annotations.classdeclaration.IfMobileContext;
import com.github.arachnidium.model.support.annotations.classdeclaration.IfMobileDefaultContextIndex;
import com.github.arachnidium.model.support.annotations.classdeclaration.TimeOut;
import com.github.arachnidium.model.support.annotations.classdeclaration.rootelements.CommonRootElementReader;
import com.github.arachnidium.model.support.annotations.classdeclaration.rootelements.ElementReaderForMobilePlatforms;
import com.github.arachnidium.model.support.annotations.classdeclaration.rootelements.IRootElementReader;

/**
 * 
 * This is utility class for inner usage
 *
 */
abstract class AnnotationReadingUtil {
	private static ClassDeclarationReader classDeclarationReader = new ClassDeclarationReader();

	private AnnotationReadingUtil() {
		super();
	}

	/**
	 * Creates an instance of {@link HowToGetByFrames} class if
	 * the given class is annotated by {@link Frame}.
	 * 
	 * @param params  
	 * @param targetClass It is a class which is supposed to be annotated by {@link Frame}
	 * 
	 * @return A {@link HowToGetByFrames} strategy instance if the 
	 * given class is annotated by {@link Frame} <br/>
	 * <br/>
	 * <code>null</code> if the 
	 * given class isn't annotated by {@link Frame}
	 */	
	static HowToGetByFrames getHowToGetByFramesStrategy(Class<?> targetClass){
		List<Object> framePath = classDeclarationReader
				.getFramePath(classDeclarationReader.getAnnotations(
						Frame.class, targetClass));
		if (framePath.size() != 0) {	
			HowToGetByFrames howTo = new HowToGetByFrames();
			framePath.forEach((chainElement) -> {
				howTo.addNextFrame(chainElement);
			});
			return howTo;
		}
		return null;
	}
	
	/**
	 * This methods transforms
	 * values of annotations that marks
	 * the given class to strategies 
	 * {@link HowToGetBrowserWindow} or {@link HowToGetMobileScreen} 
	 * 
	 *@param indexAnnotation is the class of annotation which 
	 * is expected marks the given class
	 *possible annotations are {@link IfBrowserDefaultPageIndex} and {@link IfMobileDefaultContextIndex}.
	 * 
	 *@param handleUniqueIdentifiers is the class of annotation which 
	 * is expected marks the given class
	 * Possible annotations are {@link IfBrowserURL} and {@link IfMobileAndroidActivity}.
	 * 
	 *@param additionalStringIdentifieris the class of annotation which 
	 * is expected marks the given class
	 * Possible annotations are {@link IfBrowserPageTitle} and {@link IfMobileContext}.
	 * 
	 *@param annotated is a given class that can be marked by annotations above
	 * 
	 *@param howToClass is the class of strategy that combines values of 
	 * annotations above. Available classes are {@link HowToGetBrowserWindow} 
	 * and {@link HowToGetMobileScreen}
	 * 
	 * @return the instance of a strategy class defined by 
	 *@param howToClass
	 * 
	 * @throws ReflectiveOperationException
	 */
	static <T extends IHowToGetHandle> T getHowToGetHandleStrategy(
			Class<? extends Annotation> indexAnnotation,
			Class<? extends Annotation> handleUniqueIdentifiers,
			Class<? extends Annotation> additionalStringIdentifier,
			Class<?> annotated, Class<T> howToClass)
			throws ReflectiveOperationException {
		Annotation[] indexAnnotations = classDeclarationReader
				.getAnnotations(indexAnnotation, annotated);
		Integer index = null;
		if (indexAnnotations.length > 0) {
			index = classDeclarationReader.getIndex(indexAnnotations[0]);
		}

		Annotation[] handleUniqueIdentifiers2 = classDeclarationReader
				.getAnnotations(handleUniqueIdentifiers, annotated);
		List<String> identifiers = classDeclarationReader
				.getRegExpressions(handleUniqueIdentifiers2);
		if (identifiers.size() == 0) {
			identifiers = null;
		}

		String additionalStringIdentifier2 = null;
		Annotation[] additionalStringIdentifiers = classDeclarationReader
				.getAnnotations(additionalStringIdentifier, annotated);
		if (additionalStringIdentifiers.length > 0) {
			additionalStringIdentifier2 = classDeclarationReader
					.getRegExpressions(additionalStringIdentifiers).get(0);
		}

		if (index == null && identifiers == null
				&& additionalStringIdentifier2 == null) {
			return null;
		}

		try {
			T result = howToClass.newInstance();
			if (index != null) {
				result.setExpected(index);
			}
			if (identifiers != null) {
				result.setExpected(identifiers);
			}
			if (additionalStringIdentifier2 != null) {
				result.setExpected(additionalStringIdentifier2);
			}
			return result;
		} catch (InstantiationException | IllegalAccessException e) {
			throw e;
		}
	}	
	
	/**
	 * Returns {@link Long} value of implicit waiting for some page/screen if the 
	 * target class is marked by {@link TimeOut} annotation
	 * @param annotated is the target class which is supposed to be annotated 
	 * by {@link TimeOut}
	 * @return {@link Long} value if annotation is present. <code>null</code> otherwise
	 */
	static Long getTimeOut(Class<?> annotated) {
		TimeOut[] timeOuts = classDeclarationReader.getAnnotations(
				TimeOut.class, annotated);
		if (timeOuts.length == 0) {
			return null;
		}
		return classDeclarationReader.getTimeOut(timeOuts[0]);
	}
	
	static IRootElementReader getRootElementReader(WebDriverEncapsulation webDriverEncapsulation){
		if (webDriverEncapsulation.getInstantiatedSupportedDriver().isForBrowser()){
			return new CommonRootElementReader();
		}
		return new ElementReaderForMobilePlatforms();
	}
}
