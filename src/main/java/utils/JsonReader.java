package utils; // Adjust the package as needed

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Map;

public class JsonReader {
    private Map<String, String> data;

    /**
     * Constructor that loads JSON data from a resource file.
     * @param resourceName The name of the JSON file in src/test/resources (e.g., "testdata.json")
     */
    public JsonReader(String resourceName) {
        Gson gson = new Gson();
        InputStream is = getClass().getClassLoader().getResourceAsStream(resourceName);
        if (is == null) {
            throw new RuntimeException("Resource not found: " + resourceName);
        }
        try (InputStreamReader reader = new InputStreamReader(is)) {
            Type type = new TypeToken<Map<String, String>>(){}.getType();
            data = gson.fromJson(reader, type);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to read JSON resource: " + resourceName);
        }
    }

    /**
     * Retrieves the value associated with a given key from the JSON data.
     * @param key The key to look up
     * @return The value associated with the key
     * @throws RuntimeException if the key is not found
     */
    public String getValue(String key) {
        if (!data.containsKey(key)) {
            throw new RuntimeException("Key not found in JSON data: " + key);
        }
        return data.get(key);
    }
}