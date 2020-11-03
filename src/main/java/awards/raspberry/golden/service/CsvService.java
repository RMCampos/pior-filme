package awards.raspberry.golden.service;

import awards.raspberry.golden.entity.MovieEntity;
import awards.raspberry.golden.utils.CsvUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CsvService {

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    MovieService movieService;

    public List<MovieEntity> createMovieListFromCsv(List<String> lines) {
        final String header = lines.get(0);

        final char separator = CsvUtils.findSeparator(header);
        if (separator == ' ') {
            throw new RuntimeException("Unable to get columns separator from first line!");
        }

        final String[] headers = CsvUtils.findColumns(header, separator);

        // Remove first line (header)
        lines = lines.subList(1, lines.size());

        List<MovieEntity> movies = new ArrayList<>();

        int lineNumber = 1;
        for (String line : lines) {
            if (line.indexOf(separator) == -1) {
                logger.warn("Invalid Line: {}! Missing semicolon!", lineNumber);
                continue;
            }

            logger.info("Reading line {}", lineNumber);

            String[] parts = CsvUtils.findColumns(line, separator);
            MovieEntity movie = new MovieEntity();

            for (int i=0, len=parts.length; i<len; i++) {
                String columnName = headers[i];
                String value = parts[i];

                columnName = columnName.substring(0, 1).toUpperCase()
                        + columnName.substring(1).toLowerCase();

                try {
                    Method method = MovieEntity.class.getMethod("set" + columnName, String.class);
                    logger.info("Saving {} as String", columnName);
                    method.invoke(movie, value);
                    continue;
                } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException ivk) {
                    logger.warn("Unable to save: {} as a String", columnName);
                }

                try {
                    Method method = MovieEntity.class.getMethod("set" + columnName, Integer.class);
                    logger.info("Saving {} as Integer", columnName);
                    method.invoke(movie, Integer.parseInt(value));
                } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException ivk) {
                    logger.warn("Unable to save: {} as a Integer", columnName);
                }
            }

            movies.add(movie);
            lineNumber += 1;
        }

        return movies;
    }

    public void processMoviesFromResouce(Resource resource) {
        try {
            InputStream inputStream = resource.getInputStream();
            BufferedInputStream reader = new BufferedInputStream(inputStream);

            String content = new String(reader.readAllBytes(), StandardCharsets.UTF_8);

            String[] linesArray = content.split("\r\n|\n");
            List<String> lines = Arrays.asList(linesArray);
            logger.info("CSV Lines count: " + lines.size());

            List<MovieEntity> movies = createMovieListFromCsv(lines);
            logger.info("Movies count: " + movies.size());

            movieService.addMovies(movies);
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

    @PostConstruct
    public void init() {
        try {
            ClassLoader cl = this.getClass().getClassLoader();
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
            Resource[] resources = resolver.getResources("classpath:/*.csv");
            
            logger.info("CSV files found: " + resources.length);

            for (Resource resource : resources) {
                logger.info("Process file: {}", resource.getFilename());
                processMoviesFromResouce(resource);
            }
        } catch (IOException iex) {
            iex.printStackTrace();
        }
    }
}
