package store.service;

import java.time.LocalDate;
import java.util.List;
import store.domain.Customer;
import store.domain.Product;
import store.domain.Products;
import store.domain.Promotion;
import store.domain.Promotions;
import store.domain.vo.PurchaseProducts;
import store.dto.ReceiptDto;
import store.dto.StockDto;
import store.util.InputParser;
import store.view.InputView;

public class StoreService {

    private PurchaseProducts purchaseProducts;
    private Products products;
    private Promotions promotions;
    private Customer customer;

    public void readPromotionFile(List<String> readPromotions) {
        readPromotions.removeFirst();
        promotions = Promotions.newInstance();
        for (String readPromotion : readPromotions) {
            String[] split = readPromotion.split(",");
            String name = split[0];
            int buy = Integer.parseInt(split[1]);
            int get = Integer.parseInt(split[2]);
            LocalDate startDate = LocalDate.parse(split[3]);
            LocalDate endDate = LocalDate.parse(split[4]);
            promotions.addPromotion(name, buy, get, startDate, endDate);
        }
    }

    public void readProductsFile(List<String> readProducts) {
        readProducts.removeFirst();
        products = Products.newInstance();
        for (String readProduct : readProducts) {
            String[] split = readProduct.split(",");
            String name = split[0];
            int price = Integer.parseInt(split[1]);
            int quantity = Integer.parseInt(split[2]);
            Promotion promotion = promotions.get(split[3]);
            products.addProduct(name, price, quantity, promotion);
        }
    }

    public StockDto getStockDto() {
        return products.getStockDto();
    }

    public void registerPurchaseProducts() {
        String rawPurchaseProducts = InputView.readPurchaseProducts();
        List<String> parsePurchaseProducts = InputParser.parsePurchaseProducts(rawPurchaseProducts);
        purchaseProducts = PurchaseProducts.newInstance();
        for (String productName : parsePurchaseProducts) {
            purchaseProducts.addProduct(products, productName);
        }
    }

    public void handleFreeProducts() {
        customer = Customer.newInstance();

        List<Product> lessPromotionProducts = purchaseProducts.getLessPromotionProducts(products);
        for (Product product : lessPromotionProducts) {
            int purchaseQuantity = purchaseProducts.getPurchaseQuantity(product);
            int free = product.getFreeProductQuantity();

            String rawChoice = InputView.readFreeProductChoice(product.getName(), free);
            boolean choice = InputParser.parseChoice(rawChoice);

            if (choice) {
                customer.addProductForPromotion(product, purchaseQuantity + free);
                continue;
            }
            customer.addProductForPromotion(product, purchaseQuantity);
        }
    }

    public void handleOverProducts() {
        List<Product> morePromotionProducts = purchaseProducts.getMorePromotionProducts(products);
        for (Product product : morePromotionProducts) {
            int purchaseQuantity = purchaseProducts.getPurchaseQuantity(product);
            int impossiblePromotionQuantity = product.getImpossiblePromotionQuantity(purchaseQuantity);

            String rawChoice = InputView.readImpossiblePromotionChoice(product.getName(),
                    impossiblePromotionQuantity);
            boolean choice = InputParser.parseChoice(rawChoice);

            if (choice) {
                customer.addProductForPromotion(product, purchaseQuantity);
                continue;
            }
            customer.addProductForPromotion(product, purchaseQuantity - impossiblePromotionQuantity);
        }
    }


    public void handleNormalProducts() {
        List<Product> normalPromotionProducts = purchaseProducts.getNormalPromotionProducts(products);
        for (Product product : normalPromotionProducts) {
            int purchaseQuantity = purchaseProducts.getPurchaseQuantity(product);
            customer.addProductForPromotion(product, purchaseQuantity);
        }
    }

    public void handleNoPromotionProducts() {
        List<Product> noPromotionProducts = purchaseProducts.getNoPromotionProducts(products);
        for (Product product : noPromotionProducts) {
            int purchaseQuantity = purchaseProducts.getPurchaseQuantity(product);
            customer.addProductForNormal(product, purchaseQuantity);
        }
    }

    public void setMembershipDiscountAmount() {
        customer.setMembershipDiscountAmount();
    }

    public String getStockResult() {
        return products.getStockForFile();
    }

    public ReceiptDto getReceiptResult() {
        return customer.getReceipt();
    }
}
