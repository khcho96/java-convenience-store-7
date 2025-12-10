package store.domain;

import java.util.Objects;

public class Product {

    private final String name;
    private final int price;
    private int promotionQuantity;
    private int normalQuantity;
    private final Promotion promotion;

    private Product(String name, int price, int promotionQuantity, int normalQuantity, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.promotionQuantity = promotionQuantity;
        this.normalQuantity = normalQuantity;
        this.promotion = promotion;
    }

    public static Product of(String name, int price, int quantity, Promotion promotion) {
        if (promotion == null) {
            return new Product(name, price, 0, quantity, promotion);
        }
        return new Product(name, price, quantity, 0, promotion);
    }

    public void setQuantity(int quantity, Promotion promotion) {
        if (promotion == null) {
            normalQuantity = quantity;
            return;
        }
        promotionQuantity = quantity;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Product product = (Product) object;
        return Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getPromotionQuantity() {
        return promotionQuantity;
    }

    public int getNormalQuantity() {
        return normalQuantity;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public String getNormalQuantityForOutput() {
        if (normalQuantity == 0) {
            return "재고 없음";
        }
        return normalQuantity + "개";
    }

    public String getPriceForOutput() {
        return String.format("%,d원", price);
    }

    public String getPromotionForOutput() {
        if (promotion == null) {
            return "";
        }
        return promotion.getName();
    }

    public String getPromotionQuantityForOutput() {
        if (promotionQuantity == 0) {
            return "재고 없음";
        }
        return promotionQuantity + "개";
    }
}
