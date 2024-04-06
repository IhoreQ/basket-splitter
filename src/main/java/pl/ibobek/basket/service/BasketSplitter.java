package pl.ibobek.basket.service;

import pl.ibobek.basket.io.JSONReader;
import pl.ibobek.basket.model.Product;
import pl.ibobek.basket.model.ProductListComparator;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class BasketSplitter {

    private final Map<String, List<String>> config;

    public BasketSplitter(String absolutePathToFile) throws IOException {
        this.config = JSONReader.readConfigFromFile(absolutePathToFile);
    }

    public Map<String, List<String>> split(List<String> items) {
        List<Product> products = items.stream()
                .map(Product::new)
                .toList();

        Map<String, List<Product>> groupedProducts = groupProductsByDeliveryOption(products);
        Map<String, List<Product>> deliveryGroups = splitProductsToDeliveryGroups(groupedProducts);

        return mapDeliveryProductsToStringLists(deliveryGroups);
    }

    private Map<String, List<Product>> groupProductsByDeliveryOption(List<Product> products) {
        Map<String, List<Product>> groupedProducts = new HashMap<>();

        products.forEach(product -> {
            List<String> deliveryOptions = config.getOrDefault(product.getName(), Collections.emptyList());
            deliveryOptions.forEach(option -> groupedProducts.computeIfAbsent(option, k -> new ArrayList<>()).add(product));
        });

        return groupedProducts;
    }

    private Map<String, List<Product>> splitProductsToDeliveryGroups(Map<String, List<Product>> groupedProducts) {
        Map<String, List<Product>> deliveryGroups = new HashMap<>();
        PriorityQueue<Map.Entry<String, List<Product>>> queue = new PriorityQueue<>(new ProductListComparator());

        for (Map.Entry<String, List<Product>> entry : groupedProducts.entrySet()) {
            queue.offer(entry);
        }

        while (!queue.isEmpty()) {
            var currentEntry = queue.poll();
            List<Product> nonTakenProducts = currentEntry.getValue()
                    .stream()
                    .filter(product -> !product.isTaken())
                    .toList();

            if (nonTakenProducts.isEmpty())
                break;

            deliveryGroups.put(currentEntry.getKey(), nonTakenProducts);
            nonTakenProducts.forEach(product -> product.setTaken(true));

            List<Map.Entry<String, List<Product>>> queueItems = new ArrayList<>();
            while (!queue.isEmpty()) {
                queueItems.add(queue.poll());
            }
            queueItems.forEach(queue::offer);
        }

        return deliveryGroups;
    }

    private Map<String, List<String>> mapDeliveryProductsToStringLists(Map<String, List<Product>> deliveryGroups) {
        return deliveryGroups.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(Product::toString)
                                .collect(Collectors.toList())
                ));
    }
}
