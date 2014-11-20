package com.github.arachnidium.model.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import net.sf.cglib.proxy.MethodProxy;

import org.apache.commons.lang3.ArrayUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.github.arachnidium.core.HowToGetBrowserWindow;
import com.github.arachnidium.core.HowToGetMobileScreen;
import com.github.arachnidium.core.fluenthandle.IHowToGetHandle;
import com.github.arachnidium.model.abstractions.ModelObjectInterceptor;
import com.github.arachnidium.model.interfaces.IDecomposable;
import com.github.arachnidium.model.interfaces.IDecomposableByHandles;
import com.github.arachnidium.model.support.HowToGetByFrames;
import com.github.arachnidium.model.support.annotations.classdeclaration.Frame;
import com.github.arachnidium.model.support.annotations.classdeclaration.IfBrowserDefaultPageIndex;
import com.github.arachnidium.model.support.annotations.classdeclaration.IfBrowserPageTitle;
import com.github.arachnidium.model.support.annotations.classdeclaration.IfBrowserURL;
import com.github.arachnidium.model.support.annotations.classdeclaration.IfMobileAndroidActivity;
import com.github.arachnidium.model.support.annotations.classdeclaration.IfMobileContext;
import com.github.arachnidium.model.support.annotations.classdeclaration.IfMobileDefaultContextIndex;
import com.github.arachnidium.model.support.annotations.classdeclaration.TimeOut;
import com.github.arachnidium.model.support.annotations.classdeclaration.rootelements.IRootElementReader;
import com.github.arachnidium.model.support.annotations.classdeclaration.rootelements.RootAndroidElement;
import com.github.arachnidium.model.support.annotations.classdeclaration.rootelements.RootElement;
import com.github.arachnidium.model.support.annotations.classdeclaration.rootelements.RootIOSElement;

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
 *@param RootElementReader. Here the algorithm of the getting {@link By} strategy
 *when class is marked by {@link RootElement}, {@link RootAndroidElement} and 
 *{@link RootIOSElement}. These annotations are used when where are constant root
 *{@link WebElement}'s.
 *
 */
public abstract class ApplicationInterceptor<IndexAnnotation extends Annotation, 
HandleUniqueIdentifiers extends Annotation, 
AdditionalStringIdentifier extends Annotation, 
HowTo extends IHowToGetHandle, RootElementReader extends IRootElementReader>
		extends ModelObjectInterceptor {
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
			Class<RootElementReader> rootReader = (Class<RootElementReader>) Class.forName(pType
					.getActualTypeArguments()[4].getTypeName());
			
			// the first parameter is a class which instance we
			Class<?> desiredClass = (Class<?>) args[0];// want

			// There is nothing to do if all parameters apparently defined
			if (!paramClasses.contains(IHowToGetHandle.class)
					|| !paramClasses.contains(HowToGetByFrames.class)
					|| !paramClasses.contains(long.class)|| !paramClasses.contains(By.class)) {

				HowTo how = ModelSupportUtil.getDefinedParameter(method, howTo, args);
				if (how == null)
					how = ModelSupportUtil.getHowToGetHandleStrategy(indexAnnotationClass,
							huiA, asiA, desiredClass, howTo);

				Integer index = ModelSupportUtil.getDefinedParameter(method, int.class, args);
				// if index of a window/screen was defined
				if (how != null && index != null) {
					how.setExpected(index.intValue());
				}

				// frame strategy
				HowToGetByFrames howToGetByFrames = ModelSupportUtil
						.getDefinedParameter(method, HowToGetByFrames.class,
								args);
				if (howToGetByFrames == null)
					howToGetByFrames = ModelSupportUtil
							.getHowToGetByFramesStrategy(desiredClass);
				
				//By strategy of the getting root element
				By rootBy = ModelSupportUtil.getDefinedParameter(method, By.class, args);
				if (rootBy==null){
					IRootElementReader rootElementReader = rootReader.newInstance();
					rootBy = rootElementReader.readClassAndGetBy(desiredClass, 
							((Application<?, ?>) application).getWrappedDriver());
				}

				Long timeOutLong = ModelSupportUtil.getDefinedParameter(method, long.class, args);
				if (timeOutLong == null)
					timeOutLong = ModelSupportUtil.getTimeOut(desiredClass);

				// attempt to substitute methods is described below
				Object[] newArgs = new Object[] { desiredClass };
				if (how != null) {
					newArgs = ArrayUtils.add(newArgs, how);
				} else if (index != null) {
					newArgs = ArrayUtils.add(newArgs, index.intValue());
				}

				if (howToGetByFrames != null) {
					newArgs = ArrayUtils.add(newArgs, howToGetByFrames);
				}

				if (rootBy != null) {
					newArgs = ArrayUtils.add(newArgs, rootBy);
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
