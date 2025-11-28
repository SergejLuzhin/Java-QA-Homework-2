package helpers;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "file:src/main/resources/xpath.properties"
})
public interface XpathProperties extends Config {
    @Config.Key("ym.search.text.input")
    String ymSearchInputXpath();

    @Config.Key("ym.search.button")
    String ymSearchButtonXpath();

    @Config.Key("ym.catalog.button")
    String ymCatalogButtonXpath();

    @Config.Key("ym.catalog.category")
    String ymCatalogCategoryXpath();

    @Config.Key("ym.catalog.subcategory")
    String ymCatalogSubcategoryXpath();

    @Config.Key("ym.filter.priceMin")
    String ymFilterPriceMinXpath();

    @Config.Key("ym.filter.priceMax")
    String ymFilterPriceMaxXpath();

    @Config.Key("ym.filter.brand")
    String ymFilterBrandXpath();

    @Config.Key("ym.card.on.all.pages")
    String ymCardsOnAllPagesXpath();
}
