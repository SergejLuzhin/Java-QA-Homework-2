package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElementsLocatedBy;

import static helpers.Properties.testProperties;
import static helpers.Properties.xpathProperties;


public class YandexMarketBasePage {
    protected WebDriver driver;

    protected WebElement searchInput;

    protected WebElement searchButton;

    protected WebElement catalogButton;

    protected WebDriverWait wait;

    public YandexMarketBasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, testProperties.defaultTimeout());

        wait.until(visibilityOfElementLocated(By.xpath(xpathProperties.ymSearchInputXpath())));
        this.searchInput = driver.findElement(By.xpath(xpathProperties.ymSearchInputXpath()));

        this.searchButton = driver.findElement(By.xpath(xpathProperties.ymSearchButtonXpath()));

        this.catalogButton = wait.until(
                visibilityOfElementLocated(By.xpath(xpathProperties.ymCatalogButtonXpath()))
        );
    }

    public void find(String query) {
        searchInput.sendKeys(query);
        searchButton.click();
    }

    public void clickOnCatalogButton() {
        wait.until(visibilityOfElementLocated(By.xpath(xpathProperties.ymCatalogButtonXpath())));
        catalogButton.click();
    }

    public void hoverOnCategoryInCatalog(String category) {
        String xpath = xpathProperties.ymCatalogCategoryXpath().replace("*category*", category);

        WebElement categoryElement = wait.until(
                visibilityOfElementLocated(By.xpath(xpath))
        );

        Actions actions = new Actions(driver);
        actions.moveToElement(categoryElement).perform();
    }

    public void clickOnSubcategoryInCatalog(String subcategory) {
        String xpath = xpathProperties.ymCatalogSubcategoryXpath().replace("*subcategory*", subcategory);

        WebElement subcategoryElement = wait.until(
                visibilityOfElementLocated(By.xpath(xpath))
        );

        subcategoryElement.click();
    }

    public void setFilterPriceMin(int price) {
        String xpath = xpathProperties.ymFilterPriceMinXpath();

        WebElement inputFilterPriceMin = wait.until(
                visibilityOfElementLocated(By.xpath(xpath))
        );

        inputFilterPriceMin.sendKeys(Integer.toString(price));
    }

    public void setFilterPriceMax(int price) {
        String xpath = xpathProperties.ymFilterPriceMaxXpath();

        WebElement inputFilterPriceMax = wait.until(
                visibilityOfElementLocated(By.xpath(xpath))
        );

        inputFilterPriceMax.sendKeys(Integer.toString(price));
    }

    public void clickBrandCheckbox(List<String> brands) {
        for (String brand : brands) {
            String xpath = xpathProperties.ymFilterBrandXpath().replace("*brand*", brand);

            WebElement brandFilterElement = wait.until(
                    visibilityOfElementLocated(By.xpath(xpath))
            );

            brandFilterElement.click();
        }
    }

    public void cardsOnAllPagesCount_OLD() {
        String xpath = xpathProperties.ymCardsOnAllPagesXpath();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //WebDriverWait waitCards = new WebDriverWait(driver, 10);

        By cardsLocator = By.xpath(xpath);

        List <WebElement> cardsOnPage = driver.findElements(By.xpath(xpath));

        System.out.println("cards count: " + cardsOnPage.size());
    }

    public void cardsOnAllPagesCount() {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        By PRODUCT_CARDS = By.xpath(xpathProperties.ymCardsOnAllPagesXpath());

        long endTime = System.currentTimeMillis() + 60_000; // общий таймаут скролла 30 сек
        int previousCount = 0;

        List<WebElement> cards = new ArrayList<>();

        while (System.currentTimeMillis() < endTime) {
            // текущие карточки
            cards = driver.findElements(PRODUCT_CARDS);
            int currentCount = cards.size();
            System.out.println("Сейчас карточек: " + currentCount);

            // если новых больше не появилось — выходим
            if (currentCount == previousCount) {
                System.out.println("Новых карточек не появилось, выходим из цикла");
                break;
            }

            previousCount = currentCount;

            // скроллим в самый низ
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

            // даём странице время подгрузить товары
            try {
                Thread.sleep(20000); // 1 секунда, можно подправить
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        System.out.println("cards count: " + cards.size());
    }
}
