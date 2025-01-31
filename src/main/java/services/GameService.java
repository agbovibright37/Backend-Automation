package services;

import org.json.JSONObject;

import config.ConfigManager;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

public class GameService {

    //  1. List all games with enhanced error handling
    public static Response getAllGames() {
        RestAssured.baseURI = ConfigManager.getProperty("base.url");

        System.out.println("Sending request to fetch all games...");

        Response response = null;
        try {
            response = given()
                    .header("Accept", "application/json")
                    .when()
                    .get(ConfigManager.getProperty("games.endpoint"))
                    .then()
                    .extract()
                    .response();

            System.out.println("Response received: Status Code -> " + response.getStatusCode());

            if (response.getStatusCode() != 200) {
                System.err.println("Unexpected response: " + response.getBody().asPrettyString());
            }

        } catch (Exception e) {
            System.err.println("Error fetching games: " + e.getMessage());
            throw new RuntimeException("Game fetch failed!", e);
        }

        return response;
    }

    // 2. Fetch a specific game by ID
    public static Response getGameById(int gameId) {
        RestAssured.baseURI = ConfigManager.getProperty("base.url");

        return given()
                .header("Accept", "application/json")
                .when()
                .get(ConfigManager.getProperty("games.endpoint") + "/" + gameId)
                .then()
                .extract()
                .response();
    }

    // 3. Create a new game (With Authentication Token)
    public static Response createGame(String name, String releaseDate, int reviewScore, String category, String rating) {
        RestAssured.baseURI = ConfigManager.getProperty("base.url");

        // Get Authentication Token from AuthService
        String authToken = AuthService.getAuthToken();

        // Create JSON body for game creation
        JSONObject requestBody = new JSONObject();
        requestBody.put("name", name);
        requestBody.put("releaseDate", releaseDate);
        requestBody.put("reviewScore", reviewScore);
        requestBody.put("category", category);
        requestBody.put("rating", rating);

        return given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .body(requestBody.toString())
                .when()
                .post(ConfigManager.getProperty("games.endpoint"))
                .then()
                .extract()
                .response();
    }

    // 4. Update an existing game by ID
    public static Response updateGame(int gameId, String name, String releaseDate, int reviewScore, String category, String rating) {
        RestAssured.baseURI = ConfigManager.getProperty("base.url");

        // Get Authentication Token
        String authToken = AuthService.getAuthToken();

        // Create JSON body for game update
        JSONObject requestBody = new JSONObject();
        requestBody.put("name", name);
        requestBody.put("releaseDate", releaseDate);
        requestBody.put("reviewScore", reviewScore);
        requestBody.put("category", category);
        requestBody.put("rating", rating);

        return given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .body(requestBody.toString())
                .when()
                .put(ConfigManager.getProperty("games.endpoint") + "/" + gameId)
                .then()
                .extract()
                .response();
    }

    // 5. Delete a game by ID
    public static Response deleteGameById(int gameId) {
        RestAssured.baseURI = ConfigManager.getProperty("base.url");

        // Get Authentication Token
        String authToken = AuthService.getAuthToken();

        return given()
                .header("Authorization", "Bearer " + authToken) // Ensure token is included
                .when()
                .delete(ConfigManager.getProperty("games.endpoint") + "/" + gameId)
                .then()
                .extract()
                .response();
    }

}
