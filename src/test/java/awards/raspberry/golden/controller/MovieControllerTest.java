package awards.raspberry.golden.controller;

import awards.raspberry.golden.service.MovieService;
import awards.raspberry.golden.vo.AwardWinner;
import awards.raspberry.golden.vo.MovieInterval;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest
@RunWith(SpringRunner.class)
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean(name = "movie")
    private MovieService movieService;

    @Test
    public void getAwardsIntervalSuccessTest() throws Exception {
        MovieInterval mi = new MovieInterval();
        mi.getMinList().add(new AwardWinner());
        mi.getMaxList().add(new AwardWinner());

        Mockito.when(movieService.getAwardsInterval())
                .thenReturn(mi);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/movie/awards-interval")
                .accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Mockito.verify(movieService, Mockito.times(1)).getAwardsInterval();
        Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    }
}
