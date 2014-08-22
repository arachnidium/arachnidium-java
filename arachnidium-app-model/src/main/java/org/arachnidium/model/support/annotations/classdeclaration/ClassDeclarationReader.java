package org.arachnidium.model.support.annotations.classdeclaration;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ByIdOrName;
import org.openqa.selenium.support.How;

/**
 * Reads annotations which mark classes
 *
 */
public abstract class ClassDeclarationReader {

	private static final String REG_EXP_METHOD = "regExp";
	private static final String INDEX_METHOD = "index";
	private static final String TIME_OUT = "timeOut";
	private static final Class<?>[] ANNOTATION_METHOD_PARAM_CLASSES = new Class<?>[] {};
	private static final Object[] ANNOTATION_METHOD_PARAM_VALUES = new Object[] {};

	// @Frame
	private static final String STRING_PATH_METHOD = "stringPath";
	private static final String FRAME_INDEX_METHOD = "frameIndex";
	private static final String HOW_TO_GET_FRAME_ELEMENT = "howToGet";
	private static final String HOW_TO_GET_LOCATOR_VALUE = "locator";
	

	// --@Frame@SuppressWarnings("unchecked")
	@SuppressWarnings("unchecked")
	private static <T extends Object> T getValue(Annotation a, String methodName) {
		try {
			Method m = a.getClass().getMethod(methodName,
					ANNOTATION_METHOD_PARAM_CLASSES);
			return (T) m.invoke(a, ANNOTATION_METHOD_PARAM_VALUES);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Returns "regExp()" value
	 * @param a An instance of Annotation which has "regExp()" method 
	 */
	private static String getRegExpression(Annotation a){
		return getValue(a, REG_EXP_METHOD);
	}
	
	/**
	 * Returns "index()" value
	 * @param a An instance of Annotation which has "index()" method 
	 */	
	public  static Integer getIndex(Annotation a){
		return getValue(a, INDEX_METHOD);
	}	
	
	/**
	 * Returns "regExp()" value
	 * @param a An instance of Annotation{@A1}. A1 has "regExp()" method 
	 */		
	public static List<String> getRegExpressions(Annotation[] a){
		List<String> result = new ArrayList<String>();
		for (Annotation annotation: a){
			result.add(getRegExpression(annotation));
		}		
		return result;		
 	}
	
	private static By getBy(How how, String value){
		switch (how) {
		case CLASS_NAME:
			return By.className(value);
		case CSS:
			return By.cssSelector(value);
		case ID:
			return By.id(value);
		case LINK_TEXT:
			return By.linkText(value);	
		case NAME:
			return By.name(value);		
		case PARTIAL_LINK_TEXT:
			return By.partialLinkText(value);	
		case TAG_NAME:
			return By.tagName(value);	
		case XPATH:
			return By.xpath(value);				
		default:
			return new ByIdOrName(value);
		}
	}
	
	/**
	 * Reads {@link Frames} annotation
	 */
	public static List<Object> getFramePath(Frame[] frames){
		List<Object> result = new ArrayList<Object>();
		String info = STRING_PATH_METHOD + ", " + FRAME_INDEX_METHOD + ", {" +
		HOW_TO_GET_FRAME_ELEMENT + " & " + HOW_TO_GET_LOCATOR_VALUE + "}";
		for (Frame frame: frames){
			Object[] filled = new Object[3];
			String path = getValue(frame, STRING_PATH_METHOD);
			if (!Frame.ILLEGAL_FRAME_STRING_PATH.equals(path)){
				filled[0] = path; 
			}
			int index = getValue(frame, FRAME_INDEX_METHOD);
			if (Frame.ILLEGAL_FRAME_INDEX != index){
				filled[1] = index;
			}
			
			String locator = getValue(frame, HOW_TO_GET_LOCATOR_VALUE);
			if (!Frame.ILLEGAL_LOCATOR.equals(locator)){
				filled[2] = getBy(getValue(frame, HOW_TO_GET_FRAME_ELEMENT), locator); 
			}
			
			int filledCount = 0;
			Object willBeReturned = null;
			for (Object filledObject: filled){
				if (filledObject == null){
					continue;
				}
				filledCount ++;
				if (filledCount > 1){
					throw new IllegalArgumentException("Annotation " + frame.getClass().getName() + ": only one of " + info + " should be filled!");
				}
				willBeReturned = filledObject;
			}
			if (filledCount == 0){
				throw new IllegalArgumentException("Annotation " + frame.getClass().getName() + ": one of " + info + " should be filled!");
			}
			result.add(willBeReturned);
		}
		return result;
	}
	
	/**
	 * Attempts to return
	 * annotations which mark class or superclass of the given class 
	 */
	public static <T extends Annotation> T[] getAnnotations(Class<T> requiredAnnotation, Class<?> target){
		T[] result = target.getAnnotationsByType(requiredAnnotation);
		Class<?> superC = target.getSuperclass();
		while (result.length == 0 && superC != null){
			result = superC.getAnnotationsByType(requiredAnnotation);
			superC = superC.getSuperclass();
		}
		return result;
	}

	private ClassDeclarationReader() {
		super();
	}

	/**
	 * Returns "timeOut()" value
	 * @param a An instance of Annotation which has "timeOut()" method 
	 */	
	public static long getTimeOut(Annotation a){
		return getValue(a, TIME_OUT);
	}
	
}
