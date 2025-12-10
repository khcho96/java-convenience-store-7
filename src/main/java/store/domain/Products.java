package store.domain;

import java.util.ArrayList;
import java.util.List;
import store.dto.StockDto;

public class Products {

    private final List<Product> products;

    public Products() {
        this.products = new ArrayList<>();
    }

    public static Products newInstance() {
        return new Products();
    }

    public String getStock(String stock) {
        StringBuilder st = new StringBuilder(stock);
        for (Product product : products) {
            if (product.getPromotion() == null) {
                st.append(product.getName() + "," + product.getPrice() + "," + product.getNormalQuantity() + ","
                        + "null" + "\n");
                continue;
            }
            st.append(product.getName() + "," + product.getPrice() + "," + product.getPromotionQuantity() + ","
                    + product.getPromotion().getName() + "\n");
            st.append(product.getName() + "," + product.getPrice() + "," + product.getNormalQuantity() + "," + "null"
                    + "\n");
        }
        return st.toString();
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

    public StockDto getStockDto() {
        StringBuilder st = new StringBuilder();
        for (Product product : products) {
            if (product.getPromotion() == null) {
                st.append("- " + product.getName() + " " + product.getPriceForOutput() + " " + product.getNormalQuantityForOutput() + " "
                        + "\n");
                continue;
            }
            st.append("- " + product.getName() + " " + product.getPriceForOutput() + " " + product.getPromotionQuantityForOutput() + " "
                    + product.getPromotionForOutput() + "\n");
            st.append("- " + product.getName() + " " + product.getPriceForOutput() + " " + product.getNormalQuantityForOutput() + " "
                    + "\n");
        }
        return new StockDto(st.toString());
    }
}
