package pl.ibobek.basket.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JSONTestReader {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public <T> T readFromFile(String fileName, TypeReference<T> valueTypeRef) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        return objectMapper.readValue(classLoader.getResource(fileName), valueTypeRef);
    }

    public List<String> readListFromFile(String fileName) throws IOException {
        TypeReference<List<String>> typeRef = new TypeReference<>() {};
        return readFromFile(fileName, typeRef);
    }

    public Map<String, List<String>> readMapFromFile(String fileName) throws IOException {
        TypeReference<Map<String, List<String>>> typeRef = new TypeReference<>() {};
        return readFromFile(fileName, typeRef);
    }
}
