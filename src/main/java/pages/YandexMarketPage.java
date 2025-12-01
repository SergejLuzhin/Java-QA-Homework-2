package pages;

import entity.Product;
import helpers.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.Keys.ENTER;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

import static helpers.Properties.testProperties;
import static helpers.Properties.xpathProperties;

/**
 * Page Object для работы со страницами Яндекс Маркета.
 * Инкапсулирует общие элементы и действия: поиск, работа с каталогом,
 * фильтрами и карточками товаров.
 *
 * Использует WebDriver, получаемый из {@link Driver#getWebDriver()}.
 *
 * @author Сергей Лужин
 */
public class YandexMarketPage {

    /**
     * Коллекция товаров, отображённых на текущей странице.
     * Заполняется на основе найденных карточек товаров.
     */
    public List<Product> productsOnPage;

    /**
     * Экземпляр WebDriver, используемый для взаимодействия со страницей.
     */
    protected WebDriver driver;

    /**
     * Веб-элемент поля ввода поискового запроса на странице Яндекс Маркета.
     */
    protected WebElement searchInput;

    /**
     * Веб-элемент кнопки запуска поиска по введённому запросу.
     */
    protected WebElement searchButton;

    /**
     * Веб-элемент кнопки запуска поиска по введённому запросу.
     */
    protected WebElement catalogButton;

    protected WebDriverWait wait;

    /**
     * Конструктор инициализирует элементы страницы,
     * ожидая появления ключевых элементов поиска и каталога.
     *
     *
     * @author Сергей Лужин
     */
    public YandexMarketPage() {
        this.driver = Driver.getWebDriver();
        this.wait = new WebDriverWait(driver, testProperties.defaultTimeout());

        this.searchInput = driver.findElement(By.xpath(xpathProperties.ymSearchInputXpath()));

        this.searchButton = driver.findElement(By.xpath(xpathProperties.ymSearchButtonXpath()));

        this.catalogButton = driver.findElement(By.xpath(xpathProperties.ymCatalogButtonXpath()));

        this.productsOnPage = new ArrayList<>();
    }

    /**
     * Выполняет поиск товара по текстовому запросу.
     *
     * @param query строка запроса для поиска
     *
     * @author Сергей Лужин
     */
    public void findViaSearchInput(String query) {
        searchInput = Driver.getWebDriver().findElement(By.xpath(xpathProperties.ymSearchInputXpath()));
        searchInput.sendKeys(query);
        searchInput.sendKeys(ENTER);
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
        }
    }

    /**
     * Возвращает список веб-элементов карточек товаров на текущей странице.
     *
     * @return список WebElement карточек товаров
     *
     * @author Сергей Лужин
     */
    public List<WebElement> getAllProductCardsOnPage() {
        return driver.findElements(By.xpath(xpathProperties.ymCardsOnAllPagesXpath()));
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
     * Возвращает числовое значение цены товара из карточки.
     * Очищает текст от пробелов и нецифровых символов перед преобразованием.
     *
     * @param element веб-элемент карточки товара
     * @return цена товара в виде целого числа
     *
     * @author Сергей Лужин
     */
    public int getProductCardPrice(WebElement element) {
        return Integer.parseInt(element.findElement(By.xpath(xpathProperties.ymCardPriceAddonXpath())).getText()
                .replaceAll("[\\s\\u00A0\\u2006\\u2007\\u2008\\u2009\\u200A]", "")
                .replaceAll("[^\\d]", ""));
    }
}
