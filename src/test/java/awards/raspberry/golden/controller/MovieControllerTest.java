package awards.raspberry.golden.controller;

import awards.raspberry.golden.service.MovieService;
import awards.raspberry.golden.vo.MovieInterval;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

//@RunWith(MockitoJUnitRunner.class)
public class MovieControllerTest {

    /*
    private MockMvc mockMvc;

    @InjectMocks
    private MovieController movieController;

    @Mock
    private MovieService movieService;

    @Mock
    private MovieInterval movieInterval;

    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();
    }

    @Test
    public void getAwardsIntervalSuccessTest() throws Exception {
        Mockito.when(movieService.getAwardsInterval())
                .thenReturn(movieInterval);

        String url = "/movie/awards-interval";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Mockito.verify(movieService, Mockito.times(1)).getAwardsInterval();
        Assert.assertEquals(HttpStatus.OK, mvcResult.getResponse().getStatus());
        Assert.assertSame(movieInterval, mvcResult.getResponse());
    }
    */

    // TODO: finish here!
}
