package steps;

import entity.Product;
import helpers.Driver;
import helpers.Scroller;
import helpers.SoftChecker;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import static org.junit.jupiter.api.Assertions.assertAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.YandexMarketBasePage;


import java.util.ArrayList;
import java.util.List;

/**
 * Класс с шагами для работы с интерфейсом Яндекс Маркета.
 * Используется в тестах как набор переиспользуемых действий:
 * открытие сайта, выбор категории, установка фильтров и работа с карточками товаров.
 *
 * @author Сергей Лужин
 */
public class YandexMarketSteps {

    /**
     * Открывает указанный URL в переданном экземпляре WebDriver
     * и сохраняет этот драйвер во внутреннее статическое поле.
     *
     * @param url           адрес сайта, который нужно открыть
     *
     * @author Сергей Лужин
     */
    @Step("Переходим на сайт: {url}")
    public static void openSite(String url){
        Driver.getWebDriver().get(url);
    }

    /**
     * Переходит в каталог Яндекс Маркета, выбирает указанную категорию
     * и подкатегорию.
     *
     * @param category    название категории каталога
     * @param subcategory название подкатегории каталога
     * @param ymPage      объект страницы Яндекс Маркета
     *
     * @author Сергей Лужин
     */
    @Step("Выбираем категорию '{category}' и подкатегорию {subcategory} в каталоге")
    public static void chooseCategory(String category, String subcategory, YandexMarketBasePage ymPage) {
        ymPage.clickOnCatalogButton();
        ymPage.hoverOnCategoryInCatalog(category);
        ymPage.clickOnSubcategoryInCatalog(subcategory);
    }

    /**
     * Устанавливает фильтры поиска по цене и брендам на странице каталога.
     *
     * @param minPrice минимальная цена фильтра
     * @param maxPrice максимальная цена фильтра
     * @param brands   список брендов, по которым нужно отфильтровать товары
     * @param ymPage   объект страницы Яндекс Маркета
     *
     * @author Сергей Лужин
     */
    @Step("Устанавливаем фильтры поиска: Минимальная цена - {minPrice}, Максимальная цена - {maxPrice}, Бренды - {brands}")
    public static void setFilters(int minPrice, int maxPrice, List<String> brands, YandexMarketBasePage ymPage) {
        ymPage.setFilterPriceMin(minPrice);
        ymPage.setFilterPriceMax(maxPrice);
        ymPage.clickBrandCheckbox(brands);
    }

    /**
     * Прокручивает страницу до конца вниз (или до истечения таймаута) и обратно вверх,
     * после чего возвращает список всех карточек товаров на странице.
     *
     * @param ymPage      объект страницы Яндекс Маркета
     * @return список WebElement, представляющих карточки товаров
     *
     * @author Сергей Лужин
     */
    @Step("Получаем список всех карточек товаров на странице")
    public static void getAllProductCards(YandexMarketBasePage ymPage) {
        Scroller.scrollToBottomOfPage();
        Scroller.scrollToTopOfPage();
        List<WebElement> productCards = ymPage.getAllProductCardsOnPage();

        for (WebElement productCard : productCards) {
            Product.saveProductFromElement(productCard, ymPage);
        }
    }

    /*@Step("Получаем список всех элементов товаров и проверяем все элементы на странице на соответсвие фильтрам")
    public static List<WebElement> checkAllProductCardsForFilters(YandexMarketBasePage ymPage, int minPrice, int maxPrice, List<String> brands) {
        List<WebElement> productCardsElements = new ArrayList<>();
        Scroller.scrollToBottomOfPage();
        Scroller.scrollToTopOfPage();
        productCardsElements = ymPage.getAllProductCardsOnPage();

        List<String> wrongPriceProductTitles = new ArrayList<>();
        List<String> wrongTitleProductTitles = new ArrayList<>();
        for (WebElement productCard : productCardsElements) {
            String productCardTitle = ymPage.getProductCardTitle(productCard);

            if (ymPage.getProductCardPrice(productCard) < minPrice || ymPage.getProductCardPrice(productCard) > maxPrice) {
                wrongPriceProductTitles.add(productCardTitle);
            }
            if (brands.stream().anyMatch(brand -> productCardTitle.contains(brand))) {
                wrongTitleProductTitles.add(productCardTitle);
            }
        }

        assertAll("Проверяем найденные карточки товаров на соответсвие и количесвто",
                () -> Assertions.assertTrue(productCardsElements.size()));
    }*/

    @Step("Получаем список всех элементов товаров и проверяем все элементы на странице на соответсвие фильтрам")
    public static void runChecksSoftly(YandexMarketBasePage yandexMarketBeforeSearch, YandexMarketBasePage yandexMarketAfterSearch, int checkedAmount, int minPrice, int maxPrice, List<String> brands, int indexOfCheckedElement) {
        List<String> wrongPriceProductTitles = new ArrayList<>();
        List<String> wrongTitleProductTitles = new ArrayList<>();
        for (Product product : yandexMarketBeforeSearch.productsOnPage) {

            if (product.getPrice() < minPrice || product.getPrice() > maxPrice) {
                wrongPriceProductTitles.add(product.getTitle());
            }
            if (!brands.stream().anyMatch(brand -> product.getTitle().contains(brand)) && !product.getTitle().isEmpty()) {
                wrongTitleProductTitles.add(product.getTitle());
            }
        }

        Product checkedProduct = yandexMarketBeforeSearch.productsOnPage.get(indexOfCheckedElement);

        boolean productIsFoundOnPage = yandexMarketAfterSearch.productsOnPage.stream()
                .anyMatch(p -> p.equals(checkedProduct));

        Assertions.assertAll(
                () -> SoftChecker.checkProductFound(productIsFoundOnPage, checkedProduct,
                        "Проверяем: что " + checkedProduct.getTitle() + " был найден на странице после поиска",
                        "Товар " + checkedProduct.getTitle() + " не был найден на странице после поиска"),
                () -> SoftChecker.checkPriceFilter(wrongPriceProductTitles, minPrice, maxPrice),
                () -> SoftChecker.checkBrandFilter(wrongTitleProductTitles, brands),
                () -> SoftChecker.checkProductsCount(yandexMarketBeforeSearch.productsOnPage.size(), checkedAmount)
        );
    }

    /**
     * Переходит к карточке товара с указанным номером в списке
     * и возвращает название этого товара.
     *
     * @param elementList   список карточек товаров
     * @param elementNumber индекс товара в списке, название которого нужно получить
     * @param ymPage      объект страницы Яндекс Маркета
     * @return название выбранного товара
     *
     * @author Сергей Лужин
     */
    /*@Step("Возвращаемся в начало списка и сохраняем название товара под номером {elementNumber} на странице")
    public static String getProductName(List<WebElement> elementList, int elementNumber, YandexMarketBasePage ymPage){
        Scroller.goToElementOnPage(elementList.get(elementNumber));
        return ymPage.getProductCardTitle(elementList.get(elementNumber));
    }*/

    /**
     * Выполняет поиск товара на Яндекс Маркете по переданному текстовому запросу.
     * Перед поиском прокручивает страницу к началу,
     * это необходимо для того, чтобы поле поиска корректно работало
     *
     * @param query поисковый запрос, по которому нужно найти товары
     * @param ymPage      объект страницы Яндекс Маркета
     *
     * @author Сергей Лужин
     */
    @Step("Делаем поиск на Яндекс Маркете по запросу: {query}")
    public static void goBySearchQuery(String query, YandexMarketBasePage ymPage) {
        Scroller.scrollToTopOfPage();
        ymPage.findViaSearchInput(query);
    }

    /**
     * Прокручивает страницу вниз и вверх, получает все карточки товаров на странице
     * и возвращает список их названий.
     *
     * @param ymPage      объект страницы Яндекс Маркета
     * @return список названий всех товаров на текущей странице
     *
     * @author Сергей Лужин
     */
   /* @Step("Получаем названия всех товаров на странице")
    public static List<String> getAllProductsTitles(YandexMarketBasePage ymPage) {
        Scroller.scrollToBottomOfPage();
        Scroller.scrollToTopOfPage();
        List<WebElement> productCardsOnPage = ymPage.getAllProductCardsOnPage();
        return ymPage.getAllProductCardTitlesFromList(productCardsOnPage);
    }*/

    /**
     * Проверяет, что заголовок (title) текущей страницы
     * соответствует выбранной подкатегории каталога.
     * Валидация выполняется через assertTrue с сообщением об ошибке.
     *
     * @param subcategory ожидаемое название подкатегории в заголовке страницы
     *
     * @author Сергей Лужин
     */
    @Step("Проверяем, что открытая страница сответсвует категории {subcategory}")
    public static void checkPageTitle(String subcategory) {
        Assertions.assertTrue(Driver.getWebDriver().getTitle().contains(subcategory), "Тайтл " + Driver.getWebDriver().getTitle() + " на сайте не соответствует категории " + subcategory);
    }

    /**
     * Проверяет, что ранее сохранённый товар присутствует
     * в списке названий товаров на текущей странице.
     *
     * @param savedProductTitle название товара, которое ожидается на странице
     * @param ymPage            объект страницы Яндекс Маркета,
     *                          используемый для получения списка товаров
     *
     * @author Сергей Лужин
     */
   /* @Step("Проверяем, что товар {savedProductTitle} был найден на странице")
    public static void checkSavedProduct(String savedProductTitle, YandexMarketBasePage ymPage) {
        Assertions.assertTrue(getAllProductsTitles(ymPage).contains(savedProductTitle), "Товар " + savedProductTitle + " не был найден на странице");
    }*/

    /**
     * Выполняет мягкую проверку количества найденных товаров на странице.
     * Проверяет, что фактическое число карточек товаров больше заданного порога.
     *
     * @param productCards          список карточек товаров на странице
     * @param checkedProductsAmount минимальное ожидаемое количество товаров
     *
     * @author Сергей Лужин
     */
    @Step("Проверяем, что после перехода по категории было найдено не меньше {checkedProductsAmount} товаров")
    public static void softCheckProductsAmountOnPage(List<WebElement> productCards, int checkedProductsAmount) {
        Assertions.assertTrue(productCards.size() > checkedProductsAmount, "Тест прошел, но товаров в заданной категории было меньше " + checkedProductsAmount + ". Было найдено только " + productCards.size() + " товаров.");
    }

}
