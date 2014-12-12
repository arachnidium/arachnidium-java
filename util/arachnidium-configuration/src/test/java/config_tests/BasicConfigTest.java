package config_tests;

import org.aspectj.lang.reflect.AdviceKind;
import org.junit.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;

import com.github.arachnidium.util.configuration.Configuration;

public class BasicConfigTest {
	
  private Configuration test1;	
  private Configuration test2;
	
  @Test
  public void test() {
	  TestHelper helperDefault = Configuration.byDefault.getSection(TestHelper.class);
	  TestHelper helper1 = test1.getSection(TestHelper.class);
	  TestHelper helper2 = test2.getSection(TestHelper.class);
	  
	  System.out.println(Configuration.byDefault.toString());
	  
	  Assert.assertEquals(AdviceKind.AFTER, helperDefault.getAspectKind());
	  Assert.assertEquals(new Integer("2"), helperDefault.getInt1());
	  Assert.assertEquals(new Integer("3"), helperDefault.getInt2());
	  
	  Assert.assertEquals(helperDefault.getAspectKind(), helper1.getAspectKind());
	  Assert.assertEquals(helper1.getInt1(), helper1.getInt1());
	  Assert.assertEquals(new Integer("4"), helper1.getInt2());
	  System.out.println(test1.toString());
	  
	  Assert.assertEquals(AdviceKind.BEFORE,  helper2.getAspectKind());
	  Assert.assertEquals(new Integer("5"), helper2.getInt1());
	  Assert.assertEquals(new Integer("6"), helper2.getInt2());
	  System.out.println(test2.toString());
	  
  }
  
  @BeforeTest
  public void beforeTest() {
	  test1 = Configuration.get("src/test/resources/test1.json");
	  test2 = Configuration.get("src/test/resources/test2.json");
  }

}
