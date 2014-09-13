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
 *<p>This an iterceptor of {@link Application} methods.
 *<p>It invokes methods. If some exception is thrown
 *<p>it attempts to handle it implicitly 
 *<p>
 *<p>Also it performs the substitution of methods specified 
 *<p>by {@link IDecomposable} and {@link IDecomposableByHandles}. 
 *<p>This substitution depends on annotations that mark 
 *<p>class of {@link Application}. Possible annotations are 
 *<p> described by parameters below  
 *<p>
 *@param <IndexAnnotation>
 *<p>possible annotations are {@link IfBrowserDefaultPageIndex} and {@link IfMobileDefaultContextIndex}.
 *<p>These annotations are used when class describes UI or the fragment UI  
 *<p>which is stationed on not the first browser window or mobile context by default. 
 *<p>It is always the second, the third ad so on.
 *<p> 
 *@param <HandleUniqueIdentifiers>
 *<p>Possible annotations are {@link IfBrowserURL} and {@link IfMobileAndroidActivity}.
 *<p>These annotations are used when it is possible that UI or the fragment of UI
 *<p>can be identified by the set of page URLs or Android activities (!!! Android-only feature, ignored by iOS)
 *<p>This set should be limited. URL or activity value can be specified by regular expression
 *<p>
 *@param <AdditionalStringIdentifier>
 *<p>Possible annotations are {@link IfBrowserPageTitle} and {@link IfMobileContext}.
 *<p>These annotations are used when it is possible that UI or the fragment of UI
 *<p>can be additionally identified by page title or mobile context name.
 *<p>Title or mobile context name value can be specified by regular expression
 *<p>
 *@param <HowTo>
 *<p>Possible classes are {@link HowToGetBrowserWindow} and {@link HowToGetMobileScreen}
 *<p>By instances of this classes parameters (see above) will be combined
 *<p>
 *@see
 *<p> Annotation that additionally used:
 *<p> 
 *{@link Frame}
 *<p>
 *{@link TimeOut}
 *<p>
 */
public abstract class ApplicationInterceptor<IndexAnnotation extends Annotation, 
HandleUniqueIdentifiers extends Annotation, 
AdditionalStringIdentifier extends Annotation, 
HowTo extends IHowToGetHandle>
		extends ModelObjectInterceptor {

	/**
	 *<p> This methods transforms
	 *<p> values of annotations that marks
	 *<p> the given class to strategies 
	 *<p> {@link HowToGetBrowserWindow} or {@link HowToGetMobileScreen} 
	 *<p> 
	 *@param indexAnnotation is the class of annotation which 
	 *<p> is expected marks the given class
	 *<p>possible annotations are {@link IfBrowserDefaultPageIndex} and {@link IfMobileDefaultContextIndex}.
	 *<p> 
	 *@param handleUniqueIdentifiers is the class of annotation which 
	 *<p> is expected marks the given class
	 *<p> Possible annotations are {@link IfBrowserURL} and {@link IfMobileAndroidActivity}.
	 *<p> 
	 *@param additionalStringIdentifieris the class of annotation which 
	 *<p> is expected marks the given class
	 *<p> Possible annotations are {@link IfBrowserPageTitle} and {@link IfMobileContext}.
	 *<p> 
	 *@param annotated is a given class that can be marked by annotations above
	 *<p> 
	 *@param howToClass is the class of strategy that combines values of 
	 *<p> annotations above. Available classes are {@link HowToGetBrowserWindow} 
	 *<p> and {@link HowToGetMobileScreen}
	 *<p> 
	 *<p> @return the instance of a strategy class defined by 
	 *@param howToClass
	 *<p> 
	 *<p> @throws ReflectiveOperationException
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
	 *<p>This methods invokes methods and performs
	 *<p>the substitution of methods specified 
     *<p>by {@link IDecomposable} and {@link IDecomposableByHandles}. 
     *<p>
     *@see MethodInterceptor#intercept(Object, Method, Object[], MethodProxy) 
     *<p>
     *@see ModelObjectInterceptor#intercept(Object, Method, Object[], MethodProxy)
     *<p>
     *@see DefaultInterceptor#intercept(Object, Method, Object[], MethodProxy)
     *<p>
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
