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

    @Config.Key("ym.catalog.button.large")
    String ymCatalogButtonXpath();

    @Config.Key("ym.catalog.category")
    String ymCatalogCategoryXpath();
}
