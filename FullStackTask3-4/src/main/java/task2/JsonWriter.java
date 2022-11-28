package task2;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * This class write Map to JSON
 */
public class JsonWriter {

    private final String path;

    public JsonWriter(String path) {
        this.path = path;
    }

    public void write(Map<String,Double> map){
        try (FileWriter fileWriter = new FileWriter(path)) {

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
            String json = objectMapper.writeValueAsString(map);

            fileWriter.write(json);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
