package helpers;

import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import java.lang.reflect.Proxy;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Динамический прокси для WebElement, который:
 * перехватывает все вызовы методов элемента,
 * делает скриншот страницы,
 * прикрепляет скриншот к отчёту Allure,
 * и только потом вызывает реальный метод у исходного WebElement.
 */
public class ElementScreenshotProxy implements InvocationHandler {

    /**
     * Реальный элемент, с которым Selenium взаимодействует на странице.
     */
    private final WebElement element;

    /**
     * Драйвер браузера, используемый для снятия скриншотов.
     */
    private final WebDriver driver;

    /**
     * Создаёт обработчик вызовов для прокси WebElement.
     *
     * @param element реальный WebElement, над которым будут выполняться действия
     * @param driver  WebDriver, через который можно сделать скриншот страницы
     */
    public ElementScreenshotProxy(WebElement element, WebDriver driver) {
        this.element = element;
        this.driver = driver;
    }

    /**
     * Метод вызывается при каждом обращении к любому методу прокси-объекта WebElement.
     *
     * @param proxy  сам прокси-объект (обычно не используется)
     * @param method отражение (reflection) метода, который был вызван (click, sendKeys и т.д.)
     * @param args   аргументы, с которыми был вызван метод
     * @return результат выполнения реального метода на исходном WebElement
     * @throws Throwable если реальный метод выбросит исключение
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // Перед каждым действием с элементом делаем скриншот и прикрепляем его к Allure.
        attachScreenshot(method.getName());

        // Вызываем тот же самый метод на реальном WebElement с теми же аргументами.
        return method.invoke(element, args);
    }

    /**
     * Делает скриншот текущего состояния страницы и добавляет его
     * в отчёт Allure как вложение.
     *
     * @param action название действия/метода (например: "click", "sendKeys")
     */
    private void attachScreenshot(String action) {
        try {
            // Получаем скриншот как массив байтов
            byte[] screenshot =
                    ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

            // Добавляем вложение в Allure-отчёт
            Allure.getLifecycle().addAttachment(
                    "Действие: " + action, // заголовок вложения
                    "image/png",         // MIME-тип
                    "png",               // расширение файла
                    screenshot           // содержимое
            );

        } catch (Exception ignore) {
            // Здесь намеренно игнорируем ошибки, чтобы не ронять тесты
            // из-за возможных проблем со скриншотами.
        }
    }

    /**
     * Оборачивает реальный WebElement в динамический прокси.
     * Возвращаемый объект реализует интерфейс WebElement, но
     * все его методы проходят через {@link ElementScreenshotProxy}.
     *
     * @param element реальный WebElement, который нужно "обернуть"
     * @param driver  WebDriver, используемый для снятия скриншотов
     * @return прокси-объект WebElement с автоматическими скриншотами
     */
    public static WebElement wrap(WebElement element, WebDriver driver) {
        return (WebElement) Proxy.newProxyInstance(
                element.getClass().getClassLoader(), // загрузчик классов
                new Class[]{WebElement.class},       // интерфейсы, которые реализует прокси
                new ElementScreenshotProxy(element, driver) // обработчик вызовов
        );
    }
}