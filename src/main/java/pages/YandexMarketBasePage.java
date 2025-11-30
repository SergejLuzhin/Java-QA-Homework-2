package pages;

import helpers.Screenshoter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

import static helpers.Properties.testProperties;
import static helpers.Properties.xpathProperties;

/**
 * Базовая страница Яндекс Маркета.
 * Содержит общие элементы, действия и вспомогательные методы,
 * необходимые для взаимодействия с интерфейсом каталога:
 * поиск, навигация по категориям, работа с фильтрами,
 * прокрутка страницы, получение карточек товаров и т.д.
 *
 * Все Page Object классы могут наследоваться от этого класса
 * или использовать его методы через композицию.
 *
 * @author Сергей Лужин
 */
public class YandexMarketBasePage {

    /**
     * Экземпляр WebDriver, используемый для работы со страницей.
     *
     * @author Сергей Лужин
     */
    protected WebDriver driver;

    /**
     * Поле ввода поискового запроса.
     *
     * @author Сергей Лужин
     */
    protected WebElement searchInput;


    /**
     * Кнопка запуска поиска.
     *
     * @author Сергей Лужин
     */
    protected WebElement searchButton;

    /**
     * Кнопка открытия каталога.
     *
     * @author Сергей Лужин
     */
    protected WebElement catalogButton;

    /**
     * Объект явных ожиданий WebDriver.
     *
     * @author Сергей Лужин
     */
    protected WebDriverWait wait;

    /**
     * Конструктор инициализирует элементы страницы,
     * ожидая появления ключевых элементов поиска и каталога.
     *
     * @param driver экземпляр WebDriver, используемый для работы со страницей
     *
     * @author Сергей Лужин
     */
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

    /**
     * Выполняет поиск товара по текстовому запросу.
     *
     * @param query строка запроса для поиска
     *
     * @author Сергей Лужин
     */
    public void find(String query) {
        searchInput.click();
        searchInput.sendKeys(query);
        searchButton.click();
    }

    /**
     * Нажимает кнопку каталога.
     *
     * @author Сергей Лужин
     */
    public void clickOnCatalogButton() {
        wait.until(visibilityOfElementLocated(By.xpath(xpathProperties.ymCatalogButtonXpath())));
        catalogButton.click();
    }

    /**
     * Наводит курсор на категорию каталога.
     *
     * @param category название категории
     *
     * @author Сергей Лужин
     */
    public void hoverOnCategoryInCatalog(String category) {
        String xpath = xpathProperties.ymCatalogCategoryXpath().replace("*category*", category);

        WebElement categoryElement = wait.until(
                visibilityOfElementLocated(By.xpath(xpath))
        );

        Actions actions = new Actions(driver);
        actions.moveToElement(categoryElement).perform();
    }

    /**
     * Кликает по подкатегории каталога.
     *
     * @param subcategory название подкатегории
     *
     * @author Сергей Лужин
     */
    public void clickOnSubcategoryInCatalog(String subcategory) {
        String xpath = xpathProperties.ymCatalogSubcategoryXpath().replace("*subcategory*", subcategory);

        WebElement subcategoryElement = wait.until(
                visibilityOfElementLocated(By.xpath(xpath))
        );

        subcategoryElement.click();
    }

    /**
     * Устанавливает минимальную цену в фильтре товаров.
     *
     * @param price минимальная цена
     * @author Сергей Лужин
     */
    public void setFilterPriceMin(int price) {
        String xpath = xpathProperties.ymFilterPriceMinXpath();

        WebElement inputFilterPriceMin = wait.until(
                visibilityOfElementLocated(By.xpath(xpath))
        );

        inputFilterPriceMin.sendKeys(Integer.toString(price));

        //ждем прогрузки первой карточки товара, чтобы продолжить
        wait.until(visibilityOfElementLocated(By.xpath("(" + xpathProperties.ymCardsOnAllPagesXpath() + ")[1]")));
    }

    /**
     * Устанавливает максимальную цену в фильтре товаров.
     *
     * @param price максимальная цена
     * @author Сергей Лужин
     */
    public void setFilterPriceMax(int price) {
        String xpath = xpathProperties.ymFilterPriceMaxXpath();

        WebElement inputFilterPriceMax = wait.until(
                visibilityOfElementLocated(By.xpath(xpath))
        );

        inputFilterPriceMax.sendKeys(Integer.toString(price));

        //ждем прогрузки первой карточки товара, чтобы продолжить
        wait.until(visibilityOfElementLocated(By.xpath("(" + xpathProperties.ymCardsOnAllPagesXpath() + ")[1]")));
    }

    /**
     * Устанавливает фильтры брендов, кликая по каждому бренду в списке.
     *
     * @param brands список брендов для фильтрации
     *
     * @author Сергей Лужин
     */
    public void clickBrandCheckbox(List<String> brands) {
        for (String brand : brands) {
            String xpath = xpathProperties.ymFilterBrandXpath().replace("*brand*", brand);

            WebElement brandFilterElement = wait.until(
                    visibilityOfElementLocated(By.xpath(xpath))
            );

            brandFilterElement.click();

            //ждем прогрузки первой карточки товара, чтобы продолжить
            wait.until(visibilityOfElementLocated(By.xpath("(" + xpathProperties.ymCardsOnAllPagesXpath() + ")[1]")));
        }
    }

    /**
     * Возвращает список карточек товаров на текущей странице.
     *
     * @return список WebElement карточек товаров
     *
     * @author Сергей Лужин
     */
    public List<WebElement> getAllProductCardsOnPage() {
        return driver.findElements(By.xpath(xpathProperties.ymCardsOnAllPagesXpath()));
    }

    /**
     * Плавно прокручивает страницу до самого низа,
     * или до максимального количества шагов,
     * фиксируя страницу скриншотами.
     *
     * @author Сергей Лужин
     */
    public void scrollToBottomOfPage() {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        int scrollStep = 600;          // на сколько пикселей скроллим за шаг
        long pauseMs = 400;            // «плавная» пауза между шагами
        int maxSteps = 50;             // защита от бесконечного цикла

        for (int i = 0; i < maxSteps; i++) {
            Screenshoter.attachScreenshot("Прокрутка страницы вниз", driver);

            // скроллим вниз на scrollStep пикселей
            js.executeScript("window.scrollBy(0, arguments[0]);", scrollStep);

            long targetTime = System.currentTimeMillis() + pauseMs;
            new FluentWait<>(driver)
                    .withTimeout(Duration.ofMillis(pauseMs + 50))
                    .pollingEvery(Duration.ofMillis(50))
                    .ignoring(Exception.class)
                    .until(d -> System.currentTimeMillis() >= targetTime);

            // проверяем, дошли ли до низа страницы
            long offset = ((Number) js.executeScript("return window.pageYOffset;")).longValue();      // текущая вертикальная позиция
            long windowHeight = ((Number) js.executeScript("return window.innerHeight;")).longValue(); // видимая высота окна
            long docHeight = ((Number) js.executeScript("return document.body.scrollHeight;")).longValue(); // общая высота страницы

            if (offset + windowHeight >= docHeight) {
                break;
            }
        }
    }

    /**
     * Плавно прокручивает страницу до самого верха,
     * или до максимального количества шагов,
     * (как правило скролла вверх должно всегда хватать,
     * так как он быстрее, чем скролл вниз)
     * фиксируя страницу скриншотами.
     *
     * @author Сергей Лужин
     */
    public void scrollToTopOfPage() {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        int scrollStep = 1000;          // на сколько пикселей скроллим за шаг
        long pauseMs = 200;            // «плавная» пауза между шагами
        int maxSteps = 50;             // защита от бесконечного цикла

        for (int i = 0; i < maxSteps; i++) {
            Screenshoter.attachScreenshot("Прокрутка страницы вверх", driver);

            // скроллим вверх на scrollStep пикселей (отрицательное значение)
            js.executeScript("window.scrollBy(0, arguments[0]);", -scrollStep);

            long targetTime = System.currentTimeMillis() + pauseMs;
            new FluentWait<>(driver)
                    .withTimeout(Duration.ofMillis(pauseMs + 50))
                    .pollingEvery(Duration.ofMillis(50))
                    .ignoring(Exception.class)
                    .until(d -> System.currentTimeMillis() >= targetTime);

            // проверяем, дошли ли до верха страницы
            long offset = ((Number) js.executeScript("return window.pageYOffset;")).longValue(); // текущая вертикальная позиция скролла

            if (offset <= 0) {
                break; // уже в самом верху — выходим
            }
        }
    }

    /**
     * Прокручивает страницу к указанному элементу.
     *
     * @param element элемент, к которому выполняется прокрутка
     *
     * @author Сергей Лужин
     */
    public void goToElementOnPage(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior:'instant', block:'center'});", element);
    }

    /**
     * Возвращает текст заголовка карточки товара.
     *
     * @param element веб-элемент карточки товара
     * @return название товара
     *
     * @author Сергей Лужин
     */
    public String getProductCardTitle(WebElement element){
        WebElement titleElement = element.findElement(By.xpath(xpathProperties.ymCardTitleAddonXpath()));
        return titleElement.getText();
    }

    /**
     * Получает названия всех товаров из списка карточек.
     *
     * @param productCards список веб-элементов карточек товаров
     * @return список названий товаров
     *
     * @author Сергей Лужин
     */
    public List<String> getAllProductCardTitlesFromList(List<WebElement> productCards) {
        List<String> productTitles = new ArrayList<>();

        for (WebElement productCard : productCards) {
            productTitles.add(getProductCardTitle(productCard));
        }

        return productTitles;
    }
}
