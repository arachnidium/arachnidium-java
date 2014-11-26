package web;

import org.junit.Assert;
import org.testng.annotations.Test;

import com.github.arachnidium.model.browser.WebFactory;
import com.github.arachnidium.web.google.AnyPage;
import com.github.arachnidium.web.google.Google;
import com.github.arachnidium.web.google.exceptionhandling.AnnotataedTestExceptionHandler;
import com.github.arachnidium.web.google.exceptionhandling.AnnotatedLinksAreFoundExceptionThrowing;
import com.github.arachnidium.web.google.exceptionhandling.AnnotatedSearchBarExceptionThrowing;
import com.github.arachnidium.web.google.exceptionhandling.LinksAreFoundExceptionThrowing;
import com.github.arachnidium.web.google.exceptionhandling.SearchBarExceptionThrowing;
import com.github.arachnidium.web.google.exceptionhandling.TestExceptionHandler;

public class GoogleExceptionInterceptionTest {

	@SuppressWarnings("rawtypes")
	private void workWithGoogle(Google google,
			Class<? extends SearchBarExceptionThrowing> class1,
			Class<? extends LinksAreFoundExceptionThrowing> class2,
			boolean explicitlyCheckOut) throws Exception {
		TestExceptionHandler eh1 = new TestExceptionHandler();
		TestExceptionHandler eh2 = new TestExceptionHandler();

		SearchBarExceptionThrowing<?> sbet = google.getPart(class1);

		sbet.checkInExceptionHandler(eh1);
		sbet.performSearch("Hello world Wikipedia");
		if (explicitlyCheckOut)
			Assert.assertEquals(true, eh1.isExceptionCatched);

		eh1.isExceptionCatched = false;

		if (!explicitlyCheckOut) {
			Assert.assertEquals(true,
					AnnotataedTestExceptionHandler.isExceptionCatched_Static);
			AnnotataedTestExceptionHandler.isExceptionCatched_Static = false;
		}

		LinksAreFoundExceptionThrowing<?> lafet = google.getPart(class2);
		lafet.checkInExceptionHandler(eh2);

		Assert.assertNotSame(0, lafet.getLinkCount());
		if (explicitlyCheckOut)
			Assert.assertEquals(true, eh2.isExceptionCatched);

		eh2.isExceptionCatched = false;

		if (!explicitlyCheckOut) {
			Assert.assertEquals(true,
					AnnotataedTestExceptionHandler.isExceptionCatched_Static);
			AnnotataedTestExceptionHandler.isExceptionCatched_Static = false;
		}

		lafet.openLinkByIndex(1);

		if (explicitlyCheckOut) {
			Assert.assertEquals(true, eh2.isExceptionCatched);
			Assert.assertEquals(false, eh1.isExceptionCatched);
		}
		eh2.isExceptionCatched = false;

		if (!explicitlyCheckOut) {
			Assert.assertEquals(true,
					AnnotataedTestExceptionHandler.isExceptionCatched_Static);
			AnnotataedTestExceptionHandler.isExceptionCatched_Static = false;
		}

		AnyPage anyPage = google.getPart(AnyPage.class, 1);
		anyPage.close();
		if (explicitlyCheckOut) {
			Assert.assertEquals(false, eh2.isExceptionCatched);
			Assert.assertEquals(false, eh1.isExceptionCatched);
		}

		if (!explicitlyCheckOut) {
			Assert.assertEquals(false,
					AnnotataedTestExceptionHandler.isExceptionCatched_Static);
			AnnotataedTestExceptionHandler.isExceptionCatched_Static = false;
		}
	}
	
	@SuppressWarnings("rawtypes")
	private void test(Google google, Class<? extends SearchBarExceptionThrowing> class1,
			 Class<? extends LinksAreFoundExceptionThrowing> class2, boolean explicitlyCheckOut) throws Exception {
		try {
			workWithGoogle(google, class1, class2, explicitlyCheckOut);
		} finally {
			google.quit();
		}
	}

	@Test(description = "This test checks exception interception and handling")
	public void exceptionInterceptionTest() throws Exception{
		test(new WebFactory().launch(Google.class, "http://www.google.com/"), 
				SearchBarExceptionThrowing.class, 
				LinksAreFoundExceptionThrowing.class, true);
		
	}
	
	@Test(description = "This test checks exception interception and handling. Annotated classes")
	public void exceptionInterceptionTest2() throws Exception{
		test(new WebFactory().launch(Google.class, "http://www.google.com/"), 
				AnnotatedSearchBarExceptionThrowing.class, 
				AnnotatedLinksAreFoundExceptionThrowing.class, false);
		
	}	
}
