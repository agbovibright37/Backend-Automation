package services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import config.ConfigManager;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

public class AuthService {

    private static final Logger logger = LogManager.getLogger(AuthService.class);

    // 1. Retrieve Authentication Token
    public static String getAuthToken() {
        logger.info("[AuthService] Requesting authentication token...");

        // Create JSON request body
        JSONObject requestBody = new JSONObject();
        requestBody.put("username", "admin");  // Ensure correct credentials
        requestBody.put("password", "admin");

        // Make API request
        Response response = given()
                .baseUri(ConfigManager.getProperty("base.url")) // Set base URI
                .contentType("application/json") // Ensure correct content type
                .body(requestBody.toString()) // Send JSON payload
                .when()
                .post(ConfigManager.getProperty("auth.endpoint")) // Call API
                .then()
                .extract()
                .response();

        // Log response time
        logger.info("[AuthService] Response received in {} ms", response.getTime());

        // Handle potential authentication failures
        if (response.getStatusCode() != 200) {
            logger.error("[AuthService] Authentication failed! Status: {}", response.getStatusCode());
            return "";
        }

        // Extract token safely
        String token = response.jsonPath().getString("token");

        if (token == null || token.isEmpty()) {
            logger.error("[AuthService] Token retrieval failed!");
            return "";
        }

        logger.info("[AuthService] Authentication successful. Token: {}", token);
        return token;
    }

    // 2. Simulate Failed Authentication
    public static String getInvalidAuthToken() {
        logger.info("[AuthService] Attempting authentication with invalid credentials...");

        // Create JSON request body with incorrect credentials
        JSONObject requestBody = new JSONObject();
        requestBody.put("username", "wronguser");
        requestBody.put("password", "wrongpass");

        // Make API request
        Response response = given()
                .baseUri(ConfigManager.getProperty("base.url"))
                .contentType("application/json")
                .body(requestBody.toString())
                .when()
                .post(ConfigManager.getProperty("auth.endpoint"))
                .then()
                .extract()
                .response();

        // Log response time
        logger.info("[AuthService] Response received in {} ms", response.getTime());

        // Expect failure (status code 401 or similar)
        if (response.getStatusCode() != 200) {
            logger.info("[AuthService] Authentication failed as expected. Status: {}", response.getStatusCode());
            return "";
        }

        // Extract token (should not be present)
        String token = response.jsonPath().getString("token");

        if (token != null && !token.isEmpty()) {
            logger.warn("⚠️ [AuthService] Unexpected token received for invalid credentials!");
        }

        return (token != null) ? token : "";
    }
}
