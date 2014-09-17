package org.arachnidium.model.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import net.sf.cglib.proxy.MethodProxy;

import org.apache.commons.lang3.ArrayUtils;
import org.arachnidium.core.HowToGetBrowserWindow;
import org.arachnidium.core.HowToGetMobileScreen;
import org.arachnidium.core.fluenthandle.IHowToGetHandle;
import org.arachnidium.model.abstractions.ModelObjectInterceptor;
import org.arachnidium.model.interfaces.IDecomposable;
import org.arachnidium.model.interfaces.IDecomposableByHandles;
import org.arachnidium.model.support.HowToGetByFrames;
import org.arachnidium.model.support.annotations.classdeclaration.ClassDeclarationReader;
import org.arachnidium.model.support.annotations.classdeclaration.Frame;
import org.arachnidium.model.support.annotations.classdeclaration.IfBrowserDefaultPageIndex;
import org.arachnidium.model.support.annotations.classdeclaration.IfBrowserPageTitle;
import org.arachnidium.model.support.annotations.classdeclaration.IfBrowserURL;
import org.arachnidium.model.support.annotations.classdeclaration.IfMobileAndroidActivity;
import org.arachnidium.model.support.annotations.classdeclaration.IfMobileContext;
import org.arachnidium.model.support.annotations.classdeclaration.IfMobileDefaultContextIndex;
import org.arachnidium.model.support.annotations.classdeclaration.TimeOut;

/**
 *This an iterceptor of {@link Application} methods.
 *It invokes methods. If some exception is thrown
 *it attempts to handle it implicitly 
 *
 *Also it performs the substitution of methods specified 
 *by {@link IDecomposable} and {@link IDecomposableByHandles}. 
 *This substitution depends on annotations that mark 
 *class of {@link Application}. Possible annotations are 
 * described by parameters below <br/> 
 * <br/> 
 * Annotations that additionally used: <br/> 
 *- {@link Frame} <br/> 
 *- {@link TimeOut} <br/> 
 *
 *@param <IndexAnnotation>
 *possible annotations are {@link IfBrowserDefaultPageIndex} and {@link IfMobileDefaultContextIndex}.
 *These annotations are used when class describes UI or the peace of UI
 *on the given browser window\mobile context defined by the index (e.g. the first, the seconds and so
 *on).  
 * 
 *@param <HandleUniqueIdentifiers>
 *Possible annotations are {@link IfBrowserURL} and {@link IfMobileAndroidActivity}.
 *These annotations are used when it is possible that UI or the peace of UI
 *can be identified by the set of page URLs or Android activities (!!! Android-only feature, ignored by iOS).
 *This set should be limited. Each one URL or activity value can be specified by regular expression
 *
 *@param <AdditionalStringIdentifier>
 *Possible annotations are {@link IfBrowserPageTitle} and {@link IfMobileContext}.
 *These annotations are used when it is possible that UI or the peace of UI
 *can be additionally identified by the page title or mobile context name.
 *Each one title or context name value can be specified by regular expression
 *
 *@param <HowTo>
 *Possible classes are {@link HowToGetBrowserWindow} and {@link HowToGetMobileScreen}
 *By instances of this classes parameters (see above) will be combined
 *
 */
public abstract class ApplicationInterceptor<IndexAnnotation extends Annotation, 
HandleUniqueIdentifiers extends Annotation, 
AdditionalStringIdentifier extends Annotation, 
HowTo extends IHowToGetHandle>
		extends ModelObjectInterceptor {

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
	private HowTo getHowToGetHandleStrategy(
			Class<IndexAnnotation> indexAnnotation,
			Class<HandleUniqueIdentifiers> handleUniqueIdentifiers,
			Class<AdditionalStringIdentifier> additionalStringIdentifier,
			Class<?> annotated, Class<HowTo> howToClass)
			throws ReflectiveOperationException {
		IndexAnnotation[] indexAnnotations = ClassDeclarationReader
				.getAnnotations(indexAnnotation, annotated);
		Integer index = null;
		if (indexAnnotations.length > 0) {
			index = ClassDeclarationReader.getIndex(indexAnnotations[0]);
		}

		HandleUniqueIdentifiers[] handleUniqueIdentifiers2 = ClassDeclarationReader
				.getAnnotations(handleUniqueIdentifiers, annotated);
		List<String> identifiers = ClassDeclarationReader
				.getRegExpressions(handleUniqueIdentifiers2);
		if (identifiers.size() == 0) {
			identifiers = null;
		}

		String additionalStringIdentifier2 = null;
		AdditionalStringIdentifier[] additionalStringIdentifiers = ClassDeclarationReader
				.getAnnotations(additionalStringIdentifier, annotated);
		if (additionalStringIdentifiers.length > 0) {
			additionalStringIdentifier2 = ClassDeclarationReader
					.getRegExpressions(additionalStringIdentifiers).get(0);
		}

		if (index == null && identifiers == null
				&& additionalStringIdentifier2 == null) {
			return null;
		}

		try {
			HowTo result = howToClass.newInstance();
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

	private Long getTimeOut(Class<?> annotated) {
		TimeOut[] timeOuts = ClassDeclarationReader.getAnnotations(
				TimeOut.class, annotated);
		if (timeOuts.length == 0) {
			return null;
		}
		return ClassDeclarationReader.getTimeOut(timeOuts[0]);
	}

	/**
	 *Invokes methods and performs
	 *the substitution of methods specified 
     *by {@link IDecomposable} and {@link IDecomposableByHandles}. 
     *
     *@see MethodInterceptor#intercept(Object, Method, Object[], MethodProxy) 
     *
     *@see ModelObjectInterceptor#intercept(Object, Method, Object[], MethodProxy)
     *
     *@see DefaultInterceptor#intercept(Object, Method, Object[], MethodProxy)
     *
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object intercept(Object application, Method method, Object[] args,
			MethodProxy methodProxy) throws Throwable {
		try {
			if (!method.getName().equals(GET_PART)) {
				return super.intercept(application, method, args, methodProxy);
			}

			List<Class<?>> paramClasses = Arrays.asList(method
					.getParameterTypes());
			Type generic = this.getClass().getGenericSuperclass();
			ParameterizedType pType = (ParameterizedType) generic;
			Class<IndexAnnotation> indexAnnotationClass = (Class<IndexAnnotation>) Class
					.forName(pType.getActualTypeArguments()[0].getTypeName());
			Class<HandleUniqueIdentifiers> huiA = (Class<HandleUniqueIdentifiers>) Class
					.forName(pType.getActualTypeArguments()[1].getTypeName());
			Class<AdditionalStringIdentifier> asiA = (Class<AdditionalStringIdentifier>) Class
					.forName(pType.getActualTypeArguments()[2].getTypeName());
			Class<HowTo> howTo = (Class<HowTo>) Class.forName(pType
					.getActualTypeArguments()[3].getTypeName());

			// There is nothing to do if all parameters apparently defined
			if (!paramClasses.contains(IHowToGetHandle.class)
					|| !paramClasses.contains(HowToGetByFrames.class)
					|| !paramClasses.contains(long.class)) {

				HowTo how = null;
				if (!paramClasses.contains(IHowToGetHandle.class)){
					how = getHowToGetHandleStrategy(indexAnnotationClass,
						huiA, asiA, (Class<?>) args[0], howTo);
						// the first parameter is a class which instance we
						// want
				}
				else{
					how = (HowTo) args[ModelSupportUtil.getParameterIndex(
							method.getParameters(), howTo)];
				}

				int paramIndex = ModelSupportUtil.getParameterIndex(
						method.getParameters(), int.class);
				Integer index = null;
				if (paramIndex >= 0) {
					index = (Integer) args[paramIndex];
				}

				// if index of a window/screen was defined
				if (how != null && index != null) {
					how.setExpected(index.intValue());
				}

				HowToGetByFrames howToGetByFrames = null;
				if (!paramClasses.contains(HowToGetByFrames.class)) {
					howToGetByFrames = ifClassIsAnnotatedByFrames((Class<?>) args[0]);
					// the first parameter is a class which instance we want
				}

				Long timeOutLong = null;
				paramIndex = ModelSupportUtil.getParameterIndex(
						method.getParameters(), long.class);
				if (paramIndex >= 0) {
					timeOutLong = (Long) args[paramIndex];
				} else {
					timeOutLong = getTimeOut((Class<?>) args[0]);
					// the first parameter is a class which instance we want
				}

				// attempt to substitute methods is described below
				Object[] newArgs = new Object[] { args[0] };
				if (how != null) {
					newArgs = ArrayUtils.add(newArgs, how);
				} else if (index != null) {
					newArgs = ArrayUtils.add(newArgs, index.intValue());
				}

				if (howToGetByFrames != null) {
					newArgs = ArrayUtils.add(newArgs, howToGetByFrames);
				}

				if (timeOutLong != null) {
					newArgs = ArrayUtils.add(newArgs, timeOutLong.longValue());
				}

				args = newArgs;
				method = ModelSupportUtil.getSuitableMethod(
						application.getClass(), GET_PART, args);
				methodProxy = ModelSupportUtil.getMethodProxy(
						application.getClass(), method);

			}
			return super.intercept(application, method, args, methodProxy);
		} catch (Exception e) {
			throw e;
		}
	}

}
