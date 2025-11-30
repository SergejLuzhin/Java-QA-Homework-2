package helpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.util.concurrent.TimeUnit;

import static helpers.Properties.testProperties;

/**
 * Класс для централизованного создания и хранения экземпляра WebDriver.
 * Создаёт ChromeDriver, оборачивает его в EventFiringWebDriver
 * и настраивает окно и неявные ожидания.
 *
 * Экземпляр драйвера сохраняется в статическом поле webDriver,
 * чтобы его можно было использовать в разных частях тестового проекта.
 *
 * @author Сергей Лужин
 */
public class Driver {
    /**
     * Общий экземпляр WebDriver, доступный во всём проекте.
     * Инициализируется методом create().
     *
     * @author Сергей Лужин
     */
    public static WebDriver webDriver;

    /**
     * Создаёт и настраивает экземпляр WebDriver для дальнейшего использования.
     * Устанавливает системное свойство пути к ChromeDriver,
     * создаёт базовый ChromeDriver, оборачивает его в EventFiringWebDriver
     * с регистрацией слушателя AllureScreenshotListener,
     * разворачивает окно на весь экран и задаёт неявное ожидание.
     *
     * Результат сохраняется в статическом поле webDriver.
     *
     * @author Сергей Лужин
     */
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

