package helpers;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;

public class SoftChecker {

    @Step("{displayName}")
    public static void check(boolean condition, String displayName, String failMessage) {
        if (!condition) {
            Allure.step(failMessage);
        }

        Assertions.assertTrue(
                condition
        );
    }
}

