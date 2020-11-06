package awards.raspberry.golden.service;

import awards.raspberry.golden.entity.MovieEntity;
import awards.raspberry.golden.repository.MovieRepository;
import awards.raspberry.golden.utils.CsvUtils;
import awards.raspberry.golden.utils.ProducersUtils;
import awards.raspberry.golden.vo.AwardWinner;
import awards.raspberry.golden.vo.MovieInterval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;


@Service
public class MovieService {

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    MovieRepository movieRepository;

    public MovieInterval getAwardsInterval() {
        return getAwardsInterval(null);
    }

    public MovieInterval getAwardsInterval(List<MovieEntity> movieEntityList) {
        if (movieEntityList == null) {
            movieEntityList = movieRepository.findAllByWinner("yes");
        }

        if (movieEntityList.isEmpty()) {
            logger.info("No winner producer!");
            return new MovieInterval();
        }

        logger.info("Movies: " + movieEntityList.size());

        final Map<String, List<Integer>> producersWinMap = new HashMap<>();
        for (MovieEntity movieEntity : movieEntityList) {

            List<String> producers = ProducersUtils.getProducersList(movieEntity.getProducers());
            for (String producer : producers) {
                if (!producersWinMap.containsKey(producer)) {
                    producersWinMap.put(producer, new ArrayList<>());
                }

                producersWinMap.get(producer).add(movieEntity.getYear());
            }
        }

        logger.info("Winners: " + producersWinMap.size());

        List<AwardWinner> awardWinners = new ArrayList<>();

        for (Map.Entry<String, List<Integer>> entry : producersWinMap.entrySet()) {
            final String producer = entry.getKey();
            final List<Integer> yearList = entry.getValue();
            final int winCount = yearList.size();

            // Ignore producers that didn't won at least twice
            if (winCount < 2) {
                continue;
            }

            // Sort by Year
            movieEntityList.sort(Comparator.comparing(MovieEntity::getYear));

            // Min
            Integer min1 = yearList.get(0);
            Integer min2 = yearList.get(1);
            AwardWinner awardWinnerMin = new AwardWinner();
            awardWinnerMin.setProducer(producer);
            awardWinnerMin.setInterval(min2 - min1);
            awardWinnerMin.setPreviousWin(min1);
            awardWinnerMin.setFollowingWin(min2);
            awardWinners.add(awardWinnerMin);

            // Max
            Integer max1 = yearList.get(winCount-2);
            Integer max2 = yearList.get(winCount-1);
            AwardWinner awardWinnerMax = new AwardWinner();
            awardWinnerMax.setProducer(producer);
            awardWinnerMax.setInterval(max2 - max1);
            awardWinnerMax.setPreviousWin(max1);
            awardWinnerMax.setFollowingWin(max2);
            awardWinners.add(awardWinnerMax);
        }

        if (awardWinners.isEmpty()) {
            logger.info("No award winners this time!");
            return new MovieInterval();
        }

        // Sort by interval
        awardWinners.sort(Comparator.comparing(AwardWinner::getInterval));

        MovieInterval mi = new MovieInterval();

        // Obter o  produtor com  maior  intervalo  entre  dois  prêmios
        if (awardWinners.size() > 0) {
            mi.getMaxList().add(awardWinners.get(awardWinners.size() - 1));
        }
        if (awardWinners.size() > 1) {
            // Avoid duplication
            AwardWinner win = awardWinners.get(awardWinners.size() - 2);
            if (!mi.getMaxList().contains(win)) {
                mi.getMaxList().add(win);
            }
        }

        // e o que obteve dois prêmios mais rápido
        if (awardWinners.size() > 0) {
            mi.getMinList().add(awardWinners.get(0));
        }
        if (awardWinners.size() > 1) {
            // Avoid duplication
            AwardWinner win = awardWinners.get(1);
            if (!mi.getMinList().contains(win)) {
                mi.getMinList().add(win);
            }
        }

        return mi;
    }

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
                    method.invoke(movie, value);
                } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException ivk) {
                    try {
                        Method method = MovieEntity.class.getMethod("set" + columnName, Integer.class);
                        method.invoke(movie, Integer.parseInt(value));
                    } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException noex) {
                        noex.printStackTrace();
                    }
                }
            }

            movies.add(movie);
            lineNumber += 1;
        }

        return movies;
    }
}
