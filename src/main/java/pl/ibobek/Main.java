package pl.ibobek;

import pl.ibobek.basket.service.BasketSplitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        BasketSplitter basketSplitter;
        try {
            basketSplitter = new BasketSplitter("./src/test/resources/config.json");
        } catch (IOException e) {
            System.err.println("An error occurred during JSON parsing");
            return;
        }

        List<String> items = new ArrayList<>(List.of(
                "Fond - Chocolate",
                "Chocolate - Unsweetened",
                "Nut - Almond, Blanched, Whole",
                "Haggis",
                "Mushroom - Porcini Frozen",
                "Cake - Miini Cheesecake Cherry",
                "Sauce - Mint",
                "Longan",
                "Bag Clear 10 Lb",
                "Nantucket - Pomegranate Pear",
                "Puree - Strawberry",
                "Numi - Assorted Teas",
                "Apples - Spartan",
                "Garlic - Peeled",
                "Cabbage - Nappa",
                "Bagel - Whole White Sesame",
                "Tea - Apple Green Tea"));
        Map<String, List<String>> result = basketSplitter.split(items);
        System.out.println(result);
    }
}