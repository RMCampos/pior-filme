package awards.raspberry.golden.service;

import awards.raspberry.golden.entity.MovieEntity;
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
import java.util.logging.Logger;

@Service
public class CsvService {

    private final Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    MovieService movieService;

    public List<MovieEntity> createMovieListFromCsv(List<String> lines) {
        // Remove first line (header)
        lines = lines.subList(1, lines.size()-1);

        List<MovieEntity> movies = new ArrayList<>();

        for (String line : lines) {
            String[] parts = line.split(";");
            MovieEntity movie = new MovieEntity();

            // Year
            try {
                movie.setYear(Integer.parseInt(parts[0].trim()));
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                e.printStackTrace();
            }

            // Title
            try {
                movie.setTitle(parts[1].trim());
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }

            // Studios
            try {
                movie.setStudios(parts[2].trim());
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }

            // Producers
            try {
                movie.setProducers(parts[3].trim());
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }

            // Winner
            if (parts.length > 4) {
                try {
                    String win = parts[4].trim();
                    movie.setWinner(win.toUpperCase().equals("YES"));
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }

            movies.add(movie);
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
