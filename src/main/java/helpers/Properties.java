package helpers;

import org.aeonbits.owner.ConfigFactory;

public class Properties {
    public static TestProperties testProperties = ConfigFactory.create(TestProperties.class);

    public static XpathProperties xpathProperties = ConfigFactory.create(XpathProperties.class);
}
