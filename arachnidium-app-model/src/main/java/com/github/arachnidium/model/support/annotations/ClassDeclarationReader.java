package com.github.arachnidium.model.support.annotations;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ByIdOrName;
import org.openqa.selenium.support.How;
import com.github.arachnidium.util.reflect.annotations.AnnotationUtil;

/**
 * Reads annotations which mark classes.
 * It is the inner utility class.
 */
public final class ClassDeclarationReader {

	private static final String REG_EXP_METHOD = "regExp";
	private static final String INDEX_METHOD = "index";
	private static final String TIME_OUT = "timeOut";
	// @Frame
	private static final String STRING_PATH_METHOD = "stringPath";
	private static final String FRAME_INDEX_METHOD = "frameIndex";
	private static final String HOW_TO_GET_FRAME_ELEMENT = "howToGet";
	private static final String HOW_TO_GET_LOCATOR_VALUE = "locator";
	
	private ClassDeclarationReader(){
		super();
	}

	/**
	 * Returns "regExp()" value
	 * @param a An instance of Annotation which has "regExp()" method 
	 */
	private static String getRegExpression(Annotation a){
		return AnnotationUtil.getValue(a, REG_EXP_METHOD);
	}
	
	/**
	 * Returns "index()" value
	 * @param a An instance of Annotation which has "index()" method 
	 */	
	public static Integer getIndex(Annotation a){
		return AnnotationUtil.getValue(a, INDEX_METHOD);
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
			String path = AnnotationUtil.getValue(frame, STRING_PATH_METHOD);
			if (!Frame.ILLEGAL_FRAME_STRING_PATH.equals(path)){
				filled[0] = path; 
			}
			int index = AnnotationUtil.getValue(frame, FRAME_INDEX_METHOD);
			if (Frame.ILLEGAL_FRAME_INDEX != index){
				filled[1] = index;
			}
			
			String locator = AnnotationUtil.getValue(frame, HOW_TO_GET_LOCATOR_VALUE);
			if (!Frame.ILLEGAL_LOCATOR.equals(locator)){
				filled[2] = getBy(AnnotationUtil.getValue(frame, HOW_TO_GET_FRAME_ELEMENT), locator); 
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
	 * Returns "timeOut()" value
	 * @param a An instance of Annotation which has "timeOut()" method 
	 */	
	public static long getTimeOut(Annotation a){
		return AnnotationUtil.getValue(a, TIME_OUT);
	}
	
}
