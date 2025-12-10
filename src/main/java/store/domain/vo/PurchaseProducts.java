package store.domain.vo;

import static store.constant.ErrorMessage.EXCEED_ERROR;
import static store.constant.ErrorMessage.NO_EXIST_ERROR;

import java.util.HashMap;
import java.util.Map;
import store.domain.Products;

public class PurchaseProducts {

    private final Map<String, Integer> purchaseProducts;

    private PurchaseProducts() {
        this.purchaseProducts = new HashMap<>();
    }

    public static PurchaseProducts newInstance() {
        return new PurchaseProducts();
    }

    public void addProduct(Products products, String productName) {
        validateNoExist(products, productName);
        validateMax(products, productName);
        purchaseProducts.put(productName, purchaseProducts.getOrDefault(productName, 0) + 1);
    }

    private void validateMax(Products products, String productName) {
        if (products.getQuantityForProduct(productName) <= purchaseProducts.getOrDefault(productName, 0)) {
            throw new IllegalArgumentException(EXCEED_ERROR.getErrorMessage());
        }
    }

    private void validateNoExist(Products products, String productName) {
        if (!products.contains(productName)) {
            throw new IllegalArgumentException(NO_EXIST_ERROR.getErrorMessage());
        }
    }

    @Override
    public String toString() {
        return purchaseProducts.toString();
    }
}
