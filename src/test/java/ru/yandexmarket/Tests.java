package ru.yandexmarket;

import helpers.Driver;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.YandexMarketBasePage;


import java.util.List;

import static steps.YandexMarketSteps.*;
import static helpers.Properties.testProperties;

/**
 * Набор UI-тестов для проверки работы каталога Яндекс Маркета.
 *
 * Содержит параметризованный тест, который проверяет:
 * корректность перехода по категориям, применение фильтров
 * и поиск товара по его названию.
 *
 * @author Сергей Лужин
 */
public class Tests extends BaseTests {

    /**
     * Параметризованный тест проверки каталога Яндекс Маркета по заданным фильтрам.
     *
     * Алгоритм теста:
     * 1) Открывает главную страницу Яндекс Маркета.
     * 2) Переходит в указанную категорию и подкатегорию.
     * 3) Проверяет, что заголовок страницы содержит название подкатегории.
     * 4) Устанавливает фильтр по цене и брендам.
     * 5) Получает список карточек товаров и сохраняет одну по заданному индексу.
     * 6) Ищет сохраненный товар через строку поиска.
     * 7) Проверяет, что товар присутствует в результатах поиска.
     * 8) Проверяет, что общее количество найденных товаров после первого перехода по категории больше заданного порога.
     *
     * @param category              основная категория каталога
     * @param subcategory           подкатегория каталога
     * @param minPrice              минимальная цена фильтра
     * @param maxPrice              максимальная цена фильтра
     * @param brands                список брендов для фильтрации
     * @param checkedElementIndex   индекс проверяемого товара в списке карточек (который сохраняем)
     * @param checkedProductsAmount минимальное ожидаемое количество товаров в категории
     *
     * @author Сергей Лужин
     */
    @Feature("Проверка каталога яндекс маркета")
    @DisplayName("Проверка каталога по заданным параметрам: ")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("helpers.DataProvider#providerYMtestCatalog")
    public void testYandexMarketCatalog(String category, String subcategory, int minPrice, int maxPrice, List<String> brands, int checkedElementIndex, int checkedProductsAmount){
        openSite(testProperties.yandexMarketUrl());
        YandexMarketBasePage yandexMarketBasePage = new YandexMarketBasePage();
        chooseCategory(category, subcategory, yandexMarketBasePage);
        checkPageTitle(subcategory);
        setFilters(minPrice, maxPrice, brands, yandexMarketBasePage);
        List<WebElement> productCards = getAllProductCards(yandexMarketBasePage);
        String savedProductTitle = getProductName(productCards, checkedElementIndex, yandexMarketBasePage);
        System.out.println(Driver.getWebDriver().findElement(By.xpath("//div[contains(@data-auto, 'SerpList')]//div[contains(@data-apiary-widget-name, 'SnippetConstructor')]//div[contains(@data-auto-themename, 'listDetailed')]//span[contains(@data-auto, 'price')]/span")).getText());
        //
        goBySearchQuery(savedProductTitle, yandexMarketBasePage);
        checkSavedProduct(savedProductTitle, yandexMarketBasePage);
        softCheckProductsAmountOnPage(productCards, checkedProductsAmount);
    }


    @Test
    public void test() {
        System.out.println(Driver.getWebDriver().findElement(By.xpath("//div[contains(@data-auto, 'SerpList')]//div[contains(@data-apiary-widget-name, 'SnippetConstructor')]//div[contains(@data-auto-themename, 'listDetailed')]//span[contains(@data-auto, 'price')]")).getText());
    }

}
