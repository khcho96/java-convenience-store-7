package store.util;

import java.util.ArrayList;
import java.util.List;
import store.constant.Option;

public final class InputParser {

    private static final String FIRST_DELIMITER = ",";
    private static final String SECOND_DELIMITER = "-";

    private InputParser() {
    }

    public static Option parseOption(String rawInput) {
        return Option.from(rawInput.strip());
    }

    public static List<String> parseItems(String rawInput) {
        rawInput = rawInput.strip();
        Validator.validateCsvFormat(rawInput);

        return getItems(rawInput);
    }

    private static List<String> getItems(String rawInput) {
        List<String> list = new ArrayList<>();
        String[] split = rawInput.split(FIRST_DELIMITER);
        for (String s : split) {
            String strip = s.strip();
            String[] split1 = strip.substring(1, strip.length() - 1).split(SECOND_DELIMITER);

            for (int i = 0; i < NumberConvertor.convertToNumber(split1[1]); i++) {
                list.add(split1[0].strip());
            }
        }
        return list;
    }
}
