package store.util;

import java.util.HashMap;
import java.util.Map;
import store.constant.Option;

public final class InputParser {

    private static final String FIRST_DELIMITER = ",";
    private static final String SECOND_DELIMITER = "-";

    private InputParser() {
    }

    public static Option parseOption(String rawInput) {
        return Option.from(rawInput.strip());
    }

    public static Map<String, Integer> parseItems(String rawInput) {
        rawInput = rawInput.strip();
        Validator.validateCsvFormat(rawInput);

        return getItems(rawInput);
    }

    private static Map<String, Integer> getItems(String rawInput) {
        Map<String, Integer> items = new HashMap<>();
        String[] split = rawInput.split(FIRST_DELIMITER);
        for (String s : split) {
            String strip = s.strip();
            String[] split1 = strip.substring(1, strip.length() - 1).split(SECOND_DELIMITER);

            items.put(split1[0], NumberConvertor.convertToNumber(split1[1]));
        }
        return items;
    }
}
