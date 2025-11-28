package helpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import static helpers.Properties.testProperties;

public class DriverFactory {

    public static WebDriver create() {
        // Указываем путь к драйверу
        System.setProperty("webdriver.chrome.driver", testProperties.driverChromeWindows());

        // задаем параметры
        // DesiredCapabilities capabilities = new DesiredCapabilities();
        // capabilities.setCapability(CapabilityType.PAGE_LOAD_STRATEGY,"none");

        // Создаём драйвер
        WebDriver base = new ChromeDriver();

        EventFiringWebDriver driver = new EventFiringWebDriver(base);
        driver.register(new AllureScreenshotListener(driver));

        return driver;
    }
}

