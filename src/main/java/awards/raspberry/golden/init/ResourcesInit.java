package awards.raspberry.golden.init;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;

import awards.raspberry.golden.service.CsvService;

@Service
public class ResourcesInit {

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    CsvService csvService;
    
    @PostConstruct
    public void init() {
        try {
            ClassLoader cl = this.getClass().getClassLoader();
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
            Resource[] resources = resolver.getResources("classpath:/*.csv");
            
            logger.info("CSV files found: " + resources.length);

            for (Resource resource : resources) {
                logger.info("Process file: {}", resource.getFilename());
                csvService.processResouceFile(resource);
            }
        } catch (IOException iex) {
            iex.printStackTrace();
        }
    }
}
