package store.domain;

import java.time.LocalDate;
import java.util.Objects;
import store.time.DateTime;

public class Product {

    private final String name;
    private final int price;
    private int promotionQuantity;
    private int normalQuantity;
    private final Promotion promotion;
    private boolean isChecked;

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

    public int getPrice(int quantity) {
        return price * quantity;
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

    public boolean isPromotion() {
        LocalDate now = DateTime.now();
        return promotion != null
                && !now.isBefore(promotion.getStartDate())
                && !now.isAfter(promotion.getEndDate());
    }

    public boolean isLessPromotionProduct(int purchaseQuantity) {
        int buy = promotion.getBuy();
        int get = promotion.getGet();
        int mod = purchaseQuantity % (buy + get);

        if (purchaseQuantity >= promotionQuantity) {
            return false;
        }

        int maxPurchaseQuantity = purchaseQuantity + (buy + get - mod);

        return buy <= mod && maxPurchaseQuantity <= promotionQuantity;
    }

    public boolean isMorePromotionProduct(int purchaseQuantity) {
        int buy = promotion.getBuy();
        int get = promotion.getGet();
        int setSize = buy + get;
        int mod = purchaseQuantity % setSize;
        int minPromotionQuantity = purchaseQuantity - mod;

        return minPromotionQuantity > promotionQuantity;
    }

    public int getFreeProductQuantity() {
        return promotion.getGet();
    }

    public void updateStock(int quantity) {
        if (this.promotionQuantity >= quantity) {
            this.promotionQuantity -= quantity;
            return;
        }

        quantity -= this.promotionQuantity;
        this.promotionQuantity = 0;

        this.normalQuantity -= quantity;
    }

    public int getImpossiblePromotionQuantity(int purchaseQuantity) {
        int buy = promotion.getBuy();
        int get = promotion.getGet();
        int setSize = buy + get;
        int mod = promotionQuantity % setSize;
        int maxPromotionQuantity = promotionQuantity - mod;

        return purchaseQuantity - maxPromotionQuantity;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setCheckedTrue() {
        isChecked = true;
    }
}
