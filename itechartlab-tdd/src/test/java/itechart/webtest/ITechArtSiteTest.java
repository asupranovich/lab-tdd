package itechart.webtest;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

@Ignore
public class ITechArtSiteTest {

    @BeforeClass
    public static void setUp() {
        System.setProperty("webdriver.gecko.driver", "d:\\sashino\\temp\\drivers\\geckodriver.exe");
//        System.setProperty("webdriver.chrome.driver", "d:\\sashino\\temp\\drivers\\chromedriver.exe");
    }

    @Test
    public void testSite() {
        open("http://www.itechart.com");
        $$("a[href='/careers/']").shouldHaveSize(1);

        $("a[href='/careers/']").click();

//        $(".positions").shouldHave(text("JAVA TEAM LEAD"));
	$(".positions").shouldHave(text("RUBY TEAM LEAD"));
    }

}
