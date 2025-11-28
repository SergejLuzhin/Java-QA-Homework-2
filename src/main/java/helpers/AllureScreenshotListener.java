package helpers;

import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;

import java.io.ByteArrayInputStream;

public class AllureScreenshotListener extends AbstractWebDriverEventListener {

    private final WebDriver driver;

    public AllureScreenshotListener(WebDriver driver) {
        this.driver = driver;
    }

    private void attachScreenshot(String name) {
        try {
            if (driver instanceof TakesScreenshot) {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                Allure.addAttachment(name, new ByteArrayInputStream(screenshot));
            }
        } catch (Exception ignored) {}
    }

    // Клик
    @Override
    public void afterClickOn(WebElement element, WebDriver driver) {
        attachScreenshot("Клик");
    }

    // sendKeys / изменение значения
    @Override
    public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keys) {
        attachScreenshot("Набор текста");
    }

    // Поиск элемента
   /* @Override
    public void afterFindBy(By by, WebElement element, WebDriver driver) {
        attachScreenshot("После поиска по: " + by.toString());
    }*/

    // Навигация
    @Override
    public void afterNavigateTo(String url, WebDriver driver) {
        attachScreenshot("Перереход по: " + url);
    }

    @Override
    public void onException(Throwable throwable, WebDriver driver) {
        attachScreenshot("Ошибка");
    }
}
