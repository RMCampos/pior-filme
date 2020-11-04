package awards.raspberry.golden.service;

import awards.raspberry.golden.entity.MovieEntity;
import awards.raspberry.golden.repository.MovieRepository;
import awards.raspberry.golden.vo.AwardWinner;
import awards.raspberry.golden.vo.MovieInterval;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
public class MovieServiceTest {

    @Autowired
    MovieService movieService;

    @MockBean
    MovieRepository movieRepository;

    @TestConfiguration
    static class MovieServiceTestConfiguration {
        @Bean
        public MovieService movieService() {
            return new MovieService();
        }
    }

    @Before
    public void setup() {
        List<MovieEntity> movieEntityList = new ArrayList<>();
        // Winner min 1
        movieEntityList.add(createMovieEntity(2008, "1"));
        movieEntityList.add(createMovieEntity(2009, "1"));
        // Winner min 2
        movieEntityList.add(createMovieEntity(2018, "2"));
        movieEntityList.add(createMovieEntity(2019, "2"));
        // Winner max 1
        movieEntityList.add(createMovieEntity(1900, "1"));
        movieEntityList.add(createMovieEntity(1999, "1"));
        // Winner max 2
        movieEntityList.add(createMovieEntity(2000, "2"));
        movieEntityList.add(createMovieEntity(2099, "2"));

        Mockito.when(movieRepository.findAllByWinner("yes"))
                .thenReturn(movieEntityList);
    }

    @Test
    public void getAwardsIntervalTest() {
        MovieInterval movieInterval = movieService.getAwardsInterval();

        Assert.assertFalse("Min List should not be empty!", movieInterval.getMinList().isEmpty());
        AwardWinner awardWinnerMin1 = movieInterval.getMinList().get(0);
        AwardWinner awardWinnerMin2 = movieInterval.getMinList().get(1);
        Assert.assertEquals("Min interval 1 should be 1", 1, awardWinnerMin1.getInterval());
        Assert.assertEquals("Min interval 2 should be 1", 1, awardWinnerMin2.getInterval());

        Assert.assertFalse("Max List should not be empty!", movieInterval.getMaxList().isEmpty());
        AwardWinner awardWinnerMax1 = movieInterval.getMaxList().get(0);
        AwardWinner awardWinnerMax2 = movieInterval.getMaxList().get(1);
        Assert.assertEquals("Max interval 1 should be 99", 99, awardWinnerMax1.getInterval());
        Assert.assertEquals("Max interval 2 should be 99", 99, awardWinnerMax2.getInterval());
    }

    private MovieEntity createMovieEntity(Integer year, String producerIdx) {
        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setYear(year);
        movieEntity.setTitle("Winner " + producerIdx);
        movieEntity.setStudios("Studio " + producerIdx);
        movieEntity.setProducers("Producer " + producerIdx);
        movieEntity.setWinner("yes");
        return movieEntity;
    }
}
