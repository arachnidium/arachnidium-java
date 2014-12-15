package com.github.arachnidium.model.common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.ContextAware;
import org.openqa.selenium.NoSuchContextException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.core.fluenthandle.IHowToGetHandle;
import com.github.arachnidium.core.settings.supported.ESupportedDrivers;
import com.github.arachnidium.model.abstractions.ModelObject;
import com.github.arachnidium.model.interfaces.IDecomposable;
import com.github.arachnidium.model.support.ByNumbered;
import com.github.arachnidium.model.support.HowToGetByFrames;
import com.github.arachnidium.model.support.annotations.MobileContextNamePatterns;
import com.github.arachnidium.model.support.annotations.rootelements.IRootElementReader;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

class DecomposableListInterceptor implements MethodInterceptor {
	private final Field lisField;
	private final IDecomposable invoker;
	private final ESupportedDrivers supportedDriver;
	private final Class<? extends IDecomposable> required;
	private final boolean isInvokerApp;

	DecomposableListInterceptor(Field field, 
			IDecomposable invoker,
			ESupportedDrivers supportedDriver) {
		lisField = field;
		this.invoker = invoker;
		this.supportedDriver = supportedDriver;
		required = DecompositionUtil.
				getClassFromTheList(lisField);
		isInvokerApp = Application.class.isAssignableFrom(invoker.getClass());
	}

	@SuppressWarnings("unchecked")
	private Handle getActualHandle() {
		Class<? extends IDecomposable> required = DecompositionUtil
				.getClassFromTheList(lisField);

		if (isInvokerApp) {
			IHowToGetHandle how = DecompositionUtil
					.getRelevantHowToGetHandleStrategy(supportedDriver,
							lisField);

			if (how == null)
				how = DecompositionUtil.getRelevantHowToGetHandleStrategy(
						supportedDriver, required);
			if (how != null) {
				Long timeOutLong = DecompositionUtil.getTimeOut(lisField);

				if (timeOutLong == null)
					timeOutLong = DecompositionUtil.getTimeOut(required);
				if (timeOutLong != null) {
					return ((Application<?, IHowToGetHandle>) invoker).getPart(
							FunctionalPart.class, how, timeOutLong.longValue())
							.getHandle();
				} else {
					return ((Application<?, IHowToGetHandle>) invoker).getPart(
							FunctionalPart.class, how).getHandle();
				}

			} else {
				return ((ModelObject<?>) invoker).getHandle();
			}
		} else {
			return ((ModelObject<?>) invoker).getHandle();
		}
	}
	
	private HowToGetByFrames getHowToGetByFramesStrategy(){
		HowToGetByFrames howToGetByFrames = DecompositionUtil.getHowToGetByFramesStrategy(lisField);
		if (howToGetByFrames == null){
			howToGetByFrames = DecompositionUtil.getHowToGetByFramesStrategy(required);
		}
		return howToGetByFrames;
	}
	
	private By getBy(){
		IRootElementReader reader =  DecompositionUtil.getRootElementReader(supportedDriver);
		By by = reader.readClassAndGetBy(lisField, supportedDriver);
		if (by == null){
			by = reader.readClassAndGetBy(required, supportedDriver);
		}
		return by;
	}
	
	private List<IDecomposable> buildList() {
		ArrayList<IDecomposable> result = new ArrayList<>();
		Handle handle = getActualHandle();

		// if the required window context is not present
		try {
			handle.switchToMe();
		} catch (NoSuchWindowException | NoSuchContextException e) {
			return result;
		}

		WebDriver driver = handle.driverEncapsulation.getWrappedDriver();
		HowToGetByFrames howToGetByFrames = null;

		if (!ContextAware.class.isAssignableFrom(driver.getClass())) {
			howToGetByFrames = getHowToGetByFramesStrategy();
		} else {
			if (!((ContextAware) driver).getContext().contains(
					MobileContextNamePatterns.NATIVE))
				howToGetByFrames = getHowToGetByFramesStrategy();
		}

		if (howToGetByFrames != null)
			try {
				howToGetByFrames.switchTo(driver);
			} catch (NoSuchFrameException | NoSuchElementException e) {
				handle.switchToMe();
				return result;
			}

		By by = getBy();
		if (by == null) {
			if (isInvokerApp) {
				result.add(DecompositionUtil.get(required,
						Application.DEFAULT_PARAMETERS_FOR_DECOPMOSITION,
						new Object[] { handle, howToGetByFrames, by }));
			} else {
				result.add(DecompositionUtil.get(required,
						FunctionalPart.DEFAULT_PARAMETERS_FOR_DECOPMOSITION,
						new Object[] { invoker, howToGetByFrames, by }));
			}
			return result;
		}

		int totalElements = driver.findElements(by).size();
		for (int i=0; i< totalElements; i ++){
			if (isInvokerApp) {
				result.add(DecompositionUtil.get(required,
						Application.DEFAULT_PARAMETERS_FOR_DECOPMOSITION,
						new Object[] { handle, howToGetByFrames, new ByNumbered(by, i)}));
			} else {
				result.add(DecompositionUtil.get(required,
						FunctionalPart.DEFAULT_PARAMETERS_FOR_DECOPMOSITION,
						new Object[] { invoker, howToGetByFrames, new ByNumbered(by, i)}));
			}			
		}
		return result;
	}
	
	@Override
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {		
		return method.invoke(buildList(), args);
	}

}
