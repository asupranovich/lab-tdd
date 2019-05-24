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
        System.setProperty("webdriver.gecko.driver", "d:\\temp\\ffdriver\\geckodriver.exe");
    }

    @Test
    public void testSite() {
        open("http://www.itechart.com");
        $$(".join-our-team").shouldHaveSize(1);

        $(".join-our-team").click();

        $(".content-main").shouldHave(text("JAVA SENIOR SOFTWARE DEVELOPER"));
//		$(".content-main").shouldHave(text("PYTHON SENIOR SOFTWARE DEVELOPER"));
    }

}
