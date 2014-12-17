package com.github.arachnidium.model.common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.apache.commons.lang3.ArrayUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchContextException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.SearchContext;

import com.github.arachnidium.core.fluenthandle.IHowToGetHandle;
import com.github.arachnidium.core.interfaces.ISwitchesToItself;
import com.github.arachnidium.core.settings.supported.ESupportedDrivers;
import com.github.arachnidium.model.abstractions.ModelObject;
import com.github.arachnidium.model.interfaces.IDecomposable;
import com.github.arachnidium.model.support.ByNumbered;
import com.github.arachnidium.model.support.HowToGetByFrames;
import com.github.arachnidium.model.support.annotations.rootelements.IRootElementReader;

class DecomposableListInterceptor implements MethodInterceptor {
	private final Field lisField;
	private final ModelObject<?> invoker;
	private final ESupportedDrivers supportedDriver;
	private final Class<? extends IDecomposable> required;
	private final boolean isInvokerApp;

	private final IHowToGetHandle howToGetHandlestrategy;
	private final Long timeOutLong;
	private final HowToGetByFrames howToGetByFrames;
	private final By by;

	DecomposableListInterceptor(Field field, ModelObject<?> invoker,
			ESupportedDrivers supportedDriver) {
		lisField = field;
		this.invoker = invoker;
		this.supportedDriver = supportedDriver;
		required = DecompositionUtil.getClassFromTheList(lisField);
		isInvokerApp = Application.class.isAssignableFrom(invoker.getClass());
		howToGetHandlestrategy = returnHowToGetHandleStrategy();
		timeOutLong = getTimeOut();
		howToGetByFrames = getHowToGetByFramesStrategy();
		by = getBy();
	}

	private IHowToGetHandle returnHowToGetHandleStrategy() {
		if (isInvokerApp) {
			IHowToGetHandle how = DecompositionUtil
					.getRelevantHowToGetHandleStrategy(supportedDriver,
							lisField);

			if (how == null)
				how = DecompositionUtil.getRelevantHowToGetHandleStrategy(
						supportedDriver, required);
			return how;
		}
		return null;
	}

	private Long getTimeOut() {
		if (isInvokerApp) {
			Long timeOutLong = DecompositionUtil.getTimeOut(lisField);

			if (timeOutLong == null)
				timeOutLong = DecompositionUtil.getTimeOut(required);
			return timeOutLong;
		}
		return null;
	}

	private HowToGetByFrames getHowToGetByFramesStrategy() {
		HowToGetByFrames howToGetByFrames = DecompositionUtil
				.getHowToGetByFramesStrategy(lisField);
		if (howToGetByFrames == null) {
			howToGetByFrames = DecompositionUtil
					.getHowToGetByFramesStrategy(required);
		}
		return howToGetByFrames;
	}

	private By getBy() {
		IRootElementReader reader = DecompositionUtil
				.getRootElementReader(supportedDriver);
		By by = reader.readClassAndGetBy(lisField, supportedDriver);
		if (by == null) {
			by = reader.readClassAndGetBy(required, supportedDriver);
		}
		return by;
	}

	private static Object[] clearArgs(Object[] args) {
		Object[] result = new Object[] {};
		for (Object arg : args) {
			if (arg == null) {
				continue;
			}
			result = ArrayUtils.add(result, arg);
		}
		return result;
	}

	private IDecomposable returnPart(Class<? extends IDecomposable> target) {
		Object[] args = null;
		if (isInvokerApp) {
			args = clearArgs(new Object[] { target, howToGetHandlestrategy,
					howToGetByFrames, timeOutLong });
		} else {
			args = clearArgs(new Object[] { target, howToGetByFrames });
		}
		Method method = MethodReadingUtil.getSuitableMethod(invoker.getClass(),
				DecompositionUtil.GET_PART, args);
		try {
			return (IDecomposable) method.invoke(invoker, args);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// TODO to be refactored
	private List<IDecomposable> buildList() {
		ArrayList<IDecomposable> result = new ArrayList<>();
		// FunctionalPart<?> intermediate = returnIntermediatePart();

		if (by == null) {
			IDecomposable element = returnPart(required);
			try {
				if (ISwitchesToItself.class
						.isAssignableFrom(element.getClass())) {
					((ISwitchesToItself) element).switchToMe();
				}
				result.add(element);
				return result;
			} catch (NoSuchWindowException | NoSuchContextException
					| NoSuchFrameException | NoSuchElementException e) {
				return result;
			}
		}

		FunctionalPart<?> intermediate = (FunctionalPart<?>) returnPart(FunctionalPart.class);
		try {
			intermediate.switchToMe();
		} catch (NoSuchWindowException | NoSuchContextException
				| NoSuchFrameException | NoSuchElementException e) {
			return result;
		}

		SearchContext sc = intermediate.getCurrentSearcContext();
		int totalElements = sc.findElements(by).size();
		for (int i = 0; i < totalElements; i++) {
			if (isInvokerApp) {
				result.add(DecompositionUtil.get(required,
						Application.DEFAULT_PARAMETERS_FOR_DECOPMOSITION,
						new Object[] { intermediate.getHandle(),
								howToGetByFrames, new ByNumbered(by, i) }));
			} else {
				result.add(DecompositionUtil.get(required,
						FunctionalPart.DEFAULT_PARAMETERS_FOR_DECOPMOSITION,
						new Object[] { invoker, howToGetByFrames,
								new ByNumbered(by, i) }));
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
