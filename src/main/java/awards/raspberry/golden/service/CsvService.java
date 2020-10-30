package awards.raspberry.golden.service;

import awards.raspberry.golden.entity.MovieEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvService {

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    MovieService movieService;

    public List<MovieEntity> createMovieListFromCsv(List<String> lines) {
        // Remove first line (header)
        lines = lines.subList(1, lines.size());
        logger.info("Ignorind first line (heading)");

        List<MovieEntity> movies = new ArrayList<>();

        final String warnTemplate = "Unable to extract {} from line {}: {}";
        int lineNumber = 1;
        for (String line : lines) {
            if (line.indexOf(';') == -1) {
                logger.warn("Invalid Line: {}! Missing semicolon!", lineNumber);
                continue;
            }

            logger.info("Reading line {}", lineNumber);

            String[] parts = line.split(";");
            MovieEntity movie = new MovieEntity();

            // Year
            if (parts.length > 0) {
                try {
                    movie.setYear(Integer.parseInt(parts[0].trim()));
                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    logger.warn(warnTemplate, "Year", lineNumber, e.getMessage());
                }
            }

            // Title
            if (parts.length > 1) {
                try {
                    movie.setTitle(parts[1].trim());
                } catch (IndexOutOfBoundsException e) {
                    logger.warn(warnTemplate, "Title", lineNumber, e.getMessage());
                }
            }

            // Studios
            if (parts.length > 2) {
                try {
                    movie.setStudios(parts[2].trim());
                } catch (IndexOutOfBoundsException e) {
                    logger.warn(warnTemplate, "Studios", lineNumber, e.getMessage());
                }
            }

            // Producers
            if (parts.length > 3) {
                try {
                    movie.setProducers(parts[3].trim());
                } catch (IndexOutOfBoundsException e) {
                    logger.warn(warnTemplate, "Producers", lineNumber, e.getMessage());
                }
            }

            // Winner
            if (parts.length > 4) {
                try {
                    String win = parts[4].trim();
                    movie.setWinner(win.toUpperCase().equals("YES"));
                } catch (IndexOutOfBoundsException e) {
                    logger.warn(warnTemplate, "Winner", lineNumber, e.getMessage());
                }
            }

            movies.add(movie);
            lineNumber += 1;
        }

        return movies;
    }

    public void processMoviesFromResouce(Resource resource) {
        try {
            logger.info("CSV File URI: " + resource.getURI());

            File csvFile = new File(resource.getURI());
            List<String> lines = Files.readAllLines(csvFile.toPath());
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
        Resource resource = resourceLoader.getResource("classpath:" + "movielist.csv");
        processMoviesFromResouce(resource);
    }
}
