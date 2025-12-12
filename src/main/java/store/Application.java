package store;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import store.domain.Customer;
import store.domain.Product;
import store.domain.Products;
import store.domain.Promotion;
import store.domain.Promotions;
import store.domain.vo.PurchaseProducts;
import store.dto.StockDto;
import store.util.InputParser;
import store.util.file.FileReader;
import store.view.InputView;
import store.view.OutputView;

public class Application {

    public static void main(String[] args) throws IOException {
        FileReader promotionReader = new FileReader("src/main/resources/promotions.md");
        List<String> readPromotions = promotionReader.readLines();
        readPromotions.removeFirst();
        Promotions promotions = Promotions.newInstance();
        for (String readPromotion : readPromotions) {
            String[] split = readPromotion.split(",");
            String name = split[0];
            int buy = Integer.parseInt(split[1]);
            int get = Integer.parseInt(split[2]);
            LocalDate startDate = LocalDate.parse(split[3]);
            LocalDate endDate = LocalDate.parse(split[4]);
            promotions.addPromotion(name, buy, get, startDate, endDate);
        }

        FileReader fr = new FileReader("src/main/resources/products.md");
        List<String> readProducts = fr.readLines();
        readProducts.removeFirst();
        Products products = Products.newInstance();
        for (String readProduct : readProducts) {
            String[] split = readProduct.split(",");
            String name = split[0];
            int price = Integer.parseInt(split[1]);
            int quantity = Integer.parseInt(split[2]);
            Promotion promotion = promotions.get(split[3]);
            products.addProduct(name, price, quantity, promotion);
        }

        StockDto stockDto = products.getStockDto();
        OutputView.printStock(stockDto);

        String rawPurchaseProducts = InputView.readPurchaseProducts();
        List<String> parsePurchaseProducts = InputParser.parsePurchaseProducts(rawPurchaseProducts);
        PurchaseProducts purchaseProducts = PurchaseProducts.newInstance();
        for (String productName : parsePurchaseProducts) {
            purchaseProducts.addProduct(products, productName);
        }

        // 4
        Customer customer = Customer.newInstance();

        List<Product> lessPromotionProducts = purchaseProducts.getLessPromotionProducts(products);
        for (Product product : lessPromotionProducts) {
            int purchaseQuantity = purchaseProducts.getPurchaseQuantity(product);
            int free = product.getFreeProductQuantity();

            String rawChoice = InputView.readFreeProductChoice(product.getName(), free);
            boolean choice = InputParser.parseChoice(rawChoice);

            if (choice) {
                customer.addProduct(product, purchaseQuantity + free);
                continue;
            }
            customer.addProduct(product, purchaseQuantity);
        }

        // 5
        List<Product> morePromotionProducts = purchaseProducts.getMorePromotionProducts(products);
        for (Product product : morePromotionProducts) {
            int purchaseQuantity = purchaseProducts.getPurchaseQuantity(product);
            int impossiblePromotionQuantity = product.getImpossiblePromotionQuantity(purchaseQuantity);

            String rawChoice = InputView.readImpossiblePromotionChoice(product.getName(), impossiblePromotionQuantity);
            boolean choice = InputParser.parseChoice(rawChoice);

            if (choice) {
                customer.addProduct(product, purchaseQuantity);
                continue;
            }
            customer.addProduct(product, purchaseQuantity - impossiblePromotionQuantity);
        }

        // 6
        List<Product> normalPromotionProducts = purchaseProducts.getNormalPromotionProducts(products);
        for (Product product : normalPromotionProducts) {
            int purchaseQuantity = purchaseProducts.getPurchaseQuantity(product);
            customer.addProduct(product, purchaseQuantity);
        }

        // 7
//        purchaseProducts.getNoPromotionProducts(products);
//
//        OutputView.printStock(products.getStockDto());
//        System.out.println(customer.getPurchaseProducts());
//        System.out.println(customer.getPresentProducts());

        // 8
    }
}
