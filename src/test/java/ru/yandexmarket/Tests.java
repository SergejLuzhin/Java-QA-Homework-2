package ru.yandexmarket;

import io.qameta.allure.Feature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static steps.Steps.*;
import static helpers.Properties.testProperties;

public class Tests extends BaseTests {

    @Feature("Проверка тайтла")
    @DisplayName("Проверка тайтла сайта")
    @Test
    public void firstTest(){
        openSite(testProperties.yandexMarketUrl(), driver);
        chooseCategoryInCatalog("Электроника");
    }

    @Test
    public void secondTest(){
        driver.get(testProperties.bellIntegratorUrl());
        String title = driver.getTitle();
        System.out.println(title);
        Assertions.assertTrue(title.contains("Bell Integrator"),"Тайтл "+title+" на сайте не содержит Bell Integrator");
    }


}
