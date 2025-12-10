package store;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import store.domain.Promotions;
import store.util.file.FileReader;

public class Application {

    public static void main(String[] args) throws IOException {
        FileReader promotionReader = new FileReader("src/main/resources/promotions.md");
        List<String> readPromotions = promotionReader.readLines();
        Promotions promotions = Promotions.newInstance();
        readPromotions.removeFirst();
        for (String readPromotion : readPromotions) {
            String[] split = readPromotion.split(",");
            String name = split[0];
            int buy = Integer.parseInt(split[1]);
            int get = Integer.parseInt(split[2]);
            LocalDate startDate = LocalDate.parse(split[3]);
            LocalDate endDate = LocalDate.parse(split[3]);
            promotions.addPromotion(name, buy, get, startDate, endDate);
        }



//
//        OutputView.printStock();
//        InputView.readPurchaseProducts();
    }
}
