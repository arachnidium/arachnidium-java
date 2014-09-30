package com.github.arachnidium.fake_pageobject;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;

public class FakePageObject<S extends Handle> extends FunctionalPart<S> {

	public final long TIME_OUT = 4700; //MILLISECONDS
	public final TimeUnit TIME_UNIT = TimeUnit.MILLISECONDS;
	
	private static final String FAKE_CLASS =  "fakelocator";
	
	@FindBy(className = FAKE_CLASS)
	private List<WebElement> fakeElements;
	
	protected FakePageObject(S handle) {
		super(handle);
		load();
	}

	@InteractiveMethod
	public long fakeInteractiveMethod(){
		long start = Calendar.getInstance().getTimeInMillis();
		fakeElements.size();
		long benchMark = Calendar.getInstance().getTimeInMillis();
		return benchMark - start;
	}
	
	@InteractiveMethod
	public long fakeInteractiveMethod1_1(){
		long start = Calendar.getInstance().getTimeInMillis();
		getWrappedDriver().findElements(By.className(FAKE_CLASS)).size();
		long benchMark = Calendar.getInstance().getTimeInMillis();
		return benchMark - start;
	}
	
	@InteractiveMethod
	@WithImplicitlyWait(timeOut = TIME_OUT, timeUnit = TimeUnit.MILLISECONDS)
	public long fakeInteractiveMethod2(){
		long start = Calendar.getInstance().getTimeInMillis();
		fakeElements.size();
		long benchMark = Calendar.getInstance().getTimeInMillis();
		return benchMark - start;
	}	
	
	@InteractiveMethod
	@WithImplicitlyWait(timeOut = TIME_OUT, timeUnit = TimeUnit.MILLISECONDS)
	public long fakeInteractiveMethod2_1(){
		long start = Calendar.getInstance().getTimeInMillis();
		getWrappedDriver().findElements(By.className(FAKE_CLASS)).size();
		long benchMark = Calendar.getInstance().getTimeInMillis();
		return benchMark - start;		
	}	
	
	@InteractiveMethod
	@WithImplicitlyWait(timeOut = TIME_OUT, timeUnit = TimeUnit.MILLISECONDS)
	public long fakeInteractiveMethod3(){
		return fakeInteractiveMethod();
	}	
	
	@InteractiveMethod
	@WithImplicitlyWait(timeOut = TIME_OUT, timeUnit = TimeUnit.MILLISECONDS)
	public long fakeInteractiveMethod3_1(){
		return fakeInteractiveMethod1_1();		
	}	
	
	@InteractiveMethod
	@WithImplicitlyWait(timeOut = TIME_OUT, timeUnit = TimeUnit.MILLISECONDS)
	public long fakeInteractiveMethod4(){
		fakeInteractiveMethod();
		long start = Calendar.getInstance().getTimeInMillis();
		fakeElements.size();
		long benchMark = Calendar.getInstance().getTimeInMillis();
		return benchMark - start;
	}	
	
	@InteractiveMethod
	@WithImplicitlyWait(timeOut = TIME_OUT, timeUnit = TimeUnit.MILLISECONDS)
	public long fakeInteractiveMethod4_1(){
		fakeInteractiveMethod1_1();	
		long start = Calendar.getInstance().getTimeInMillis();
		getWrappedDriver().findElements(By.className(FAKE_CLASS)).size();
		long benchMark = Calendar.getInstance().getTimeInMillis();
		return benchMark - start;
	}	
	
	@InteractiveMethod
	public long fakeInteractiveMethod5(){
		return fakeInteractiveMethod2();
	}	
	
	@InteractiveMethod
	public long fakeInteractiveMethod5_1(){
		return fakeInteractiveMethod2_1();	
	}			
}
