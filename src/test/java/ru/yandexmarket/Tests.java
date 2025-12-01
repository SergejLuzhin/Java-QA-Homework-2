package ru.yandexmarket;

import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.YandexMarketBasePage;


import java.util.List;

import static steps.YandexMarketSteps.*;
import static helpers.Properties.testProperties;

public class Tests extends BaseTests {


    @Feature("Проверка каталога яндекс маркета")
    @DisplayName("Проверка каталога по заданным параметрам: ")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("helpers.DataProvider#providerYMtestCatalog")
    public void testYandexMarketCatalog(String category, String subcategory, int minPrice, int maxPrice, List<String> brands, int indexOfCheckedElement, int checkedProductsAmount){
        openSite(testProperties.yandexMarketUrl());
        YandexMarketBasePage yandexMarketBeforeSearch = new YandexMarketBasePage();
        chooseCategory(category, subcategory, yandexMarketBeforeSearch);
        checkPageTitle(subcategory);
        setFilters(minPrice, maxPrice, brands, yandexMarketBeforeSearch);
        getAllProductCards(yandexMarketBeforeSearch);
        goBySearchQuery(yandexMarketBeforeSearch.productsOnPage.get(indexOfCheckedElement).getTitle(), yandexMarketBeforeSearch);
        YandexMarketBasePage yandexMarketAfterSearch = new YandexMarketBasePage();
        getAllProductCards(yandexMarketAfterSearch);
        runChecksSoftly(yandexMarketBeforeSearch, yandexMarketAfterSearch, checkedProductsAmount,minPrice, maxPrice, brands, indexOfCheckedElement);
    }

}
