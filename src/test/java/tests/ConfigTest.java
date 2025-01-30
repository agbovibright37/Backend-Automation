package tests;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

import config.ConfigManager;

public class ConfigTest {
    @Test
    public void testConfigValues() {
        assertEquals(ConfigManager.getProperty("base.url"), "https://www.videogamedb.uk");
        assertEquals(ConfigManager.getProperty("auth.endpoint"), "/api/authenticate");
        assertEquals(ConfigManager.getProperty("games.endpoint"), "/api/videogame");
    }
}
