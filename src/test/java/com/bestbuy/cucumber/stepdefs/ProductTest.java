package com.bestbuy.cucumber.stepdefs;

import com.bestbuy.stepinfo.ProductSteps;
import com.bestbuy.utils.TestUtils;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;

import static org.hamcrest.Matchers.equalTo;

public class ProductTest {
    static String name = "Samsung Tablet" + TestUtils.getRandomValue();
    static String type = "Electronics" + TestUtils.getRandomValue();
    static double price = 12.99;
    static String upc = "1234545641";
    static double shipping = 11.99;
    static String description = "Universal";
    static String manufacturer = "Walmart";
    static String model = "String";
    static String url = "String";
    static String image = "String";
    static long productId;
    @Steps
    ProductSteps productSteps;

    @When("^User sends a GET request to products endpoint, they must get back a valid status code 200$")
    public void userSendsAGETRequestToProductsEndpointTheyMustGetBackAValidStatusCode() {
        productSteps.getAllProducts().log().all().statusCode(200);
    }


    @When("^I create a new product by providing the information name type upc price\"([^\"]*)\" description\"([^\"]*)\"model$")
    public void iCreateANewProductByProvidingTheInformationNameTypeUpcPriceDescriptionModel(double price, String description) {
        productId = productSteps.createNewProduct(name, type, price, upc, shipping, description, manufacturer, model, url, image).statusCode(201).log().all().extract().response()
                .body().jsonPath().getLong("id");
        System.out.println(productId);
    }

    @Then("^I verify the product is created$")
    public void iVerifyTheProductIsCreated() {
        productSteps.getProductByID(productId).log().all().statusCode(200);

    }


    @When("^I send GET request to product endpoint with Id \"([^\"]*)\" , I should received the product information$")
    public void iSendGETRequestToProductEndpointWithIdIShouldReceivedTheProductInformation(String _productId) {
        productSteps.getProductByID(productId).log().all().statusCode(200);

    }

    @When("^I update a created product providing the new name upc price and description$")
    public void iUpdateACreatedProductProvidingTheNewNameUpcPriceAndDescription() {
        name = name + "_Changed";
        price = 89.99;
        upc = upc + "_added";
        productSteps.updateProduct(productId, name, type, upc, price, description, model).log().all().statusCode(200);


    }

    @Then("^I verify the product is updated$")
    public void iVerifyTheProductIsUpdated() {
        productSteps.getProductByID(productId).body("name", equalTo(name)).log().all();

    }

    @When("^I delete a created product ,they must get back a valid status code is 200$")
    public void iDeleteACreatedProductTheyMustGetBackAValidStatusCodeIs() {
        productSteps.deleteProduct(productId).log().all().statusCode(200);

    }

    @Then("^I verify the product is deleted$")
    public void iVerifyTheProductIsDeleted() {

        productSteps.getProductByID(productId).log().all().statusCode(404);
    }
}
