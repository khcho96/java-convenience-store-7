package store;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import store.domain.Products;
import store.domain.Promotion;
import store.domain.Promotions;
import store.dto.StockDto;
import store.util.file.FileReader;
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
            LocalDate endDate = LocalDate.parse(split[3]);
            promotions.addPromotion(name, buy, get, startDate, endDate);
        }

        FileReader fr = new FileReader("src/main/resources/products.md");
        List<String> readProducts = fr.readLines();
        String initStock = readProducts.removeFirst() + "\n";
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

//        InputView.readPurchaseProducts();
    }
}
