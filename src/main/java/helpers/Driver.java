package helpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import java.util.concurrent.TimeUnit;

import static helpers.Properties.testProperties;


public class Driver {

    private static WebDriver webDriver;

    public static WebDriver getWebDriver () {
        return webDriver;
    }

    public static void create() {
        System.setProperty("webdriver.chrome.driver", testProperties.driverChrome());

        WebDriver base = new ChromeDriver();

        EventFiringWebDriver driver = new EventFiringWebDriver(base);
        driver.register(new AllureScreenshotListener());

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(testProperties.defaultTimeout(), TimeUnit.SECONDS);

        webDriver = driver;
    }
}

