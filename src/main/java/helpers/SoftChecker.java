package helpers;

import entity.Product;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;

import java.util.List;

public class SoftChecker {

    @Step("{displayName}")
    public static void checkProductFound(boolean condition, Product expected, String displayName, String failMessage) {
        if (!condition) {
            Allure.step(failMessage);
        }

        Assertions.assertTrue(
                condition
        );
    }

    @Step("Проверяем, что найденные товару соответствуют фильтру цен")
    public static void checkPriceFilter(List<String> wrongTitles, int minPrice, int maxPrice) {
        Assertions.assertTrue(
                wrongTitles.isEmpty(),
                "Были найдены товары не соответствующие фильтрам цен: " + wrongTitles
        );
    }

    @Step("Проверяем соответствие брендам")
    public static void checkBrandFilter(List<String> wrongTitles, List<String> brands) {
        if (!wrongTitles.isEmpty()) {
            Allure.step("Ошибка: Были найдены товары не соответствующие фильтрам брендов: " + wrongTitles);
        }

        Assertions.assertTrue(
                wrongTitles.isEmpty(),
                "Были найдены товары не соответствующие фильтрам брендов: " + wrongTitles
        );
    }

    @Step("Проверяем количество найденных товаров: {actual} > {expected}")
    public static void checkProductsCount(int actual, int expected) {
        Assertions.assertTrue(
                actual > expected,
                "Было найдено товаров меньше необходимого количества. Было найдено только " + actual
        );
    }
}

