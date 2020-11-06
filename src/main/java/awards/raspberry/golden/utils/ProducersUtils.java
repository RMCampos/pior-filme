package awards.raspberry.golden.utils;

import java.util.ArrayList;
import java.util.List;

public class ProducersUtils {

    public static List<String> getProducersList(String producers) {
        final List<String> producersList = new ArrayList<>();
        if (producers == null || producers.isEmpty()) {
            return producersList;
        }

        producers = producers.replace(" and ", ", ");

        String[] split1 = producers.split(",");

        if (split1.length == 0) {
            producersList.add(producers);
            return producersList;
        }

        for (String producer : split1) {
            producersList.add(producer.trim());
        }

        return producersList;
    }
}
