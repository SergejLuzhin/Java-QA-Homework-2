package pages;

import helpers.ElementScreenshotProxy;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

import static helpers.Properties.testProperties;
import static helpers.Properties.xpathProperties;


public class YandexMarketBasePage {
    protected WebDriver driver;

    protected WebElement searchInput;
    //protected WebElement wrappedSearchInput;

    protected WebElement searchButton;
    //protected WebElement wrappedSearchButton;

    protected WebElement catalogButton;

    protected WebDriverWait wait;

    public YandexMarketBasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, testProperties.defaultTimeout());

        wait.until(visibilityOfElementLocated(By.xpath(xpathProperties.ymSearchInputXpath())));
        this.searchInput = driver.findElement(By.xpath(xpathProperties.ymSearchInputXpath()));
        //this.wrappedSearchInput = ElementScreenshotProxy.wrap(searchInput, driver);

        this.searchButton = driver.findElement(By.xpath(xpathProperties.ymSearchButtonXpath()));
        //this.wrappedSearchButton = ElementScreenshotProxy.wrap(searchButton, driver);

        this.catalogButton = driver.findElement(By.xpath(xpathProperties.ymCatalogButtonXpath()));
    }

    public void find(String query) {
        searchInput.sendKeys(query);
        searchButton.click();
    }

    public void chooseCategoryInCatalog(String category) {
        wait.until(visibilityOfElementLocated(By.xpath(xpathProperties.ymCatalogButtonXpath())));
        catalogButton.click();
        //WebElement categoryElement = wait.until(visibilityOfElementLocated(By.xpath(xpathProperties.ymCatalogElectronicsXpath())));
        WebElement categoryElement = driver.findElement(By.xpath(String.format(xpathProperties.ymCatalogCategoryXpath(), category)));
        categoryElement.click();
    }
}
