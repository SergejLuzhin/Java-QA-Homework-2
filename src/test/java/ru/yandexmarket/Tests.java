package ru.yandexmarket;

import helpers.Assertions;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static steps.Steps.*;
import static helpers.Properties.testProperties;

public class Tests extends BaseTests {

    @Feature("Проверка каталога яндекс маркета")
    @DisplayName("Проверка каталога Ноутбуков")
    @Test
    public void testYandexMarketCatalog(){
        openSite(testProperties.yandexMarketUrl(), driver);
        chooseCategory("Электроника", "Ноутбуки");
        String title = driver.getTitle();
        Assertions.assertTrue(title.contains("Ноутбуки"), "Тайтл " + title + " на сайте не соответствует категории " + "Ноутбуки");
        setFilters(10000, 20000);
        checkCardsOnPage(1);
    }

    /*@Test
    public void secondTest(){
        driver.get(testProperties.bellIntegratorUrl());
        String title = driver.getTitle();
        System.out.println(title);
        Assertions.assertTrue(title.contains("Bell Integrator"),"Тайтл "+title+" на сайте не содержит Bell Integrator");
    }*/


}
