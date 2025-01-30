package services;

import org.json.JSONObject;

import config.ConfigManager;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

public class AuthService {

    public static String getAuthToken() {
        // Create JSON request body
        JSONObject requestBody = new JSONObject();
        requestBody.put("username", "admin");  // Updated to match API doc
        requestBody.put("password", "admin");

        // Make API request
        Response response = given()
                .baseUri(ConfigManager.getProperty("base.url"))  // Set base URI
                .contentType("application/json")  // Ensure correct content type
                .body(requestBody.toString())  // Send JSON payload
        .when()
                .post(ConfigManager.getProperty("auth.endpoint"))  // Call API
        .then()
                .statusCode(200)  // Expect 200 OK
                .extract()
                .response();

        // Extract token safely
        String token = response.jsonPath().getString("token");
        return (token != null) ? token : "";  // Avoid null returns
    }
}
