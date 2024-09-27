package com.automation.steps;

import com.automation.pojo.AddObjectPojo;
import com.automation.utils.ConfigReader;
import com.automation.utils.RestAssuredUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.Assert;

public class ResponseSteps {

    @Then("verify status code is {int}")
    public void verify_status_code_is(int statusCode) {
        Assert.assertEquals(statusCode, RestAssuredUtils.getStatusCode());
    }

    @And("verify id is not empty")
    public void verifyIdIsNotEmpty() {
        String resourceId = RestAssuredUtils.getResponse().jsonPath().getString("id");
        Assert.assertFalse(resourceId.isEmpty());
    }

    @And("store created id into {string}")
    public void storeCreatedIdInto(String key) {
        ConfigReader.setConfigValue(key, RestAssuredUtils.getResponse().jsonPath().getString("id"));
    }

    @And("verify response is same as the request passed in post call")
    public void mapTheResponseToAClass() throws JsonProcessingException {
        String jsonFolderPath = ConfigReader.getConfigValue("json.folder.path");
        String jsonBody = RestAssuredUtils.getDataFromFile(jsonFolderPath + "add_object.json");

        ObjectMapper om = new ObjectMapper();
        AddObjectPojo actual = om.readValue(jsonBody, AddObjectPojo.class);

        RestAssuredUtils.setBodyUsingPojo(actual);
        AddObjectPojo expected = RestAssuredUtils.getResponse().as(AddObjectPojo.class);
        Assert.assertEquals(actual, expected);

    }


    @And("verify response message is {string}")
    public void verifyResponseMessageIs(String message) {
        message = message.replace("@id", ConfigReader.getConfigValue("object.id"));
        Assert.assertEquals(message, RestAssuredUtils.getResponseFieldValue("message"));


    }
}
