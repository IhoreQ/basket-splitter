package pl.ibobek.basket.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.ibobek.basket.helper.JSONTestReader;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class BasketSplitterTest {

    private final JSONTestReader jsonTestReader = new JSONTestReader();

    @ParameterizedTest
    @MethodSource("invalidConfigFiles")
    @DisplayName("Should throw IOException for files in an invalid format")
    void shouldThrowIOExceptionWhenParsing(String fileName) {
        String absolutePathToFile = getAbsolutePathFromResourceName(fileName);

        assertThrows(IOException.class,
                () -> new BasketSplitter(absolutePathToFile),
                "Parsing the file should throw an IOException for file: " + absolutePathToFile);
    }

    @ParameterizedTest
    @MethodSource("simpleBasketFiles")
    @DisplayName("Should return valid delivery groups")
    void splitUsingSimpleConfigTest(String configFileName, String basketFileName, String expectedResultFileName) throws IOException {
        String absolutePathToFile = getAbsolutePathFromResourceName(configFileName);
        BasketSplitter basketSplitter = new BasketSplitter(absolutePathToFile);

        List<String> products = jsonTestReader.readListFromFile(basketFileName);
        Map<String, List<String>> expectedDeliveryGroups = jsonTestReader.readMapFromFile(expectedResultFileName);
        Map<String, List<String>> result = basketSplitter.split(products);

        assertEquals(expectedDeliveryGroups, result,
                "Split result should match the expected delivery groups for basket file: " + absolutePathToFile);
    }

    private String getAbsolutePathFromResourceName(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resourceUrl = classLoader.getResource(fileName);

        if (resourceUrl == null) {
            throw new IllegalArgumentException("Resource file not found: " + fileName);
        }

        try {
            return Paths.get(resourceUrl.toURI()).toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert resource URL to URI", e);
        }
    }

    private static Stream<String> invalidConfigFiles() {
        return Stream.of(
                "invalid-config.json",
                "empty-config-file.json",
                "dummy-config-file.json"
        );
    }

    private static Stream<Arguments> simpleBasketFiles() {
        return Stream.of(
                arguments("simple-config.json", "simple-basket-1.json", "expected-simple-basket-1-result.json"),
                arguments("simple-config.json", "simple-basket-2.json", "expected-simple-basket-2-result.json"),
                arguments("simple-config.json", "simple-basket-3.json", "expected-simple-basket-3-result.json"),
                arguments("config.json", "basket-1.json", "expected-basket-1-result.json"),
                arguments("config.json", "basket-2.json", "expected-basket-2-result.json")
        );
    }
}
