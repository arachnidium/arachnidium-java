package com.github.arachnidium.core.bean;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.openqa.selenium.Alert;
import org.openqa.selenium.ContextAware;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebDriver.TargetLocator;
import org.openqa.selenium.WebElement;
import org.springframework.context.support.AbstractApplicationContext;

import com.github.arachnidium.core.interfaces.IDestroyable;
import com.github.arachnidium.util.configuration.interfaces.IConfigurationWrapper;

@Aspect
class AspectWebDriver extends AbstractAspect {

	private static final List<Class<?>> listenable = new ArrayList<Class<?>>() {
		private static final long serialVersionUID = 1L;
		{
			add(WebDriver.class);
			add(WebElement.class);
			add(Navigation.class);
			add(TargetLocator.class);
			add(ContextAware.class);
			add(Alert.class);
			add(Options.class);
		}
	};

	private final IDestroyable destroyable;
	private final AbstractApplicationContext context;
	
	private final String POINTCUT_VALUE = "execution(* org.openqa.selenium.WebDriver.*(..)) || "
			+ "execution(* org.openqa.selenium.WebElement.*(..)) ||"
			+ "execution(* org.openqa.selenium.WebDriver.Navigation.*(..)) || "
			+ "execution(* org.openqa.selenium.WebDriver.Options.*(..)) || "
			+ "execution(* org.openqa.selenium.WebDriver.TargetLocator.*(..)) || "
			+ "execution(* org.openqa.selenium.JavascriptExecutor.*(..)) || "
			+ "execution(* org.openqa.selenium.ContextAware.*(..)) || "
			+ "execution(* org.openqa.selenium.Alert.*(..)) || "
			+ "execution(* io.appium.java_client.MobileElement.*(..)) || "
			+ "execution(* io.appium.java_client.AppiumDriver.*(..)) || "
			+ "execution(* io.appium.java_client.android.AndroidDriver.*(..)) || "
			+ "execution(* io.appium.java_client.ios.IOSDriver.*(..)) || "
		    + "execution(* io.appium.java_client.android.AndroidElement.*(..)) || "
			+ "execution(* io.appium.java_client.ios.IOSElement.*(..))"
			;
	


	public AspectWebDriver(
			IConfigurationWrapper configurationWrapper,
			IDestroyable destroyable, AbstractApplicationContext context) {
		super(configurationWrapper);
		this.context = context;
		this.destroyable = destroyable;
	}

	private static Class<?> getClassForProxy(Class<?> classOfObject) {
		for (Class<?> c : listenable) {
			if (!c.isAssignableFrom(classOfObject)) {
				continue;
			}
			return c;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private <T> T getListenable(Object object) {
		Class<?> classForProxy = getClassForProxy(object.getClass());
		if (classForProxy != null) {
			return (T) classForProxy.cast(object);
		}
		return null;
	}

	public void beforeNavigateTo(JoinPoint joinPoint) {
		//TODO
	}

	public void afterNavigateTo(JoinPoint joinPoint) {
		//TODO
	}
	
	public void beforeNavigateBack(JoinPoint joinPoint) {
		//TODO
	}


	public void afterNavigateBack(JoinPoint joinPoint) {
		//TODO
	}

	public void beforeNavigateForward(JoinPoint joinPoint) {
		//TODO
	}

	public void afterNavigateForward(JoinPoint joinPoint) {
		//TODO
	}

	public void beforeFindBy(JoinPoint joinPoint) {
		//TODO
	}

	public void afterFindBy(JoinPoint joinPoint) {
		//TODO
	}

	public void beforeClickOn(JoinPoint joinPoint) {
		//TODO
	}

	public void afterClickOn(JoinPoint joinPoint) {
		//TODO
	}

	public void beforeChangeValueOf(JoinPoint joinPoint) {
		//TODO
	}

	public void afterChangeValueOf(JoinPoint joinPoint) {
		//TODO
	}

	public void beforeScript(JoinPoint joinPoint) {
		//TODO
	}

	public void afterScript(JoinPoint joinPoint) {
		//TODO
	}

	public void onException(Throwable throwable, JoinPoint joinPoint) {
		//TODO
	}

	public void afterAlertAccept(JoinPoint joinPoint) {
		//TODO
	}

	public void afterAlertDismiss(JoinPoint joinPoint) {
		//TODO
	}


	public void afterAlertSendKeys(JoinPoint joinPoint) {
		//TODO
	}

	public void afterSubmit(JoinPoint joinPoint) {
		//TODO
	}

	public void beforeAlertAccept(JoinPoint joinPoint) {
		//TODO
	}

	public void beforeAlertDismiss(JoinPoint joinPoint) {
		//TODO
	}

	public void beforeAlertSendKeys(JoinPoint joinPoint) {
		//TODO
	}

	public void beforeSubmit(JoinPoint joinPoint) {
		//TODO
	}

	private Object transformToListenable(Object result) {
		if (result == null) { // maybe it was "void"
			return result;
		}
		Object o = getListenable(result);
		if (o != null) { // ...so listenable object will be returned! ha-ha-ha
			result = context.getBean(MainBeanConfiguration.COMPONENT_BEAN, o);
		}
		return result;
	}

	// List of WebElement
	private List<Object> returnProxyList(List<Object> originalList) {
		try {
			List<Object> proxyList = new ArrayList<>();
			for (Object o : originalList) {
				if (getClassForProxy(o.getClass()) == null) {
					proxyList.add(o);
				}
				proxyList.add(context.getBean(
						MainBeanConfiguration.COMPONENT_BEAN, o));
			}
			return proxyList;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * @see com.github.arachnidium.core.bean.AbstractAspect#doAround(org.aspectj.lang.ProceedingJoinPoint)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Around(POINTCUT_VALUE)
	public Object doAround(ProceedingJoinPoint point) throws Throwable {
		Throwable t = null;
		Object result = null;
		try {
			result = point.proceed();
		} catch (Exception e) {
			t = e;
			;
		}
		if (t != null) {
			throw getRootCause(t);
		}

		if (result == null) { // maybe it was "void"
			return result;
		}
		if (List.class.isAssignableFrom(result.getClass())) {
			return returnProxyList((List<Object>) result);
		}
		return transformToListenable(result);
	}

	public void beforeQuit(JoinPoint joinPoint) {
		//TODO
	}

}
