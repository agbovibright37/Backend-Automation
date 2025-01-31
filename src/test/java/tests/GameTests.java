package tests;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import services.GameService;

@Epic("Video Game API Testing")
@Feature("Game Management")
public class GameTests {

    /*// ✅ 1. Test listing all games
    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test to fetch all video games and verify response structure")
    @Story("Get All Games")
    public void testGetAllGames() {
        Response response = GameService.getAllGames();

        Allure.addAttachment("Response", response.getBody().asPrettyString());

        assertEquals(response.getStatusCode(), 200, "Expected status code 200");

        // Validate response contains expected fields
        String responseBody = response.getBody().asString();
        assertTrue(responseBody.contains("id"), "Response should contain 'id' field");
        assertTrue(responseBody.contains("name"), "Response should contain 'name' field");
        assertTrue(responseBody.contains("category"), "Response should contain 'category' field");
        assertTrue(responseBody.contains("reviewScore"), "Response should contain 'reviewScore' field");
    }*/
    // ✅ 1. Test listing all games
    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test to fetch all video games and verify response structure")
    @Story("Get All Games")
    public void testGetAllGames() {
        System.out.println("🔹 [Test Case 1.1] Starting test: Fetch all games...");

        Response response = GameService.getAllGames();
        Allure.addAttachment("Response", response.getBody().asPrettyString());

        assertEquals(response.getStatusCode(), 200, "❌ Expected status code 200");
        System.out.println("✅ [Test Case 1.2] Status code 200: PASSED");

        // Validate response contains expected fields
        String responseBody = response.getBody().asString();
        assertTrue(responseBody.contains("id"), "❌ Response should contain 'id' field");
        System.out.println("✅ [Test Case 1.3] Response contains 'id' field: PASSED");

        assertTrue(responseBody.contains("name"), "❌ Response should contain 'name' field");
        System.out.println("✅ [Test Case 1.4] Response contains 'name' field: PASSED");

        assertTrue(responseBody.contains("category"), "❌ Response should contain 'category' field");
        System.out.println("✅ [Test Case 1.5] Response contains 'category' field: PASSED");

        assertTrue(responseBody.contains("reviewScore"), "❌ Response should contain 'reviewScore' field");
        System.out.println("✅ [Test Case 1.6] Response contains 'reviewScore' field: PASSED");

        // New test case: Ensure at least one game exists
        List<Integer> gameIds = response.jsonPath().getList("id");
        assertFalse(gameIds.isEmpty(), "❌ Expected at least one game in the list");
        System.out.println("✅ [Test Case 1.7] At least one game exists in list: PASSED");

        // New test case: Ensure all game IDs are positive
        for (Integer id : gameIds) {
            assertTrue(id > 0, "❌ Found a game with invalid ID: " + id);
            System.out.println("✅ [Test Case 1.8] Valid Game ID: " + id + " PASSED");
        }

        System.out.println("🎉 [Test Case 1] Get all games: COMPLETED SUCCESSFULLY!");
    }

    /*// ✅ 2. Test fetching a specific game by ID
    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Test to fetch a specific game by ID and validate its response")
    @Story("Get Game By ID")
    public void testGetGameById() {
        int gameId = 1;  // Ensure this ID exists

        Response response = GameService.getGameById(gameId);

        Allure.addAttachment("Requested Game ID", String.valueOf(gameId));
        Allure.addAttachment("Response", response.getBody().asPrettyString());

        assertEquals(response.getStatusCode(), 200, "Expected status code 200");
        assertTrue(response.getBody().asString().contains("\"id\":" + gameId), "Response should contain game ID " + gameId);
    } */
    // ✅ 2. Test fetching a specific game by ID
    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Test to fetch a specific game by ID and validate its response")
    @Story("Get Game By ID")
    public void testGetGameById() {
        int gameId = 1;  // Ensure this ID exists

        System.out.println("\n[Test Case 2.1] Fetching game with ID: " + gameId);

        Response response = GameService.getGameById(gameId);

        Allure.addAttachment("Requested Game ID", String.valueOf(gameId));
        Allure.addAttachment("Response", response.getBody().asPrettyString());

        System.out.println("[Test Case 2.2] Received response: " + response.getBody().asPrettyString());

        // ✅ Assert that the status code is 200
        assertEquals(response.getStatusCode(), 200, "[Test Case 2.3] Expected status code 200");
        System.out.println("[Test Case 2.3] ✅ Status code is 200");

        // ✅ Validate response contains expected fields
        String responseBody = response.getBody().asString();

        assertTrue(responseBody.contains("\"id\":" + gameId), "[Test Case 2.4] Response should contain game ID: " + gameId);
        System.out.println("[Test Case 2.4] ✅ Response contains correct Game ID");

        System.out.println("[Test Case 2] ✅ Game fetch test PASSED!");
    }

    /*// ✅ 3. Test creating a new game
    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test creating a new video game and verifying response")
    @Story("Create Game")
    public void testCreateGame() {
        String gameName = "NewGame" + System.currentTimeMillis();
        String releaseDate = "2025-01-30";
        int reviewScore = 95;
        String category = "Adventure";
        String rating = "Everyone";

        Response response = GameService.createGame(gameName, releaseDate, reviewScore, category, rating);

        Allure.addAttachment("Created Game Details", gameName + " - " + category);
        Allure.addAttachment("Response", response.getBody().asPrettyString());

        assertEquals(response.getStatusCode(), 200, "Expected status code 200");

        // Validate the response contains the correct game details
        String responseBody = response.getBody().asString();
        assertTrue(responseBody.contains(gameName), "Response should contain game name");
        assertTrue(responseBody.contains(category), "Response should contain game category");
    }*/

   // ✅ 3. Test creating a new game
@Test
@Severity(SeverityLevel.CRITICAL)
@Description("Test creating a new video game and verifying response")
@Story("Create Game")
public void testCreateGame() {
    String gameName = "NewGame" + System.currentTimeMillis();
    String releaseDate = "2025-01-30";
    int reviewScore = 95;
    String category = "Adventure";
    String rating = "Everyone";

    System.out.println("\n[Test Case 3.1] Creating a new game: " + gameName);

    Response response = GameService.createGame(gameName, releaseDate, reviewScore, category, rating);

    Allure.addAttachment("Created Game Details", gameName + " - " + category);
    Allure.addAttachment("Response", response.getBody().asPrettyString());

    System.out.println("[Test Case 3.2] Received response: " + response.getBody().asPrettyString());

    // ✅ Assert that the status code is 200
    assertEquals(response.getStatusCode(), 200, "[Test Case 3.3] Expected status code 200");
    System.out.println("[Test Case 3.3] ✅ Status code is 200");

    // ✅ Validate the response contains the correct game details
    String responseBody = response.getBody().asString();

    assertTrue(responseBody.contains(gameName), "[Test Case 3.4] Response should contain game name");
    System.out.println("[Test Case 3.4] ✅ Response contains the correct game name");

    assertTrue(responseBody.contains(category), "[Test Case 3.5] Response should contain game category");
    System.out.println("[Test Case 3.5] ✅ Response contains the correct game category");

    System.out.println("[Test Case 3] ✅ Game creation test PASSED!");
}


    /*// ✅ 4. Test updating an existing game
    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Test updating an existing game with new details")
    @Story("Update Game")
    public void testUpdateGame() {
        int gameId = 1;  // Use an existing game ID from API

        String updatedName = "Updated Game " + System.currentTimeMillis();
        String updatedReleaseDate = "2025-02-01";
        int updatedReviewScore = 99;
        String updatedCategory = "Action";
        String updatedRating = "Teen";

        Response response = GameService.updateGame(gameId, updatedName, updatedReleaseDate, updatedReviewScore, updatedCategory, updatedRating);

        Allure.addAttachment("Updated Game ID", String.valueOf(gameId));
        Allure.addAttachment("Response", response.getBody().asPrettyString());

        assertEquals(response.getStatusCode(), 200, "Expected status code 200");

        // Validate response contains updated data
        String responseBody = response.getBody().asString();
        assertTrue(responseBody.contains(updatedName), "Response should contain updated game name");
        assertTrue(responseBody.contains(updatedCategory), "Response should contain updated game category");
    } */

   // ✅ 4. Test updating an existing game (Read-Only Mode: Will Not Persist)
@Test
@Severity(SeverityLevel.NORMAL)
@Description("Test updating an existing game with new details")
@Story("Update Game")
public void testUpdateGame() {
    int gameId = 1;  // Use an existing game ID from API

    String updatedName = "Updated Game " + System.currentTimeMillis();
    String updatedReleaseDate = "2025-02-01";
    int updatedReviewScore = 99;
    String updatedCategory = "Action";
    String updatedRating = "Teen";

    System.out.println("\n[Test Case 4.1] Attempting to update Game ID: " + gameId);

    Response response = GameService.updateGame(gameId, updatedName, updatedReleaseDate, updatedReviewScore, updatedCategory, updatedRating);

    Allure.addAttachment("Updated Game ID", String.valueOf(gameId));
    Allure.addAttachment("Response", response.getBody().asPrettyString());

    System.out.println("[Test Case 4.2] Received response: " + response.getBody().asPrettyString());

    // ✅ Assert that the status code is 200
    assertEquals(response.getStatusCode(), 200, "[Test Case 4.3] Expected status code 200");
    System.out.println("[Test Case 4.3] ✅ Status code is 200");

    // ✅ Validate response contains updated data (Read-Only: These will not persist)
    String responseBody = response.getBody().asString();

    assertTrue(responseBody.contains(updatedName), "[Test Case 4.4] Response should contain updated game name");
    System.out.println("[Test Case 4.4] ✅ Response contains the updated game name");

    assertTrue(responseBody.contains(updatedCategory), "[Test Case 4.5] Response should contain updated game category");
    System.out.println("[Test Case 4.5] ✅ Response contains the updated game category");

    System.out.println("[Test Case 4] ✅ Game update test PASSED (But data is not persisted due to read-only mode)");

    /*
     * ❗ Read-Only API: These would normally be included but are commented out
     * because the update does not actually persist.
     *
     * // 🔹 Fetch the updated game and verify changes
     * Response fetchUpdatedResponse = GameService.getGameById(gameId);
     * assertEquals(fetchUpdatedResponse.getStatusCode(), 200, "[Test Case 4.6] Expected status code 200");
     *
     * String updatedResponseBody = fetchUpdatedResponse.getBody().asString();
     * assertTrue(updatedResponseBody.contains(updatedName), "[Test Case 4.7] Game name should be updated");
     * assertTrue(updatedResponseBody.contains(updatedCategory), "[Test Case 4.8] Game category should be updated");
     *
     * System.out.println("[Test Case 4.6 - 4.8] ✅ Game update verification passed!");
     */
}


    /*// ✅ 5. Test deleting a game
    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Description("Test deleting a game from the system (should return 404 because API is read-only)")
    @Story("Delete Game")
    public void testDeleteGame() {
        // 🔹 Step 1: Create a game (this won't persist because the API is read-only)
        String gameName = "TestGame" + System.currentTimeMillis();
        String releaseDate = "2025-01-30";
        int reviewScore = 95;
        String category = "Action";
        String rating = "Mature";

        Response createResponse = GameService.createGame(gameName, releaseDate, reviewScore, category, rating);
        assertEquals(createResponse.getStatusCode(), 200, "Expected status code 200 when creating a game");

        // 🔹 Step 2: Extract the newly created game's ID (but it won’t actually persist)
        int gameId = createResponse.jsonPath().getInt("id");

        // 🔹 Step 3: Attempt to delete the game
        Response deleteResponse = GameService.deleteGameById(gameId);

        Allure.addAttachment("Attempted Deletion ID", String.valueOf(gameId));
        Allure.addAttachment("Response", deleteResponse.getBody().asPrettyString());

        // ❗ Since the API is READ-ONLY, we expect 404 instead of 200
        assertEquals(deleteResponse.getStatusCode(), 404, "Expected status code 404 because API does not persist games");

        // 🔹 Step 4: Verify game is not found (should return 404)
        Response fetchDeletedResponse = GameService.getGameById(gameId);
        assertEquals(fetchDeletedResponse.getStatusCode(), 404, "Expected status code 404 because game was never actually saved");
    }*/

   // ✅ 5. Test deleting a game (Read-Only Mode: Will Not Persist)
@Test
@Severity(SeverityLevel.BLOCKER)
@Description("Test deleting a game from the system (should return 404 because API is read-only)")
@Story("Delete Game")
public void testDeleteGame() {
    // 🔹 Step 1: Create a game (this won't persist because the API is read-only)
    String gameName = "TestGame" + System.currentTimeMillis();
    String releaseDate = "2025-01-30";
    int reviewScore = 95;
    String category = "Action";
    String rating = "Mature";

    System.out.println("\n[Test Case 5.1] Attempting to create a game for deletion test: " + gameName);

    Response createResponse = GameService.createGame(gameName, releaseDate, reviewScore, category, rating);

    Allure.addAttachment("Created Game", gameName + " - " + category);
    Allure.addAttachment("Response", createResponse.getBody().asPrettyString());

    System.out.println("[Test Case 5.2] Received response: " + createResponse.getBody().asPrettyString());

    // ✅ Assert that the status code is 200
    assertEquals(createResponse.getStatusCode(), 200, "[Test Case 5.3] Expected status code 200 when creating a game");
    System.out.println("[Test Case 5.3] ✅ Game creation simulated (Read-Only)");

    // 🔹 Step 2: Extract the newly created game's ID (but it won’t actually persist)
    int gameId = createResponse.jsonPath().getInt("id");
    System.out.println("[Test Case 5.4] Extracted Game ID for deletion: " + gameId);

    // 🔹 Step 3: Attempt to delete the game
    Response deleteResponse = GameService.deleteGameById(gameId);

    Allure.addAttachment("Attempted Deletion ID", String.valueOf(gameId));
    Allure.addAttachment("Response", deleteResponse.getBody().asPrettyString());

    System.out.println("[Test Case 5.5] Attempting to delete game ID: " + gameId);
    System.out.println("[Test Case 5.6] Received response: " + deleteResponse.getBody().asPrettyString());

    // ❗ Since the API is READ-ONLY, we expect 404 instead of 200
    assertEquals(deleteResponse.getStatusCode(), 404, "[Test Case 5.7] Expected status code 404 because API does not persist games");
    System.out.println("[Test Case 5.7] ✅ Delete request acknowledged, but game is not actually removed (Read-Only Mode)");

    // 🔹 Step 4: Verify game is not found (should return 404)
    Response fetchDeletedResponse = GameService.getGameById(gameId);

    assertEquals(fetchDeletedResponse.getStatusCode(), 404, "[Test Case 5.8] Expected status code 404 because game was never actually saved");
    System.out.println("[Test Case 5.8] ✅ Verified game is not found after deletion attempt");

    System.out.println("[Test Case 5] ✅ Delete Game test PASSED! (Read-Only Mode)");

    /*
     * ❗ Read-Only API: These would normally be included but are commented out
     * because the delete operation does not actually remove the game.
     *
     * // 🔹 Fetch the game after deletion
     * Response fetchDeletedResponse = GameService.getGameById(gameId);
     * assertEquals(fetchDeletedResponse.getStatusCode(), 404, "[Test Case 5.9] Game should be deleted");
     *
     * System.out.println("[Test Case 5.9] ✅ Verified game is deleted successfully!");
     */
}

}
