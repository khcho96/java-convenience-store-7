package store.domain.vo;

import static store.constant.ErrorMessage.EXCEED_ERROR;
import static store.constant.ErrorMessage.NO_EXIST_ERROR;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import store.domain.Product;
import store.domain.Products;

public class PurchaseProducts {

    private final Map<String, Integer> purchaseProducts;

    private PurchaseProducts() {
        this.purchaseProducts = new LinkedHashMap<>();
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

    public Map<String, Integer> getPurchaseProducts() {
        return purchaseProducts;
    }

    public List<Product> getLessPromotionProducts(Products products) {
        List<Product> lessProducts = new ArrayList<>();
        for (Product product : products.getProducts()) {
            // 이미 계산한 상품인지 확인
            if (product.isChecked()) {
                continue;
            }

            // 프로모션인지 또는 구매 상품인지 판단
            if (!product.isPromotion() || !purchaseProducts.containsKey(product.getName())) {
                continue;
            }

            // 주문 개수 < 프로모션 적용 개수인 상품은 주문 개수를 값으로 저장
            if (product.isLessPromotionProduct(purchaseProducts.get(product.getName()))) {
                lessProducts.add(product);
                product.setCheckedTrue();
            }
        }
        return lessProducts;
    }

    public List<Product> getMorePromotionProducts(Products products) {
        List<Product> moreProducts = new ArrayList<>();
        for (Product product : products.getProducts()) {
            // 이미 계산한 상품인지 확인
            if (product.isChecked()) {
                continue;
            }

            // 프로모션인지 또는 구매 상품인지 판단
            if (!product.isPromotion() || !purchaseProducts.containsKey(product.getName())) {
                continue;
            }

            if (product.isMorePromotionProduct(purchaseProducts.get(product.getName()))) {
                moreProducts.add(product);
                product.setCheckedTrue();
            }
        }
        return moreProducts;
    }

    public int getPurchaseQuantity(Product product) {
        return purchaseProducts.get(product.getName());
    }

    public List<Product> getNormalPromotionProducts(Products products) {
        List<Product> normalPromotionProducts = new ArrayList<>();
        for (Product product : products.getProducts()) {
            // 이미 계산한 상품인지 확인
            if (product.isChecked()) {
                continue;
            }

            // 프로모션인지 또는 구매 상품인지 판단
            if (!product.isPromotion() || !purchaseProducts.containsKey(product.getName())) {
                continue;
            }

            if (!product.isLessPromotionProduct(purchaseProducts.get(product.getName()))
                    && !product.isMorePromotionProduct(purchaseProducts.get(product.getName()))) {
                normalPromotionProducts.add(product);
                product.setCheckedTrue();
            }
        }
        return normalPromotionProducts;
    }

    public List<Product> getNoPromotionProducts(Products products) {
        List<Product> noPromotionProducts = new ArrayList<>();
        for (Product product : products.getProducts()) {
            // 이미 계산한 상품인지 확인
            if (product.isChecked()) {
                continue;
            }

            if (!product.isPromotion() && purchaseProducts.containsKey(product.getName())) {
                noPromotionProducts.add(product);
                product.setCheckedTrue();
            }
        }
        return noPromotionProducts;
    }
}
