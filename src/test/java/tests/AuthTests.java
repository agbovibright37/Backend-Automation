package tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import services.AuthService;

@Epic("Authentication API Testing")
@Feature("User Authentication")
public class AuthTests {

    private static final Logger logger = LogManager.getLogger(AuthTests.class);

    //Auth1. Verify authentication with valid credentials 
    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Description("Test to verify successful authentication and token retrieval")
    @Story("Generate Auth Token")
    public void testAuthentication() {
        logger.info("[Test Case 1.1] Attempting to authenticate and retrieve token...");

        String token = AuthService.getAuthToken();
        Allure.addAttachment("Generated Token", token);

        assertNotNull(token, "Token should not be null");
        logger.info("[Test Case 1.2] Token successfully retrieved: {}", token);

        //Ensure token has a valid length
        assertTrue(token.length() > 20, "Token length should be greater than 20");
        logger.info("[Test Case 1.3] Token length is valid");

        logger.info("[Test Case 1] Authentication test PASSED!");
    }

    // Auth 2. Validate Token Format
    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test to ensure authentication token format is valid")
    @Story("Validate Token Format")
    public void testTokenFormat() {
        logger.info("[Test Case 2.1] Checking token format...");

        String token = AuthService.getAuthToken();
        Allure.addAttachment("Generated Token", token);

        assertNotNull(token, "Token should not be null");

        // Ensure token follows the standard format
        assertTrue(token.contains("."), "Token format should contain at least one '.' separator");
        logger.info("[Test Case 2.2] Token format is valid");

        logger.info("[Test Case 2] Token format validation PASSED!");
    }

    // Auth 3. This is to help Handle Failed Authentication
    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test to simulate failed authentication with invalid credentials")
    @Story("Invalid Login Attempt")
    public void testInvalidAuthentication() {
        logger.info("[Test Case 3.1] Attempting authentication with invalid credentials...");

        String invalidToken = AuthService.getInvalidAuthToken(); 
        Allure.addAttachment("Invalid Token Response", invalidToken);

        // here authentication should fail and returns no valid token
        assertTrue(invalidToken == null || invalidToken.isEmpty(), "Expected authentication failure, but received a token");
        logger.info("[Test Case 3.2] Authentication correctly failed with invalid credentials");

        logger.info("[Test Case 3] Invalid authentication test PASSED!");
    }
}
