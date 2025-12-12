package store;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import store.domain.Customer;
import store.domain.Product;
import store.domain.Products;
import store.domain.Promotion;
import store.domain.Promotions;
import store.domain.vo.PurchaseProducts;
import store.dto.StockDto;
import store.util.InputParser;
import store.util.file.FileReader;
import store.util.file.FileWriter;
import store.view.InputView;
import store.view.OutputView;

public class Application {

    static PurchaseProducts purchaseProducts;
    static Products products;
    static Promotions promotions;
    static Customer customer;

    public static void main(String[] args) throws IOException {
        FileReader promotionReader = new FileReader("src/main/resources/promotions.md");
        List<String> readPromotions = promotionReader.readLines();
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

        FileReader fr = new FileReader("src/main/resources/products.md");
        List<String> readProducts = fr.readLines();
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

        while (true) {
            try {
                StockDto stockDto = products.getStockDto();
                OutputView.printStock(stockDto);
                String rawPurchaseProducts = InputView.readPurchaseProducts();
                List<String> parsePurchaseProducts = InputParser.parsePurchaseProducts(rawPurchaseProducts);
                purchaseProducts = PurchaseProducts.newInstance();
                for (String productName : parsePurchaseProducts) {
                    purchaseProducts.addProduct(products, productName);
                }
                break;
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e);
            }
        }

        // 4

        while (true) {
            try {
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
                break;
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e);
            }
        }

        // 5
        while (true) {
            try {
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
                break;
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e);
            }
        }

        // 6
        List<Product> normalPromotionProducts = purchaseProducts.getNormalPromotionProducts(products);
        for (Product product : normalPromotionProducts) {
            int purchaseQuantity = purchaseProducts.getPurchaseQuantity(product);
            customer.addProductForPromotion(product, purchaseQuantity);
        }

        // 7
        List<Product> noPromotionProducts = purchaseProducts.getNoPromotionProducts(products);
        for (Product product : noPromotionProducts) {
            int purchaseQuantity = purchaseProducts.getPurchaseQuantity(product);
            customer.addProductForNormal(product, purchaseQuantity);
        }

        // 8
        while (true) {
            try {
                String rawChoice = InputView.readMembershipChoice();
                boolean choice = InputParser.parseChoice(rawChoice);

                if (choice) {
                    customer.setMembershipDiscountAmount();
                }
                break;
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e);
            }
        }

        FileWriter fw = new FileWriter("src/main/resources/temp products.md");
        fw.writeAll(products.getStockForFile());
    }
}
