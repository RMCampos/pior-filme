package awards.raspberry.golden.controller;

import awards.raspberry.golden.service.MovieService;
import awards.raspberry.golden.vo.MovieInterval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/movie", headers = "Accept=application/json")
public class MovieController {

    @Autowired
    MovieService movieService;

    @GetMapping(value = "/awards-interval", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MovieInterval> getAwardsInterval() {
        MovieInterval mi = movieService.getAwardsInterval();

        if (mi.empty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(mi, HttpStatus.OK);
    }
}
