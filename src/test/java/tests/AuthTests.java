package tests;

import static org.testng.Assert.assertNotNull;
import org.testng.annotations.Test;

import services.AuthService;

public class AuthTests {

    @Test
    public void testAuthentication() {
        String token = AuthService.getAuthToken();
        assertNotNull(token, "Token should not be null");
    }
}
