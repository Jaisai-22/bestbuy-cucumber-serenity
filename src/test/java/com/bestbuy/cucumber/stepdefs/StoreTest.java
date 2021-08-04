package com.bestbuy.cucumber.stepdefs;

import com.bestbuy.stepinfo.StoreSteps;
import com.bestbuy.utils.TestUtils;
import cucumber.api.PendingException;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;

import static org.hamcrest.CoreMatchers.equalTo;

public class StoreTest {

    static String name = "Decorama" + TestUtils.getRandomValue();
    static String type = "Dollar Saving" + TestUtils.getRandomValue();
    static String address = "123 Semley Road";
    static String address2 = "";
    static String city = "London" + TestUtils.getRandomValue();
    static String state = "MN";
    static String zip = "234531";
    static float lat = 45.087f;
    static float lng = 23.123f;
    static String hours = "String";
    static long storeId;


    @Steps
    StoreSteps storesSteps;


    @When("^User sends a GET request to the stores endpoint, they must get back a valid status code 200$")
    public void userSendsAGETRequestToTheStoresEndpointTheyMustGetBackAValidStatusCode() {
        storesSteps.getAllStores().log().all().statusCode(200);
    }

    @When("^I create a new stores by providing the information name type address addresstwo city state zip lat \"([^\"]*)\" lng \"([^\"]*)\" hours \"([^\"]*)\"$")
    public void iCreateANewStoresByProvidingTheInformationNameTypeAddressAddresstwoCityStateZipLatLngHours(float lat, float lng, String hours) {
        storeId = storesSteps.createNewStore(name, type, address, address2, city, state, zip, lat, lng, hours).log().all().extract().response()
                .body().jsonPath().getLong("id");
        System.out.println("store id is : " + storeId);
    }

    @Then("^I verify the stores is created$")
    public void iVerifyTheStoresIsCreated() {
        storesSteps.getStoreById(storeId).log().all().statusCode(200);
    }

    @When("^I send GET request to stores endpoint with Id \"([^\"]*)\", I should received the store information$")
    public void iSendGETRequestToStoresEndpointWithIdIShouldReceivedTheStoreInformation(String id)  {
        storesSteps.getStoreById(storeId).log().all().statusCode(200);

    }

    @When("^I update a created store providing the new name type and hours$")
    public void iUpdateACreatedStoreProvidingTheNewNameTypeAndHours() {
        name = name + "_updated";
        type = type + "_added";
        address = address + "_updated";
        address2 = address2 + "_new";
        city = city + "_new";

        storesSteps.updateStore(storeId, name, type, address, address2, city).log().all().statusCode(200);
    }

    @Then("^I verify the store is updated$")
    public void iVerifyTheStoreIsUpdated() {
        storesSteps.getStoreById(storeId).log().all().body("name", equalTo(name));
    }

    @When("^I delete a created store, they must get back a valid status code is 200$")
    public void iDeleteACreatedStoreTheyMustGetBackAValidStatusCodeIs() {
        storesSteps.deleteStore(storeId).log().all().statusCode(200);
    }

    @Then("^I verify the store is deleted$")
    public void iVerifyTheStoreIsDeleted() {
        storesSteps.getStoreById(storeId).log().all().statusCode(404);
    }
}
