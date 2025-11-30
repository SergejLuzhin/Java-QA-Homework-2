package helpers;

import io.qameta.allure.Step;

/**
 * Вспомогательный класс-обёртка над JUnit Assertions.
 * Добавляет шаг в Allure-отчёт при выполнении проверок,
 * для удобства логирования проверок
 *
 * @author Сергей Лужин
 */
public class Assertions {

    /**
     * Собственный ассерт, вызывающий метод assertTrue из JUnit,
     * с добавленной аннотацией шага
     *
     * @param condition логическое условие, которое должно быть истинным
     * @param message   сообщение об ошибке при провале проверки
     *
     * @author Сергей Лужин
     */
    @Step("Проверяем, что нет ошибки: '{message}'")
    public static void assertTrue(boolean condition, String message) {
        org.junit.jupiter.api.Assertions.assertTrue(condition,message);
    }
}
