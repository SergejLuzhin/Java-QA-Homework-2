package entity;

import org.openqa.selenium.WebElement;
import pages.YandexMarketBasePage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Product {
   // public static List<List<Product>> savedProducts = new ArrayList<>();

    private final String title;
    private final int price;

    public Product (String title, int price) {
       this.title = title;
       this.price = price;
   }

   public int getPrice() {
        return price;
   }

   public String getTitle() {
        return title;
   }

   public static void saveProductFromElement(WebElement productElement, YandexMarketBasePage ymPage) {
       String productTitle = ymPage.getProductCardTitle(productElement);
       int productPrice = ymPage.getProductCardPrice(productElement);
      // savedProducts.get(pageNumber).add(new Product(productTitle, productPrice));
       ymPage.productsOnPage.add(new Product(productTitle, productPrice));
   }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Product product = (Product) obj;
        return price == product.price &&
                Objects.equals(title, product.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, price);
    }
}
