package store.domain;

import java.util.ArrayList;
import java.util.List;

public class Products {

    private final List<Product> products;

    public Products() {
        this.products = new ArrayList<>();
    }

    public static Products newInstance() {
        return new Products();
    }

    public void addProduct(String name, int price, int quantity, Promotion promotion) {
        for (Product product : products) {
            if (product.getName().equals(name)) {
                product.setQuantity(quantity, promotion);
                return;
            }
        }
        products.add(Product.of(name, price, quantity, promotion));
    }
}
