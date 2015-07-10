package implicitliwait_test;

import java.util.concurrent.TimeUnit;

import com.github.arachnidium.core.components.common.TimeOut;
import com.github.arachnidium.util.configuration.Configuration;

import org.junit.Assert;
import org.testng.annotations.Test;

import com.github.arachnidium.fake_pageobject.FakePageObject;
import com.github.arachnidium.model.browser.WebFactory;
import com.github.arachnidium.web.google.Google;

public class ImlicitlyWaitTest {

	private final static long ACCEPTABLE_DELTA_MILLS = 2000; //environment issues

	private static void checkDifferent(long etalonTime,
			TimeUnit etalonTimeUnit, long currentMillis) {
		long etalonMillis = TimeUnit.MILLISECONDS.convert(etalonTime,
				etalonTimeUnit);
		Assert.assertEquals(
				true,
				(Math.abs(currentMillis - etalonMillis) < ACCEPTABLE_DELTA_MILLS));
	}

	@Test(description = "This test checks the ability to optionally change timeout")
	public void imlicitlyWaitTest() {
		Configuration configuration = Configuration
				.get("src/test/resources/configs/desctop/firefox.json");
		Google g = new WebFactory(configuration).launch(Google.class, "http://www.google.com/");
		try {
			FakePageObject<?> fakePageObject = g.getPart(FakePageObject.class);

            TimeOut timeOut = fakePageObject.getWebDriverEncapsulation().getTimeOut();
			checkDifferent(timeOut.getImplicitlyWaitTimeOut(),
                    timeOut.getImplicitlyWaitTimeUnit(),
                    fakePageObject.fakeInteractiveMethod());

			checkDifferent(timeOut.getImplicitlyWaitTimeOut(), timeOut.getImplicitlyWaitTimeUnit(),
					fakePageObject.fakeInteractiveMethod1_1());

			checkDifferent(fakePageObject.TIME_OUT, fakePageObject.TIME_UNIT,
					fakePageObject.fakeInteractiveMethod2());

			checkDifferent(fakePageObject.TIME_OUT, fakePageObject.TIME_UNIT,
					fakePageObject.fakeInteractiveMethod2_1());

            checkDifferent(timeOut.getImplicitlyWaitTimeOut(),
                    timeOut.getImplicitlyWaitTimeUnit(),
                    fakePageObject.fakeInteractiveMethod());

            checkDifferent(timeOut.getImplicitlyWaitTimeOut(), timeOut.getImplicitlyWaitTimeUnit(),
                    fakePageObject.fakeInteractiveMethod1_1());

            checkDifferent(fakePageObject.TIME_OUT, fakePageObject.TIME_UNIT,
					fakePageObject.fakeInteractiveMethod3());

            checkDifferent(fakePageObject.TIME_OUT, fakePageObject.TIME_UNIT,
					fakePageObject.fakeInteractiveMethod3_1());

            checkDifferent(timeOut.getImplicitlyWaitTimeOut(),
                    timeOut.getImplicitlyWaitTimeUnit(),
                    fakePageObject.fakeInteractiveMethod());

            checkDifferent(timeOut.getImplicitlyWaitTimeOut(), timeOut.getImplicitlyWaitTimeUnit(),
                    fakePageObject.fakeInteractiveMethod1_1());
			
			checkDifferent(fakePageObject.TIME_OUT, fakePageObject.TIME_UNIT,
					fakePageObject.fakeInteractiveMethod4());

			checkDifferent(fakePageObject.TIME_OUT, fakePageObject.TIME_UNIT,
					fakePageObject.fakeInteractiveMethod4_1());

            checkDifferent(timeOut.getImplicitlyWaitTimeOut(),
                    timeOut.getImplicitlyWaitTimeUnit(),
                    fakePageObject.fakeInteractiveMethod());

            checkDifferent(timeOut.getImplicitlyWaitTimeOut(), timeOut.getImplicitlyWaitTimeUnit(),
                    fakePageObject.fakeInteractiveMethod1_1());

			checkDifferent(fakePageObject.TIME_OUT, fakePageObject.TIME_UNIT,
					fakePageObject.fakeInteractiveMethod5());

			checkDifferent(fakePageObject.TIME_OUT, fakePageObject.TIME_UNIT,
					fakePageObject.fakeInteractiveMethod5_1());

            checkDifferent(timeOut.getImplicitlyWaitTimeOut(),
                    timeOut.getImplicitlyWaitTimeUnit(),
                    fakePageObject.fakeInteractiveMethod());

            checkDifferent(timeOut.getImplicitlyWaitTimeOut(), timeOut.getImplicitlyWaitTimeUnit(),
                    fakePageObject.fakeInteractiveMethod1_1());
		} finally {
			g.quit();
		}

	}
}
