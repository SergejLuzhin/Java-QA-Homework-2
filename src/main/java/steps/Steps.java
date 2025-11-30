package steps;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.YandexMarketBasePage;

import java.util.ArrayList;
import java.util.List;

import static helpers.Properties.testProperties;

/**
 * Класс с шагами для работы с интерфейсом Яндекс Маркета.
 * Используется в тестах как набор переиспользуемых действий:
 * открытие сайта, выбор категории, установка фильтров и работа с карточками товаров.
 *
 * @author Сергей Лужин
 */
public class Steps {
    /**
     * Текущий экземпляр WebDriver, используемый в шагах.
     * Инициализируется при открытии сайта в методе {@link #openSite(String, WebDriver)}.
     *
     * @author Сергей Лужин
     */
    private static WebDriver driver;


    /**
     * Открывает указанный URL в переданном экземпляре WebDriver
     * и сохраняет этот драйвер во внутреннее статическое поле.
     *
     * @param url           адрес сайта, который нужно открыть
     * @param currentDriver экземпляр WebDriver, в котором будет открыт сайт
     *
     * @author Сергей Лужин
     */
    @Step("Переходим на сайт: {url}")
    public static void openSite(String url, WebDriver currentDriver){
        driver = currentDriver;
        driver.get(url);
        //WebDriverWait wait = new WebDriverWait(driver, testProperties.defaultTimeout());
    }

    /**
     * Переходит в каталог Яндекс Маркета, выбирает указанную категорию
     * и подкатегорию.
     *
     * @param category    название категории каталога
     * @param subcategory название подкатегории каталога
     *
     * @author Сергей Лужин
     */
    @Step("Выбираем категорию '{category}' и подкатегорию {subcategory} в каталоге")
    public static void chooseCategory(String category, String subcategory) {
        YandexMarketBasePage yandexMarketBasePage = new YandexMarketBasePage(driver);
        yandexMarketBasePage.clickOnCatalogButton();
        yandexMarketBasePage.hoverOnCategoryInCatalog(category);
        yandexMarketBasePage.clickOnSubcategoryInCatalog(subcategory);
    }

    /**
     * Устанавливает фильтры поиска по цене и брендам на странице каталога.
     *
     * @param minPrice минимальная цена фильтра
     * @param maxPrice максимальная цена фильтра
     * @param brands   список брендов, по которым нужно отфильтровать товары
     *
     * @author Сергей Лужин
     */
    @Step("Устанавливаем фильтры поиска: Минимальная цена - {minPrice}, Максимальная цена - {maxPrice}, Бренды - {brands}")
    public static void setFilters(int minPrice, int maxPrice, List<String> brands) {
        YandexMarketBasePage yandexMarketBasePage = new YandexMarketBasePage(driver);
        yandexMarketBasePage.setFilterPriceMin(minPrice);
        yandexMarketBasePage.setFilterPriceMax(maxPrice);
        yandexMarketBasePage.clickBrandCheckbox(brands);
    }

    /**
     * Прокручивает страницу до конца вниз (или до истечения таймаута) и обратно вверх,
     * после чего возвращает список всех карточек товаров на странице.
     *
     * @return список WebElement, представляющих карточки товаров
     *
     * @author Сергей Лужин
     */
    @Step("Получаем список всех карточек товаров на странице")
    public static List<WebElement> getAllProductCards() {
        YandexMarketBasePage yandexMarketBasePage = new YandexMarketBasePage(driver);
        yandexMarketBasePage.scrollToBottomOfPage();
        yandexMarketBasePage.scrollToTopOfPage();
        return yandexMarketBasePage.getAllProductCardsOnPage();
    }

    /**
     * Переходит к карточке товара с указанным номером в списке
     * и возвращает название этого товара.
     *
     * @param elementList   список карточек товаров
     * @param elementNumber индекс товара в списке, название которого нужно получить
     * @return название выбранного товара
     *
     * @author Сергей Лужин
     */
    @Step("Возвращаемся в начало списка и сохраняем название товара под номером {elementNumber} на странице")
    public static String getProductName(List<WebElement> elementList, int elementNumber){
        YandexMarketBasePage yandexMarketBasePage = new YandexMarketBasePage(driver);
        yandexMarketBasePage.goToElementOnPage(elementList.get(elementNumber));
        return yandexMarketBasePage.getProductCardTitle(elementList.get(elementNumber));
    }

    /**
     * Выполняет поиск товара на Яндекс Маркете по переданному текстовому запросу.
     * Перед поиском прокручивает страницу к началу,
     * это необходимо для того, чтобы поле поиска корректно работало
     *
     * @param query поисковый запрос, по которому нужно найти товары
     *
     * @author Сергей Лужин
     */
    @Step("Делаем поиск на Яндекс Маркете по запросу: {query}")
    public static void goBySearchQuery(String query) {
        YandexMarketBasePage yandexMarketBasePage = new YandexMarketBasePage(driver);
        yandexMarketBasePage.scrollToTopOfPage();
        yandexMarketBasePage.find(query);
    }

    /**
     * Прокручивает страницу вниз и вверх, получает все карточки товаров на странице
     * и возвращает список их названий.
     *
     * @return список названий всех товаров на текущей странице
     *
     * @author Сергей Лужин
     */
    @Step("Получаем названия всех товаров на странице")
    public static List<String> getAllProductsTitles() {
        YandexMarketBasePage yandexMarketBasePage = new YandexMarketBasePage(driver);
        yandexMarketBasePage.scrollToBottomOfPage();
        yandexMarketBasePage.scrollToTopOfPage();
        List<WebElement> productCardsOnPage = yandexMarketBasePage.getAllProductCardsOnPage();
        return yandexMarketBasePage.getAllProductCardTitlesFromList(productCardsOnPage);
    }

}
