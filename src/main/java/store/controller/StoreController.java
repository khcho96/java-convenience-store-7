package store.controller;

import java.io.IOException;
import java.util.List;
import store.service.StoreService;
import store.util.file.FileReader;

public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    public void run() throws IOException {
        registerFileInfo();


    }

    private void registerFileInfo() throws IOException {
        FileReader fileReader = new FileReader("src/main/resources/promotions.md");
        List<String> readLines = fileReader.readLines();
        storeService.registerPromotions(readLines);

        fileReader = new FileReader("src/main/resources/products.md");
        readLines = fileReader.readLines();
        storeService.registerProducts(readLines);
    }
}

