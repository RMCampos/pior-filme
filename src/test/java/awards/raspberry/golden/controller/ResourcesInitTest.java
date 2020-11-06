package awards.raspberry.golden.controller;

import awards.raspberry.golden.entity.MovieEntity;
import awards.raspberry.golden.service.CsvService;
import awards.raspberry.golden.service.MovieService;
import awards.raspberry.golden.vo.MovieInterval;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebMvcTest
@RunWith(SpringRunner.class)
public class ResourcesInitTest {

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    private MockMvc mockMvc;

    @MockBean(name = "movieService")
    private MovieService movieService;

    @Test
    public void getAwardsIntervalSuccessTest() throws Exception {
        List<String> linhasCsv = obterLinhasCsv();
        List<MovieEntity> movieEntities = obterMoviesFromCsv(linhasCsv);
        MovieInterval movieInterval = obterMovieInterval(movieEntities);

        Mockito.when(movieService.createMovieListFromCsv(linhasCsv))
                .thenReturn(movieEntities);

        Mockito.when(movieService.getAwardsInterval())
                .thenReturn(movieInterval);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/movie/awards-interval")
                .accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Mockito.verify(movieService, Mockito.times(1)).getAwardsInterval();
        Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());

        MovieInterval movieResponse = parseJson(mvcResult.getResponse().getContentAsString());
        Assert.assertFalse(movieResponse.empty());
        Assert.assertEquals(1, movieResponse.getMinList().size());
        Assert.assertEquals(1, movieResponse.getMinList().get(0).getInterval());
        Assert.assertEquals("Joel Silver", movieResponse.getMinList().get(0).getProducer());
        Assert.assertEquals(1, movieResponse.getMaxList().size());
        Assert.assertEquals(13, movieResponse.getMaxList().get(0).getInterval());
        Assert.assertEquals("Matthew Vaughn", movieResponse.getMaxList().get(0).getProducer());
    }

    private List<String> obterLinhasCsv() {
        try {
            ClassLoader cl = this.getClass().getClassLoader();
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
            Resource[] resources = resolver.getResources("classpath:/movielist.csv");

            logger.info("CSV files found: " + resources.length);

            if (resources.length > 0) {
                CsvService csvService = new CsvService();
                return csvService.readFileFromResource(resources[0]);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        logger.info("No CSV files found!");
        return new ArrayList<>();
    }

    private List<MovieEntity> obterMoviesFromCsv(List<String> lines) {
        MovieService movieService = new MovieService();
        return movieService.createMovieListFromCsv(lines);
    }

    private MovieInterval obterMovieInterval(List<MovieEntity> movieEntities) {
        // only winners
        movieEntities = movieEntities
                .stream()
                .filter(x -> x.getWinner().equals("yes"))
                .collect(Collectors.toList());

        return new MovieService().getAwardsInterval(movieEntities);
    }

    private MovieInterval parseJson(String json) {
        try {
            logger.info(json);
            MovieInterval mi = new ObjectMapper().readValue(json, MovieInterval.class);
            return mi;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return new MovieInterval();
    }
}
