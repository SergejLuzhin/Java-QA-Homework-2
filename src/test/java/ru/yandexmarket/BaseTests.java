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

/**
 * Базовый класс для UI-тестов.
 *
 * Отвечает за инициализацию перед каждым тестом и завершение работы WebDriver после каждого теста.
 * Все тестовые классы наследуются от данного класса, чтобы использовать
 * единые настройки драйвера, таймауты и логику закрытия браузера.
 *
 * @author Сергей Лужин
 */
public class BaseTests {

    /**
     * Экземпляр WebDriver, используемый во всех тестах, наследующих данный класс.
     *
     * @author Сергей Лужин
     */
    protected WebDriver driver;

    /**
     * Метод, выполняемый перед каждым тестом.
     *
     * Создаёт экземпляр WebDriver через {@link DriverFactory},
     * разворачивает окно браузера на весь экран и задаёт
     * неявное ожидание, заданное в тестовых настройках.
     *
     * @author Сергей Лужин
     */
    @BeforeEach
    public void before() {
        //DesiredCapabilities capabilities = new DesiredCapabilities();
        //capabilities.setCapability(CapabilityType.PAGE_LOAD_STRATEGY,"none");

        //driver = new ChromeDriver(capabilities);

        driver = DriverFactory.create();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(testProperties.defaultTimeout(), TimeUnit.SECONDS);
    }

    /**
     * Метод, выполняемый после каждого теста.
     *
     * Завершает работу WebDriver, закрывая браузер.
     *
     * @author Сергей Лужин
     */
    @AfterEach
    public void after() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        driver.quit();
    }
}
