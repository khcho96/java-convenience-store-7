package store.controller;

import java.io.IOException;
import java.util.List;
import store.dto.ReceiptDto;
import store.dto.StockDto;
import store.service.StoreService;
import store.util.InputParser;
import store.util.file.FileReader;
import store.util.file.FileWriter;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    public void run() throws IOException {
        while (true) {
            readFiles();

            StockDto stockDto = storeService.getStockDto();
            OutputView.printStock(stockDto);

            registerPurchaseProducts();

            handleFreeProducts();
            handleOverProducts();
            handleNormalProducts();
            handleNoPromotionProducts();
            setMembershipDiscountAmount();

            writeStockFile();
            printResult();

            String rawChoice = InputView.readMorePurchaseChoice();
            boolean choice = InputParser.parseChoice(rawChoice);
            if (!choice) {
                break;
            }
        }
    }

    private void readFiles() throws IOException {
        FileReader promotionReader = new FileReader("src/main/resources/promotions.md");
        List<String> readPromotions = promotionReader.readLines();
        storeService.readPromotionFile(readPromotions);

        FileReader fr = new FileReader("src/main/resources/products.md");
        List<String> readProducts = fr.readLines();
        storeService.readProductsFile(readProducts);
    }

    private void registerPurchaseProducts() {
        while (true) {
            try {
                storeService.registerPurchaseProducts();
                return;
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e);
            }
        }
    }

    private void handleFreeProducts() {
        while (true) {
            try {
                storeService.handleFreeProducts();
                return;
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e);
            }
        }
    }

    private void handleOverProducts() {
        while (true) {
            try {
                storeService.handleOverProducts();
                return;
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e);
            }
        }
    }

    private void handleNormalProducts() {
        while (true) {
            try {
                storeService.handleNormalProducts();
                return;
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e);
            }
        }
    }

    private void handleNoPromotionProducts() {
        while (true) {
            try {
                storeService.handleNoPromotionProducts();
                return;
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e);
            }
        }
    }

    private void setMembershipDiscountAmount() {
        while (true) {
            try {
                String rawChoice = InputView.readMembershipChoice();
                boolean choice = InputParser.parseChoice(rawChoice);

                if (choice) {
                    storeService.setMembershipDiscountAmount();
                }
                break;
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e);
            }
        }
    }

    private void writeStockFile() throws IOException {
        FileWriter fw = new FileWriter("src/main/resources/products.md");

        String stockResult = storeService.getStockResult();
        fw.writeAll(stockResult);
    }

    private void printResult() {
        ReceiptDto receiptDto = storeService.getReceiptResult();
        OutputView.printReceipt(receiptDto);
    }
}