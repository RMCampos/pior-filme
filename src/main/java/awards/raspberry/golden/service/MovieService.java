package awards.raspberry.golden.service;

import awards.raspberry.golden.entity.MovieEntity;
import awards.raspberry.golden.repository.IMovieRepository;
import awards.raspberry.golden.vo.MovieInterval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MovieService {

    @Autowired
    IMovieRepository movieRepository;

    public void addMovies(List<MovieEntity> movies) {
        movieRepository.saveAll(movies);
    }

    public MovieInterval getAwardsInterval() {
        return new MovieInterval();
    }
}
