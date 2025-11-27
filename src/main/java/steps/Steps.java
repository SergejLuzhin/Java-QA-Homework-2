package steps;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.YandexMarketBasePage;

import static helpers.Properties.testProperties;

public class Steps {
    private static WebDriverWait wait;
    private static WebDriver driver;

    @Step("Переходим на сайт: {url}")
    public static void openSite(String url, WebDriver currentDriver){
        driver = currentDriver;
        driver.get(url);
        wait = new WebDriverWait(driver,testProperties.defaultTimeout());
    }

    @Step("Делаем поиск на Яндекс Маркете по запросу: {query}")
    public static void goBySearchQuery(String query) {
        YandexMarketBasePage yandexMarketBasePage = new YandexMarketBasePage(driver);
        yandexMarketBasePage.find(query);
    }

    @Step("Выбираем категорию в каталоге: {category}")
    public static void chooseCategoryInCatalog(String category) {
        YandexMarketBasePage yandexMarketBasePage = new YandexMarketBasePage(driver);
        yandexMarketBasePage.chooseCategoryInCatalog(category);
    }
}
