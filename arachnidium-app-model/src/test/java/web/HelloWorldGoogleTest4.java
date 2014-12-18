package web;

import org.junit.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;

import com.github.arachnidium.model.browser.WebFactory;
import com.github.arachnidium.web.google.Google;

public class HelloWorldGoogleTest4 {
	
  private Google google;	
	
  @Test(description = "This test verifies ability to instantiate lists of page objects")
  public void test() {
	  Assert.assertEquals(1, google.foundLinks1.size());
	  Assert.assertEquals(true, google.foundLinks1.get(0).getLinkCount() == 10);
	  
	  Assert.assertEquals(10, google.foundLinks2.size());
	  Assert.assertEquals(1,  google.foundLinks2.get(0).getLinkCount());
	  
	  Assert.assertEquals(10, google.foundLinks3.size());
	  Assert.assertEquals(1, google.foundLinks3.get(0).getLinkCount());	
	  
	  Assert.assertEquals(1, google.foundLinks4.size());
	  Assert.assertEquals(1, google.foundLinks4.get(0).getLinkCount());	
	  
	  Assert.assertEquals(1, google.foundLinks5.size());
	  Assert.assertEquals(1, google.foundLinks5.get(0).getLinkCount());	
	  
	  Assert.assertEquals(0, google.foundLinks6.size());
	  Assert.assertEquals(0, google.foundLinks7.size());
	  Assert.assertEquals(0, google.foundLinks8.size());
			  
	  Assert.assertEquals(1, google.linksAreFound.foundLinks1.size());
	  Assert.assertEquals(true, google.linksAreFound.foundLinks1.get(0).getLinkCount() == 10);
	  
	  Assert.assertEquals(10, google.linksAreFound.foundLinks2.size());
	  Assert.assertEquals(1,  google.linksAreFound.foundLinks2.get(0).getLinkCount());
	  
	  Assert.assertEquals(10, google.linksAreFound.foundLinks3.size());
	  Assert.assertEquals(1, google.linksAreFound.foundLinks3.get(0).getLinkCount());	
	  
	  Assert.assertEquals(1, google.linksAreFound.foundLinks4.size());
	  Assert.assertEquals(1, google.linksAreFound.foundLinks4.get(0).getLinkCount());
	  
	  Assert.assertEquals(1, google.linksAreFound.foundLinks5.size());
	  Assert.assertEquals(1, google.linksAreFound.foundLinks5.get(0).getLinkCount());	
	  
	  Assert.assertEquals(0, google.linksAreFound.foundLinks6.size());
	  Assert.assertEquals(0, google.linksAreFound.foundLinks7.size());
	  Assert.assertEquals(10, google.linksAreFound.foundLinks8.size());
  }
 
  @BeforeTest
  public void beforeTest() {
	  google = new WebFactory().launch(Google.class, "http://www.google.com/");	 
	  google.performSearch("Hello world Wikipedia");
  }
  
  @AfterTest
  public void afterTest(){
	  google.quit();	  
  }

}
