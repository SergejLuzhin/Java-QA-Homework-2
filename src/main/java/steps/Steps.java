package steps;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.YandexMarketBasePage;

import java.util.ArrayList;
import java.util.List;

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

    @Step("Выбираем категорию '{category}' в каталоге")
    public static void chooseCategory(String category, String subcategory) {
        YandexMarketBasePage yandexMarketBasePage = new YandexMarketBasePage(driver);
        yandexMarketBasePage.clickOnCatalogButton();
        yandexMarketBasePage.hoverOnCategoryInCatalog(category);
        yandexMarketBasePage.clickOnSubcategoryInCatalog(subcategory);
    }

    @Step("Устанавливаем фильтры поиска: Минимальная цена - {minPrice}, Максимальная цена - {maxPrice}")
    public static void setFilters(int minPrice, int maxPrice) {
        YandexMarketBasePage yandexMarketBasePage = new YandexMarketBasePage(driver);
        yandexMarketBasePage.setFilterPriceMin(minPrice);
        yandexMarketBasePage.setFilterPriceMax(maxPrice);

        List<String> brands = new ArrayList<>();
        brands.add("Lenovo");
        brands.add("HP");

        yandexMarketBasePage.clickBrandCheckbox(brands);
    }

    @Step("Считаем карточки на всех страницах")
    public static void checkCardsOnPage(int pageNumber) {
        YandexMarketBasePage yandexMarketBasePage = new YandexMarketBasePage(driver);
        yandexMarketBasePage.cardsOnAllPagesCount();
    }

}
