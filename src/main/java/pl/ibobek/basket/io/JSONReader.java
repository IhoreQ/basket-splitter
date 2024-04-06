package pl.ibobek.basket.io;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class JSONReader {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Map<String, List<String>> readConfigFromFile(String absolutePathToFile) throws IOException {
        String fileContent = readFileAsString(absolutePathToFile);
        return objectMapper.readValue(fileContent, new TypeReference<>(){});
    }

    private static String readFileAsString(String absolutePathToFile) throws IOException {
        Path path = Paths.get(absolutePathToFile);
        return Files.readString(path);
    }
}
