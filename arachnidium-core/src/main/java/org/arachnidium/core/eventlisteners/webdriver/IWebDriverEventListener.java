package org.arachnidium.core.eventlisteners.webdriver;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

/**
 * @author s.tihomirov For some functions that are not declared in
 *         WebDriverEventListener
 */
public interface IWebDriverEventListener extends WebDriverEventListener {

	@BeforeMethod(clazzes = Navigation.class, methods = "to", arguments = {
			String.class, WebDriver.class })
	void beforeNavigateTo(String url, WebDriver driver);

	@AfterMethod(clazzes = Navigation.class, methods = "to", arguments = {
			String.class, WebDriver.class })
	void afterNavigateTo(String url, WebDriver driver);

	@BeforeMethod(clazzes = Navigation.class, methods = "back")
	void beforeNavigateBack(WebDriver driver);

	@AfterMethod(clazzes = Navigation.class, methods = "back")
	void afterNavigateBack(WebDriver driver);

	@BeforeMethod(clazzes = Navigation.class, methods = "forward")
	void beforeNavigateForward(WebDriver driver);

	@AfterMethod(clazzes = Navigation.class, methods = "forward")
	void afterNavigateForward(WebDriver driver);

	@BeforeMethod(clazzes = { WebDriver.class, WebElement.class }, methods = "findElement, findElements", arguments = {
			By.class, WebElement.class, WebDriver.class })
	void beforeFindBy(By by, WebElement element, WebDriver driver);

	@AfterMethod(clazzes = { WebDriver.class, WebElement.class }, methods = "findElement, findElements", arguments = {
			By.class, WebElement.class, WebDriver.class })
	void afterFindBy(By by, WebElement element, WebDriver driver);

	@BeforeMethod(clazzes = { WebElement.class }, methods = "click", arguments = {
			WebElement.class, WebDriver.class })
	void beforeClickOn(WebElement element, WebDriver driver);

	@AfterMethod(clazzes = { WebElement.class }, methods = "click", arguments = {
			WebElement.class, WebDriver.class })
	void afterClickOn(WebElement element, WebDriver driver);

	@BeforeMethod(clazzes = { WebElement.class }, methods = { "sendKeys",
			"clear", "setValue" }, arguments = { WebElement.class,
			WebDriver.class })
	void beforeChangeValueOf(WebElement element, WebDriver driver);

	@AfterMethod(clazzes = { WebElement.class }, methods = { "sendKeys",
			"clear", "setValue" }, arguments = { WebElement.class,
			WebDriver.class })
	void afterChangeValueOf(WebElement element, WebDriver driver);

	@BeforeMethod(clazzes = JavascriptExecutor.class, methods = {
			"executeScript", "executeAsyncScript" }, arguments = {
			String.class, WebDriver.class })
	void beforeScript(String script, WebDriver driver);

	@AfterMethod(clazzes = JavascriptExecutor.class, methods = {
			"executeScript", "executeAsyncScript" }, arguments = {
			String.class, WebDriver.class })
	void afterScript(String script, WebDriver driver);

	void onException(Throwable throwable, WebDriver driver);

	@BeforeMethod(clazzes = Alert.class, methods = "accept", arguments = {
			WebDriver.class, Alert.class })
	public void beforeAlertAccept(WebDriver driver, Alert alert);

	@AfterMethod(clazzes = Alert.class, methods = "accept", arguments = {
			WebDriver.class, Alert.class })
	public void afterAlertAccept(WebDriver driver, Alert alert);

	@BeforeMethod(clazzes = Alert.class, methods = "dismiss", arguments = {
			WebDriver.class, Alert.class })
	public void beforeAlertDismiss(WebDriver driver, Alert alert);

	@AfterMethod(clazzes = Alert.class, methods = "dismiss", arguments = {
			WebDriver.class, Alert.class })
	public void afterAlertDismiss(WebDriver driver, Alert alert);

	@BeforeMethod(clazzes = Alert.class, methods = "sendKeys", arguments = {
			WebDriver.class, Alert.class, String.class })
	public void beforeAlertSendKeys(WebDriver driver, Alert alert, String keys);

	@AfterMethod(clazzes = Alert.class, methods = "sendKeys", arguments = {
			WebDriver.class, Alert.class, String.class })
	public void afterAlertSendKeys(WebDriver driver, Alert alert, String keys);

	@BeforeMethod(clazzes = WebElement.class, methods = "submit", arguments = {
			WebDriver.class, WebElement.class })
	public void beforeSubmit(WebDriver driver, WebElement element);

	@AfterMethod(clazzes = WebElement.class, methods = "submit", arguments = {
			WebDriver.class, WebElement.class })
	public void afterSubmit(WebDriver driver, WebElement element);

	@BeforeMethod(clazzes = { WebDriver.class, WebElement.class }, methods = {
			"findElementByAccessibilityId", "findElementByAndroidUIAutomator",
			"findElementByIosUIAutomation", "findElementsByAccessibilityId",
			"findElementsByAndroidUIAutomator",
			"findElementsByIosUIAutomation", "getNamedTextField" }, arguments = {
			String.class, WebElement.class, WebDriver.class})
	void beforeFindBy(String by, WebElement element, WebDriver driver);

	@BeforeMethod(clazzes = { WebDriver.class, WebElement.class }, methods = {
			"findElementByAccessibilityId", "findElementByAndroidUIAutomator",
			"findElementByIosUIAutomation", "findElementsByAccessibilityId",
			"findElementsByAndroidUIAutomator",
			"findElementsByIosUIAutomation", "getNamedTextField" }, arguments = {
			String.class, WebElement.class, WebDriver.class})
	void afterFindBy(String by, WebElement element, WebDriver driver);
}
