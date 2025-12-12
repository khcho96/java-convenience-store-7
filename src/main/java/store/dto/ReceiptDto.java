package store.dto;

import java.util.Map;
import store.domain.Product;

public record ReceiptDto(Map<Product, Integer> purchaseProducts, Map<Product, Integer> presentProducts,
                         int membershipDiscountAmount) {

}
