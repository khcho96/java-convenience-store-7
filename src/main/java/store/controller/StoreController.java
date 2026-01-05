package store.controller;

import java.io.IOException;
import java.util.List;
import store.constant.Option;
import store.domain.Stock;
import store.service.StoreService;
import store.util.InputParser;
import store.util.Retry;
import store.util.file.FileReader;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    public void run() throws IOException {
        registerFileInfo();

        while (true) {
            Stock stock = storeService.getStock();
            OutputView.printStock(stock);

            if (getOption().equals(Option.NO)) {
                break;
            }
        }

    }

    private void registerFileInfo() throws IOException {
        FileReader fileReader = new FileReader("src/main/resources/promotions.md");
        List<String> readLines = fileReader.readLines();
        storeService.registerPromotions(readLines);

        fileReader = new FileReader("src/main/resources/products.md");
        readLines = fileReader.readLines();
        storeService.registerProducts(readLines);
    }

    public Option getOption() {
        return Retry.retryUntilSuccess(() ->
                InputParser.parseOption(InputView.readMenuOption())
        );
    }
}

