package store.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Stock {

    private final List<Item> items;

    public Stock() {
        this.items = new ArrayList<>();
    }

    public static Stock newInstance() {
        return new Stock();
    }

    public void addItem(String itemName, int price, int quantity, Promotion promotion) {
        Optional<Item> item = items.stream()
                .filter(it -> it.getName().equals(itemName))
                .findFirst();

        item.ifPresent(it -> it.setNormalQuantity(quantity));

        items.add(Item.of(itemName, price, quantity, promotion));
    }
}
