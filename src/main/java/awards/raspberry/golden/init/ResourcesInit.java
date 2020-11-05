package awards.raspberry.golden.init;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import awards.raspberry.golden.entity.MovieEntity;
import awards.raspberry.golden.repository.MovieRepository;
import awards.raspberry.golden.service.MovieService;
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

    @Autowired
    MovieService movieService;

    @Autowired
    MovieRepository movieRepository;
    
    @PostConstruct
    public void init() {
        try {
            ClassLoader cl = this.getClass().getClassLoader();
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
            Resource[] resources = resolver.getResources("classpath:/*.csv");
            
            logger.info("CSV files found: " + resources.length);

            final Map<String, List<String>> fileLinesMap = new HashMap<>();

            for (Resource resource : resources) {
                logger.info("Process file: {}", resource.getFilename());

                final List<String> csvLines = csvService.readFileFromResource(resource);
                if (!csvLines.isEmpty()) {
                    fileLinesMap.put(resource.getFilename(), csvLines);
                }
            }

            if (fileLinesMap.isEmpty()) {
                logger.info("No files to save!");
            }

            for (Map.Entry<String, List<String>> entry : fileLinesMap.entrySet()) {
                final String fileName = entry.getKey();
                final List<String> lines = entry.getValue();

                logger.info("FileName: {}, lines: {}", fileName, lines.size());

                List<MovieEntity> movies = movieService.createMovieListFromCsv(lines);
                movieRepository.saveAll(movies);

                logger.info("Saved: {}", movies.size());
            }
        } catch (IOException iex) {
            iex.printStackTrace();
        }
    }
}
