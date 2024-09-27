package com.automation.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RestAssuredUtils {
    static RequestSpecification requestSpecification = RestAssured.given(); //A RestAssured object that contains the configuration for the HTTP request
    static String endPoint;  //Holds the URL endpoint for the API request
    static Response response;  //Stores the response received after making an HTTP request

    //Sets the endpoint URL for API requests
    public static void setEndPoint(String endPoint) {
        RestAssuredUtils.endPoint = endPoint;
    }

    //Makes an HTTP POST request to the specified endpoint
    public static Response post() {
        requestSpecification.log().all();  //Logs the details of the request (headers, body, etc.)
        response = requestSpecification.post(endPoint);  //Executes the POST request and stores the response
        response.then().log().all();  //Logs the details of the response (status, body, etc.)
        return response;  //Returns the response object
    }

    //Makes an HTTP GET request to the specified endpoint
    public static Response get() {
        requestSpecification.log().all();
        response = requestSpecification.get(endPoint);
        response.then().log().all();
        return response;
    }

    //Makes an HTTP PUT request to the specified endpoint
    public static Response put() {
        requestSpecification.log().all();
        response = requestSpecification.put(endPoint);
        response.then().log().all();
        return response;
    }

    //Makes an HTTP DELETE request to the specified endpoint
    public static Response delete() {
        requestSpecification.log().all();
        response = requestSpecification.delete(endPoint);
        response.then().log().all();
        return response;
    }

    //Sets a header for the HTTP request
    public static void setHeader(String key, String value) {
        requestSpecification = requestSpecification.header(key, value);  //Adds a header with the specified key and value to the request specification
    }

    //Sets the request body from a JSON file
    public static void setBody(String fileName) {
        String jsonFolderPath = ConfigReader.getConfigValue("json.folder.path");
        requestSpecification = requestSpecification.body(getDataFromFile(jsonFolderPath + fileName));
    }

    //Retrieves the status code from the response
    public static int getStatusCode() {
        return response.getStatusCode();
    }

    //Reads the content of a file and returns it as a string
    public static String getDataFromFile(String filepath) {
        String content = null;
        try {
            content = new Scanner(new FileInputStream(filepath)).useDelimiter("\\Z").next();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return content;
    }

    //Returns the stored response object
    public static Response getResponse() {
        return response;
    }

    //Retrieves a specific field value from the JSON response using a JSON path
    public static String getResponseFieldValue(String jsonPath) {
        return response.jsonPath().getString(jsonPath);
    }

    //Sets the request body using a Plain Old Java Object (POJO)
    public static void setBodyUsingPojo(Object object) {
        requestSpecification = requestSpecification.body(object);  //Serializes the POJO into JSON and sets it as the request body
    }
}

