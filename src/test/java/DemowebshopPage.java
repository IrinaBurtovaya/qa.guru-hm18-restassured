import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class DemowebshopPage {

    public String login = "qaguru@qa.guru",
            password = "qaguru@qa.guru1",
            authCookieName = "NOPCOMMERCE.AUTH";

    SelenideElement booksCatalog = $x("(//a[@href='/books'])[3]");
    SelenideElement item = $(byText("Health Book"));
    SelenideElement addToCartButton = $x("(//input[@value=\"Add to cart\"])[1]");

    void doLogin() {
        open("/login");
        $("#Email").setValue(login);
        $("#Password").setValue(password)
                .pressEnter();
    }
}
