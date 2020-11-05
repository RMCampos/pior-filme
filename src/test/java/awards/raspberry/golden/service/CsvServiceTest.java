package awards.raspberry.golden.service;

import awards.raspberry.golden.entity.MovieEntity;
import awards.raspberry.golden.repository.MovieRepository;
import awards.raspberry.golden.service.CsvService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
public class CsvServiceTest {

    @Autowired
    MovieService movieService;

    @MockBean
    MovieRepository movieRepository;

    @TestConfiguration
    static class CsvServiceTestConfiguration {
        @Bean
        public MovieService movieService() {
            return new MovieService();
        }
    }

    @Captor
    ArgumentCaptor<List<MovieEntity>> moviesListCaptor;

    @Before
    public void setup() {
        List<MovieEntity> movieEntityList = new ArrayList<>();
        movieEntityList.add(createMovieEntity(2020));

        Mockito.when(movieRepository.saveAll(moviesListCaptor.capture()))
                .thenReturn(movieEntityList);
    }

    @Test
    public void createMovieListFromCsvTest() {
        String[] lines = new String[] {
                "year;title;studios;producers;winner",
                "2020;Name;Studio here;Ricardo Campos;yes"
        };

        List<MovieEntity> movieEntities = movieService.createMovieListFromCsv(
                Arrays.asList(lines)
        );

        Assert.assertFalse("Must not be empty!", movieEntities.isEmpty());

        MovieEntity movieEntity = movieEntities.get(0);
        Assert.assertEquals("Year must be '2020'", "2020", movieEntity.getYear().toString());
        Assert.assertEquals("Title must be 'Name'", "Name", movieEntity.getTitle());
        Assert.assertEquals("Studios must be 'Studio here'", "Studio here", movieEntity.getStudios());
        Assert.assertEquals("Producers must be 'Ricardo Campos'", "Ricardo Campos", movieEntity.getProducers());
        Assert.assertEquals("Winner must be yes", "yes", movieEntity.getWinner());
    }

    @Test
    public void createUnorderedMovieListFromCsvTest() {
        String[] lines = new String[] {
                "title;year;studios;producers;winner",
                "Name;2020;Studio here;Ricardo Campos;yes"
        };

        List<MovieEntity> movieEntities = movieService.createMovieListFromCsv(
                Arrays.asList(lines)
        );

        Assert.assertFalse("Must not be empty!", movieEntities.isEmpty());

        MovieEntity movieEntity = movieEntities.get(0);
        Assert.assertEquals("Year must be '2020'", "2020", movieEntity.getYear().toString());
        Assert.assertEquals("Title must be 'Name'", "Name", movieEntity.getTitle());
        Assert.assertEquals("Studios must be 'Studio here'", "Studio here", movieEntity.getStudios());
        Assert.assertEquals("Producers must be 'Ricardo Campos'", "Ricardo Campos", movieEntity.getProducers());
        Assert.assertEquals("Winner must be yes", "yes", movieEntity.getWinner());
    }

    private MovieEntity createMovieEntity(Integer year) {
        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setYear(year);
        movieEntity.setTitle("Name");
        movieEntity.setStudios("Studio here");
        movieEntity.setProducers("Ricardo Campos");
        movieEntity.setWinner("yes");
        return movieEntity;
    }
}
