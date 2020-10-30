package awards.raspberry.golden.controller;

import awards.raspberry.golden.service.MovieService;
import awards.raspberry.golden.vo.MovieInterval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    MovieService movieService;

    @GetMapping(value = "/awards-interval")
    public MovieInterval postCsvFile() {
        return movieService.getAwardsInterval();
    }
}
