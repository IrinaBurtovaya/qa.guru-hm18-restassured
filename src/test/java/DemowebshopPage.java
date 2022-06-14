import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static helpers.CustomApiListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;

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

    void doLoginWithToken() {
        step("Open minimal content, because cookie can be set when site is opened", () ->
                open("/Themes/DefaultClean/Content/images/logo.png"));

        step("Get cookie by api and set it to browser", () -> {
            String authCookieValue = given()
                    .filter(withCustomTemplates())
                    .contentType("application/x-www-form-urlencoded")
                    .formParam("Email", login)
                    .formParam("Password", password)
                    .log().all()  //выводит все в консоль
                    .when()
                    .post("/login")
                    .then()
                    .log().all()
                    .statusCode(302)
                    .extract().cookie(authCookieName);

            step("Set cookie to browser", () -> {
                Cookie authCookie = new Cookie(authCookieName, authCookieValue);
                WebDriverRunner.getWebDriver().manage().addCookie(authCookie);
            });
        });
    }
}
