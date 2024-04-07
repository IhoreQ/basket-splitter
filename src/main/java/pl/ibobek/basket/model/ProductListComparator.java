package pl.ibobek.basket.model;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ProductListComparator implements Comparator<Map.Entry<String, List<Product>>> {

    @Override
    public int compare(Map.Entry<String, List<Product>> entry1, Map.Entry<String, List<Product>> entry2) {
        int countOfNonTakenProducts1 = (int) entry1.getValue().stream()
                .filter(product -> !product.isTaken())
                .count();

        int countOfNonTakenProducts2 = (int) entry2.getValue().stream()
                .filter(product -> !product.isTaken())
                .count();

        return Integer.compare(countOfNonTakenProducts2, countOfNonTakenProducts1);
    }
}
