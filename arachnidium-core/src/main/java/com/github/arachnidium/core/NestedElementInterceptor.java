package com.github.arachnidium.core;


import com.github.arachnidium.core.components.common.TimeOut;
import com.github.arachnidium.util.inheritance.MethodInheritanceUtil;
import com.github.arachnidium.util.proxy.DefaultInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

class NestedElementInterceptor extends DefaultInterceptor {

    private final Handle handle;

    NestedElementInterceptor(Handle handle) {
        this.handle = handle;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args,
                            MethodProxy proxy) throws Throwable {
        final WebDriver driver = handle.driverEncapsulation.getWrappedDriver();
        if (MethodInheritanceUtil.isOverriddenFrom(method, WrapsDriver.class))
            return driver;

        TimeOut timeOut = handle.driverEncapsulation.getTimeOut();
        long timeOutValue = timeOut.getImplicitlyWaitTimeOut();
        TimeUnit timeUnit = timeOut.getImplicitlyWaitTimeUnit();
        try {
            handle.switchToMe();
            timeOut.implicitlyWait(0, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait(driver, TimeUnit.SECONDS.convert(timeOutValue, timeUnit));
            return method.invoke(wait.until(ExpectedConditions.presenceOfElementLocated(handle.by)), args);
        } finally {
            timeOut.implicitlyWait(timeOutValue, timeUnit);
        }

    }

}
