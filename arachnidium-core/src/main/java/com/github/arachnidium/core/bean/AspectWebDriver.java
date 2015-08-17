package com.github.arachnidium.core.bean;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
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
	private final WebDriver driver;
	private final DefaultWebDriverEventListener defaultWebDriverEventListener;
	
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
			final WebDriver driver,
			IConfigurationWrapper configurationWrapper,
			IDestroyable destroyable, AbstractApplicationContext context) {
		super(configurationWrapper);
		this.driver = driver;
		this.context = context;
		this.destroyable = destroyable;
		this.defaultWebDriverEventListener = new DefaultWebDriverEventListener(configurationWrapper);
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
	
	@Before("execution(* org.openqa.selenium.WebDriver.Navigation.get(..))  || "
			+ "execution(* org.openqa.selenium.WebDriver.Navigation.to(..))")
	public void beforeNavigateTo(JoinPoint joinPoint) throws Throwable{
		try {
			String url = String.valueOf(joinPoint.getArgs()[0]);
			defaultWebDriverEventListener.beforeNavigateTo(url, driver);
		}catch(Throwable t){
			throw getRootCause(t);
		}
	}

	@After("execution(* org.openqa.selenium.WebDriver.Navigation.get(..))  || "
			+ "execution(* org.openqa.selenium.WebDriver.Navigation.to(..))")
	public void afterNavigateTo(JoinPoint joinPoint)  throws Throwable{
		try {
			String url = String.valueOf(joinPoint.getArgs()[0]);
			defaultWebDriverEventListener.afterNavigateTo(url, driver);
		}catch(Throwable t){
			throw getRootCause(t);
		}
	}
	
	@Before("execution(* org.openqa.selenium.WebDriver.Navigation.back(..))")
	public void beforeNavigateBack(JoinPoint joinPoint) throws Throwable{
		try {
			defaultWebDriverEventListener.beforeNavigateBack(driver);
		}catch(Throwable t){
			throw getRootCause(t);
		}
	}

	@After("execution(* org.openqa.selenium.WebDriver.Navigation.back(..))")
	public void afterNavigateBack(JoinPoint joinPoint) throws Throwable{
		try {
			defaultWebDriverEventListener.afterNavigateBack(driver);
		}catch(Throwable t){
			throw getRootCause(t);
		}
	}

	@Before("execution(* org.openqa.selenium.WebDriver.Navigation.forward(..))")
	public void beforeNavigateForward(JoinPoint joinPoint)  throws Throwable{
		try {
			defaultWebDriverEventListener.beforeNavigateForward(driver);
		}catch(Throwable t){
			throw getRootCause(t);
		}
	}

	@After("execution(* org.openqa.selenium.WebDriver.Navigation.forward(..))")
	public void afterNavigateForward(JoinPoint joinPoint) throws Throwable{
		try {
			defaultWebDriverEventListener.afterNavigateForward(driver);
		}catch(Throwable t){
			throw getRootCause(t);
		}
	}

	@Before("execution(* org.openqa.selenium.SearchContext.findElement(..)) || "
			+ "execution(* org.openqa.selenium.SearchContext.findElements(..))")
	public void beforeFindBy(JoinPoint joinPoint) throws Throwable{
		try {
			Object target =  joinPoint.getTarget();
			By by = (By) joinPoint.getArgs()[0];
			if (!WebElement.class.isAssignableFrom(target.getClass()))
				defaultWebDriverEventListener.beforeFindBy(by, null, driver);
			else
				defaultWebDriverEventListener.beforeFindBy(by, (WebElement) target, driver);
		}catch(Throwable t){
			throw getRootCause(t);
		}	
	}

	@After("execution(* org.openqa.selenium.SearchContext.findElement(..)) || "
			+ "execution(* org.openqa.selenium.SearchContext.findElements(..))")
	public void afterFindBy(JoinPoint joinPoint) throws Throwable{
		try {
			Object target =  joinPoint.getTarget();
			By by = (By) joinPoint.getArgs()[0];
			if (!WebElement.class.isAssignableFrom(target.getClass()))
				defaultWebDriverEventListener.afterFindBy(by, null, driver);
			else
				defaultWebDriverEventListener.afterFindBy(by, (WebElement) target, driver);
		}catch(Throwable t){
			throw getRootCause(t);
		}	
	}

	@Before("execution(* org.openqa.selenium.WebElement.click(..))")
	public void beforeClickOn(JoinPoint joinPoint) throws Throwable{
		try {
			Object target =  joinPoint.getTarget();
			defaultWebDriverEventListener.beforeClickOn((WebElement) target, driver);
		}catch(Throwable t){
			throw getRootCause(t);
		}	
	}

	@After("execution(* org.openqa.selenium.WebElement.click(..))")
	public void afterClickOn(JoinPoint joinPoint) throws Throwable{
		try {
			Object target =  joinPoint.getTarget();
			defaultWebDriverEventListener.afterClickOn((WebElement) target, driver);
		}catch(Throwable t){
			throw getRootCause(t);
		}
	}

	@Before("execution(* org.openqa.selenium.WebElement.sendKeys(..)) || "
			+ "execution(* org.openqa.selenium.WebElement.clear(..))")
	public void beforeChangeValueOf(JoinPoint joinPoint) throws Throwable{
		try {
			Object target =  joinPoint.getTarget();
			defaultWebDriverEventListener.beforeChangeValueOf((WebElement) target, driver);
		}catch(Throwable t){
			throw getRootCause(t);
		}
	}

	@After("execution(* org.openqa.selenium.WebElement.sendKeys(..)) || "
			+ "execution(* org.openqa.selenium.WebElement.clear(..))")
	public void afterChangeValueOf(JoinPoint joinPoint) throws Throwable{
		try {
			Object target =  joinPoint.getTarget();
			defaultWebDriverEventListener.afterChangeValueOf((WebElement) target, driver);
		}catch(Throwable t){
			throw getRootCause(t);
		}
	}

	@Before("execution(* org.openqa.selenium.JavascriptExecutor.executeScript(..)) || "
			+ "execution(* org.openqa.selenium.JavascriptExecutor.executeAsyncScript(..))")
	public void beforeScript(JoinPoint joinPoint) throws Throwable{
		try {
			String script = String.valueOf(joinPoint.getArgs()[0]);
			defaultWebDriverEventListener.beforeScript(script, driver);
		}catch(Throwable t){
			throw getRootCause(t);
		}
	}

	@After("execution(* org.openqa.selenium.JavascriptExecutor.executeScript(..)) || "
			+ "execution(* org.openqa.selenium.JavascriptExecutor.executeAsyncScript(..))")
	public void afterScript(JoinPoint joinPoint) throws Throwable{
		try {
			String script = String.valueOf(joinPoint.getArgs()[0]);
			defaultWebDriverEventListener.afterScript(script, driver);
		}catch(Throwable t){
			throw getRootCause(t);
		}
	}

	@After("execution(* org.openqa.selenium.Alert.accept(..))")
	public void afterAlertAccept(JoinPoint joinPoint) throws Throwable{
		try {
			Object target =  joinPoint.getTarget();
			defaultWebDriverEventListener.afterAlertAccept(driver, (Alert) target); 
		}catch(Throwable t){
			throw getRootCause(t);
		}
	}

	@After("execution(* org.openqa.selenium.Alert.dismiss(..))")
	public void afterAlertDismiss(JoinPoint joinPoint) throws Throwable{
		try {
			Object target =  joinPoint.getTarget();
			defaultWebDriverEventListener.afterAlertDismiss(driver, (Alert) target); 
		}catch(Throwable t){
			throw getRootCause(t);
		}
	}

	@After("execution(* org.openqa.selenium.Alert.sendKeys(..))")
	public void afterAlertSendKeys(JoinPoint joinPoint) throws Throwable{
		try {
			Object target =  joinPoint.getTarget();
			String keys = String.valueOf(joinPoint.getArgs()[0]);
			defaultWebDriverEventListener.afterAlertSendKeys(driver, (Alert) target, keys);
		}catch(Throwable t){
			throw getRootCause(t);
		}
	}

	@After("execution(* org.openqa.selenium.WebElement.submit(..))")
	public void afterSubmit(JoinPoint joinPoint) throws Throwable{
		try {
			Object target =  joinPoint.getTarget();
			defaultWebDriverEventListener.afterSubmit(driver, (WebElement) target);
		}catch(Throwable t){
			throw getRootCause(t);
		}
	}

	@Before("execution(* org.openqa.selenium.Alert.accept(..))")
	public void beforeAlertAccept(JoinPoint joinPoint) throws Throwable{
		try {
			Object target =  joinPoint.getTarget();
			defaultWebDriverEventListener.beforeAlertAccept(driver, (Alert) target); 
		}catch(Throwable t){
			throw getRootCause(t);
		}
	}

	@Before("execution(* org.openqa.selenium.Alert.dismiss(..))")
	public void beforeAlertDismiss(JoinPoint joinPoint) throws Throwable{
		try {
			Object target =  joinPoint.getTarget();
			defaultWebDriverEventListener.beforeAlertDismiss(driver, (Alert) target);
		}catch(Throwable t){
			throw getRootCause(t);
		}
	}

	@Before("execution(* org.openqa.selenium.Alert.sendKeys(..))")
	public void beforeAlertSendKeys(JoinPoint joinPoint) throws Throwable{
		try {
			Object target =  joinPoint.getTarget();
			String keys = String.valueOf(joinPoint.getArgs()[0]);
			defaultWebDriverEventListener.beforeAlertSendKeys(driver, (Alert) target, keys);
		}catch(Throwable t){
			throw getRootCause(t);
		}
	}

	@Before("execution(* org.openqa.selenium.WebElement.submit(..))")
	public void beforeSubmit(JoinPoint joinPoint) throws Throwable{
		try {
			Object target =  joinPoint.getTarget();
			defaultWebDriverEventListener.beforeSubmit(driver, (WebElement) target);
		}catch(Throwable t){
			throw getRootCause(t);
		}
	}

	private Object transformToListenable(Object result) {
		if (result == null) { // maybe it was "void"
			return result;
		}
		Object o = getListenable(result);
		if (o != null) { // ...so listenable object will be returned! ha-ha-ha
			result = context.getBean(WebDriverBeanConfiguration.COMPONENT_BEAN, o);
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
						WebDriverBeanConfiguration.COMPONENT_BEAN, o));
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
		} catch (Throwable e) {
			t = e;
		}
		if (t != null) {
			Throwable rootCause = getRootCause(t);
			defaultWebDriverEventListener.onException(rootCause, driver);
			throw rootCause;
		}

		if (result == null) { // maybe it was "void"
			return result;
		}
		if (List.class.isAssignableFrom(result.getClass())) {
			return returnProxyList((List<Object>) result);
		}
		return transformToListenable(result);
	}

	@Before("execution(* org.openqa.selenium.WebDriver.quit(..))")
	public void beforeQuit(JoinPoint joinPoint) throws Throwable{
		try {
			destroyable.destroy();
			defaultWebDriverEventListener.beforeQuit(driver);
		}catch(Throwable t){
			throw getRootCause(t);
		}
	}

}
