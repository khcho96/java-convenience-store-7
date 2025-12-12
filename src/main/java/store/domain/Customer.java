package store.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Customer {

    private final Map<Product, List<Integer>> purchaseProducts;
    private final Map<Product, List<Integer>> presentProducts;

    private Customer() {
        purchaseProducts = new HashMap<>();
        presentProducts = new HashMap<>();
    }

    public static Customer newInstance() {
        return new Customer();
    }

    public void addProduct(Product product, int purchaseQuantity) {
        // 구매 상품
        int buy = product.getPromotion().getBuy();
        int get = product.getPromotion().getGet();
        int setSize = buy + get;
        int setQuantity = purchaseQuantity / setSize;

        int presentQuantity = get * setQuantity;
        purchaseProducts.put(product, List.of(purchaseQuantity, presentQuantity));

        product.updateStock(purchaseQuantity ,0);
    }

    public Map<Product, List<Integer>> getPresentProducts() {
        return presentProducts;
    }

    public Map<Product, List<Integer>> getPurchaseProducts() {
        return purchaseProducts;
    }
}
