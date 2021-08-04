package com.bestbuy.cucumber.stepdefs;

import com.bestbuy.stepinfo.ServiceSteps;
import com.bestbuy.utils.TestUtils;
import cucumber.api.PendingException;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;

import static org.hamcrest.CoreMatchers.equalTo;

public class ServiceTest {

    static String name = "Electronics Experience Shop" + TestUtils.getRandomValue();
    static long servicesId;

    @Steps
    ServiceSteps serviceSteps;

    @When("^Users sends a GET request to the services endpoint, they must get back a valid status code 200$")
    public void usersSendsAGETRequestToTheServicesEndpointTheyMustGetBackAValidStatusCode() {
        serviceSteps.getAllServices().statusCode(200);

    }

    @When("^I create a new services by providing the information name$")
    public void iCreateANewServicesByProvidingTheInformationName() {
        servicesId = serviceSteps.createNewServices(name).log().all().statusCode(201).extract().response()
                .body().jsonPath().getLong("id");
        System.out.println("Service ID is " + servicesId);
    }

    @Then("^I verify the services is created$")
    public void iVerifyTheServicesIsCreated() {
        serviceSteps.getServicesById(servicesId).log().all().statusCode(200);
        System.out.println("Service ID is " + servicesId);

    }

    @When("^I send GET request to services endpoint Id \"([^\"]*)\",I should received the services information$")
    public void iSendGETRequestToServicesEndpointIdIShouldReceivedTheServicesInformation(String id) {
        serviceSteps.getServicesById(servicesId).log().all().statusCode(200);


    }

    @When("^I update a created services providing the new name$")
    public void iUpdateACreatedServicesProvidingTheNewGame() {
        name = name + "_updated";

        serviceSteps.updateServices(servicesId, name).statusCode(200);


    }

    @Then("^I verify the services is updated$")
    public void iVerifyTheServicesIsUpdated() {
        serviceSteps.getServicesById(servicesId).body("name", equalTo(name));
    }

    @When("^I delete a created services, they must get back a valid status code is 200$")
    public void iDeleteACreatedServicesTheyMustGetBackAValidStatusCodeIs() {
        serviceSteps.deleteServicesById(servicesId).statusCode(200);
    }

    @Then("^I verify the services is created is deleted$")
    public void iVerifyTheServicesIsCreatedIsDeleted() {
        serviceSteps.getServicesById(servicesId).statusCode(404);
    }
}
