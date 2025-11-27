package helpers;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "file:src/main/resources/test.properties"
})
public interface TestProperties extends Config {
    @Config.Key("default.timeout")
    int defaultTimeout();

    @Config.Key("yandex-market.url")
    String yandexMarketUrl();

    @Config.Key("driver.chrome.windows")
    String driverChromeWindows();

    @Config.Key("driver.chrome.macos")
    String driverChromeMacos();

    @Config.Key("bellintegrator.url")
    String bellIntegratorUrl();
}
