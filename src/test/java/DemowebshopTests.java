import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.restassured.AllureRestAssured;

import org.junit.jupiter.api.*;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static helpers.CustomApiListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class DemowebshopTests extends TestBase {

    DemowebshopPage demowebshopPage = new DemowebshopPage();

    @Test
    @DisplayName("Successful authorization to some demowebshop (UI)")
    void loginTest() {
        step("Open login page", () ->
                open("/login"));

        step("Fill login form", () -> {
            $("#Email").setValue(demowebshopPage.login);
            $("#Password").setValue(demowebshopPage.password)
                    .pressEnter();
        });

        step("Verify successful authorization", () ->
                $(".account").shouldHave(text(demowebshopPage.login)));
    }

    @Test
    @DisplayName("Successful authorization to some demowebshop (API + UI)")
    void loginWithApiTest() {

        step("Open minimal content, because cookie can be set when site is opened", () ->
                open("/Themes/DefaultClean/Content/images/logo.png"));

        step("Get cookie by api and set it to browser", () -> {
            String authCookieValue = given()
                    .contentType("application/x-www-form-urlencoded")
                    .formParam("Email", demowebshopPage.login)
                    .formParam("Password", demowebshopPage.password)
                    .log().all()
                    .when()
                    .post("/login")
                    .then()
                    .log().all()
                    .statusCode(302)
                    .extract().cookie(demowebshopPage.authCookieName);

            step("Set cookie to browser", () -> {
                Cookie authCookie = new Cookie(demowebshopPage.authCookieName, authCookieValue);
                WebDriverRunner.getWebDriver().manage().addCookie(authCookie);
            });
        });

        step("Open main page", () ->
                open(""));
        step("Verify successful authorization", () ->
                $(".account").shouldHave(text(demowebshopPage.login)));
    }

    @Test
    @DisplayName("Successful authorization to some demowebshop (API + UI)")
    void loginWithApiAndAllureListenerTest() {

        step("Open minimal content, because cookie can be set when site is opened", () ->
                open("/Themes/DefaultClean/Content/images/logo.png"));

        step("Get cookie by api and set it to browser", () -> {
            String authCookieValue = given()
                    .filter(new AllureRestAssured())
                    .contentType("application/x-www-form-urlencoded")
                    .formParam("Email", demowebshopPage.login)
                    .formParam("Password", demowebshopPage.password)
                    .log().all()  //выводит все в консоль
                    .when()
                    .post("/login")
                    .then()
                    .log().all()
                    .statusCode(302)
                    .extract().cookie(demowebshopPage.authCookieName);

            step("Set cookie to browser", () -> {
                Cookie authCookie = new Cookie(demowebshopPage.authCookieName, authCookieValue);
                WebDriverRunner.getWebDriver().manage().addCookie(authCookie);
            });
        });

        step("Open main page", () ->
                open(""));
        step("Verify successful authorization", () ->
                $(".account").shouldHave(text(demowebshopPage.login)));
    }

    @Test
    @DisplayName("Successful authorization to some demowebshop (API + UI)")
    void loginWithApiAndCustomListenerTest() {

        step("Open minimal content, because cookie can be set when site is opened", () ->
                open("/Themes/DefaultClean/Content/images/logo.png"));

        step("Get cookie by api and set it to browser", () -> {
            String authCookieValue = given()
                    .filter(withCustomTemplates())
                    .contentType("application/x-www-form-urlencoded")
                    .formParam("Email", demowebshopPage.login)
                    .formParam("Password", demowebshopPage.password)
                    .log().all()  //выводит все в консоль
                    .when()
                    .post("/login")
                    .then()
                    .log().all()
                    .statusCode(302)
                    .extract().cookie(demowebshopPage.authCookieName);

            step("Set cookie to browser", () -> {
                Cookie authCookie = new Cookie(demowebshopPage.authCookieName, authCookieValue);
                WebDriverRunner.getWebDriver().manage().addCookie(authCookie);
            });
        });

        step("Open main page", () ->
                open(""));
        step("Verify successful authorization", () ->
                $(".account").shouldHave(text(demowebshopPage.login)));
    }

    @Test
    @DisplayName("Adding an item to the shopping cart (UI)")
    void checkItemInTheShoppingCart() {

        step("Do login", () ->
                demowebshopPage.doLogin());

        step("Open books catalog", () ->
                demowebshopPage.booksCatalog.click());

        step("Choose item", () -> demowebshopPage.item.click());

        step("Adding the item to the shopping cart", () -> demowebshopPage.addToCartButton.click());

        step("Verify successful adding an item to the shop cart", () -> $(".page-body")
                .shouldHave(text("Health Book")));
    }

    @Test
    @DisplayName("Adding an item to the shopping cart (UI + API)")
    void checkItemInTheShoppingCartUiPlusApiTest() {
        step("Do login", () ->
                demowebshopPage.doLogin());

        step("Check items in the shopping cart", () ->
                given()
                        .when()
                        .post(baseURI + "/cart")
                        .then()
                        .log().body()
                        .extract().body().equals("Health Book"));
    }

    @Test
    @DisplayName("Adding an item to the shopping cart (API)")
    void checkItemInTheShoppingCartApiTest() {
        step("Open minimal content, because cookie can be set when site is opened", () ->
                open("/Themes/DefaultClean/Content/images/logo.png"));
        step("Get cookie by api and set it to browser", () -> {
            String authCookieValue = given()
                    .filter(withCustomTemplates())
                    .contentType("application/x-www-form-urlencoded")
                    .formParam("Email", demowebshopPage.login)
                    .formParam("Password", demowebshopPage.password)
                    .log().all()  //выводит все в консоль
                    .when()
                    .post("/login")
                    .then()
                    .log().all()
                    .statusCode(302)
                    .extract().cookie(demowebshopPage.authCookieName);
            step("Set cookie to browser", () -> {
                Cookie authCookie = new Cookie(demowebshopPage.authCookieName, authCookieValue);
                WebDriverRunner.getWebDriver().manage().addCookie(authCookie);
            });
        });
        step("Opening the shopping cart", () -> {
            open(baseURI + "/cart");
        });
        step("Verify that the item is in the shop cart", () -> {
            $(".page-body")
                    .shouldHave(text("Health Book"));
        });
    }
}
