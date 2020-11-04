package awards.raspberry.golden.service;

import awards.raspberry.golden.entity.MovieEntity;
import awards.raspberry.golden.repository.MovieRepository;
import awards.raspberry.golden.vo.AwardWinner;
import awards.raspberry.golden.vo.MovieInterval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class MovieService {

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    MovieRepository movieRepository;

    public MovieInterval getAwardsInterval() {
        final List<MovieEntity> movieEntityList = movieRepository.findAllByWinner("yes");

        if (movieEntityList.isEmpty()) {
            logger.info("No winner producer!");
            return new MovieInterval();
        }

        final Map<String, List<MovieEntity>> movieMap = new HashMap<>();
        for (MovieEntity movieEntity : movieEntityList) {
            if (!movieMap.containsKey(movieEntity.getProducers())) {
                movieMap.put(movieEntity.getProducers(), new ArrayList<>());
            }
            movieMap.get(movieEntity.getProducers()).add(movieEntity);
        }

        logger.info("Movies: " + movieEntityList.size());

        List<AwardWinner> awardWinners = new ArrayList<>();

        for (Map.Entry<String, List<MovieEntity>> entry : movieMap.entrySet()) {
            final String producer = entry.getKey();
            final List<MovieEntity> movieEntities = entry.getValue();
            final int winCount = movieEntities.size();

            // Ignore producers that didn't won at least twice
            if (winCount < 2) {
                continue;
            }

            // Sort by Year
            movieEntityList.sort(Comparator.comparing(MovieEntity::getYear));

            // Min
            MovieEntity min1 = movieEntities.get(0);
            MovieEntity min2 = movieEntities.get(1);
            AwardWinner awardWinnerMin = new AwardWinner();
            awardWinnerMin.setProducer(producer);
            awardWinnerMin.setInterval(min2.getYear() - min1.getYear());
            awardWinnerMin.setPreviousWin(min1.getYear());
            awardWinnerMin.setFollowingWin(min2.getYear());
            awardWinners.add(awardWinnerMin);

            // Max
            MovieEntity max1 = movieEntities.get(winCount-2);
            MovieEntity max2 = movieEntities.get(winCount-1);
            AwardWinner awardWinnerMax = new AwardWinner();
            awardWinnerMax.setProducer(producer);
            awardWinnerMax.setInterval(max2.getYear() - max1.getYear());
            awardWinnerMax.setPreviousWin(max1.getYear());
            awardWinnerMax.setFollowingWin(max2.getYear());
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
            mi.getMaxList().add(awardWinners.get(awardWinners.size() - 2));
        }

        // e o que obteve dois prêmios mais rápido
        if (awardWinners.size() > 0) {
            mi.getMinList().add(awardWinners.get(0));
        }
        if (awardWinners.size() > 1) {
            mi.getMinList().add(awardWinners.get(1));
        }

        return mi;
    }
}
