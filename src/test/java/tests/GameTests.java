package tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import services.GameService;

public class GameTests {

    // ✅ 1. Test listing all games
    @Test
    public void testGetAllGames() {
        Response response = GameService.getAllGames();

        assertEquals(response.getStatusCode(), 200, "Expected status code 200");

        // Validate response contains expected fields
        String responseBody = response.getBody().asString();
        assertTrue(responseBody.contains("id"), "Response should contain 'id' field");
        assertTrue(responseBody.contains("name"), "Response should contain 'name' field");
        assertTrue(responseBody.contains("category"), "Response should contain 'category' field");
        assertTrue(responseBody.contains("reviewScore"), "Response should contain 'reviewScore' field");
    }

    // ✅ 2. Test fetching a specific game by ID
    @Test
    public void testGetGameById() {
        int gameId = 1;  // Ensure this ID exists

        Response response = GameService.getGameById(gameId);

        assertEquals(response.getStatusCode(), 200, "Expected status code 200");
        assertTrue(response.getBody().asString().contains("\"id\":" + gameId), "Response should contain game ID " + gameId);
    }

    // ✅ 3. Test creating a new game
    @Test
    public void testCreateGame() {
        String gameName = "NewGame" + System.currentTimeMillis();
        String releaseDate = "2025-01-30";
        int reviewScore = 95;
        String category = "Adventure";
        String rating = "Everyone";

        Response response = GameService.createGame(gameName, releaseDate, reviewScore, category, rating);

        assertEquals(response.getStatusCode(), 200, "Expected status code 200");

        // Validate the response contains the correct game details
        String responseBody = response.getBody().asString();
        assertTrue(responseBody.contains(gameName), "Response should contain game name");
        assertTrue(responseBody.contains(category), "Response should contain game category");
    }

    // ✅ 4. Test updating an existing game
    @Test
    public void testUpdateGame() {
        int gameId = 1;  // Use an existing game ID from API

        String updatedName = "Updated Game " + System.currentTimeMillis();
        String updatedReleaseDate = "2025-02-01";
        int updatedReviewScore = 99;
        String updatedCategory = "Action";
        String updatedRating = "Teen";

        Response response = GameService.updateGame(gameId, updatedName, updatedReleaseDate, updatedReviewScore, updatedCategory, updatedRating);

        assertEquals(response.getStatusCode(), 200, "Expected status code 200");

        // Validate response contains updated data
        String responseBody = response.getBody().asString();
        assertTrue(responseBody.contains(updatedName), "Response should contain updated game name");
        assertTrue(responseBody.contains(updatedCategory), "Response should contain updated game category");
    }

    // ✅ 5. Test deleting a game
    @Test
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

        // ❗ Since the API is READ-ONLY, we expect 404 instead of 200
        assertEquals(deleteResponse.getStatusCode(), 404, "Expected status code 404 because API does not persist games");

        // 🔹 Step 4: Verify game is not found (should return 404)
        Response fetchDeletedResponse = GameService.getGameById(gameId);
        assertEquals(fetchDeletedResponse.getStatusCode(), 404, "Expected status code 404 because game was never actually saved");
    }

}
