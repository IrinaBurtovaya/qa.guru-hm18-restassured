package config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:config/credentials.properties")
public interface CredentialsConfig extends Config {
    String login();
    String password();

    @Key("selenoid")
    @DefaultValue("selenoid.autotests.cloud/wd/hub")
    String getSelenoid();

    @Key("browser")
    @DefaultValue("chrome")
    String getBrowser();

    @Key("browserSize")
    @DefaultValue("1920x1080")
    String getBrowserSize();

    @Key("baseUrl")
    @DefaultValue("http://demowebshop.tricentis.com")
    String getBaseUrl();

    @Key("baseURI")
    @DefaultValue("http://demowebshop.tricentis.com")
    String getBaseURI();
}
