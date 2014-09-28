package implicitliwait_test;

import java.util.concurrent.TimeUnit;
import junit.framework.Assert;
import org.arachnidium.util.configuration.Configuration;
import org.arachnidium.web.google.Google;
import org.testng.annotations.Test;
import fake_pageobject.FakePageObject;

public class ImlicitlyWaitTest {

	private final static long ACCEPTABLE_DELTA_MILLS = 1500;

	private static void checkDifferent(long etalonTime,
			TimeUnit etalonTimeUnit, long currentMillis) {
		long etalonMillis = TimeUnit.MILLISECONDS.convert(etalonTime,
				etalonTimeUnit);
		Assert.assertEquals(
				true,
				(Math.abs(currentMillis - etalonMillis) < ACCEPTABLE_DELTA_MILLS));
	}

	@Test
	public void imlicitlyWaitTest() {
		Configuration configuration = Configuration
				.get("src/test/resources/configs/desctop/firefox.json");
		Google g = Google.getNew(configuration);
		try {
			FakePageObject<?> fakePageObject = g.getPart(FakePageObject.class);

			checkDifferent(10, TimeUnit.SECONDS,
					fakePageObject.fakeInteractiveMethod());

			checkDifferent(10, TimeUnit.SECONDS,
					fakePageObject.fakeInteractiveMethod1_1());

			configuration = Configuration
					.get("src/test/resources/fake_pageobject/fakesettings.json");
			fakePageObject.getWebDriverEncapsulation().resetAccordingTo(
					configuration);

			checkDifferent(18500000, TimeUnit.MICROSECONDS,
					fakePageObject.fakeInteractiveMethod1_1());
			
			checkDifferent(18500000, TimeUnit.MICROSECONDS,
					fakePageObject.fakeInteractiveMethod());			

			checkDifferent(fakePageObject.TIME_OUT, fakePageObject.TIME_UNIT,
					fakePageObject.fakeInteractiveMethod2());

			checkDifferent(fakePageObject.TIME_OUT, fakePageObject.TIME_UNIT,
					fakePageObject.fakeInteractiveMethod2_1());
			
			checkDifferent(18500000, TimeUnit.MICROSECONDS,
					fakePageObject.fakeInteractiveMethod1_1());
			
			checkDifferent(18500000, TimeUnit.MICROSECONDS,
					fakePageObject.fakeInteractiveMethod());				
		} finally {
			g.quit();
		}

	}
}
