package awards.raspberry.golden;

import awards.raspberry.golden.entity.MovieEntity;
import awards.raspberry.golden.service.CsvService;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class CsvServiceTest {

    private CsvService csvService = new CsvService();

    @Test
    public void createMovieListFromCsvTest() {
        String[] lines = new String[] {
                "year;title;studios;producers;winner",
                "2020;Name;Studio here;Ricardo Campos;yes"
        };

        List<MovieEntity> movieEntities = csvService.createMovieListFromCsv(
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

        List<MovieEntity> movieEntities = csvService.createMovieListFromCsv(
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
}
