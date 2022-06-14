import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class DemowebshopTests extends TestBase {

    DemowebshopPage demowebshopPage = new DemowebshopPage();

    @Test
    @DisplayName("Successful authorization to some demowebshop (UI)")
    void loginTest() {
        step("Do login", () ->
                demowebshopPage.doLogin());
        step("Verify successful authorization", () ->
                $(".account").shouldHave(text(demowebshopPage.login)));
    }

    @Test
    @DisplayName("Successful authorization to some demowebshop (API + UI)")
    void loginWithApiAndCustomListenerTest() {

        step("Do Auth", () -> {
            demowebshopPage.doLoginWithToken();
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
        step("Do Auth", () -> {
            demowebshopPage.doLoginWithToken();
        });

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
        step("Do Auth", () -> {
            demowebshopPage.doLoginWithToken();
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
