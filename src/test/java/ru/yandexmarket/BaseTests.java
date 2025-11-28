package ru.yandexmarket;

import helpers.DriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.TimeUnit;

import static helpers.Properties.testProperties;

public class BaseTests {
    protected WebDriver driver;

    @BeforeEach
    public void before() {
        //System.setProperty("webdriver.chrome.driver", testProperties.driverChromeWindows());
       // System.setProperty("webdriver.chrome.driver", "C:\\Files\\WebDrivers\\chromedriver.exe");

        //DesiredCapabilities capabilities = new DesiredCapabilities();
        //capabilities.setCapability(CapabilityType.PAGE_LOAD_STRATEGY,"none");

        //driver = new ChromeDriver(capabilities);

        driver = DriverFactory.create();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(testProperties.defaultTimeout(), TimeUnit.SECONDS);
    }

    @AfterEach
    public void after() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        driver.quit();
    }
}
