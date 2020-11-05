package awards.raspberry.golden.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CsvService {

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    public List<String> readFileFromResource(Resource resource) {
        try {
            final int bufferSize = 8 * 1024;

            BufferedInputStream reader = new BufferedInputStream(
                    resource.getInputStream(),
                    bufferSize
            );

            int i;
            StringBuilder sb = new StringBuilder();

            while ((i = reader.read()) != -1) {
                sb.append((char) i);
            }

            reader.close();

            String[] linesArray = sb.toString().split("\r\n|\n");
            List<String> lines = Arrays.asList(linesArray);
            logger.info("CSV Lines count: " + lines.size());

            return lines;
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }

        return new ArrayList<>();
    }
}
