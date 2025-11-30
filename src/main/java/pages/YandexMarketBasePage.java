package pages;

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
     *
     * @author Сергей Лужин
     */
    public YandexMarketBasePage() {
        this.driver = Driver.getWebDriver();
        this.wait = new WebDriverWait(driver, testProperties.defaultTimeout());

        this.searchInput = driver.findElement(By.xpath(xpathProperties.ymSearchInputXpath()));

        this.searchButton = driver.findElement(By.xpath(xpathProperties.ymSearchButtonXpath()));

        this.catalogButton = driver.findElement(By.xpath(xpathProperties.ymCatalogButtonXpath()));
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

    public int getProductCardPrice(WebElement element) {
        return Integer.parseInt(element.findElement(By.xpath(xpathProperties.ymCardPriceAddonXpath())).getText());
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
