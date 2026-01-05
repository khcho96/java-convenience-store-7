package store.domain;

public class Item {

    private final String name;
    private final int price;
    private int proQuantity;
    private int quantity;
    private final Promotion promotion;

    private Item(String name, int price, int proQuantity, int quantity, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.proQuantity = proQuantity;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public static Item of(String name, int price, int quantity, Promotion promotion) {
        if (promotion != null) {
            return new Item(name, price, quantity, 0, promotion);
        }
        return new Item(name, price, 0, quantity, null);
    }

    public Object getName() {
        return null;
    }

    public void setNormalQuantity(int quantity) {
        this.quantity = quantity;
    }
}
