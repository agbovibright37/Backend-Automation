package tests;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger logger = LogManager.getLogger(GameTests.class);

    // 1. Test listing all games
    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test to fetch all video games and verify response structure")
    @Story("Get All Games")
    public void testGetAllGames() {
        logger.info("[Test Case 1.1] Fetching all games...");

        Response response = GameService.getAllGames();
        Allure.addAttachment("Response", response.getBody().asPrettyString());

        assertEquals(response.getStatusCode(), 200, "Expected status code 200");
        logger.info("[Test Case 1.2] Status code 200: PASSED");

        List<Integer> gameIds = response.jsonPath().getList("id");
        assertFalse(gameIds.isEmpty(), "Expected at least one game in the list");
        logger.info("[Test Case 1.3] At least one game exists in list: PASSED");

        for (Integer id : gameIds) {
            assertTrue(id > 0, "Found a game with invalid ID: " + id);
            logger.info("[Test Case 1.4] Valid Game ID: {} PASSED", id);
        }

        logger.info("[Test Case 1] Get all games: COMPLETED SUCCESSFULLY!");
    }

    // 2. Test fetching a specific game by ID
    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Test to fetch a specific game by ID and validate its response")
    @Story("Get Game By ID")
    public void testGetGameById() {
        int gameId = 1; 

        logger.info("[Test Case 2.1] Fetching game with ID: {}", gameId);
        Response response = GameService.getGameById(gameId);

        Allure.addAttachment("Requested Game ID", String.valueOf(gameId));
        Allure.addAttachment("Response", response.getBody().asPrettyString());

        assertEquals(response.getStatusCode(), 200, "[Test Case 2.2] Expected status code 200");
        logger.info("[Test Case 2.2] Status code is 200");

        assertTrue(response.getBody().asString().contains("\"id\":" + gameId), "[Test Case 2.3] Response should contain game ID: " + gameId);
        logger.info("[Test Case 2.3] Response contains correct Game ID");

        logger.info("[Test Case 2] Game fetch test PASSED!");
    }

    // 3. Test creating a new game
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

        logger.info("[Test Case 3.1] Creating a new game: {}", gameName);

        Response response = GameService.createGame(gameName, releaseDate, reviewScore, category, rating);

        Allure.addAttachment("Created Game Details", gameName + " - " + category);
        Allure.addAttachment("Response", response.getBody().asPrettyString());

        assertEquals(response.getStatusCode(), 200, "[Test Case 3.2] Expected status code 200");
        logger.info("[Test Case 3.2] Status code is 200");

        logger.info("[Test Case 3] Game creation test PASSED!");
    }

    // ✅ 4. Test updating an existing game
    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Test updating an existing game with new details")
    @Story("Update Game")
    public void testUpdateGame() {
        int gameId = 1;

        String updatedName = "Updated Game " + System.currentTimeMillis();
        String updatedReleaseDate = "2025-02-01";
        int updatedReviewScore = 99;
        String updatedCategory = "Action";
        String updatedRating = "Teen";

        logger.info("[Test Case 4.1] Attempting to update Game ID: {}", gameId);

        Response response = GameService.updateGame(gameId, updatedName, updatedReleaseDate, updatedReviewScore, updatedCategory, updatedRating);

        Allure.addAttachment("Updated Game ID", String.valueOf(gameId));
        Allure.addAttachment("Response", response.getBody().asPrettyString());

        assertEquals(response.getStatusCode(), 200, "[Test Case 4.2] Expected status code 200");
        logger.info("[Test Case 4.2] Status code is 200");

        logger.info("[Test Case 4] Game update test PASSED! (Read-Only Mode: Changes won’t persist)");
    }

    // ✅ 5. Test deleting a game 
    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Description("Test deleting a game from the system (should return 404 because API is read-only)")
    @Story("Delete Game")
    public void testDeleteGame() {
        String gameName = "TestGame" + System.currentTimeMillis();
        String releaseDate = "2025-01-30";
        int reviewScore = 95;
        String category = "Action";
        String rating = "Mature";

        logger.info("[Test Case 5.1] Creating a game for deletion test: {}", gameName);

        Response createResponse = GameService.createGame(gameName, releaseDate, reviewScore, category, rating);
        assertEquals(createResponse.getStatusCode(), 200, "[Test Case 5.2] Expected status code 200 when creating a game");

        int gameId = createResponse.jsonPath().getInt("id");
        logger.info("[Test Case 5.3] Extracted Game ID for deletion: {}", gameId);

        Response deleteResponse = GameService.deleteGameById(gameId);
        Allure.addAttachment("Attempted Deletion ID", String.valueOf(gameId));
        Allure.addAttachment("Response", deleteResponse.getBody().asPrettyString());

        assertEquals(deleteResponse.getStatusCode(), 404, "[Test Case 5.4] Expected status code 404 because API does not persist games");
        logger.info("[Test Case 5.4] Delete request acknowledged, but game is not actually removed (Read-Only Mode)");

        Response fetchDeletedResponse = GameService.getGameById(gameId);
        assertEquals(fetchDeletedResponse.getStatusCode(), 404, "[Test Case 5.5] Expected status code 404 because game was never actually saved");
        logger.info("[Test Case 5.5] Verified game is not found after deletion attempt");

        logger.info("[Test Case 5] Delete Game test PASSED! (Read-Only Mode)");
    }
}
