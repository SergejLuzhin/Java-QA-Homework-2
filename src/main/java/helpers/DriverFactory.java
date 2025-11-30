package helpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import static helpers.Properties.testProperties;

/**
 * Фабрика для создания и настройки экземпляра WebDriver.
 * Отвечает за указание пути к ChromeDriver, создание базового драйвера
 * и обёртку его в EventFiringWebDriver с регистрацией слушателя
 * для снятия скриншотов в Allure.
 *
 * @author Сергей Лужин
 */
public class DriverFactory {

    /**
     * Создаёт и настраивает экземпляр WebDriver для использования в тестах.
     * Устанавливает системное свойство пути к драйверу Chrome, создаёт
     * базовый ChromeDriver, оборачивает его в EventFiringWebDriver и
     * регистрирует {@link AllureScreenshotListener} для автоматического
     * прикрепления скриншотов к отчётам Allure.
     *
     * @return настроенный экземпляр WebDriver
     *
     * @author Сергей Лужин
     */
    public static WebDriver create() {
        // Указываем путь к драйверу
        System.setProperty("webdriver.chrome.driver", testProperties.driverChrome());

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

